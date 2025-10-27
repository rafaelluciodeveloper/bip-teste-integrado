package com.beneficio.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.logging.LoggingSystem;

/**
 * Configuração para desabilitar o Logback e usar o sistema de logging do WildFly.
 *
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class LoggingConfig {

    /**
     * Desabilita o Logback para evitar conflito com o sistema de logging do WildFly.
     */
    @Bean
    @ConditionalOnClass(LoggingSystem.class)
    @ConditionalOnMissingBean(LoggingSystem.class)
    public LoggingSystem loggingSystem() {
        return null;
    }
}