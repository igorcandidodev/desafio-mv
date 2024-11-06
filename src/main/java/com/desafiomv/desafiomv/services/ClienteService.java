package com.desafiomv.desafiomv.services;

import com.desafiomv.desafiomv.dtos.ClienteDto;
import com.desafiomv.desafiomv.dtos.EnderecoDto;
import com.desafiomv.desafiomv.entities.*;
import com.desafiomv.desafiomv.repositories.ClienteRepository;
import com.desafiomv.desafiomv.repositories.EmpresaRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepository; 

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, EmpresaRepository empresaRepository) {
        this.clienteRepository = clienteRepository;
        this.empresaRepository = empresaRepository;
    }

    public List<Cliente> buscarTodosClientes() {
        return clienteRepository.findAllAtivas();
    }

    public void excluirCliente(Cliente cliente) {
        if(cliente.getContas() != null && !cliente.getContas().isEmpty()) {
            cliente.getContas().forEach(conta -> conta.setAtiva(false));
        }

        cliente.setAtivo(false);
        cliente.setDataAtualizacao(ZonedDateTime.now());
        clienteRepository.save(cliente);
    }

    public Cliente criarCliente(ClienteDto clienteDto, Long idEmpresa) {
        var empresa = empresaRepository.findById(idEmpresa).orElseThrow(() -> new ObjectNotFoundException("Empresa não encontrada", Empresa.class));
        
        Cliente cliente;
        Endereco endereco = null;

        if(clienteDto.endereco() != null) {
            endereco = new Endereco(clienteDto.endereco().logradouro(), clienteDto.endereco().numero(), clienteDto.endereco().complemento(), clienteDto.endereco().bairro(), clienteDto.endereco().cidade(), clienteDto.endereco().estado(), clienteDto.endereco().cep());
        }

        if(clienteDto.cpf() != null) {
            cliente = new ClientePessoaFisica(clienteDto.nome(), clienteDto.email(), clienteDto.dataNascimento(), endereco, clienteDto.cpf());
        } else if(clienteDto.cnpj() != null) {
            cliente = new ClientePessoaJuridica(clienteDto.nome(), clienteDto.email(), endereco, clienteDto.cnpj());
        } else {
            throw new IllegalArgumentException("Cliente deve possuir um CPF ou CNPJ.");
        }

        // percorrendo todas as contas e movimentações e adicionando ao cliente
        clienteDto.contas().forEach(contaDto -> {
            var conta = new Conta(contaDto.codigoBanco(), contaDto.agencia(), contaDto.agenciaDigitoVerificador(), contaDto.numeroConta(), contaDto.contaDigitoVerificador(), cliente);

            contaDto.movimentacoes().forEach(movimentacaoDto -> {
                var movimentacao = new  Movimentacao(movimentacaoDto.descricao(), movimentacaoDto.tipoMovimentacao(), movimentacaoDto.valor(), conta);
                conta.adicionarMovimentacao(movimentacao);
            });

            conta.atualizarSaldo();

            cliente.adicionarConta(conta);
        });

        var clienteSaved =  clienteRepository.save(cliente);

        empresa.adicionarCliente(clienteSaved);
        empresaRepository.save(empresa);

        return clienteSaved;
    }

    public Cliente atualizarCliente(Cliente cliente, ClienteDto clienteDto) {
        cliente.setNome(clienteDto.nome());
        cliente.setEmail(clienteDto.email());

        if(clienteDto.endereco() != null) {
            cliente.setEndereco(atualizarInformacoesEndereco(cliente.getEndereco(), clienteDto.endereco()));
        }

        cliente.setDataAtualizacao(ZonedDateTime.now());

       return clienteRepository.save(cliente);
    }

    private Endereco atualizarInformacoesEndereco(Endereco enderecoAntigo, EnderecoDto enderecoDto) {
        if(enderecoDto.logradouro() != null) {
            enderecoAntigo.setLogradouro(enderecoDto.logradouro());
        }
        if(enderecoDto.numero() != null) {
            enderecoAntigo.setNumero(enderecoDto.numero());
        }
        if(enderecoDto.complemento() != null) {
            enderecoAntigo.setComplemento(enderecoDto.complemento());
        }
        if(enderecoDto.bairro() != null) {
            enderecoAntigo.setBairro(enderecoDto.bairro());
        }
        if(enderecoDto.cidade() != null) {
            enderecoAntigo.setCidade(enderecoDto.cidade());
        }
        if(enderecoDto.estado() != null) {
            enderecoAntigo.setEstado(enderecoDto.estado());
        }
        if(enderecoDto.cep() != null) {
            enderecoAntigo.setCep(enderecoDto.cep());
        }

        enderecoAntigo.setDataAtualizacao(ZonedDateTime.now());

        return enderecoAntigo;
    }

}
