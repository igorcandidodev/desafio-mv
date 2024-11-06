package com.desafiomv.desafiomv.entities;

import com.desafiomv.desafiomv.entities.enums.TipoMovimentacao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "contas")
@Where(clause = "ativa = true")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigoBanco;
    private String agencia;
    private String agenciaDigitoVerificador;
    private String numeroConta;
    private String contaDigitoVerificador;
    private BigDecimal saldo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private boolean ativa;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonManagedReference
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    @JsonManagedReference
    private Empresa empresa;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<Movimentacao> movimentacoes;

    public Conta(String codigoBanco, String agencia, String agenciaDigitoVerificador, String numeroConta, String contaDigitoVerificador, Cliente cliente) {
        this.codigoBanco = codigoBanco;
        this.agencia = agencia;
        this.agenciaDigitoVerificador = agenciaDigitoVerificador;
        this.numeroConta = numeroConta;
        this.contaDigitoVerificador = contaDigitoVerificador;
        this.cliente = cliente;
        this.empresa = null;
        this.saldo = BigDecimal.valueOf(0.0);
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
        this.movimentacoes = new HashSet<>();
        this.ativa = true;
    }

    public Conta(String codigoBanco, String agencia, String agenciaDigitoVerificador, String numeroConta, String contaDigitoVerificador, Empresa empresa) {
        this.codigoBanco = codigoBanco;
        this.agencia = agencia;
        this.agenciaDigitoVerificador = agenciaDigitoVerificador;
        this.numeroConta = numeroConta;
        this.contaDigitoVerificador = contaDigitoVerificador;
        this.cliente = null;
        this.empresa = empresa;
        this.saldo = BigDecimal.valueOf(0.0);
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
        this.movimentacoes = new HashSet<>();
        this.ativa = true;
    }

    public Conta(String codigoBanco, String agencia, String agenciaDigitoVerificador, String numeroConta, String contaDigitoVerificador) {
        this.codigoBanco = codigoBanco;
        this.agencia = agencia;
        this.agenciaDigitoVerificador = agenciaDigitoVerificador;
        this.numeroConta = numeroConta;
        this.contaDigitoVerificador = contaDigitoVerificador;
        this.cliente = null;
        this.empresa = null;
        this.saldo = BigDecimal.valueOf(0.0);
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
        this.ativa = true;
        this.movimentacoes = new HashSet<>();
    }

    public Conta() {
    }

    public Long getId() {
        return id;
    }

    public String getCodigoBanco() {
        return codigoBanco;
    }

    public void setCodigoBanco(String codigoBanco) {
        this.codigoBanco = codigoBanco;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getAgenciaDigitoVerificador() {
        return agenciaDigitoVerificador;
    }

    public void setAgenciaDigitoVerificador(String agenciaDigitoVerificador) {
        this.agenciaDigitoVerificador = agenciaDigitoVerificador;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getContaDigitoVerificador() {
        return contaDigitoVerificador;
    }

    public void setContaDigitoVerificador(String contaDigitoVerificador) {
        this.contaDigitoVerificador = contaDigitoVerificador;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Set<Movimentacao> getMovimentacoes() {
        return movimentacoes;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void adicionarMovimentacao(Movimentacao movimentacao) {
        this.movimentacoes.add(movimentacao);
    }

    public void atualizarSaldo() {
        BigDecimal saldo = BigDecimal.valueOf(0.0);
        for (Movimentacao movimentacao : this.movimentacoes) {
            if (movimentacao.getTipoMovimentacao().equals(TipoMovimentacao.CREDITO)) {
                saldo = saldo.add(movimentacao.getValor());
            } else {
                saldo = saldo.subtract(movimentacao.getValor());
            }
        }
        this.saldo = saldo;
    }

    public int getQuantidadeMovimentacoesUltimos30Dias() {
        // Define a data limite como 30 dias atrás
        ZonedDateTime dataLimite = ZonedDateTime.now().minusDays(30);

        // Conta o número de movimentações com data posterior à dataLimite
        return (int) movimentacoes.stream()
                .filter(movimentacao -> movimentacao.getDataCriacao().isAfter(ChronoZonedDateTime.from(dataLimite)))
                .count();
    }

}
