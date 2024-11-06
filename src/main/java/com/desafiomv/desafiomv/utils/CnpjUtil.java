package com.desafiomv.desafiomv.utils;

public class CnpjUtil {

    public static String formatCnpj(String cnpj) {
        if (cnpj == null || cnpj.length() != 14) {
            throw new IllegalArgumentException("CNPJ deve conter 14 dígitos.");
        }
        return cnpj.substring(0, 2) + "." +
                cnpj.substring(2, 5) + "." +
                cnpj.substring(5, 8) + "/" +
                cnpj.substring(8, 12) + "-" +
                cnpj.substring(12, 14);
    }
}
