package com.github.tombessa.autofundsexplorer.service.impl;

import com.github.tombessa.autofundsexplorer.model.dto.FIIDTO;
import com.github.tombessa.autofundsexplorer.model.dto.PatrimonialDTO;
import com.github.tombessa.autofundsexplorer.model.dto.PropriedadeDTO;
import com.github.tombessa.autofundsexplorer.service.AutoFundsExplorerService;
import com.github.tombessa.autofundsexplorer.util.Constants;
import com.github.tombessa.autofundsexplorer.util.HttpUtil;
import com.github.tombessa.autofundsexplorer.util.StringUtil;
import com.github.tombessa.autofundsexplorer.util.exception.BusinessException;
import com.google.common.base.Charsets;
import com.google.gson.stream.MalformedJsonException;
import org.apache.commons.collections.list.SetUniqueList;
import org.springframework.stereotype.Service;
import com.google.gson.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AutoFundsExplorerServiceImpl implements AutoFundsExplorerService {

    private final HttpUtil httpUtil;
    private final StringUtil stringUtil;

    public AutoFundsExplorerServiceImpl(HttpUtil httpUtil, StringUtil stringUtil) {
        this.httpUtil = httpUtil;
        this.stringUtil = stringUtil;
    }

    //https://www.fundsexplorer.com.br/wp-json/funds/v1/get-ranking

    private List<FIIDTO> generateRanking(List<FIIDTO> ret){
        try {
            String json =  HttpUtil.get("https://www.fundsexplorer.com.br/wp-json/funds/v1/get-ranking");
            JsonParser jsonParser = new JsonParser();
            json = json.replace("\"[","[")
                    .replace("]\"", "]")
                    .replace("null","\\\"\\\"")
                    .replace("{\\\"", "{\"")
                    .replace("\\\"}", "\"}")
                    .replace("\\\":\\\"", "\":\"")
                    .replace("\\\",\\\"", "\",\"")
            ;
            JsonArray jsonArray = jsonParser.parse(json).getAsJsonArray();

            Gson gson = new Gson();
            jsonArray.forEach(jsonElement -> {
                ret.add(gson.fromJson(jsonElement, FIIDTO.class));
            });
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(Constants.ERRO_HTTP);
        }
    }

    @Override
    public List<FIIDTO> ranking(List<FIIDTO> ret) {
        ret = this.generateRanking(ret);
        ret.forEach(fiidto -> {
            List<PatrimonialDTO> patrimonialDTOS = new ArrayList<>();
            patrimonialDTOS = patrimonials(fiidto.getTicker(), patrimonialDTOS);
            Map<String, Double> variacao = this.getVariacaoPatrimonio(
                    patrimonialDTOS
            );
            fiidto.setVariacao(variacao);
            if(!((fiidto.getSetor().equals("Indefinido"))||(fiidto.getSetor().equals("Papéis")))){
                List<PropriedadeDTO> propriedadeDTOS = new ArrayList<>();
                propriedadeDTOS = this.propriedades(fiidto.getTicker(), propriedadeDTOS);
                fiidto.setDadosPatrimonio(this.getDadosPropriedade(propriedadeDTOS));
            }
        });
        return ret;
    }

    @Override
    public List<FIIDTO> rankingHistorico(List<FIIDTO> ret, LocalDate oInicio, LocalDate oFim) {
        ret = this.generateRanking(ret);
        ret.forEach(fiidto -> {
            List<PatrimonialDTO> patrimonialDTOS = new ArrayList<>();
            patrimonialDTOS = patrimonials(fiidto.getTicker(), patrimonialDTOS);
            Map<String, Double> variacao = this.getVariacaoPatrimonio(
                    patrimonialDTOS,
                    oInicio,
                    oFim
            );
            fiidto.setVariacao(variacao);
            if(!((fiidto.getSetor().equals("Indefinido"))||(fiidto.getSetor().equals("Pap\\u00e9is"))||(fiidto.getSetor().equals("Outros")))){
                List<PropriedadeDTO> propriedadeDTOS = new ArrayList<>();
                propriedadeDTOS = this.propriedades(fiidto.getTicker(), propriedadeDTOS);
                fiidto.setDadosPatrimonio(this.getDadosPropriedade(propriedadeDTOS));
            }
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

    private Map<String, Double> getVariacaoPatrimonio(List<PatrimonialDTO> item){
        Map<String, Double> retorno = new HashMap<>();
        retorno.put(Constants.MINIMO, 0D);
        retorno.put(Constants.MAXIMO, 0D);
        item.forEach(patrimonialDTO -> patrimonialDTO.getPeriodo());
        List<PatrimonialDTO> filtered = item.stream()
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
            json = new String(json.getBytes(Charsets.UTF_8), StandardCharsets.UTF_8);
            json = json.replace("\"[","[")
                    .replace("]\"", "]")
                    .replace("null","\\\"\\\"")
                    .replace("{\\\"", "{\"")
                    .replace("\\\"}", "\"}")
                    .replace("\\\":\\\"", "\":\"")
                    .replace("\\\",\\\"", "\",\"")
            ;
            JsonParser jsonParser = new JsonParser();
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

