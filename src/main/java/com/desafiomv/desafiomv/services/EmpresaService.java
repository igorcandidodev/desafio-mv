package com.desafiomv.desafiomv.services;

import com.desafiomv.desafiomv.dtos.EmpresaDto;
import com.desafiomv.desafiomv.entities.Conta;
import com.desafiomv.desafiomv.entities.Empresa;
import com.desafiomv.desafiomv.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    @Autowired
    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public Empresa buscarEmpresaPorId(Long id) {
        return empresaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));
    }

    public Empresa criarEmpresa(EmpresaDto empresaDto) {
        if(empresaRepository.findByCnpj(empresaDto.cnpj()).isPresent()) {
            throw new IllegalArgumentException("CNPJ já cadastrado");
        }
        if(empresaRepository.findByEmail(empresaDto.email()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Empresa empresa = new Empresa(empresaDto.nome(), empresaDto.email(), empresaDto.cnpj());
        empresaDto.contas().forEach(contaDto -> empresa.adicionarConta(new Conta(contaDto.codigoBanco(), contaDto.agencia(), contaDto.agenciaDigitoVerificador(), contaDto.numeroConta(), contaDto.contaDigitoVerificador(), empresa)));
        return empresaRepository.save(empresa);
    }
}
