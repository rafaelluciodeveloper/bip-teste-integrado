package com.beneficio.backend;

import com.beneficio.backend.dto.TransferenciaRequest;
import com.beneficio.ejb.BeneficioEjbLocal;
import com.beneficio.ejb.entity.Beneficio;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes unitários para o BeneficioController.
 * Testa todos os endpoints REST relacionados aos benefícios.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@WebMvcTest(com.beneficio.backend.controller.BeneficioController.class)
class BeneficioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeneficioEjbLocal beneficioEjb;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Testa o endpoint de listagem de benefícios.
     * 
     * @throws Exception se houver erro durante o teste
     */
    @Test
    void testListBeneficios() throws Exception {
        List<Beneficio> beneficios = Arrays.asList(
                new Beneficio("Beneficio A", "Descrição A", new BigDecimal("1000.00")),
                new Beneficio("Beneficio B", "Descrição B", new BigDecimal("500.00"))
        );
        
        when(beneficioEjb.findAll()).thenReturn(beneficios);

        mockMvc.perform(get("/beneficios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nome").value("Beneficio A"))
                .andExpect(jsonPath("$[1].nome").value("Beneficio B"));
    }

    /**
     * Testa o endpoint de busca de benefício por ID com sucesso.
     * 
     * @throws Exception se houver erro durante o teste
     */
    @Test
    void testGetBeneficioById() throws Exception {
        Beneficio beneficio = new Beneficio("Beneficio A", "Descrição A", new BigDecimal("1000.00"));
        beneficio.setId(1L);
        
        when(beneficioEjb.findById(1L)).thenReturn(beneficio);

        mockMvc.perform(get("/beneficios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Beneficio A"));
    }

    /**
     * Testa o endpoint de busca de benefício por ID quando não encontrado.
     * 
     * @throws Exception se houver erro durante o teste
     */
    @Test
    void testGetBeneficioByIdNotFound() throws Exception {
        when(beneficioEjb.findById(999L)).thenReturn(null);

        mockMvc.perform(get("/beneficios/999"))
                .andExpect(status().isNotFound());
    }

    /**
     * Testa o endpoint de criação de benefício.
     * 
     * @throws Exception se houver erro durante o teste
     */
    @Test
    void testCreateBeneficio() throws Exception {
        Beneficio beneficio = new Beneficio("Novo Beneficio", "Nova Descrição", new BigDecimal("750.00"));
        beneficio.setId(1L);
        
        when(beneficioEjb.save(any(Beneficio.class))).thenReturn(beneficio);

        mockMvc.perform(post("/beneficios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beneficio)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Novo Beneficio"));
    }

    /**
     * Testa o endpoint de atualização de benefício.
     * 
     * @throws Exception se houver erro durante o teste
     */
    @Test
    void testUpdateBeneficio() throws Exception {
        Beneficio beneficio = new Beneficio("Beneficio Atualizado", "Descrição Atualizada", new BigDecimal("1200.00"));
        beneficio.setId(1L);
        
        when(beneficioEjb.update(eq(1L), any(Beneficio.class))).thenReturn(beneficio);

        mockMvc.perform(put("/beneficios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beneficio)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Beneficio Atualizado"));
    }

    /**
     * Testa o endpoint de remoção de benefício.
     * 
     * @throws Exception se houver erro durante o teste
     */
    @Test
    void testDeleteBeneficio() throws Exception {
        doNothing().when(beneficioEjb).deleteById(1L);

        mockMvc.perform(delete("/beneficios/1"))
                .andExpect(status().isNoContent());

        verify(beneficioEjb).deleteById(1L);
    }

    /**
     * Testa o endpoint de transferência com sucesso.
     * 
     * @throws Exception se houver erro durante o teste
     */
    @Test
    void testTransferSuccess() throws Exception {
        TransferenciaRequest request = new TransferenciaRequest();
        request.setFromId(1L);
        request.setToId(2L);
        request.setAmount(new BigDecimal("100.00"));
        
        doNothing().when(beneficioEjb).transfer(1L, 2L, new BigDecimal("100.00"));

        mockMvc.perform(post("/beneficios/transferir")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Transferência realizada com sucesso"));
    }

    /**
     * Testa o endpoint de transferência com saldo insuficiente.
     * 
     * @throws Exception se houver erro durante o teste
     */
    @Test
    void testTransferInsufficientFunds() throws Exception {
        TransferenciaRequest request = new TransferenciaRequest();
        request.setFromId(1L);
        request.setToId(2L);
        request.setAmount(new BigDecimal("100.00"));
        
        doThrow(new IllegalStateException("Saldo insuficiente"))
                .when(beneficioEjb).transfer(1L, 2L, new BigDecimal("100.00"));

        mockMvc.perform(post("/beneficios/transferir")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Saldo insuficiente"))
                .andExpect(jsonPath("$.error").value("Estado inválido"))
                .andExpect(jsonPath("$.status").value(409));
    }
}