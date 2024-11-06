package com.desafiomv.desafiomv.entities.enums;

public enum TipoMovimentacao {
    CREDITO(0),
    DEBITO(1);

    private final int tipoMovimentacao;

    TipoMovimentacao(int tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public int getTipoMovimentacao() {
        return tipoMovimentacao;
    }
}
