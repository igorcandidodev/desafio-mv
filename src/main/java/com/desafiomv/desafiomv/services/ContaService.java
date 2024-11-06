package com.desafiomv.desafiomv.services;

import com.desafiomv.desafiomv.dtos.ContaDto;
import com.desafiomv.desafiomv.entities.Cliente;
import com.desafiomv.desafiomv.entities.Conta;
import com.desafiomv.desafiomv.entities.Movimentacao;
import com.desafiomv.desafiomv.repositories.ClienteRepository;
import com.desafiomv.desafiomv.repositories.ContaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;

    public ContaService(ContaRepository contaRepository, ClienteRepository clienteRepository) {
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Conta> listarContas() {
        return contaRepository.findAllAtivas();
    }

    public void criarConta(Cliente cliente, ContaDto contaDto) {

        contaRepository.findByNumeroContaAndAgencia(contaDto.numeroConta(), contaDto.agencia())
                .ifPresent(contaExistente -> {
                    throw new IllegalArgumentException("Conta já cadastrada.");
                });
        var conta = new Conta(contaDto.codigoBanco(), contaDto.agencia(), contaDto.agenciaDigitoVerificador(),
                contaDto.numeroConta(), contaDto.contaDigitoVerificador(), cliente);

        if(contaDto.movimentacoes() != null && !contaDto.movimentacoes().isEmpty()) {
            contaDto.movimentacoes().forEach(movimentacaoDto -> {
                var movimentacao = new Movimentacao(movimentacaoDto.descricao(), movimentacaoDto.tipoMovimentacao(), movimentacaoDto.valor(), conta);
                conta.adicionarMovimentacao(movimentacao);
            });
        }

        conta.atualizarSaldo();
        cliente.adicionarConta(conta);

        clienteRepository.save(cliente);
    }

    public void excluirConta(String numeroConta, String agencia) {
        Conta conta = contaRepository.findByNumeroContaAndAgencia(numeroConta, agencia)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));

        if (!conta.getMovimentacoes().isEmpty()) {
            conta.setAtiva(false);
            conta.setDataAtualizacao(LocalDateTime.now());
            contaRepository.save(conta);
        } else {
            contaRepository.delete(conta);
        }
    }
}
