package com.github.tombessa.autofundsexplorer.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 *
 * @author antonyonne.bessa
 */
@Configuration
public class CorsConfig {

    public static final String TOKEN_HEADER = "Authorization";

    @Bean
    CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setExposedHeaders(Collections.singletonList("Content-Disposition"));
        config.addAllowedHeader(TOKEN_HEADER);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}