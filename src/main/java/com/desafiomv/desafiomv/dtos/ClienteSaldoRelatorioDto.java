package com.desafiomv.desafiomv.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record ClienteSaldoRelatorioDto(

        String nome,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yy HH:mm:ss,SSSSSS XXX", timezone = "America/Sao_Paulo")
        ZonedDateTime dataCadastro,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String cep,
        Integer movimentacao_credito,
        Integer movimentacao_debito,
        BigDecimal taxa_servico,
        BigDecimal saldo_inicial,
        BigDecimal saldo_final
) {

        @Override
        public String toString() {
                StringBuilder sb = new StringBuilder();
                return sb.append("Relatório de saldo do Cliente ").append(nome).append(":").append("\n")
                        .append("Cliente: ").append(nome).append(" - ").append("Cliente desde: ").append(String.format("%1$td/%1$tm/%1$tY", dataCadastro)).append(";").append("\n")
                        .append("Endereço: ").append(logradouro).append(", ").append(numero).append(", ").append(complemento).append(", ").append(bairro).append(", ").append(cidade).append(", ").append(estado).append(", ").append(cep).append(";").append("\n")
                        .append("Movimentações de crédito: ").append(movimentacao_credito).append(";").append("\n")
                        .append("Movimentações de débito: ").append(movimentacao_debito).append(";").append("\n")
                        .append("Total de movimentações: ").append(movimentacao_credito + movimentacao_debito).append(";").append("\n")
                        .append("Valor pago pelas movimentações: ").append(String.format("%.2f", taxa_servico)).append(";").append("\n")
                        .append("Saldo inicial: ").append(String.format("%.2f", saldo_inicial)).append(";").append("\n")
                        .append("Saldo atual: ").append(String.format("%.2f", saldo_final)).append(";").append("\n").toString();
        }
}
