package com.beneficio.ejb.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para entidade Beneficio.
 * Valida construtores, getters, setters e comportamentos.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
class BeneficioTest {

    private Beneficio beneficio;

    @BeforeEach
    void setUp() {
        beneficio = new Beneficio();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(beneficio);
        assertNull(beneficio.getId());
        assertNull(beneficio.getNome());
        assertNull(beneficio.getDescricao());
        assertNull(beneficio.getValor());
        assertTrue(beneficio.getAtivo());
        assertNull(beneficio.getVersion());
    }

    @Test
    void testParameterizedConstructor() {
        String nome = "Benefício Teste";
        String descricao = "Descrição do benefício";
        BigDecimal valor = new BigDecimal("1000.00");

        Beneficio beneficioCompleto = new Beneficio(nome, descricao, valor);

        assertEquals(nome, beneficioCompleto.getNome());
        assertEquals(descricao, beneficioCompleto.getDescricao());
        assertEquals(valor, beneficioCompleto.getValor());
        assertTrue(beneficioCompleto.getAtivo());
    }

    @Test
    void testSettersAndGetters() {
        Long id = 1L;
        String nome = "Benefício Teste";
        String descricao = "Descrição do benefício";
        BigDecimal valor = new BigDecimal("1000.00");
        Boolean ativo = true;
        Long version = 1L;

        beneficio.setId(id);
        beneficio.setNome(nome);
        beneficio.setDescricao(descricao);
        beneficio.setValor(valor);
        beneficio.setAtivo(ativo);
        beneficio.setVersion(version);

        assertEquals(id, beneficio.getId());
        assertEquals(nome, beneficio.getNome());
        assertEquals(descricao, beneficio.getDescricao());
        assertEquals(valor, beneficio.getValor());
        assertEquals(ativo, beneficio.getAtivo());
        assertEquals(version, beneficio.getVersion());
    }

    @Test
    void testEqualsAndHashCode() {
        Beneficio beneficio1 = new Beneficio("Benefício A", "Descrição A", new BigDecimal("1000.00"));
        Beneficio beneficio2 = new Beneficio("Benefício A", "Descrição A", new BigDecimal("1000.00"));
        Beneficio beneficio3 = new Beneficio("Benefício B", "Descrição B", new BigDecimal("500.00"));

        // Teste de igualdade
        assertEquals(beneficio1, beneficio2);
        assertNotEquals(beneficio1, beneficio3);

        // Teste de hashCode
        assertEquals(beneficio1.hashCode(), beneficio2.hashCode());
        assertNotEquals(beneficio1.hashCode(), beneficio3.hashCode());
    }

    @Test
    void testToString() {
        beneficio.setId(1L);
        beneficio.setNome("Benefício Teste");
        beneficio.setDescricao("Descrição do benefício");
        beneficio.setValor(new BigDecimal("1000.00"));
        beneficio.setAtivo(true);

        String toString = beneficio.toString();

        assertNotNull(toString);
        assertTrue(toString.contains("Benefício Teste"));
        assertTrue(toString.contains("1000.00"));
    }

    @Test
    void testAllArgsConstructor() {
        Long id = 1L;
        String nome = "Benefício Teste";
        String descricao = "Descrição do benefício";
        BigDecimal valor = new BigDecimal("1000.00");
        Boolean ativo = true;
        Long version = 1L;

        Beneficio beneficioCompleto = new Beneficio(id, nome, descricao, valor, ativo, version);

        assertEquals(id, beneficioCompleto.getId());
        assertEquals(nome, beneficioCompleto.getNome());
        assertEquals(descricao, beneficioCompleto.getDescricao());
        assertEquals(valor, beneficioCompleto.getValor());
        assertEquals(ativo, beneficioCompleto.getAtivo());
        assertEquals(version, beneficioCompleto.getVersion());
    }

    @Test
    void testDefaultAtivoValue() {
        Beneficio novoBeneficio = new Beneficio("Novo Benefício", "Nova Descrição", new BigDecimal("500.00"));
        
        assertTrue(novoBeneficio.getAtivo());
    }

    @Test
    void testValorPrecision() {
        BigDecimal valorComPrecisao = new BigDecimal("1234.56");
        beneficio.setValor(valorComPrecisao);

        assertEquals(valorComPrecisao, beneficio.getValor());
        assertEquals(2, beneficio.getValor().scale());
    }

    @Test
    void testVersionIncrement() {
        Long versionInicial = 0L;
        Long versionAtualizada = 1L;

        beneficio.setVersion(versionInicial);
        assertEquals(versionInicial, beneficio.getVersion());

        beneficio.setVersion(versionAtualizada);
        assertEquals(versionAtualizada, beneficio.getVersion());
    }
}
