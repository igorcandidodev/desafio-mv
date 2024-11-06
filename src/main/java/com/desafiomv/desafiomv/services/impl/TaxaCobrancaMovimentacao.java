package com.desafiomv.desafiomv.services.impl;

import com.desafiomv.desafiomv.services.TaxaCobranca;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TaxaCobrancaMovimentacao implements TaxaCobranca {

    @Override
    public BigDecimal calcularTaxaCobrancaPorMovimentacao(int quantidadeMovimentacoes) {
        if(quantidadeMovimentacoes > 0 && quantidadeMovimentacoes <= 10) {
            return BigDecimal.valueOf(quantidadeMovimentacoes).multiply(BigDecimal.valueOf(1.00));
        } else if(quantidadeMovimentacoes > 10 && quantidadeMovimentacoes <= 20) {
            return BigDecimal.valueOf(quantidadeMovimentacoes).multiply(BigDecimal.valueOf(0.75));
        } else {
            return BigDecimal.valueOf(quantidadeMovimentacoes).multiply(BigDecimal.valueOf(0.5));
        }
    }
}
