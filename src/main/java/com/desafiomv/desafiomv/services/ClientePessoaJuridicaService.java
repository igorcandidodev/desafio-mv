package com.desafiomv.desafiomv.services;

import com.desafiomv.desafiomv.entities.ClientePessoaJuridica;
import com.desafiomv.desafiomv.entities.Empresa;
import com.desafiomv.desafiomv.repositories.ClientePessoaJuridicaRepository;
import com.desafiomv.desafiomv.repositories.EmpresaRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientePessoaJuridicaService {

    private final ClientePessoaJuridicaRepository clientePessoaJuridicaRepository;
    private final EmpresaRepository empresaRepository;

    public ClientePessoaJuridicaService(ClientePessoaJuridicaRepository clientePessoaJuridicaRepository, EmpresaRepository empresaRepository) {
        this.clientePessoaJuridicaRepository = clientePessoaJuridicaRepository;
        this.empresaRepository = empresaRepository;
    }

    public ClientePessoaJuridica buscarPorCnpj(String cnpj) {
        return clientePessoaJuridicaRepository.findByCnpj(cnpj).orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado", ClientePessoaJuridica.class));
    }

    public ClientePessoaJuridica buscarPorCnpjEEmpresa(String cnpj, Long idEmpresa) {
        var empresa = empresaRepository.findById(idEmpresa).orElseThrow(() -> new ObjectNotFoundException("Empresa não encontrada", Empresa.class));
        return clientePessoaJuridicaRepository.findByCnpjAndEmpresas(cnpj, empresa).orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado", ClientePessoaJuridica.class));
    }
}
