package com.beneficio.ejb;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.beneficio.ejb.entity.Beneficio;
import com.beneficio.ejb.entity.Transferencia;
import com.beneficio.ejb.service.BeneficioService;

import jakarta.annotation.security.PermitAll;
import jakarta.ejb.Local;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * EJB que atua como facade para integração com o Spring Boot.
 * Delega as operações para o BeneficioService interno.
 * Implementa interfaces local e remota para acesso via JNDI.
 *
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Stateless
@Local(BeneficioEjbLocal.class)
@Remote(BeneficioEjbRemote.class)
@PermitAll
public class BeneficioEjb implements BeneficioEjbLocal, BeneficioEjbRemote {

    private BeneficioService beneficioService;

    /**
     * Inicializa o BeneficioService via lookup JNDI.
     * Usado para evitar problemas de injeção entre módulos EAR.
     */
    private BeneficioService getBeneficioService() {
        if (beneficioService == null) {
            try {
                InitialContext ctx = new InitialContext();
                beneficioService = (BeneficioService) ctx.lookup("java:module/BeneficioService");
            } catch (NamingException e) {
                throw new RuntimeException("Erro ao fazer lookup do BeneficioService", e);
            }
        }
        return beneficioService;
    }

    /**
     * Busca todos os benefícios cadastrados no sistema.
     * 
     * @return lista de todos os benefícios
     */
    public List<Beneficio> findAll() {
        return getBeneficioService().findAll();
    }

    /**
     * Busca um benefício específico pelo seu identificador.
     * 
     * @param id identificador único do benefício
     * @return benefício encontrado ou null se não existir
     */
    public Beneficio findById(Long id) {
        return getBeneficioService().findById(id).orElse(null);
    }

    /**
     * Salva um benefício aplicando validações de negócio.
     * 
     * @param beneficio benefício a ser salvo
     * @return benefício salvo com ID preenchido
     * @throws IllegalArgumentException se as validações falharem
     */
    public Beneficio save(Beneficio beneficio) {
        return getBeneficioService().save(beneficio);
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
        return getBeneficioService().update(id, beneficio);
    }

    /**
     * Remove um benefício do sistema após verificar regras de negócio.
     * 
     * @param id identificador do benefício a ser removido
     * @throws IllegalArgumentException se o benefício não for encontrado
     * @throws IllegalStateException se houver transferências associadas
     */
    public void deleteById(Long id) {
        getBeneficioService().deleteById(id);
    }

    /**
     * Busca todos os benefícios que estão ativos.
     * 
     * @return lista de benefícios ativos
     */
    public List<Beneficio> findAtivos() {
        return getBeneficioService().findAtivos();
    }

    /**
     * Busca benefícios cujo nome contenha o texto especificado.
     * 
     * @param nome texto a ser buscado no nome dos benefícios
     * @return lista de benefícios que contêm o texto no nome
     */
    public List<Beneficio> searchByNome(String nome) {
        return getBeneficioService().searchByNome(nome);
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
        getBeneficioService().transferir(fromId, toId, amount);
    }
    
    /**
     * Lista o histórico de todas as transferências realizadas.
     * 
     * @return lista de todas as transferências ordenadas por data
     */
    public List<Transferencia> getHistoricoTransferencias() {
        return getBeneficioService().getHistoricoTransferencias();
    }
    
    /**
     * Lista o histórico de transferências de um benefício específico.
     * 
     * @param beneficioId identificador do benefício
     * @return lista de transferências do benefício
     */
    public List<Transferencia> getTransferenciasByBeneficio(Long beneficioId) {
        return getBeneficioService().getTransferenciasByBeneficio(beneficioId);
    }
}