package com.beneficio.backend.config;

import com.beneficio.ejb.BeneficioEjb;
import com.beneficio.ejb.service.BeneficioService;
import com.beneficio.ejb.repository.BeneficioRepository;
import com.beneficio.ejb.repository.TransferenciaRepository;
import com.beneficio.ejb.entity.Beneficio;
import com.beneficio.ejb.entity.Transferencia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Configuração para registrar EJBs como beans do Spring.
 * Permite que o Spring Boot gerencie EJBs através de injeção de dependência.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Configuration
@EntityScan(basePackages = "com.beneficio.ejb.entity")
public class EjbConfig {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Registra o BeneficioRepository como um bean do Spring.
     * 
     * @return instância do BeneficioRepository
     */
    @Bean
    public BeneficioRepository beneficioRepository() {
        BeneficioRepository repository = new BeneficioRepository();
        // Injetar EntityManager via reflexão ou setter se disponível
        return repository;
    }

    /**
     * Registra o TransferenciaRepository como um bean do Spring.
     * 
     * @return instância do TransferenciaRepository
     */
    @Bean
    public TransferenciaRepository transferenciaRepository() {
        return new TransferenciaRepository();
    }

    /**
     * Registra o BeneficioService como um bean do Spring.
     * 
     * @return instância do BeneficioService
     */
    @Bean
    public BeneficioService beneficioService() {
        return new BeneficioService();
    }

    /**
     * Registra o BeneficioEjb como um bean do Spring.
     * 
     * @return instância do BeneficioEjb
     */
    @Bean
    public BeneficioEjb beneficioEjb() {
        return new BeneficioEjb();
    }
}
