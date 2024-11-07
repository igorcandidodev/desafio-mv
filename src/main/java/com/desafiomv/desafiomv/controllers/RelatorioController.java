package com.desafiomv.desafiomv.controllers;

import com.desafiomv.desafiomv.dtos.ClienteSaldoRelatorioDto;
import com.desafiomv.desafiomv.services.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;


    @Autowired
    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/saldo-clientes/{idEmpresa}")
    public ResponseEntity<List<ClienteSaldoRelatorioDto>> buscarClienteSaldoRelatorio(@PathVariable Long idEmpresa) {
        return ResponseEntity.ok(relatorioService.buscarClienteSaldoRelatorio(idEmpresa));
    }

    @GetMapping(value = "/saldo-clientes/formatado/{idEmpresa}", produces = "text/plain")
    public ResponseEntity<String> buscarClienteSaldoRelatorioFormatado(@PathVariable Long idEmpresa) {
        var relatorio = relatorioService.buscarClienteSaldoRelatorio(idEmpresa);
        StringBuilder relatorioFormatado = new StringBuilder();

        for(ClienteSaldoRelatorioDto relatorioCliente : relatorio) {
            relatorioFormatado.append(relatorioCliente.toString()).append("\n");
        }
        return ResponseEntity.ok(relatorioFormatado.toString());
    }
}
