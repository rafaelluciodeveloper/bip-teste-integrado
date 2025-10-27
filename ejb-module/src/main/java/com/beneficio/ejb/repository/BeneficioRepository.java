package com.beneficio.ejb.repository;

import java.util.List;

import com.beneficio.ejb.entity.Beneficio;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

/**
 * Repositório responsável pelas operações de persistência da entidade Beneficio.
 * Implementa o padrão Repository para acesso aos dados usando JPA.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@ApplicationScoped
public class BeneficioRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * Busca todos os benefícios cadastrados no sistema.
     * 
     * @return lista de todos os benefícios
     */
    public List<Beneficio> findAll() {
        TypedQuery<Beneficio> query = em.createQuery("SELECT b FROM Beneficio b", Beneficio.class);
        return query.getResultList();
    }

    /**
     * Busca um benefício específico pelo seu identificador.
     * 
     * @param id identificador único do benefício
     * @return benefício encontrado ou null se não existir
     */
    public Beneficio findById(Long id) {
        return em.find(Beneficio.class, id);
    }

    /**
     * Busca um benefício com lock pessimista de escrita.
     * Garante que a linha será bloqueada para evitar concorrência durante atualizações.
     *
     * @param id identificador único do benefício
     * @return benefício bloqueado com {@link LockModeType#PESSIMISTIC_WRITE}
     */
    public Beneficio findByIdForUpdate(Long id) {
        return em.find(Beneficio.class, id, LockModeType.PESSIMISTIC_WRITE);
    }

    /**
     * Salva um benefício no banco de dados.
     * Se o benefício não possui ID, será persistido como novo.
     * Se possui ID, será atualizado.
     * 
     * @param beneficio benefício a ser salvo
     * @return benefício salvo com ID preenchido
     */
    public Beneficio save(Beneficio beneficio) {
        if (beneficio.getId() == null) {
            em.persist(beneficio);
        } else {
            beneficio = em.merge(beneficio);
        }
        return beneficio;
    }

    /**
     * Remove um benefício do banco de dados pelo seu identificador.
     * 
     * @param id identificador único do benefício a ser removido
     */
    public void deleteById(Long id) {
        Beneficio beneficio = findById(id);
        if (beneficio != null) {
            em.remove(beneficio);
        }
    }

    /**
     * Busca todos os benefícios que estão ativos.
     * 
     * @return lista de benefícios ativos
     */
    public List<Beneficio> findByAtivoTrue() {
        return em.createQuery("SELECT b FROM Beneficio b WHERE b.ativo = true", Beneficio.class)
                  .getResultList();
    }

    /**
     * Busca benefícios cujo nome contenha o texto especificado.
     * A busca é case-insensitive e usa LIKE com wildcards.
     * 
     * @param nome texto a ser buscado no nome dos benefícios
     * @return lista de benefícios que contêm o texto no nome
     */
    public List<Beneficio> findByNomeContaining(String nome) {
        return em.createQuery("SELECT b FROM Beneficio b WHERE b.nome LIKE :nome", Beneficio.class)
                  .setParameter("nome", "%" + nome + "%")
                  .getResultList();
    }
}
