package com.desafiomv.desafiomv.repositories;

import com.desafiomv.desafiomv.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco,  Long> {

    List<Endereco> findByCep(String cep);
}
