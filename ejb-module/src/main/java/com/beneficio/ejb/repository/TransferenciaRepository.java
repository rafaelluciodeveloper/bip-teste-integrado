package com.beneficio.ejb.repository;

import java.util.List;

import com.beneficio.ejb.entity.Transferencia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

/**
 * Repositório responsável pelas operações de persistência da entidade Transferencia.
 * Implementa operações CRUD e consultas específicas para transferências.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@ApplicationScoped
public class TransferenciaRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * Salva uma transferência no banco de dados.
     * 
     * @param transferencia transferência a ser salva
     * @return transferência salva com ID preenchido
     */
    public Transferencia save(Transferencia transferencia) {
        em.persist(transferencia);
        return transferencia;
    }

    /**
     * Busca todas as transferências cadastradas.
     * 
     * @return lista de todas as transferências
     */
    public List<Transferencia> findAll() {
        TypedQuery<Transferencia> query = em.createQuery("SELECT t FROM Transferencia t ORDER BY t.dataTransferencia DESC", Transferencia.class);
        return query.getResultList();
    }

    /**
     * Busca transferências por benefício origem.
     * 
     * @param beneficioId identificador do benefício origem
     * @return lista de transferências onde o benefício foi origem
     */
    public List<Transferencia> findByBeneficioOrigem(Long beneficioId) {
        TypedQuery<Transferencia> query = em.createQuery(
            "SELECT t FROM Transferencia t WHERE t.beneficioOrigem.id = :beneficioId ORDER BY t.dataTransferencia DESC", 
            Transferencia.class);
        query.setParameter("beneficioId", beneficioId);
        return query.getResultList();
    }

    /**
     * Busca transferências por benefício destino.
     * 
     * @param beneficioId identificador do benefício destino
     * @return lista de transferências onde o benefício foi destino
     */
    public List<Transferencia> findByBeneficioDestino(Long beneficioId) {
        TypedQuery<Transferencia> query = em.createQuery(
            "SELECT t FROM Transferencia t WHERE t.beneficioDestino.id = :beneficioId ORDER BY t.dataTransferencia DESC", 
            Transferencia.class);
        query.setParameter("beneficioId", beneficioId);
        return query.getResultList();
    }

    /**
     * Conta o número de transferências associadas a um benefício.
     * 
     * @param beneficioId identificador do benefício
     * @return número de transferências onde o benefício foi origem ou destino
     */
    public Long countByBeneficio(Long beneficioId) {
        TypedQuery<Long> query = em.createQuery(
            "SELECT COUNT(t) FROM Transferencia t WHERE t.beneficioOrigem.id = :beneficioId OR t.beneficioDestino.id = :beneficioId", 
            Long.class);
        query.setParameter("beneficioId", beneficioId);
        return query.getSingleResult();
    }

    /**
     * Verifica se existem transferências associadas a um benefício.
     * 
     * @param beneficioId identificador do benefício
     * @return true se existem transferências associadas, false caso contrário
     */
    public boolean hasTransferencias(Long beneficioId) {
        return countByBeneficio(beneficioId) > 0;
    }
}
