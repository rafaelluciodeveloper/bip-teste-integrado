package com.beneficio.backend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
/**
 * Classe principal da aplicação Spring Boot.
 * Configurada para rodar como WAR dentro do WildFly.
 *
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
public class BackendApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BackendApplication.class);
    }

}