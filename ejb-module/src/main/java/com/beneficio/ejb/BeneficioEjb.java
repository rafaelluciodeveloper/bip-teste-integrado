package com.beneficio.ejb;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.beneficio.ejb.entity.Beneficio;
import com.beneficio.ejb.entity.Transferencia;
import com.beneficio.ejb.service.BeneficioService;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

/**
 * EJB que atua como facade para integração com o Spring Boot.
 * Delega as operações para o BeneficioService interno.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Stateless
public class BeneficioEjb {

    @EJB
    private BeneficioService beneficioService;

    /**
     * Busca todos os benefícios cadastrados no sistema.
     * 
     * @return lista de todos os benefícios
     */
    public List<Beneficio> findAll() {
        return beneficioService.findAll();
    }

    /**
     * Busca um benefício específico pelo seu identificador.
     * 
     * @param id identificador único do benefício
     * @return benefício encontrado ou null se não existir
     */
    public Beneficio findById(Long id) {
        return beneficioService.findById(id).orElse(null);
    }

    /**
     * Salva um benefício aplicando validações de negócio.
     * 
     * @param beneficio benefício a ser salvo
     * @return benefício salvo com ID preenchido
     * @throws IllegalArgumentException se as validações falharem
     */
    public Beneficio save(Beneficio beneficio) {
        return beneficioService.save(beneficio);
    }

    /**
     * Atualiza um benefício existente aplicando as validações de negócio.
     * 
     * @param id identificador do benefício a ser atualizado
     * @param beneficio dados do benefício para atualização
     * @return benefício atualizado
     * @throws IllegalArgumentException se o benefício não for encontrado
     */
    public Beneficio update(Long id, Beneficio beneficio) {
        return beneficioService.update(id, beneficio);
    }

    /**
     * Remove um benefício do sistema após verificar regras de negócio.
     * 
     * @param id identificador do benefício a ser removido
     * @throws IllegalArgumentException se o benefício não for encontrado
     * @throws IllegalStateException se houver transferências associadas
     */
    public void deleteById(Long id) {
        beneficioService.deleteById(id);
    }

    /**
     * Busca todos os benefícios que estão ativos.
     * 
     * @return lista de benefícios ativos
     */
    public List<Beneficio> findAtivos() {
        return beneficioService.findAtivos();
    }

    /**
     * Busca benefícios cujo nome contenha o texto especificado.
     * 
     * @param nome texto a ser buscado no nome dos benefícios
     * @return lista de benefícios que contêm o texto no nome
     */
    public List<Beneficio> searchByNome(String nome) {
        return beneficioService.searchByNome(nome);
    }

    /**
     * Realiza transferência de valor entre dois benefícios.
     * 
     * @param fromId identificador do benefício origem
     * @param toId identificador do benefício destino
     * @param amount valor a ser transferido
     * @throws IllegalArgumentException se os parâmetros forem inválidos
     * @throws IllegalStateException se as validações de negócio falharem
     */
    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        beneficioService.transferir(fromId, toId, amount);
    }
    
    /**
     * Lista o histórico de todas as transferências realizadas.
     * 
     * @return lista de todas as transferências ordenadas por data
     */
    public List<Transferencia> getHistoricoTransferencias() {
        return beneficioService.getHistoricoTransferencias();
    }
    
    /**
     * Lista o histórico de transferências de um benefício específico.
     * 
     * @param beneficioId identificador do benefício
     * @return lista de transferências do benefício
     */
    public List<Transferencia> getTransferenciasByBeneficio(Long beneficioId) {
        return beneficioService.getTransferenciasByBeneficio(beneficioId);
    }
}