package com.desafiomv.desafiomv.repositories;

import com.desafiomv.desafiomv.entities.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    @Query("SELECT c FROM Conta c WHERE c.numeroConta = :numeroConta AND c.agencia = :agencia AND c.ativa = true")
    Optional<Conta> findByNumeroContaAndAgencia(String numeroConta, String agencia);

    @Query("SELECT c FROM Conta c WHERE c.ativa = true")
    List<Conta> findAllAtivas();
}
