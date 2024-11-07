package com.desafiomv.desafiomv.services;

import com.desafiomv.desafiomv.entities.Cliente;
import com.desafiomv.desafiomv.entities.Conta;
import com.desafiomv.desafiomv.entities.Empresa;
import com.desafiomv.desafiomv.entities.Movimentacao;
import com.desafiomv.desafiomv.entities.enums.TipoMovimentacao;
import com.desafiomv.desafiomv.repositories.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ReceitaEmpresaService {

    private final TaxaCobranca taxaCobranca;
    private final MovimentacaoRepository movimentacaoRepository;

    @Autowired
    public ReceitaEmpresaService(TaxaCobranca taxaCobranca, MovimentacaoRepository movimentacaoRepository) {
        this.taxaCobranca = taxaCobranca;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    /**
     * Calcula a taxa de serviço por movimentação de cada conta de cada cliente de uma empresa
     * @param empresa
     */
    public void taxaServicoPorMovimentacao(Empresa empresa) {

        for(Cliente cliente : empresa.getClientes()) {
            for(Conta conta : cliente.getContas()) {
                int quantidadeMovimentacoes = conta.getQuantidadeMovimentacoesUltimos30Dias();
                BigDecimal taxa = taxaCobranca.calcularTaxaCobrancaPorMovimentacao(quantidadeMovimentacoes);

                var movimentacaoSaida = new Movimentacao("Taxa de serviço por movimentação", TipoMovimentacao.DEBITO, taxa, conta);
                var movimentacaoEntrada = new Movimentacao("Taxa de serviço cliente: " + cliente.getNome(), TipoMovimentacao.CREDITO, taxa, empresa.getContas().iterator().next());

                conta.adicionarMovimentacao(movimentacaoSaida);
                empresa.getContas().iterator().next().adicionarMovimentacao(movimentacaoEntrada);

                conta.atualizarSaldo();
                empresa.getContas().iterator().next().atualizarSaldo();

                movimentacaoRepository.save(movimentacaoSaida);
                movimentacaoRepository.save(movimentacaoEntrada);
            }
        }

    }
}
