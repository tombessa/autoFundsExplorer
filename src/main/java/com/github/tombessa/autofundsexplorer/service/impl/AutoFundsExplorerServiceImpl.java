package com.github.tombessa.autofundsexplorer.service.impl;

import com.github.tombessa.autofundsexplorer.model.dto.PatrimonialDTO;
import com.github.tombessa.autofundsexplorer.service.AutoFundsExplorerService;
import com.github.tombessa.autofundsexplorer.util.Constants;
import com.github.tombessa.autofundsexplorer.util.HttpUtil;
import com.github.tombessa.autofundsexplorer.util.exception.BusinessException;
import org.springframework.stereotype.Service;
import com.google.gson.*;
import java.io.IOException;
import java.util.List;

@Service
public class AutoFundsExplorerServiceImpl implements AutoFundsExplorerService {

    private final HttpUtil httpUtil;

    public AutoFundsExplorerServiceImpl(HttpUtil httpUtil) {
        this.httpUtil = httpUtil;
    }

    //https://www.fundsexplorer.com.br/wp-json/funds/v1/get-ranking

    @Override
    public JsonArray ranking() {
        try {
            String json =  HttpUtil.get("https://www.fundsexplorer.com.br/wp-json/funds/v1/get-ranking");
            JsonParser jsonParser = new JsonParser();
            json = json.replace("\"[","[").replace("]\"", "]").replace("\\\"", "\"");
            JsonArray jsonArray = jsonParser.parse(json).getAsJsonArray();

            return jsonArray;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(Constants.ERRO_HTTP);
        }
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
}

