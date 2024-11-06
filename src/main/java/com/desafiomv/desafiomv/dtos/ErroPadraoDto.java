package com.desafiomv.desafiomv.dtos;

import java.time.ZonedDateTime;

public record ErroPadraoDto(
        ZonedDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path
) {
}
