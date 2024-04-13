package com.github.tombessa.autofundsexplorer.service;

import com.github.tombessa.autofundsexplorer.model.dto.FIIDTO;
import com.github.tombessa.autofundsexplorer.model.dto.PatrimonialDTO;
import com.github.tombessa.autofundsexplorer.model.dto.PropriedadeDTO;

import java.time.LocalDate;
import java.util.List;


/**
 *
 * @author antonyonne.bessa
 */

public interface AutoFundsExplorerService {
    List<FIIDTO> ranking(List<FIIDTO> ret);

    List<FIIDTO> rankingHistorico(List<FIIDTO> ret, LocalDate oInicio, LocalDate oFim);

    List<PatrimonialDTO> patrimonials(String ticket, List<PatrimonialDTO> ret);
    List<PropriedadeDTO> propriedades(String ticket, List<PropriedadeDTO> ret);
}