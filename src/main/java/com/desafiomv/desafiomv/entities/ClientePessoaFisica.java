package com.desafiomv.desafiomv.entities;

import com.desafiomv.desafiomv.entities.enums.TipoConta;
import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "cliente_pessoa_fisica")
@PrimaryKeyJoinColumn(name = "cliente_id")
public class ClientePessoaFisica extends Cliente {

    private String cpf;

    private ZonedDateTime dataNascimento;

    public ClientePessoaFisica(String nome, String email, ZonedDateTime dataNascimento, Endereco endereco, String cpf) {
        super(nome, email, endereco);
        this.dataNascimento = dataNascimento;
        super.tipoConta = TipoConta.PESSOA_FISICA;
        this.cpf = cpf;
    }

    public ClientePessoaFisica(String nome, String email, ZonedDateTime dataNascimento, String cpf) {
        super(nome, email);
        this.dataNascimento = dataNascimento;
        super.tipoConta = TipoConta.PESSOA_FISICA;
        this.cpf = cpf;
    }

    public ClientePessoaFisica() {
        super();
    }

    public String getCpf() {
        return cpf;
    }
}
