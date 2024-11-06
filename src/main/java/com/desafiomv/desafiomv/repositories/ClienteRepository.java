package com.desafiomv.desafiomv.repositories;

import com.desafiomv.desafiomv.entities.Cliente;
import com.desafiomv.desafiomv.entities.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByContas(Conta conta);

    @Query("SELECT c FROM Cliente c WHERE c.ativo = true")
    List<Cliente> findAllAtivas();
}
