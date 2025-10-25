package com.beneficio.ejb.constants;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para classe Messages.
 * Valida constantes e comportamento da classe utilitária.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
class MessagesTest {

    @Test
    void testMessagesClassCannotBeInstantiated() {
        try {
            Constructor<Messages> constructor = Messages.class.getDeclaredConstructor();
            assertTrue(Modifier.isPrivate(constructor.getModifiers()));
            
            constructor.setAccessible(true);
            assertThrows(Exception.class, constructor::newInstance);
        } catch (NoSuchMethodException e) {
            fail("Constructor should exist");
        }
    }

    @Test
    void testValidationMessages() {
        assertNotNull(Messages.PARAMETROS_NAO_PODEM_SER_NULOS);
        assertNotNull(Messages.BENEFICIO_ORIGEM_DESTINO_IGUAIS);
        assertNotNull(Messages.VALOR_DEVE_SER_POSITIVO);
        assertNotNull(Messages.NOME_OBRIGATORIO);
        assertNotNull(Messages.VALOR_OBRIGATORIO);
        assertNotNull(Messages.NOME_MAXIMO_CARACTERES);
        assertNotNull(Messages.DESCRICAO_MAXIMO_CARACTERES);
        assertNotNull(Messages.VALOR_NAO_PODE_SER_NEGATIVO);
        assertNotNull(Messages.VALOR_EXCEDE_LIMITE);
    }

    @Test
    void testBusinessMessages() {
        assertNotNull(Messages.BENEFICIO_NAO_ENCONTRADO);
        assertNotNull(Messages.BENEFICIO_ORIGEM_NAO_ENCONTRADO);
        assertNotNull(Messages.BENEFICIO_DESTINO_NAO_ENCONTRADO);
        assertNotNull(Messages.BENEFICIO_ORIGEM_INATIVO);
        assertNotNull(Messages.BENEFICIO_DESTINO_INATIVO);
        assertNotNull(Messages.SALDO_INSUFICIENTE);
        assertNotNull(Messages.NAO_PODE_EXCLUIR_COM_TRANSFERENCIAS);
    }

    @Test
    void testMessageContent() {
        assertEquals("Parâmetros não podem ser nulos", Messages.PARAMETROS_NAO_PODEM_SER_NULOS);
        assertEquals("Benefício origem e destino não podem ser iguais", Messages.BENEFICIO_ORIGEM_DESTINO_IGUAIS);
        assertEquals("Valor deve ser positivo", Messages.VALOR_DEVE_SER_POSITIVO);
        assertEquals("Nome do benefício é obrigatório", Messages.NOME_OBRIGATORIO);
        assertEquals("Valor é obrigatório", Messages.VALOR_OBRIGATORIO);
    }

    @Test
    void testMessageFormatting() {
        String mensagemFormatada = String.format(Messages.BENEFICIO_NAO_ENCONTRADO, 123L);
        assertEquals("Benefício não encontrado com ID: 123", mensagemFormatada);

        String mensagemSaldo = String.format(Messages.SALDO_INSUFICIENTE, "100.00", "200.00");
        assertEquals("Saldo insuficiente. Saldo atual: 100.00, Valor solicitado: 200.00", mensagemSaldo);
    }

    @Test
    void testAllMessagesAreNotEmpty() {
        assertFalse(Messages.PARAMETROS_NAO_PODEM_SER_NULOS.isEmpty());
        assertFalse(Messages.BENEFICIO_ORIGEM_DESTINO_IGUAIS.isEmpty());
        assertFalse(Messages.VALOR_DEVE_SER_POSITIVO.isEmpty());
        assertFalse(Messages.NOME_OBRIGATORIO.isEmpty());
        assertFalse(Messages.VALOR_OBRIGATORIO.isEmpty());
        assertFalse(Messages.NOME_MAXIMO_CARACTERES.isEmpty());
        assertFalse(Messages.DESCRICAO_MAXIMO_CARACTERES.isEmpty());
        assertFalse(Messages.VALOR_NAO_PODE_SER_NEGATIVO.isEmpty());
        assertFalse(Messages.VALOR_EXCEDE_LIMITE.isEmpty());
        assertFalse(Messages.BENEFICIO_NAO_ENCONTRADO.isEmpty());
        assertFalse(Messages.BENEFICIO_ORIGEM_NAO_ENCONTRADO.isEmpty());
        assertFalse(Messages.BENEFICIO_DESTINO_NAO_ENCONTRADO.isEmpty());
        assertFalse(Messages.BENEFICIO_ORIGEM_INATIVO.isEmpty());
        assertFalse(Messages.BENEFICIO_DESTINO_INATIVO.isEmpty());
        assertFalse(Messages.SALDO_INSUFICIENTE.isEmpty());
        assertFalse(Messages.NAO_PODE_EXCLUIR_COM_TRANSFERENCIAS.isEmpty());
    }

    @Test
    void testMessagesAreInPortuguese() {
        assertTrue(Messages.PARAMETROS_NAO_PODEM_SER_NULOS.contains("não"));
        assertTrue(Messages.BENEFICIO_ORIGEM_DESTINO_IGUAIS.contains("Benefício"));
        assertTrue(Messages.VALOR_DEVE_SER_POSITIVO.contains("deve"));
        assertTrue(Messages.NOME_OBRIGATORIO.contains("obrigatório"));
        assertTrue(Messages.VALOR_OBRIGATORIO.contains("obrigatório"));
    }

    @Test
    void testMessagesHaveConsistentFormat() {
        // Verifica se as mensagens que precisam de formatação têm o padrão correto
        assertTrue(Messages.BENEFICIO_NAO_ENCONTRADO.contains("%d"));
        assertTrue(Messages.BENEFICIO_ORIGEM_NAO_ENCONTRADO.contains("%d"));
        assertTrue(Messages.BENEFICIO_DESTINO_NAO_ENCONTRADO.contains("%d"));
        assertTrue(Messages.SALDO_INSUFICIENTE.contains("%s"));
    }
}
