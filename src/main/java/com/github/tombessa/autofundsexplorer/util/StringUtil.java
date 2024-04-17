package com.github.tombessa.autofundsexplorer.util;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StringUtil {

    private final Map<String, String> mapaAcento = new HashMap<>();
    public StringUtil(){
        mapaAcento.put("á","\u00e1");
        mapaAcento.put("à","\u00e0");
        mapaAcento.put("â","\u00e2");
        mapaAcento.put("ã","\u00e3");
        mapaAcento.put("ä","\u00e4");
        mapaAcento.put("Á","\u00c1");
        mapaAcento.put("À","\u00c0");
        mapaAcento.put("Â","\u00c2");
        mapaAcento.put("Ã","\u00c3");
        mapaAcento.put("Ä","\u00c4");
        mapaAcento.put("é","\u00e9");
        mapaAcento.put("è","\u00e8");
        mapaAcento.put("ê","\u00ea");
        mapaAcento.put("ê","\u00ea");
        mapaAcento.put("É","\u00c9");
        mapaAcento.put("È","\u00c8");
        mapaAcento.put("Ê","\u00ca");
        mapaAcento.put("Ë","\u00cb");
        mapaAcento.put("í","\u00ed");
        mapaAcento.put("ì","\u00ec");
        mapaAcento.put("î","\u00ee");
        mapaAcento.put("ï","\u00ef");
        mapaAcento.put("Í","\u00cd");
        mapaAcento.put("Ì","\u00cc");
        mapaAcento.put("Î","\u00ce");
        mapaAcento.put("Ï","\u00cf");
        mapaAcento.put("ó","\u00f3");
        mapaAcento.put("ò","\u00f2");
        mapaAcento.put("ô","\u00f4");
        mapaAcento.put("õ","\u00f5");
        mapaAcento.put("ö","\u00f6");
        mapaAcento.put("Ó","\u00d3");
        mapaAcento.put("Ò","\u00d2");
        mapaAcento.put("Ô","\u00d4");
        mapaAcento.put("Õ","\u00d5");
        mapaAcento.put("Ö","\u00d6");
        mapaAcento.put("ú","\u00fa");
        mapaAcento.put("ù","\u00f9");
        mapaAcento.put("û","\u00fb");
        mapaAcento.put("ü","\u00fc");
        mapaAcento.put("Ú","\u00da");
        mapaAcento.put("Ù","\u00d9");
        mapaAcento.put("Û","\u00db");
        mapaAcento.put("ç","\u00e7");
        mapaAcento.put("Ç","\u00c7");
        mapaAcento.put("ñ","\u00f1");
        mapaAcento.put("Ñ","\u00d1");
        mapaAcento.put("&","\u0026");
        mapaAcento.put("'","\u0027");
    }

    public String tratarMensagem(String mensagem){
        final String[] mensagemFinal = {mensagem};
        System.out.println("mensagem=>"+mensagem);
        mapaAcento.forEach((s, s2) -> {
            mensagemFinal[0] = mensagemFinal[0].replace(s2, s);
        });
        System.out.println("mensagemFinal=>"+ mensagemFinal[0]);
        return mensagemFinal[0];
    }
}
