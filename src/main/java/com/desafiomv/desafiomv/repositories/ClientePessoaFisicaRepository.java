package com.desafiomv.desafiomv.repositories;

import com.desafiomv.desafiomv.entities.ClientePessoaFisica;
import com.desafiomv.desafiomv.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientePessoaFisicaRepository extends JpaRepository<ClientePessoaFisica, Long> {

    Optional<ClientePessoaFisica> findByCpfAndEmpresas(String cpf, Empresa empresa);

    Optional<ClientePessoaFisica> findByCpf(String cpf);
}
