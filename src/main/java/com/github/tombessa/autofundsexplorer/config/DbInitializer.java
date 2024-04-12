package com.github.tombessa.autofundsexplorer.config;

import com.github.tombessa.autofundsexplorer.util.PropertiesReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


/**
 * @author antonyonne.bessa
 */
@Component
@ConditionalOnProperty(name = "app.db-init", havingValue = "true")
public class DbInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DbInitializer.class);

    private String buildVersion;

    public DbInitializer() {

        try {
            PropertiesReader propertiesReader = new PropertiesReader("properties-from-pom.properties");
            if (propertiesReader == null) {
                this.buildVersion = "";
            } else {
                this.buildVersion = propertiesReader.getProperty("appversion");
            }

        } catch (Exception e) {
            this.buildVersion = "";
        }
    }

    private void parametroLoad() {
    }

    @Override
    public void run(String... args) {
        parametroLoad();
    }
}
