package com.desafiomv.desafiomv.services;

import com.desafiomv.desafiomv.dtos.MovimentacaoFluxoDto;
import com.desafiomv.desafiomv.entities.Movimentacao;
import com.desafiomv.desafiomv.exceptions.MovimentacaoInvalidaException;
import com.desafiomv.desafiomv.repositories.ClienteRepository;
import com.desafiomv.desafiomv.repositories.ContaRepository;
import com.desafiomv.desafiomv.repositories.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository, ContaRepository contaRepository, ClienteRepository clienteRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
    }

    public Movimentacao criarMovimentacao(MovimentacaoFluxoDto movimentacaoDto) {
        var conta = contaRepository.findByNumeroContaAndAgencia(movimentacaoDto.contaOrigem().numeroConta(), movimentacaoDto.contaOrigem().agencia())
                .orElseThrow(() -> new MovimentacaoInvalidaException("Conta associada a movimentação não encontrada."));

        clienteRepository.findByContas(conta)
                .orElseThrow(() -> new MovimentacaoInvalidaException("Nenhum cliente associado a conta dessa movimentação."));

        var movimentacao = new Movimentacao(movimentacaoDto.descricao(), movimentacaoDto.tipoMovimentacao(), movimentacaoDto.valor(), conta);
        conta.adicionarMovimentacao(movimentacao);
        conta.atualizarSaldo();

        return movimentacaoRepository.save(movimentacao);
    }

    public List<Movimentacao> listarMovimentacoes() {
        return movimentacaoRepository.findAll();
    }
}
