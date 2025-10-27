package com.beneficio.ejb.service;

import com.beneficio.ejb.entity.Beneficio;
import com.beneficio.ejb.entity.Transferencia;
import com.beneficio.ejb.repository.BeneficioRepository;
import com.beneficio.ejb.repository.TransferenciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.PersistenceException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para BeneficioService.
 * Valida a lógica de negócio e operações de transferência.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class BeneficioServiceTest {

    @Mock
    private BeneficioRepository beneficioRepository;

    @Mock
    private TransferenciaRepository transferenciaRepository;

    @InjectMocks
    private BeneficioService beneficioService;

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
        when(beneficioRepository.findAll()).thenReturn(beneficios);

        List<Beneficio> result = beneficioService.findAll();

        assertEquals(2, result.size());
        assertEquals("Benefício A", result.get(0).getNome());
        assertEquals("Benefício B", result.get(1).getNome());
        verify(beneficioRepository).findAll();
    }

    @Test
    void testFindById_Existing() {
        when(beneficioRepository.findById(1L)).thenReturn(beneficio1);

        Optional<Beneficio> result = beneficioService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Benefício A", result.get().getNome());
        verify(beneficioRepository).findById(1L);
    }

    @Test
    void testFindById_NotExisting() {
        when(beneficioRepository.findById(999L)).thenReturn(null);

        Optional<Beneficio> result = beneficioService.findById(999L);

        assertFalse(result.isPresent());
        verify(beneficioRepository).findById(999L);
    }

    @Test
    void testSave_NewBeneficio() {
        Beneficio novoBeneficio = new Beneficio("Novo Benefício", "Nova Descrição", new BigDecimal("750.00"));
        when(beneficioRepository.save(any(Beneficio.class))).thenReturn(novoBeneficio);

        Beneficio result = beneficioService.save(novoBeneficio);

        assertNotNull(result);
        assertEquals("Novo Benefício", result.getNome());
        verify(beneficioRepository).save(novoBeneficio);
    }

    @Test
    void testSave_InvalidBeneficio() {
        Beneficio beneficioInvalido = new Beneficio("", "Descrição", new BigDecimal("100.00"));

        assertThrows(IllegalArgumentException.class, () -> {
            beneficioService.save(beneficioInvalido);
        });
    }

    @Test
    void testUpdate_Existing() {
        when(beneficioRepository.findById(1L)).thenReturn(beneficio1);
        when(beneficioRepository.save(any(Beneficio.class))).thenReturn(beneficio1);

        Beneficio beneficioAtualizado = new Beneficio("Benefício Atualizado", "Nova Descrição", new BigDecimal("1200.00"));
        Beneficio result = beneficioService.update(1L, beneficioAtualizado);

        assertNotNull(result);
        verify(beneficioRepository).findById(1L);
        verify(beneficioRepository).save(any(Beneficio.class));
    }

    @Test
    void testUpdate_NotExisting() {
        when(beneficioRepository.findById(999L)).thenReturn(null);

        Beneficio beneficioAtualizado = new Beneficio("Benefício Atualizado", "Nova Descrição", new BigDecimal("1200.00"));

        assertThrows(IllegalArgumentException.class, () -> {
            beneficioService.update(999L, beneficioAtualizado);
        });
    }

    @Test
    void testDeleteById_Existing() {
        when(beneficioRepository.findById(1L)).thenReturn(beneficio1);
        when(transferenciaRepository.hasTransferencias(1L)).thenReturn(false);

        beneficioService.deleteById(1L);

        verify(beneficioRepository).findById(1L);
        verify(transferenciaRepository).hasTransferencias(1L);
        verify(beneficioRepository).deleteById(1L);
    }

    @Test
    void testDeleteById_NotExisting() {
        when(beneficioRepository.findById(999L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            beneficioService.deleteById(999L);
        });
    }

    @Test
    void testDeleteById_WithTransferencias() {
        when(beneficioRepository.findById(1L)).thenReturn(beneficio1);
        when(transferenciaRepository.hasTransferencias(1L)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> {
            beneficioService.deleteById(1L);
        });
    }

    @Test
    void testFindAtivos() {
        when(beneficioRepository.findByAtivoTrue()).thenReturn(beneficios);

        List<Beneficio> result = beneficioService.findAtivos();

        assertEquals(2, result.size());
        verify(beneficioRepository).findByAtivoTrue();
    }

    @Test
    void testSearchByNome() {
        when(beneficioRepository.findByNomeContaining("Benefício")).thenReturn(beneficios);

        List<Beneficio> result = beneficioService.searchByNome("Benefício");

        assertEquals(2, result.size());
        verify(beneficioRepository).findByNomeContaining("Benefício");
    }

    @Test
    void testTransferir_Success() {
        when(beneficioRepository.findByIdForUpdate(1L)).thenReturn(beneficio1);
        when(beneficioRepository.findByIdForUpdate(2L)).thenReturn(beneficio2);
        when(beneficioRepository.save(any(Beneficio.class))).thenReturn(beneficio1);
        when(transferenciaRepository.save(any(Transferencia.class))).thenReturn(new Transferencia());

        beneficioService.transferir(1L, 2L, new BigDecimal("100.00"));

        verify(beneficioRepository).findByIdForUpdate(1L);
        verify(beneficioRepository).findByIdForUpdate(2L);
        verify(beneficioRepository, times(2)).save(any(Beneficio.class));
        verify(transferenciaRepository).save(any(Transferencia.class));
    }

    @Test
    void testTransferir_InvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> {
            beneficioService.transferir(null, 2L, new BigDecimal("100.00"));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            beneficioService.transferir(1L, null, new BigDecimal("100.00"));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            beneficioService.transferir(1L, 2L, null);
        });
    }

    @Test
    void testTransferir_SameBeneficio() {
        assertThrows(IllegalArgumentException.class, () -> {
            beneficioService.transferir(1L, 1L, new BigDecimal("100.00"));
        });
    }

    @Test
    void testTransferir_NegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            beneficioService.transferir(1L, 2L, new BigDecimal("-100.00"));
        });
    }

    @Test
    void testTransferir_ZeroAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            beneficioService.transferir(1L, 2L, BigDecimal.ZERO);
        });
    }

    @Test
    void testTransferir_BeneficioOrigemNotFound() {
        when(beneficioRepository.findByIdForUpdate(999L)).thenReturn(null);
        when(beneficioRepository.findByIdForUpdate(2L)).thenReturn(beneficio2);

        assertThrows(IllegalArgumentException.class, () -> {
            beneficioService.transferir(999L, 2L, new BigDecimal("100.00"));
        });
    }

    @Test
    void testTransferir_BeneficioDestinoNotFound() {
        when(beneficioRepository.findByIdForUpdate(1L)).thenReturn(beneficio1);
        when(beneficioRepository.findByIdForUpdate(999L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            beneficioService.transferir(1L, 999L, new BigDecimal("100.00"));
        });
    }

    @Test
    void testTransferir_BeneficioOrigemInativo() {
        beneficio1.setAtivo(false);
        when(beneficioRepository.findByIdForUpdate(1L)).thenReturn(beneficio1);
        when(beneficioRepository.findByIdForUpdate(2L)).thenReturn(beneficio2);

        assertThrows(IllegalStateException.class, () -> {
            beneficioService.transferir(1L, 2L, new BigDecimal("100.00"));
        });
    }

    @Test
    void testTransferir_BeneficioDestinoInativo() {
        beneficio2.setAtivo(false);
        when(beneficioRepository.findByIdForUpdate(1L)).thenReturn(beneficio1);
        when(beneficioRepository.findByIdForUpdate(2L)).thenReturn(beneficio2);

        assertThrows(IllegalStateException.class, () -> {
            beneficioService.transferir(1L, 2L, new BigDecimal("100.00"));
        });
    }

    @Test
    void testTransferir_InsufficientBalance() {
        when(beneficioRepository.findByIdForUpdate(1L)).thenReturn(beneficio1);
        when(beneficioRepository.findByIdForUpdate(2L)).thenReturn(beneficio2);

        assertThrows(IllegalStateException.class, () -> {
            beneficioService.transferir(1L, 2L, new BigDecimal("2000.00"));
        });
    }

    @Test
    void testTransferir_PersistenceException() {
        when(beneficioRepository.findByIdForUpdate(1L)).thenReturn(beneficio1);
        when(beneficioRepository.findByIdForUpdate(2L)).thenReturn(beneficio2);
        when(beneficioRepository.save(any(Beneficio.class))).thenThrow(new PersistenceException("Erro de persistência"));

        assertThrows(IllegalStateException.class, () -> {
            beneficioService.transferir(1L, 2L, new BigDecimal("100.00"));
        });
    }

    @Test
    void testGetHistoricoTransferencias() {
        List<Transferencia> transferencias = new ArrayList<>();
        when(transferenciaRepository.findAll()).thenReturn(transferencias);

        List<Transferencia> result = beneficioService.getHistoricoTransferencias();

        assertNotNull(result);
        verify(transferenciaRepository).findAll();
    }

    @Test
    void testGetTransferenciasByBeneficio() {
        List<Transferencia> transferenciasOrigem = new ArrayList<>();
        List<Transferencia> transferenciasDestino = new ArrayList<>();
        
        when(transferenciaRepository.findByBeneficioOrigem(1L)).thenReturn(transferenciasOrigem);
        when(transferenciaRepository.findByBeneficioDestino(1L)).thenReturn(transferenciasDestino);

        List<Transferencia> result = beneficioService.getTransferenciasByBeneficio(1L);

        assertNotNull(result);
        verify(transferenciaRepository).findByBeneficioOrigem(1L);
        verify(transferenciaRepository).findByBeneficioDestino(1L);
    }
}
