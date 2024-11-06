package com.desafiomv.desafiomv.controllers;

import com.desafiomv.desafiomv.dtos.MovimentacaoFluxoDto;
import com.desafiomv.desafiomv.entities.Movimentacao;
import com.desafiomv.desafiomv.services.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes/contas/movimentacoes")
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    @Autowired
    public MovimentacaoController(MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    @PostMapping("/")
    @Transactional
    public ResponseEntity<Movimentacao> criarMovimentacao(@RequestBody MovimentacaoFluxoDto movimentacaoDto) {
        return ResponseEntity.ok(movimentacaoService.criarMovimentacao(movimentacaoDto));
    }

    @GetMapping("/")
    public ResponseEntity<List<Movimentacao>> listarMovimentacoes() {
        return ResponseEntity.ok(movimentacaoService.listarMovimentacoes());
    }
}
