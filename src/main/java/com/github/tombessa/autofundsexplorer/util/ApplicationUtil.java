/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.tombessa.autofundsexplorer.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 *
 * @author antonyonne.bessa
 */
public class ApplicationUtil {


    private static final ApplicationUtil INSTANCE = new ApplicationUtil();
    private final static Logger LOGGER
            = Logger.getLogger(ApplicationUtil.class.getCanonicalName());
    private static final ResourceBundle auth;

    static {
        auth = ResourceBundle.getBundle("auth");
    }

    public static String getAuthProperty(final String propertyName) {
        try {
            return auth.getString(propertyName);
        } catch (MissingResourceException | ClassCastException | NullPointerException e) {
            return "";
        }
    }

    
}
