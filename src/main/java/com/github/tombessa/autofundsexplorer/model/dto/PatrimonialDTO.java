package com.github.tombessa.autofundsexplorer.model.dto;

import com.github.tombessa.autofundsexplorer.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PatrimonialDTO {
    private String periodo;
    private LocalDate oPeriodo;
    private Double valor;

    public String getPeriodo(){
        DateTimeFormatter oFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        try {
            this.oPeriodo = LocalDate.parse(this.periodo, oFormatter);
        } catch (Exception e) {}
        return this.periodo;
    }

}
