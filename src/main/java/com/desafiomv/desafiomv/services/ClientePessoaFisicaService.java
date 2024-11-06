package com.desafiomv.desafiomv.services;

import com.desafiomv.desafiomv.entities.ClientePessoaFisica;
import com.desafiomv.desafiomv.entities.Empresa;
import com.desafiomv.desafiomv.repositories.ClientePessoaFisicaRepository;
import com.desafiomv.desafiomv.repositories.EmpresaRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientePessoaFisicaService {

    private final ClientePessoaFisicaRepository clientePessoaFisicaRepository;
    private final EmpresaRepository empresaRepository;

    public ClientePessoaFisicaService(ClientePessoaFisicaRepository clientePessoaFisicaRepository, EmpresaRepository empresaRepository) {
        this.clientePessoaFisicaRepository = clientePessoaFisicaRepository;
        this.empresaRepository = empresaRepository;
    }

    public ClientePessoaFisica buscarPorCpf(String cpf) {
        return clientePessoaFisicaRepository.findByCpf(cpf).orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado", ClientePessoaFisica.class));
    }

    public ClientePessoaFisica buscarPorCpfEEmpresa(String cpf, Long idEmpresa) {
        var empresa = empresaRepository.findById(idEmpresa).orElseThrow(() -> new ObjectNotFoundException("Empresa não encontrada", Empresa.class));
        return clientePessoaFisicaRepository.findByCpfAndEmpresas(cpf, empresa).orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado", ClientePessoaFisica.class));
    }
}
