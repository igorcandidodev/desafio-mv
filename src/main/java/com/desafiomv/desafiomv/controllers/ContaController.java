package com.desafiomv.desafiomv.controllers;

import com.desafiomv.desafiomv.dtos.ContaDto;
import com.desafiomv.desafiomv.entities.Conta;
import com.desafiomv.desafiomv.services.ClientePessoaFisicaService;
import com.desafiomv.desafiomv.services.ClientePessoaJuridicaService;
import com.desafiomv.desafiomv.services.ContaService;
import com.desafiomv.desafiomv.utils.CnpjUtil;
import com.desafiomv.desafiomv.utils.CpfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes/contas")
public class ContaController {

    private final ContaService contaService;
    private final ClientePessoaFisicaService clientePessoaFisicaService;
    private final ClientePessoaJuridicaService clientePessoaJuridicaService;

    public static final int QUANTIDADE_CPF = 11;

    @Autowired
    public ContaController(ContaService contaService, ClientePessoaFisicaService clientePessoaFisicaService, ClientePessoaJuridicaService clientePessoaJuridicaService) {
        this.contaService = contaService;
        this.clientePessoaFisicaService = clientePessoaFisicaService;
        this.clientePessoaJuridicaService = clientePessoaJuridicaService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Conta>> buscarTodasContas() {
        return ResponseEntity.ok(contaService.listarContas());
    }

    @PostMapping("/cliente/{cpfCnpj}")
    @Transactional
    public ResponseEntity<Void> criarConta(@PathVariable String cpfCnpj, @RequestBody ContaDto contaDto) {
        var cpfCnpjFormatado = (cpfCnpj.length() == QUANTIDADE_CPF) ? CpfUtil.formatCpf(cpfCnpj) : CnpjUtil.formatCnpj(cpfCnpj);

        var cliente = (cpfCnpj.length() == QUANTIDADE_CPF) ? clientePessoaFisicaService.buscarPorCpf(cpfCnpjFormatado) : clientePessoaJuridicaService.buscarPorCnpj(cpfCnpjFormatado);
        contaService.criarConta(cliente, contaDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{numeroConta}/{agencia}")
    @Transactional
    public ResponseEntity<Void> excluirConta(@PathVariable String numeroConta, @PathVariable String agencia) {
        contaService.excluirConta(numeroConta, agencia);
        return ResponseEntity.noContent().build();
    }
}
