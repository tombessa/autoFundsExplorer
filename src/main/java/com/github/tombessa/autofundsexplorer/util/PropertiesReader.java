package com.github.tombessa.autofundsexplorer.util;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author antonyonne.bessa
 */
public class PropertiesReader {
    private Properties properties;

    public PropertiesReader(String propertyFileName) throws IOException {
        InputStream is = getClass().getClassLoader()
                .getResourceAsStream(propertyFileName);
        this.properties = new Properties();
        this.properties.load(is);
    }

    public String getProperty(String propertyName) {
        return this.properties.getProperty(propertyName);
    }
}