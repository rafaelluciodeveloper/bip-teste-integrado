package com.beneficio.backend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO para requisições de transferência entre benefícios.
 *
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Data
public class TransferenciaRequest {

    @NotNull(message = "ID do benefício origem é obrigatório")
    private Long fromId;

    @NotNull(message = "ID do benefício destino é obrigatório")
    private Long toId;

    @NotNull(message = "Valor da transferência é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    private BigDecimal amount;
}