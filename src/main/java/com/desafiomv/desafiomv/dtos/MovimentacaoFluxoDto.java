package com.desafiomv.desafiomv.dtos;

import com.desafiomv.desafiomv.entities.enums.TipoMovimentacao;

import java.math.BigDecimal;

public record MovimentacaoFluxoDto(
        String descricao,
        TipoMovimentacao tipoMovimentacao,
        BigDecimal valor,
        ContaFluxoDto contaOrigem
) {
}
