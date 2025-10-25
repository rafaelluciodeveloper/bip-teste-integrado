package com.beneficio.ejb.repository;

import com.beneficio.ejb.entity.Beneficio;
import com.beneficio.ejb.entity.Transferencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para TransferenciaRepository.
 * Valida operações de persistência e consultas de transferências.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class TransferenciaRepositoryTest {

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<Transferencia> query;

    @Mock
    private TypedQuery<Long> longQuery;

    @InjectMocks
    private TransferenciaRepository transferenciaRepository;

    private Beneficio beneficio1;
    private Beneficio beneficio2;
    private Transferencia transferencia1;
    private List<Transferencia> transferencias;

    @BeforeEach
    void setUp() {
        beneficio1 = new Beneficio("Benefício A", "Descrição A", new BigDecimal("1000.00"));
        beneficio1.setId(1L);

        beneficio2 = new Beneficio("Benefício B", "Descrição B", new BigDecimal("500.00"));
        beneficio2.setId(2L);

        transferencia1 = new Transferencia(beneficio1, beneficio2, new BigDecimal("100.00"));
        transferencia1.setId(1L);

        transferencias = Arrays.asList(transferencia1);
    }

    @Test
    void testSave() {
        Transferencia result = transferenciaRepository.save(transferencia1);

        assertNotNull(result);
        verify(em).persist(transferencia1);
    }

    @Test
    void testFindAll() {
        when(em.createQuery(anyString(), eq(Transferencia.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(transferencias);

        List<Transferencia> result = transferenciaRepository.findAll();

        assertEquals(1, result.size());
        assertEquals(beneficio1, result.get(0).getBeneficioOrigem());
        assertEquals(beneficio2, result.get(0).getBeneficioDestino());
        verify(em).createQuery("SELECT t FROM Transferencia t ORDER BY t.dataTransferencia DESC", Transferencia.class);
        verify(query).getResultList();
    }

    @Test
    void testFindByBeneficioOrigem() {
        when(em.createQuery(anyString(), eq(Transferencia.class))).thenReturn(query);
        when(query.setParameter("beneficioId", 1L)).thenReturn(query);
        when(query.getResultList()).thenReturn(transferencias);

        List<Transferencia> result = transferenciaRepository.findByBeneficioOrigem(1L);

        assertEquals(1, result.size());
        verify(em).createQuery("SELECT t FROM Transferencia t WHERE t.beneficioOrigem.id = :beneficioId ORDER BY t.dataTransferencia DESC", Transferencia.class);
        verify(query).setParameter("beneficioId", 1L);
        verify(query).getResultList();
    }

    @Test
    void testFindByBeneficioDestino() {
        when(em.createQuery(anyString(), eq(Transferencia.class))).thenReturn(query);
        when(query.setParameter("beneficioId", 2L)).thenReturn(query);
        when(query.getResultList()).thenReturn(transferencias);

        List<Transferencia> result = transferenciaRepository.findByBeneficioDestino(2L);

        assertEquals(1, result.size());
        verify(em).createQuery("SELECT t FROM Transferencia t WHERE t.beneficioDestino.id = :beneficioId ORDER BY t.dataTransferencia DESC", Transferencia.class);
        verify(query).setParameter("beneficioId", 2L);
        verify(query).getResultList();
    }

    @Test
    void testCountByBeneficio() {
        when(em.createQuery(anyString(), eq(Long.class))).thenReturn(longQuery);
        when(longQuery.setParameter("beneficioId", 1L)).thenReturn(longQuery);
        when(longQuery.getSingleResult()).thenReturn(2L);

        Long result = transferenciaRepository.countByBeneficio(1L);

        assertEquals(2L, result);
        verify(em).createQuery("SELECT COUNT(t) FROM Transferencia t WHERE t.beneficioOrigem.id = :beneficioId OR t.beneficioDestino.id = :beneficioId", Long.class);
        verify(longQuery).setParameter("beneficioId", 1L);
        verify(longQuery).getSingleResult();
    }

    @Test
    void testHasTransferencias_True() {
        when(em.createQuery(anyString(), eq(Long.class))).thenReturn(longQuery);
        when(longQuery.setParameter("beneficioId", 1L)).thenReturn(longQuery);
        when(longQuery.getSingleResult()).thenReturn(2L);

        boolean result = transferenciaRepository.hasTransferencias(1L);

        assertTrue(result);
        verify(em).createQuery("SELECT COUNT(t) FROM Transferencia t WHERE t.beneficioOrigem.id = :beneficioId OR t.beneficioDestino.id = :beneficioId", Long.class);
        verify(longQuery).setParameter("beneficioId", 1L);
        verify(longQuery).getSingleResult();
    }

    @Test
    void testHasTransferencias_False() {
        when(em.createQuery(anyString(), eq(Long.class))).thenReturn(longQuery);
        when(longQuery.setParameter("beneficioId", 1L)).thenReturn(longQuery);
        when(longQuery.getSingleResult()).thenReturn(0L);

        boolean result = transferenciaRepository.hasTransferencias(1L);

        assertFalse(result);
        verify(em).createQuery("SELECT COUNT(t) FROM Transferencia t WHERE t.beneficioOrigem.id = :beneficioId OR t.beneficioDestino.id = :beneficioId", Long.class);
        verify(longQuery).setParameter("beneficioId", 1L);
        verify(longQuery).getSingleResult();
    }
}
