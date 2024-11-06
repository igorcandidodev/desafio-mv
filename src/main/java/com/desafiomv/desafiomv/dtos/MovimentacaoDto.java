package com.desafiomv.desafiomv.dtos;

import com.desafiomv.desafiomv.entities.enums.TipoMovimentacao;

import java.math.BigDecimal;

public record MovimentacaoDto(
        String descricao,
        TipoMovimentacao tipoMovimentacao,
        BigDecimal valor
) {
}
