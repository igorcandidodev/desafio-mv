package com.desafiomv.desafiomv.repositories;

import com.desafiomv.desafiomv.entities.ClientePessoaJuridica;
import com.desafiomv.desafiomv.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientePessoaJuridicaRepository extends JpaRepository<ClientePessoaJuridica, Long> {

    Optional<ClientePessoaJuridica> findByCnpjAndEmpresas(String cnpj, Empresa empresa);

    Optional<ClientePessoaJuridica> findByCnpj(String cnpj);
}
