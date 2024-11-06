package com.desafiomv.desafiomv.dtos;

import java.time.ZonedDateTime;
import java.util.List;

public record ClienteDto(
        String nome,
        String cpf,
        String cnpj,
        String email,
        List<ContaDto> contas,
        EnderecoDto endereco,
        ZonedDateTime dataNascimento
) {
}
