package com.desafiomv.desafiomv.entities.enums;

public enum TipoConta {
    PESSOA_FISICA(0),
    PESSOA_JURIDICA(1);

    private final int valor;

    TipoConta(int valor) {
        this.valor = valor;
    }
}
