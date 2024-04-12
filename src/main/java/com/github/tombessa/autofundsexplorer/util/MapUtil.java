package com.github.tombessa.autofundsexplorer.util;

import java.util.Map;

public class MapUtil {

    public String mapToString(Map<String, String> item){
        final String[] strRet = {Constants.ABRE_PARENTESIS};
        item.forEach((key, value) -> {
            strRet[0] = strRet[0]
                    .concat(Constants.ASPAS).concat(key).concat(Constants.ASPAS).concat(Constants.DOIS_PONTOS)
                    .concat(Constants.ASPAS).concat(value).concat(Constants.ASPAS).concat(Constants.VIRGULA)
            ;
        });
        strRet[0] = strRet[0].concat(Constants.FECHA_PARENTESIS);
        return strRet[0];
    }
}
