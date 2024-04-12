package com.github.tombessa.autofundsexplorer.service;

import com.github.tombessa.autofundsexplorer.model.dto.PatrimonialDTO;
import com.google.gson.JsonArray;

import java.util.List;


/**
 *
 * @author antonyonne.bessa
 */

public interface AutoFundsExplorerService {
    JsonArray ranking();

    List<PatrimonialDTO> patrimonials(String ticket, List<PatrimonialDTO> ret);
}