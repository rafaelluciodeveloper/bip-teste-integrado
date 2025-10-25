package com.beneficio.ejb.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para entidade Transferencia.
 * Valida construtores, getters, setters e comportamentos.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
class TransferenciaTest {

    private Beneficio beneficioOrigem;
    private Beneficio beneficioDestino;
    private Transferencia transferencia;

    @BeforeEach
    void setUp() {
        beneficioOrigem = new Beneficio("Benefício Origem", "Descrição Origem", new BigDecimal("1000.00"));
        beneficioOrigem.setId(1L);

        beneficioDestino = new Beneficio("Benefício Destino", "Descrição Destino", new BigDecimal("500.00"));
        beneficioDestino.setId(2L);

        transferencia = new Transferencia();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(transferencia);
        assertNull(transferencia.getId());
        assertNull(transferencia.getBeneficioOrigem());
        assertNull(transferencia.getBeneficioDestino());
        assertNull(transferencia.getValor());
        assertNull(transferencia.getDataTransferencia());
        assertNull(transferencia.getObservacoes());
    }

    @Test
    void testParameterizedConstructor() {
        BigDecimal valor = new BigDecimal("100.00");
        Transferencia transferenciaCompleta = new Transferencia(beneficioOrigem, beneficioDestino, valor);

        assertEquals(beneficioOrigem, transferenciaCompleta.getBeneficioOrigem());
        assertEquals(beneficioDestino, transferenciaCompleta.getBeneficioDestino());
        assertEquals(valor, transferenciaCompleta.getValor());
        assertNotNull(transferenciaCompleta.getDataTransferencia());
    }

    @Test
    void testSettersAndGetters() {
        Long id = 1L;
        BigDecimal valor = new BigDecimal("200.00");
        LocalDateTime dataTransferencia = LocalDateTime.now();
        String observacoes = "Transferência de teste";

        transferencia.setId(id);
        transferencia.setBeneficioOrigem(beneficioOrigem);
        transferencia.setBeneficioDestino(beneficioDestino);
        transferencia.setValor(valor);
        transferencia.setDataTransferencia(dataTransferencia);
        transferencia.setObservacoes(observacoes);

        assertEquals(id, transferencia.getId());
        assertEquals(beneficioOrigem, transferencia.getBeneficioOrigem());
        assertEquals(beneficioDestino, transferencia.getBeneficioDestino());
        assertEquals(valor, transferencia.getValor());
        assertEquals(dataTransferencia, transferencia.getDataTransferencia());
        assertEquals(observacoes, transferencia.getObservacoes());
    }

    @Test
    void testAllArgsConstructor() {
        Long id = 1L;
        BigDecimal valor = new BigDecimal("300.00");
        LocalDateTime dataTransferencia = LocalDateTime.now();
        String observacoes = "Transferência completa";

        Transferencia transferenciaCompleta = new Transferencia(
            id, beneficioOrigem, beneficioDestino, valor, dataTransferencia, observacoes
        );

        assertEquals(id, transferenciaCompleta.getId());
        assertEquals(beneficioOrigem, transferenciaCompleta.getBeneficioOrigem());
        assertEquals(beneficioDestino, transferenciaCompleta.getBeneficioDestino());
        assertEquals(valor, transferenciaCompleta.getValor());
        assertEquals(dataTransferencia, transferenciaCompleta.getDataTransferencia());
        assertEquals(observacoes, transferenciaCompleta.getObservacoes());
    }

    @Test
    void testDataTransferenciaAutomatica() {
        Transferencia transferenciaNova = new Transferencia(beneficioOrigem, beneficioDestino, new BigDecimal("100.00"));

        assertNotNull(transferenciaNova.getDataTransferencia());
        assertTrue(transferenciaNova.getDataTransferencia().isBefore(LocalDateTime.now().plusSeconds(1)));
        assertTrue(transferenciaNova.getDataTransferencia().isAfter(LocalDateTime.now().minusSeconds(1)));
    }

    @Test
    void testEqualsAndHashCode() {
        Transferencia transferencia1 = new Transferencia(beneficioOrigem, beneficioDestino, new BigDecimal("100.00"));
        Transferencia transferencia2 = new Transferencia(beneficioOrigem, beneficioDestino, new BigDecimal("100.00"));
        Transferencia transferencia3 = new Transferencia(beneficioDestino, beneficioOrigem, new BigDecimal("200.00"));

        // Teste de igualdade
        assertEquals(transferencia1, transferencia2);
        assertNotEquals(transferencia1, transferencia3);

        // Teste de hashCode
        assertEquals(transferencia1.hashCode(), transferencia2.hashCode());
        assertNotEquals(transferencia1.hashCode(), transferencia3.hashCode());
    }

    @Test
    void testToString() {
        transferencia.setId(1L);
        transferencia.setBeneficioOrigem(beneficioOrigem);
        transferencia.setBeneficioDestino(beneficioDestino);
        transferencia.setValor(new BigDecimal("100.00"));
        transferencia.setDataTransferencia(LocalDateTime.now());
        transferencia.setObservacoes("Transferência de teste");

        String toString = transferencia.toString();

        assertNotNull(toString);
        assertTrue(toString.contains("100.00"));
    }

    @Test
    void testValorPrecision() {
        BigDecimal valorComPrecisao = new BigDecimal("1234.56");
        transferencia.setValor(valorComPrecisao);

        assertEquals(valorComPrecisao, transferencia.getValor());
        assertEquals(2, transferencia.getValor().scale());
    }

    @Test
    void testObservacoesOpcionais() {
        Transferencia transferenciaSemObservacoes = new Transferencia(beneficioOrigem, beneficioDestino, new BigDecimal("100.00"));

        assertNull(transferenciaSemObservacoes.getObservacoes());

        transferenciaSemObservacoes.setObservacoes("Observação adicionada");
        assertEquals("Observação adicionada", transferenciaSemObservacoes.getObservacoes());
    }

    @Test
    void testRelacionamentos() {
        transferencia.setBeneficioOrigem(beneficioOrigem);
        transferencia.setBeneficioDestino(beneficioDestino);

        assertEquals(beneficioOrigem, transferencia.getBeneficioOrigem());
        assertEquals(beneficioDestino, transferencia.getBeneficioDestino());
        assertEquals(1L, transferencia.getBeneficioOrigem().getId());
        assertEquals(2L, transferencia.getBeneficioDestino().getId());
    }
}
