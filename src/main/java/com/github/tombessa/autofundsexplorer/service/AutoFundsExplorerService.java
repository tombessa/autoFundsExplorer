package com.github.tombessa.autofundsexplorer.service;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author antonyonne.bessa
 */

public interface AutoFundsExplorerService {
    Object ranking();

    Object patrimonials(String ticket);
}