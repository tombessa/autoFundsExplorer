package com.github.tombessa.autofundsexplorer.service.impl;

import com.github.tombessa.autofundsexplorer.model.dto.FIIDTO;
import com.github.tombessa.autofundsexplorer.model.dto.PatrimonialDTO;
import com.github.tombessa.autofundsexplorer.model.dto.PropriedadeDTO;
import com.github.tombessa.autofundsexplorer.service.AutoFundsExplorerService;
import com.github.tombessa.autofundsexplorer.util.Constants;
import com.github.tombessa.autofundsexplorer.util.HttpUtil;
import com.github.tombessa.autofundsexplorer.util.exception.BusinessException;
import org.apache.commons.collections.list.SetUniqueList;
import org.springframework.stereotype.Service;
import com.google.gson.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AutoFundsExplorerServiceImpl implements AutoFundsExplorerService {

    private final HttpUtil httpUtil;

    public AutoFundsExplorerServiceImpl(HttpUtil httpUtil) {
        this.httpUtil = httpUtil;
    }

    //https://www.fundsexplorer.com.br/wp-json/funds/v1/get-ranking

    private String removeCaracteresEspeciais(String strTexto){
        String texto = strTexto;

        texto = texto.replaceAll("/[ÀÁÂÃÄÅ]/g","A");
        texto = texto.replaceAll("/[àáâãäå]/g","a");

        texto = texto.replaceAll("/[ÈÉÊË]/g","E");
        texto = texto.replaceAll("/[èéêë]/g","e");

        texto = texto.replaceAll("\\/[ÌÍÎÏ]/g","I");
        texto = texto.replaceAll("\\/[ìíîï]/g","i");

        texto = texto.replaceAll("/[ÒÓÔÕÖ]/g","O");
        texto = texto.replaceAll("/[òóôõö]/g","o");

        texto = texto.replaceAll("/[ÙÚÛÜ]/g","U");
        texto = texto.replaceAll("/[ùúûü]/g","u");

        texto = texto.replaceAll("/[Ç]/g","C");
        texto = texto.replaceAll("/[c]/g","c");

        texto = texto.replaceAll("/[Ñ]/g","N");
        texto = texto.replaceAll("/[ñ]/g","n");

        texto = texto.replaceAll("/[Ý]/g","Y");
        texto = texto.replaceAll("/[ÿý]/g","y");

        return texto;
    }

    @Override
    public List<FIIDTO> ranking(List<FIIDTO> ret) {
        try {
            String json =  HttpUtil.get("https://www.fundsexplorer.com.br/wp-json/funds/v1/get-ranking");
            JsonParser jsonParser = new JsonParser();
            json = json.replace("\"[","[").replace("]\"", "]").replace("\\\"", "\"");
            JsonArray jsonArray = jsonParser.parse(json).getAsJsonArray();

            Gson gson = new Gson();
            jsonArray.forEach(jsonElement -> {
                ret.add(gson.fromJson(jsonElement, FIIDTO.class));
            });
            ret.forEach(fiidto -> {
                List<PropriedadeDTO> propriedadeDTOS = new ArrayList<>();
                propriedadeDTOS = this.propriedades(fiidto.getTicker(), propriedadeDTOS);
                fiidto.setDadosPatrimonio(this.getDadosPropriedade(propriedadeDTOS));
            });
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(Constants.ERRO_HTTP);
        }
    }

    @Override
    public List<FIIDTO> rankingHistorico(List<FIIDTO> ret, LocalDate oInicio, LocalDate oFim) {
        ret = this.ranking(ret);
        ret.forEach(fiidto -> {
            List<PatrimonialDTO> patrimonialDTOS = new ArrayList<>();
            fiidto.setVariacao(this.getVariacaoPatrimonio(
                    patrimonials(fiidto.getTicker(), patrimonialDTOS),
                    oInicio,
                    oFim
            ));
            List<PropriedadeDTO> propriedadeDTOS = new ArrayList<>();
            propriedadeDTOS = this.propriedades(fiidto.getTicker(), propriedadeDTOS);
            fiidto.setDadosPatrimonio(this.getDadosPropriedade(propriedadeDTOS));
        });
        return ret;
    }

    private Map<String, Integer> getDadosPropriedade(List<PropriedadeDTO> item){
        Map<String, Integer> retorno = new HashMap<>();

        SetUniqueList inquilino = SetUniqueList.decorate(item.stream().map(PropriedadeDTO::getNome).collect(Collectors.toList()));
        SetUniqueList endereco = SetUniqueList.decorate(item.stream().map(PropriedadeDTO::getEndereco).collect(Collectors.toList()));
        SetUniqueList bairro = SetUniqueList.decorate(item.stream().map(PropriedadeDTO::getBairro).collect(Collectors.toList()));
        SetUniqueList cidade = SetUniqueList.decorate(item.stream().map(PropriedadeDTO::getCidade).collect(Collectors.toList()));
        SetUniqueList estado = SetUniqueList.decorate(item.stream().map(PropriedadeDTO::getEstado).collect(Collectors.toList()));
        retorno.put(Constants.INQUILINO, inquilino.size());
        retorno.put(Constants.ENDERECO, endereco.size());
        retorno.put(Constants.BAIRRO, bairro.size());
        retorno.put(Constants.CIDADE, cidade.size());
        retorno.put(Constants.ESTADO, estado.size());
        return retorno;
    }

    private Map<String, Double> getVariacaoPatrimonio(List<PatrimonialDTO> item,
                                                      LocalDate inicio,
                                                      LocalDate fim){
        Map<String, Double> retorno = new HashMap<>();
        retorno.put(Constants.MINIMO, 0D);
        retorno.put(Constants.MAXIMO, 0D);
        item.forEach(patrimonialDTO -> patrimonialDTO.getPeriodo());
        List<PatrimonialDTO> filtered = item.stream()
                .filter(patrimonialDTO -> patrimonialDTO.getOPeriodo().isAfter(inicio) && patrimonialDTO.getOPeriodo().isBefore(fim))
                .collect(Collectors.toList());

        for(int index = 0; index < filtered.size(); index++){
            Double variacao = 0D;
            if(!(index == filtered.size()-1)){
                variacao = (filtered.get(index+1).getValor() - filtered.get(index).getValor())/filtered.get(index).getValor();
                Double atual;
                if(variacao<0){
                    atual = retorno.get(Constants.MINIMO);
                    if(atual > variacao) retorno.replace(Constants.MINIMO, atual, variacao);
                }else{
                    atual = retorno.get(Constants.MAXIMO);
                    if(atual < variacao) retorno.replace(Constants.MAXIMO, atual, variacao);
                }

            }
        }
        return retorno;
    }

    @Override
    public List<PatrimonialDTO> patrimonials(String ticket, List<PatrimonialDTO> ret) {
        try {
            String json =  HttpUtil.get("https://www.fundsexplorer.com.br/wp-json/funds/v1/patrimonials/"+ticket);
            JsonParser jsonParser = new JsonParser();
            json = json.replace("\"[","[").replace("]\"", "]").replace("\\\"", "\"");
            JsonArray jsonArray = jsonParser.parse(json).getAsJsonArray();
            Gson gson = new Gson();
            jsonArray.forEach(jsonElement -> {
                ret.add(gson.fromJson(jsonElement, PatrimonialDTO.class));
            });
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(Constants.ERRO_HTTP);
        }
    }

    @Override
    public List<PropriedadeDTO> propriedades(String ticket, List<PropriedadeDTO> ret) {
        try {
            String json =  HttpUtil.get("https://www.fundsexplorer.com.br/wp-json/funds/v1/get-all-properties?ticker="+ticket);
            JsonParser jsonParser = new JsonParser();
            json = json.replace("\"[","[").replace("]\"", "]").replace("\\\"", "\"");
            json = removeCaracteresEspeciais(json);
            JsonArray jsonArray = jsonParser.parse(json).getAsJsonArray();

            Gson gson = new Gson();
            jsonArray.forEach(jsonElement -> {
                ret.add(gson.fromJson(jsonElement, PropriedadeDTO.class));
            });
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(Constants.ERRO_HTTP);
        }
    }
}

