package com.desafiomv.desafiomv.controllers.exceptions;

import com.desafiomv.desafiomv.dtos.ErroPadraoDto;
import com.desafiomv.desafiomv.exceptions.MovimentacaoInvalidaException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {

    public static final String TIME_ZONE = "America/Sao_Paulo";

    @ExceptionHandler({MovimentacaoInvalidaException.class})
    public ResponseEntity<ErroPadraoDto> handleMovimentacaoInvalidaException(MovimentacaoInvalidaException e, HttpServletRequest request) {
        var body = new ErroPadraoDto(ZonedDateTime.now(ZoneId.of(TIME_ZONE)), HttpStatus.BAD_REQUEST.value(), "Erro na movimentação", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler({ObjectNotFoundException.class})
    public ResponseEntity<ErroPadraoDto> handleObjectNotFoundException(ObjectNotFoundException e, HttpServletRequest request) {
        var body = new ErroPadraoDto(ZonedDateTime.now(ZoneId.of(TIME_ZONE)), HttpStatus.NOT_FOUND.value(), "Objeto não encontrado", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErroPadraoDto> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        var body = new ErroPadraoDto(ZonedDateTime.now(ZoneId.of(TIME_ZONE)), HttpStatus.BAD_REQUEST.value(), "Argumento inválido", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
