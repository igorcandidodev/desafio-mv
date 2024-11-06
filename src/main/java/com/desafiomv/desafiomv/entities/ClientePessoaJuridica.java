package com.desafiomv.desafiomv.entities;

import com.desafiomv.desafiomv.entities.enums.TipoConta;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.time.ZonedDateTime;

@Entity
@Table(name = "cliente_pessoa_juridica")
@PrimaryKeyJoinColumn(name = "cliente_id")
public class ClientePessoaJuridica extends Cliente {

    private String cnpj;

    public ClientePessoaJuridica(String nome, String email, Endereco endereco, String cnpj) {
        super(nome, email, endereco);
        super.tipoConta = TipoConta.PESSOA_JURIDICA;
        this.cnpj = cnpj;
    }

    public ClientePessoaJuridica(String nome, String email, String cnpj) {
        super(nome, email);
        this.cnpj = cnpj;
    }

    public ClientePessoaJuridica() {
        super();
    }

    public String getCnpj() {
        return cnpj;
    }
}
