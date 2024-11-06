package com.desafiomv.desafiomv.services;

import java.math.BigDecimal;

public interface TaxaCobranca {

    BigDecimal calcularTaxaCobrancaPorMovimentacao(int quantidadeMovimentacoes);
}
