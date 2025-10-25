package com.beneficio.ejb;

import com.beneficio.ejb.entity.Beneficio;
import com.beneficio.ejb.entity.Transferencia;
import com.beneficio.ejb.service.BeneficioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para BeneficioEjb.
 * Valida o facade EJB e delegação para o service.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class BeneficioEjbTest {

    @Mock
    private BeneficioService beneficioService;

    @InjectMocks
    private BeneficioEjb beneficioEjb;

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
        when(beneficioService.findAll()).thenReturn(beneficios);

        List<Beneficio> result = beneficioEjb.findAll();

        assertEquals(2, result.size());
        assertEquals("Benefício A", result.get(0).getNome());
        assertEquals("Benefício B", result.get(1).getNome());
        verify(beneficioService).findAll();
    }

    @Test
    void testFindById_Existing() {
        when(beneficioService.findById(1L)).thenReturn(Optional.of(beneficio1));

        Beneficio result = beneficioEjb.findById(1L);

        assertNotNull(result);
        assertEquals("Benefício A", result.getNome());
        verify(beneficioService).findById(1L);
    }

    @Test
    void testFindById_NotExisting() {
        when(beneficioService.findById(999L)).thenReturn(Optional.empty());

        Beneficio result = beneficioEjb.findById(999L);

        assertNull(result);
        verify(beneficioService).findById(999L);
    }

    @Test
    void testSave() {
        Beneficio novoBeneficio = new Beneficio("Novo Benefício", "Nova Descrição", new BigDecimal("750.00"));
        when(beneficioService.save(novoBeneficio)).thenReturn(novoBeneficio);

        Beneficio result = beneficioEjb.save(novoBeneficio);

        assertNotNull(result);
        assertEquals("Novo Benefício", result.getNome());
        verify(beneficioService).save(novoBeneficio);
    }

    @Test
    void testUpdate() {
        Beneficio beneficioAtualizado = new Beneficio("Benefício Atualizado", "Nova Descrição", new BigDecimal("1200.00"));
        when(beneficioService.update(1L, beneficioAtualizado)).thenReturn(beneficioAtualizado);

        Beneficio result = beneficioEjb.update(1L, beneficioAtualizado);

        assertNotNull(result);
        assertEquals("Benefício Atualizado", result.getNome());
        verify(beneficioService).update(1L, beneficioAtualizado);
    }

    @Test
    void testDeleteById() {
        doNothing().when(beneficioService).deleteById(1L);

        beneficioEjb.deleteById(1L);

        verify(beneficioService).deleteById(1L);
    }

    @Test
    void testFindAtivos() {
        when(beneficioService.findAtivos()).thenReturn(beneficios);

        List<Beneficio> result = beneficioEjb.findAtivos();

        assertEquals(2, result.size());
        verify(beneficioService).findAtivos();
    }

    @Test
    void testSearchByNome() {
        when(beneficioService.searchByNome("Benefício")).thenReturn(beneficios);

        List<Beneficio> result = beneficioEjb.searchByNome("Benefício");

        assertEquals(2, result.size());
        verify(beneficioService).searchByNome("Benefício");
    }

    @Test
    void testTransfer() {
        doNothing().when(beneficioService).transferir(1L, 2L, new BigDecimal("100.00"));

        beneficioEjb.transfer(1L, 2L, new BigDecimal("100.00"));

        verify(beneficioService).transferir(1L, 2L, new BigDecimal("100.00"));
    }

    @Test
    void testGetHistoricoTransferencias() {
        List<Transferencia> transferencias = Arrays.asList(
            new Transferencia(beneficio1, beneficio2, new BigDecimal("100.00"))
        );
        when(beneficioService.getHistoricoTransferencias()).thenReturn(transferencias);

        List<Transferencia> result = beneficioEjb.getHistoricoTransferencias();

        assertEquals(1, result.size());
        verify(beneficioService).getHistoricoTransferencias();
    }

    @Test
    void testGetTransferenciasByBeneficio() {
        List<Transferencia> transferencias = Arrays.asList(
            new Transferencia(beneficio1, beneficio2, new BigDecimal("100.00"))
        );
        when(beneficioService.getTransferenciasByBeneficio(1L)).thenReturn(transferencias);

        List<Transferencia> result = beneficioEjb.getTransferenciasByBeneficio(1L);

        assertEquals(1, result.size());
        verify(beneficioService).getTransferenciasByBeneficio(1L);
    }
}
