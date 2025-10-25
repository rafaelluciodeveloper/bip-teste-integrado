package com.beneficio.ejb.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade JPA que representa uma transferência entre benefícios.
 * Mapeia a tabela TRANSFERENCIA no banco de dados.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "TRANSFERENCIA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BENEFICIO_ORIGEM_ID", nullable = false)
    private Beneficio beneficioOrigem;

    @ManyToOne
    @JoinColumn(name = "BENEFICIO_DESTINO_ID", nullable = false)
    private Beneficio beneficioDestino;

    @Column(name = "VALOR", nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    @Column(name = "DATA_TRANSFERENCIA", nullable = false)
    private LocalDateTime dataTransferencia;

    @Column(name = "OBSERVACOES", length = 255)
    private String observacoes;

    /**
     * Construtor com parâmetros principais.
     * 
     * @param beneficioOrigem benefício origem da transferência
     * @param beneficioDestino benefício destino da transferência
     * @param valor valor transferido
     */
    public Transferencia(Beneficio beneficioOrigem, Beneficio beneficioDestino, BigDecimal valor) {
        this.beneficioOrigem = beneficioOrigem;
        this.beneficioDestino = beneficioDestino;
        this.valor = valor;
        this.dataTransferencia = LocalDateTime.now();
    }
}
