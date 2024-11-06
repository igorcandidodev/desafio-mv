package com.desafiomv.desafiomv.entities;

import com.desafiomv.desafiomv.entities.enums.TipoConta;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.Where;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clientes")
@Inheritance(strategy = InheritanceType.JOINED)
@Where(clause = "ativo = true")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    protected TipoConta tipoConta;
    private boolean ativo;

    private final ZonedDateTime dataCadastro;
    private ZonedDateTime dataAtualizacao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    @JsonManagedReference
    private Endereco endereco;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<Conta> contas;

    @ManyToMany(mappedBy = "clientes")
    @JsonBackReference
    private Set<Empresa> empresas;

    public Cliente(String nome, String email, Endereco endereco) {
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.contas = new HashSet<>();
        this.dataCadastro = ZonedDateTime.now();
        this.dataAtualizacao = ZonedDateTime.now();
        this.empresas = new HashSet<>();
        this.ativo = true;
    }

    public Cliente(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.endereco = null;
        this.contas = new HashSet<>();
        this.dataCadastro = ZonedDateTime.now();
        this.dataAtualizacao = ZonedDateTime.now();
        this.empresas = new HashSet<>();
        this.ativo = true;
    }

    public Cliente() {
        this.dataCadastro = ZonedDateTime.now();
        this.dataAtualizacao = ZonedDateTime.now();
        this.contas = new HashSet<>();
        this.empresas = new HashSet<>();
        this.ativo = true;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public ZonedDateTime getDataCadastro() {
        return dataCadastro;
    }

    public ZonedDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(ZonedDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Long getId() {
        return id;
    }

    public Set<Conta> getContas() {
        return contas;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public Set<Empresa> getEmpresas() {
        return empresas;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void adicionarConta(Conta conta) {
        this.contas.add(conta);
    }

    public void adicionarEmpresa(Empresa empresa) {
        this.empresas.add(empresa);
    }
}
