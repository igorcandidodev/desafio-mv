package com.desafiomv.desafiomv.services;

import com.desafiomv.desafiomv.entities.Cliente;
import com.desafiomv.desafiomv.entities.Endereco;
import com.desafiomv.desafiomv.repositories.ClienteRepository;
import com.desafiomv.desafiomv.repositories.EnderecoRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository, ClienteRepository clienteRepository) {
        this.enderecoRepository = enderecoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Endereco> buscarTodosEnderecos() {
        return enderecoRepository.findAll();
    }

    public List<Endereco> buscarEnderecosPorCep(String cep) {
        var enderecos = enderecoRepository.findByCep(cep);
        if(enderecos.isEmpty()) {
            throw new ObjectNotFoundException("Nenhum endereço encontrado para o CEP informado.", Endereco.class);
        }
        return enderecos;
    }

    public Cliente adicionarEndereco(Cliente cliente, Endereco endereco) {

        if(cliente.getEndereco() != null) {
            throw new IllegalArgumentException("Cliente já possui um endereço cadastrado.");
        }
        var enderecoSalvo = enderecoRepository.save(endereco);
        cliente.setEndereco(enderecoSalvo);
        return clienteRepository.save(cliente);
    }

    public Cliente atualizarEndereco(Cliente cliente, Endereco enderecoAtualizado) {
        var enderecoAntigo = cliente.getEndereco();
        var enderecoAtualizadoSalvo = enderecoRepository.save(setarValoresEndereco(enderecoAntigo, enderecoAtualizado));
        cliente.setEndereco(enderecoAtualizadoSalvo);
        return clienteRepository.save(cliente);

    }

    public void deletarEndereco(Cliente cliente) {
        if(cliente.getEndereco() == null) {
            throw new IllegalArgumentException("Cliente não possui endereço cadastrado.");
        }
        var endereco = cliente.getEndereco();
        cliente.setEndereco(null);
        clienteRepository.save(cliente);
        enderecoRepository.delete(endereco);
    }

    private Endereco setarValoresEndereco(Endereco enderecoAntigo, Endereco enderecoAtualizado) {
        if(enderecoAtualizado.getLogradouro() != null) {
            enderecoAntigo.setLogradouro(enderecoAtualizado.getLogradouro());
        }
        if(enderecoAtualizado.getNumero() != null) {
            enderecoAntigo.setNumero(enderecoAtualizado.getNumero());
        }
        if(enderecoAtualizado.getComplemento() != null) {
            enderecoAntigo.setComplemento(enderecoAtualizado.getComplemento());
        }
        if(enderecoAtualizado.getBairro() != null) {
            enderecoAntigo.setBairro(enderecoAtualizado.getBairro());
        }
        if(enderecoAtualizado.getCidade() != null) {
            enderecoAntigo.setCidade(enderecoAtualizado.getCidade());
        }
        if(enderecoAtualizado.getEstado() != null) {
            enderecoAntigo.setEstado(enderecoAtualizado.getEstado());
        }
        if(enderecoAtualizado.getCep() != null) {
            enderecoAntigo.setCep(enderecoAtualizado.getCep());
        }

        enderecoAntigo.setDataAtualizacao(ZonedDateTime.now());

        return enderecoAntigo;
    }
}
