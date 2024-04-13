package com.github.tombessa.autofundsexplorer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PropriedadeDTO {
    private String id;
    private String ticker;
    private String created_at;
    private String updated_at;
    private String nome;
    private String endereco;
    private String endereco_completo;
    private String bairro;
    private String cidade;
    private String estado;
    private String latitude;
    private String longitude;
    private String area;
    private String vacancia;
    private String inadimplencia;
    private String objetivo;
    private String percentual_sobre_receita;
}
