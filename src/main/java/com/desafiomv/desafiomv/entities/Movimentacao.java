package com.desafiomv.desafiomv.entities;

import com.desafiomv.desafiomv.entities.enums.TipoMovimentacao;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "movimentacoes")
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private TipoMovimentacao tipoMovimentacao;
    private BigDecimal valor;

    private ZonedDateTime dataCriacao;
    private ZonedDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "conta_id")
    @JsonManagedReference
    private Conta conta;

    public Movimentacao(String descricao, TipoMovimentacao tipoMovimentacao, BigDecimal valor, Conta conta) {
        this.descricao = descricao;
        this.tipoMovimentacao = tipoMovimentacao;
        this.valor = valor;
        this.dataCriacao = ZonedDateTime.now();
        this.dataAtualizacao = ZonedDateTime.now();
        this.conta = conta;
    }

    public Movimentacao() {
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoMovimentacao getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public ZonedDateTime getDataCriacao() {
        return dataCriacao;
    }

    public ZonedDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(ZonedDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Conta getConta() {
        return conta;
    }

}
