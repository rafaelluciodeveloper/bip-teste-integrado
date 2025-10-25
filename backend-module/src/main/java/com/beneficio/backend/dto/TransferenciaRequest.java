package com.beneficio.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para requisições de transferência entre benefícios.
 * Contém os dados necessários para realizar uma transferência.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaRequest {
    
    @NotNull(message = "ID do benefício origem é obrigatório")
    private Long fromId;
    
    @NotNull(message = "ID do benefício destino é obrigatório")
    private Long toId;
    
    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    private BigDecimal amount;
}
