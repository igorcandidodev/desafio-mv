package com.desafiomv.desafiomv.entities;

import com.desafiomv.desafiomv.entities.enums.TipoConta;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientePessoaJuridicaTest {

    @Test
    void testConstructorAndGetters() {
        String nome = "Empresa X";
        String email = "empresa@example.com";
        String cnpj = "12.345.678/0001-99";

        ClientePessoaJuridica cliente = new ClientePessoaJuridica(nome, email, cnpj);

        assertEquals(nome, cliente.getNome());
        assertEquals(email, cliente.getEmail());
        assertEquals(cnpj, cliente.getCnpj());
    }

}