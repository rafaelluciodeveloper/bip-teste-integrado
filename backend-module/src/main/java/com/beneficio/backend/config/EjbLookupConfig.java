package com.beneficio.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;
import com.beneficio.ejb.BeneficioEjbLocal;

/**
 * Configuração para lookup local do EJB via JNDI.
 * Expõe o EJB como um bean Spring para injeção nos controllers.
 *
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class EjbLookupConfig {

    /**
     * Configura o lookup do EJB de benefícios via JNDI.
     * O nome JNDI segue o padrão: java:global/ear-name/ejb-module-name/EjbClassName!InterfaceName
     *
     * @return BeneficioEjbLocal configurado via JNDI
     * @throws Exception se houver erro no lookup JNDI
     */
    @Bean
    public BeneficioEjbLocal beneficioEjb() throws Exception {
        JndiObjectFactoryBean jndi = new JndiObjectFactoryBean();
        // Nome JNDI baseado na estrutura do EAR: java:global/ear-module-1.0.0/com.beneficio-ejb-module-1.0.0/BeneficioEjb!BeneficioEjbLocal
        jndi.setJndiName("java:global/ear-module-1.0.0/com.beneficio-ejb-module-1.0.0/BeneficioEjb!com.beneficio.ejb.BeneficioEjbLocal");
        jndi.afterPropertiesSet();
        return (BeneficioEjbLocal) jndi.getObject();
    }
}