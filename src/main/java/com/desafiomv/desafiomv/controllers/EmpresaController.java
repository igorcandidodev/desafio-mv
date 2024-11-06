package com.desafiomv.desafiomv.controllers;

import com.desafiomv.desafiomv.dtos.EmpresaDto;
import com.desafiomv.desafiomv.entities.Empresa;
import com.desafiomv.desafiomv.services.EmpresaService;
import com.desafiomv.desafiomv.services.ReceitaEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;
    private final ReceitaEmpresaService receitaEmpresaService;

    @Autowired
    public EmpresaController(EmpresaService empresaService, ReceitaEmpresaService receitaEmpresaService) {
        this.empresaService = empresaService;
        this.receitaEmpresaService = receitaEmpresaService;
    }

    @PostMapping("/")
    public ResponseEntity<Empresa> criarEmpresa(@RequestBody EmpresaDto empresaDto) {
        return ResponseEntity.ok(empresaService.criarEmpresa(empresaDto));
    }

    @GetMapping("/cobrar-taxa/{id}")
    public ResponseEntity<Void> cobrarTaxaServico(@PathVariable Long id) {
        var empresa = empresaService.buscarEmpresaPorId(id);
        receitaEmpresaService.taxaServicoPorMovimentacao(empresa);

        return ResponseEntity.noContent().build();
    }
}
