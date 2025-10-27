package com.beneficio.ejb.service;

import com.beneficio.ejb.constants.Messages;
import com.beneficio.ejb.entity.Beneficio;
import com.beneficio.ejb.entity.Transferencia;
import com.beneficio.ejb.repository.BeneficioRepository;
import com.beneficio.ejb.repository.TransferenciaRepository;

import jakarta.annotation.security.PermitAll;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.OptimisticLockException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pelas regras de negócio relacionadas aos benefícios.
 * Implementa validações, operações de transferência e controle de integridade.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Stateless
@PermitAll
public class BeneficioService {

    @Inject
    private BeneficioRepository beneficioRepository;
    
    @Inject
    private TransferenciaRepository transferenciaRepository;
    
    @PersistenceContext
    private EntityManager em;

    /**
     * Busca todos os benefícios cadastrados no sistema.
     * 
     * @return lista de todos os benefícios
     */
    public List<Beneficio> findAll() {
        return beneficioRepository.findAll();
    }

    /**
     * Busca um benefício específico pelo seu identificador.
     * 
     * @param id identificador único do benefício
     * @return Optional contendo o benefício ou vazio se não encontrado
     */
    public Optional<Beneficio> findById(Long id) {
        Beneficio beneficio = beneficioRepository.findById(id);
        return Optional.ofNullable(beneficio);
    }

    /**
     * Salva um benefício aplicando as validações de negócio necessárias.
     * 
     * @param beneficio benefício a ser salvo
     * @return benefício salvo com validações aplicadas
     * @throws IllegalArgumentException se as validações falharem
     */
    public Beneficio save(Beneficio beneficio) {
        validarBeneficio(beneficio);
        return beneficioRepository.save(beneficio);
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
        Beneficio existing = beneficioRepository.findById(id);
        if (existing == null) {
            throw new IllegalArgumentException(String.format(Messages.BENEFICIO_NAO_ENCONTRADO, id));
        }
        
        beneficio.setId(id);
        return save(beneficio);
    }

    /**
     * Remove um benefício do sistema após verificar regras de negócio.
     * 
     * @param id identificador do benefício a ser removido
     * @throws IllegalArgumentException se o benefício não for encontrado
     * @throws IllegalStateException se houver transferências associadas
     */
    public void deleteById(Long id) {
        Beneficio beneficio = beneficioRepository.findById(id);
        if (beneficio == null) {
            throw new IllegalArgumentException(String.format(Messages.BENEFICIO_NAO_ENCONTRADO, id));
        }
        
        if (temTransferenciasAssociadas(id)) {
            throw new IllegalStateException(Messages.NAO_PODE_EXCLUIR_COM_TRANSFERENCIAS);
        }
        
        beneficioRepository.deleteById(id);
    }

    /**
     * Busca todos os benefícios que estão ativos.
     * 
     * @return lista de benefícios ativos
     */
    public List<Beneficio> findAtivos() {
        return beneficioRepository.findByAtivoTrue();
    }

    /**
     * Busca benefícios cujo nome contenha o texto especificado.
     * 
     * @param nome texto a ser buscado no nome dos benefícios
     * @return lista de benefícios que contêm o texto no nome
     */
    public List<Beneficio> searchByNome(String nome) {
        return beneficioRepository.findByNomeContaining(nome);
    }

    /**
     * Realiza transferência de valor entre dois benefícios com controle de concorrência.
     * Utiliza locking pessimista para evitar lost updates e garante consistência.
     * 
     * @param fromId identificador do benefício origem
     * @param toId identificador do benefício destino
     * @param amount valor a ser transferido
     * @throws IllegalArgumentException se os parâmetros forem inválidos
     * @throws IllegalStateException se as validações de negócio falharem
     */
    public void transferir(Long fromId, Long toId, BigDecimal amount) {
        if (fromId == null || toId == null || amount == null) {
            throw new IllegalArgumentException(Messages.PARAMETROS_NAO_PODEM_SER_NULOS);
        }
        
        if (fromId.equals(toId)) {
            throw new IllegalArgumentException(Messages.BENEFICIO_ORIGEM_DESTINO_IGUAIS);
        }
        
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(Messages.VALOR_DEVE_SER_POSITIVO);
        }

        Beneficio from = em.find(Beneficio.class, fromId, LockModeType.PESSIMISTIC_WRITE);
        Beneficio to = em.find(Beneficio.class, toId, LockModeType.PESSIMISTIC_WRITE);

        if (from == null) {
            throw new IllegalArgumentException(String.format(Messages.BENEFICIO_ORIGEM_NAO_ENCONTRADO, fromId));
        }
        
        if (to == null) {
            throw new IllegalArgumentException(String.format(Messages.BENEFICIO_DESTINO_NAO_ENCONTRADO, toId));
        }
        
        if (!from.getAtivo()) {
            throw new IllegalStateException(Messages.BENEFICIO_ORIGEM_INATIVO);
        }
        
        if (!to.getAtivo()) {
            throw new IllegalStateException(Messages.BENEFICIO_DESTINO_INATIVO);
        }
        
        if (from.getValor().compareTo(amount) < 0) {
            throw new IllegalStateException(String.format(Messages.SALDO_INSUFICIENTE, from.getValor(), amount));
        }

        try {
            from.setValor(from.getValor().subtract(amount));
            to.setValor(to.getValor().add(amount));

            em.merge(from);
            em.merge(to);
            
            Transferencia transferencia = new Transferencia(from, to, amount);
            transferenciaRepository.save(transferencia);
            
            em.flush();
            
        } catch (OptimisticLockException e) {
            throw new IllegalStateException("Transferência falhou devido a conflito de concorrência. Tente novamente.");
        }
    }

    /**
     * Valida os dados de um benefício conforme as regras de negócio.
     * 
     * @param beneficio benefício a ser validado
     * @throws IllegalArgumentException se alguma validação falhar
     */
    private void validarBeneficio(Beneficio beneficio) {
        if (beneficio.getNome() == null || beneficio.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException(Messages.NOME_OBRIGATORIO);
        }
        
        if (beneficio.getNome().length() > 100) {
            throw new IllegalArgumentException(Messages.NOME_MAXIMO_CARACTERES);
        }
        
        if (beneficio.getDescricao() != null && beneficio.getDescricao().length() > 255) {
            throw new IllegalArgumentException(Messages.DESCRICAO_MAXIMO_CARACTERES);
        }
        
        if (beneficio.getValor() == null) {
            throw new IllegalArgumentException(Messages.VALOR_OBRIGATORIO);
        }
        
        if (beneficio.getValor().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(Messages.VALOR_NAO_PODE_SER_NEGATIVO);
        }
        
        if (beneficio.getValor().compareTo(new BigDecimal("999999999999999.99")) > 0) {
            throw new IllegalArgumentException(Messages.VALOR_EXCEDE_LIMITE);
        }
    }

    /**
     * Verifica se existem transferências associadas a um benefício.
     * 
     * @param beneficioId identificador do benefício
     * @return true se existem transferências associadas, false caso contrário
     */
    private boolean temTransferenciasAssociadas(Long beneficioId) {
        return transferenciaRepository.hasTransferencias(beneficioId);
    }
    
    /**
     * Lista o histórico de todas as transferências realizadas.
     * 
     * @return lista de todas as transferências ordenadas por data
     */
    public List<Transferencia> getHistoricoTransferencias() {
        return transferenciaRepository.findAll();
    }
    
    /**
     * Lista o histórico de transferências de um benefício específico.
     * 
     * @param beneficioId identificador do benefício
     * @return lista de transferências do benefício
     */
    public List<Transferencia> getTransferenciasByBeneficio(Long beneficioId) {
        List<Transferencia> transferencias = new ArrayList<>();
        transferencias.addAll(transferenciaRepository.findByBeneficioOrigem(beneficioId));
        transferencias.addAll(transferenciaRepository.findByBeneficioDestino(beneficioId));
        
        // Ordenar por data de transferência (mais recente primeiro)
        transferencias.sort((t1, t2) -> t2.getDataTransferencia().compareTo(t1.getDataTransferencia()));
        
        return transferencias;
    }
}
