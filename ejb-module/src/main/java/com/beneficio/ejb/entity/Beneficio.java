package com.beneficio.ejb.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade JPA que representa um benefício no sistema.
 * Mapeia a tabela BENEFICIO no banco de dados.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "BENEFICIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Beneficio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;

    @Column(name = "DESCRICAO", length = 255)
    private String descricao;

    @Column(name = "VALOR", nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    @Column(name = "ATIVO")
    private Boolean ativo = true;

    @Version
    @Column(name = "VERSION")
    private Long version;

    /**
     * Construtor com parâmetros principais.
     * 
     * @param nome nome do benefício
     * @param descricao descrição do benefício
     * @param valor valor do benefício
     */
    public Beneficio(String nome, String descricao, BigDecimal valor) {
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.ativo = true;
    }
}
