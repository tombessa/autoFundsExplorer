package com.github.tombessa.autofundsexplorer.service.impl;

import com.github.tombessa.autofundsexplorer.service.AutoFundsExplorerService;
import com.github.tombessa.autofundsexplorer.util.Constants;
import com.github.tombessa.autofundsexplorer.util.HttpUtil;
import com.github.tombessa.autofundsexplorer.util.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.json.*;
import java.io.IOException;

@Service
public class AutoFundsExplorerServiceImpl implements AutoFundsExplorerService {

    private final HttpUtil httpUtil;

    public AutoFundsExplorerServiceImpl(HttpUtil httpUtil) {
        this.httpUtil = httpUtil;
    }

    //https://www.fundsexplorer.com.br/wp-json/funds/v1/get-ranking

    @Override
    public Object ranking() {
        try {
            String ret =  HttpUtil.get("https://www.fundsexplorer.com.br/wp-json/funds/v1/get-ranking");
            ret = (new String("{\"lista\":")).concat(ret).concat("}");
            JSONObject json = new JSONObject(ret);
            return json.get("lista");
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(Constants.ERRO_HTTP);
        }
    }

    @Override
    public Object patrimonials(String ticket) {
        try {
            String ret =  HttpUtil.get("https://www.fundsexplorer.com.br/wp-json/funds/v1/patrimonials/"+ticket);
            ret = (new String("{\"lista\":")).concat(ret).concat("}");
            JSONObject json = new JSONObject(ret);
            return json.get("lista");
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(Constants.ERRO_HTTP);
        }
    }
}

