package com.github.tombessa.autofundsexplorer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class FIIDTO {
    @Builder.Default
    private Map<String, Integer> dadosPatrimonio = new HashMap<>();
    @Builder.Default
    private Map<String, Double> variacao = new HashMap<>();
    private String post_id;
    private String ticker;
    private String dividendo;
    private String yeld;
    private String media_yield_3m;
    private String soma_yield_3m;
    private String media_yield_6m;
    private String soma_yield_6m;
    private String media_yield_12m;
    private String soma_yield_12m;
    private String variacao_cotacao_mes;
    private String rentabilidade;
    private String rentabilidade_mes;
    private String cotacao_fechamento;
    private String soma_yield_ano_corrente;
    private String ano;
    private String vpa_yield;
    private String vpa;
    private String vpa_change;
    private String pl;
    private String vpa_rent;
    private String vpa_rent_m;
    private String yield_vpa_3m_sum;
    private String yield_vpa_3m;
    private String yield_vpa_6m_sum;
    private String yield_vpa_6m;
    private String yield_vpa_12m_sum;
    private String yield_vpa_12m;
    private String setor;
    private String setor_slug;
    private String valor;
    private String liquidezmediadiaria;
    private String patrimonio;
    private String pvp;
    private String p_vpa;
    private String post_title;
    private String ativos;
    private String volatility;
    private String numero_cotista;
    private String tx_gestao;
    private String tx_admin;
    private String tx_performance;

}
