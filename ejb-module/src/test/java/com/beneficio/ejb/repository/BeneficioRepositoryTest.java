package com.beneficio.ejb.repository;

import com.beneficio.ejb.entity.Beneficio;
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
 * Testes unitários para BeneficioRepository.
 * Valida operações de persistência e consultas.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class BeneficioRepositoryTest {

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<Beneficio> query;

    @InjectMocks
    private BeneficioRepository beneficioRepository;

    private Beneficio beneficio1;
    private Beneficio beneficio2;
    private List<Beneficio> beneficios;

    @BeforeEach
    void setUp() {
        beneficio1 = new Beneficio("Benefício A", "Descrição A", new BigDecimal("1000.00"));
        beneficio1.setId(1L);
        beneficio1.setAtivo(true);

        beneficio2 = new Beneficio("Benefício B", "Descrição B", new BigDecimal("500.00"));
        beneficio2.setId(2L);
        beneficio2.setAtivo(true);

        beneficios = Arrays.asList(beneficio1, beneficio2);
    }

    @Test
    void testFindAll() {
        when(em.createQuery(anyString(), eq(Beneficio.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(beneficios);

        List<Beneficio> result = beneficioRepository.findAll();

        assertEquals(2, result.size());
        assertEquals("Benefício A", result.get(0).getNome());
        assertEquals("Benefício B", result.get(1).getNome());
        verify(em).createQuery("SELECT b FROM Beneficio b", Beneficio.class);
        verify(query).getResultList();
    }

    @Test
    void testFindById() {
        when(em.find(Beneficio.class, 1L)).thenReturn(beneficio1);

        Beneficio result = beneficioRepository.findById(1L);

        assertNotNull(result);
        assertEquals("Benefício A", result.getNome());
        verify(em).find(Beneficio.class, 1L);
    }

    @Test
    void testFindById_NotExisting() {
        when(em.find(Beneficio.class, 999L)).thenReturn(null);

        Beneficio result = beneficioRepository.findById(999L);

        assertNull(result);
        verify(em).find(Beneficio.class, 999L);
    }

    @Test
    void testSave_NewBeneficio() {
        Beneficio novoBeneficio = new Beneficio("Novo Benefício", "Nova Descrição", new BigDecimal("750.00"));

        Beneficio result = beneficioRepository.save(novoBeneficio);

        assertNotNull(result);
        verify(em).persist(novoBeneficio);
    }

    @Test
    void testSave_ExistingBeneficio() {
        beneficio1.setNome("Benefício Atualizado");
        when(em.merge(beneficio1)).thenReturn(beneficio1);

        Beneficio result = beneficioRepository.save(beneficio1);

        assertNotNull(result);
        assertEquals("Benefício Atualizado", result.getNome());
        verify(em).merge(beneficio1);
    }

    @Test
    void testDeleteById() {
        when(em.find(Beneficio.class, 1L)).thenReturn(beneficio1);

        beneficioRepository.deleteById(1L);

        verify(em).find(Beneficio.class, 1L);
        verify(em).remove(beneficio1);
    }

    @Test
    void testFindByAtivoTrue() {
        when(em.createQuery(anyString(), eq(Beneficio.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(beneficios);

        List<Beneficio> result = beneficioRepository.findByAtivoTrue();

        assertEquals(2, result.size());
        verify(em).createQuery("SELECT b FROM Beneficio b WHERE b.ativo = true", Beneficio.class);
        verify(query).getResultList();
    }

    @Test
    void testFindByNomeContaining() {
        when(em.createQuery(anyString(), eq(Beneficio.class))).thenReturn(query);
        when(query.setParameter("nome", "%Benefício%")).thenReturn(query);
        when(query.getResultList()).thenReturn(beneficios);

        List<Beneficio> result = beneficioRepository.findByNomeContaining("Benefício");

        assertEquals(2, result.size());
        verify(em).createQuery("SELECT b FROM Beneficio b WHERE b.nome LIKE :nome", Beneficio.class);
        verify(query).setParameter("nome", "%Benefício%");
        verify(query).getResultList();
    }
}
