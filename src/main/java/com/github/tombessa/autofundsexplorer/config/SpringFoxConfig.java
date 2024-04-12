package com.github.tombessa.autofundsexplorer.config;

import com.github.tombessa.autofundsexplorer.util.PropertiesReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Value;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityScheme;

/**
 *
 * @author antonyonne.bessa
 */
@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    @Value("${spring.datasource.url}")
    private String conexao;

    @Value("${spring.datasource.username}")
    private String usuarioConexao;

    @Value("${spring.application.name}")
    private String appName;



    private static final String API_KEY_NAME = "JWT_TOKEN";
    private static final String OAUTH_NAME = "spring_oauth";
    private static final String ALLOWED_PATHS = "/api/.*";
    private static final String TITLE = "autoFundsExplorer Backend - ";
    private static final String DESCRIPTION = "REST API for autoFundsExplorer -";
    private static final String VERSION = "1.0";
    private static final String BUILD_MESSAGE = " (Profile) - Build Time:";

    private String buildVersion = "";
    private String buildTimestamp = "";


    public SpringFoxConfig() throws IOException {
        try {
            PropertiesReader propertiesReader = new PropertiesReader("properties-from-pom.properties");
            if(propertiesReader==null){
                this.buildVersion = "";
                this.buildTimestamp = "";
            }else{
                this.buildVersion = propertiesReader.getProperty("appversion");
                this.buildTimestamp = propertiesReader.getProperty("timestamp");
            }

        } catch (Exception e) {
            this.buildVersion = "";
            this.buildTimestamp = "";
        }

    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title(this.TITLE)
                .description(this.DESCRIPTION + ". Build Time:" + this.buildTimestamp)
                .version(this.buildVersion)
                .license("Antonyonne Soares Bessa")
                .licenseUrl("https://github.com/tombessa?tab=repositories")
                .build();
    }

    @Bean
    public Docket api() {
        SecurityReference securityReference = SecurityReference.builder()
                .reference("basicAuth")
                .scopes(new AuthorizationScope[0])
                .build();

        ArrayList<SecurityReference> reference = new ArrayList<>(1);
        reference.add(securityReference);

        ArrayList<SecurityContext> securityContexts = new ArrayList<>(1);
        securityContexts.add(SecurityContext.builder().securityReferences(reference).build());

        ArrayList<SecurityScheme> auth = new ArrayList<>(1);
        auth.add(new BasicAuth("basicAuth"));

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.github.tombessa.autofundsexplorer.rest"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo())
                .securityContexts(securityContexts)
                .securitySchemes(auth)
                .ignoredParameterTypes(Principal.class);
    }

    public String getBuildVersion() {
        return this.buildVersion;
    }




}