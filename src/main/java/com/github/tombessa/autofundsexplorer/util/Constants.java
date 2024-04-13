package com.github.tombessa.autofundsexplorer.util;

import java.time.format.DateTimeFormatter;

/**
 *
 * @author antonyonne.bessa
 */
public class Constants {
    public static final String SP_TIMEZONE="America/Sao_Paulo";
    public static final String PT = "pt";
    public static final String BR = "BR";
    public static final String PONTO_VIRGULA = ";";
    public static final String ABRE_PARENTESIS = "(";
    public static final String FECHA_PARENTESIS = ")";
    public static final String ASPAS = "\"";
    public static final String DOIS_PONTOS = ":";
    public static final String VIRGULA = ",";

    public static final String MINIMO = "MINIMO";
    public static final String MAXIMO = "MAXIMO";

    public static final String INQUILINO = "INQUILINO";
    public static final String ENDERECO = "ENDERECO";
    public static final String BAIRRO = "BAIRRO";
    public static final String CIDADE = "CIDADE";
    public static final String ESTADO = "ESTADO";
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);


    public static final String ERRO_EMAIL_1 = "Erro no envio do e-mail";
    public static final String ERRO_HTTP = "Ocorreu um erro na requisição de HTTP";

}

