package com.desafiomv.desafiomv.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ContaDto(
        @NotNull
        String codigoBanco,
        @NotNull
        String agencia,
        String agenciaDigitoVerificador,
        @NotNull
        String numeroConta,
        String contaDigitoVerificador,
        List<MovimentacaoDto> movimentacoes
) {
}
