package com.desafiomv.desafiomv.dtos;

import java.util.List;

public record EmpresaDto(
        String nome,
        String email,
        String cnpj,
        List<ContaDto> contas
) {
}
