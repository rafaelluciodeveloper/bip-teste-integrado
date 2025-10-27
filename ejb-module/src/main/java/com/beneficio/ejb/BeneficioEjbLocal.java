package com.beneficio.ejb;

import com.beneficio.ejb.entity.Beneficio;
import com.beneficio.ejb.entity.Transferencia;
import jakarta.ejb.Local;
import java.math.BigDecimal;
import java.util.List;

/**
 * Interface local para o EJB de benefícios.
 * Permite acesso local via JNDI dentro do mesmo servidor.
 *
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Local
public interface BeneficioEjbLocal {

    /**
     * Busca todos os benefícios cadastrados no sistema.
     *
     * @return lista de todos os benefícios
     */
    List<Beneficio> findAll();

    /**
     * Busca um benefício específico pelo seu identificador.
     *
     * @param id identificador único do benefício
     * @return benefício encontrado ou null se não existir
     */
    Beneficio findById(Long id);

    /**
     * Salva um benefício aplicando validações de negócio.
     *
     * @param beneficio benefício a ser salvo
     * @return benefício salvo com ID preenchido
     * @throws IllegalArgumentException se as validações falharem
     */
    Beneficio save(Beneficio beneficio);

    /**
     * Atualiza um benefício existente aplicando as validações de negócio.
     *
     * @param id identificador do benefício a ser atualizado
     * @param beneficio dados do benefício para atualização
     * @return benefício atualizado
     * @throws IllegalArgumentException se o benefício não for encontrado
     */
    Beneficio update(Long id, Beneficio beneficio);

    /**
     * Remove um benefício do sistema após verificar regras de negócio.
     *
     * @param id identificador do benefício a ser removido
     * @throws IllegalArgumentException se o benefício não for encontrado
     * @throws IllegalStateException se houver transferências associadas
     */
    void deleteById(Long id);

    /**
     * Busca todos os benefícios que estão ativos.
     *
     * @return lista de benefícios ativos
     */
    List<Beneficio> findAtivos();

    /**
     * Busca benefícios cujo nome contenha o texto especificado.
     *
     * @param nome texto a ser buscado no nome dos benefícios
     * @return lista de benefícios que contêm o texto no nome
     */
    List<Beneficio> searchByNome(String nome);

    /**
     * Realiza transferência de valor entre dois benefícios.
     *
     * @param fromId identificador do benefício origem
     * @param toId identificador do benefício destino
     * @param amount valor a ser transferido
     * @throws IllegalArgumentException se os parâmetros forem inválidos
     * @throws IllegalStateException se as validações de negócio falharem
     */
    void transfer(Long fromId, Long toId, BigDecimal amount);

    /**
     * Lista o histórico de todas as transferências realizadas.
     *
     * @return lista de todas as transferências ordenadas por data
     */
    List<Transferencia> getHistoricoTransferencias();

    /**
     * Lista o histórico de transferências de um benefício específico.
     *
     * @param beneficioId identificador do benefício
     * @return lista de transferências do benefício
     */
    List<Transferencia> getTransferenciasByBeneficio(Long beneficioId);
}
