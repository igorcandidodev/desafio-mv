package com.desafiomv.desafiomv.dtos;

public record ContaFluxoDto(
        String codigoBanco,
        String agencia,
        String agenciaDigitoVerificador,
        String numeroConta,
        String contaDigitoVerificador
) {
}
