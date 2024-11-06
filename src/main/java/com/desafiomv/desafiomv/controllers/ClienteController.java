package com.desafiomv.desafiomv.controllers;

import com.desafiomv.desafiomv.dtos.ClienteDto;
import com.desafiomv.desafiomv.entities.Cliente;
import com.desafiomv.desafiomv.services.ClientePessoaFisicaService;
import com.desafiomv.desafiomv.services.ClientePessoaJuridicaService;
import com.desafiomv.desafiomv.services.ClienteService;
import com.desafiomv.desafiomv.utils.CnpjUtil;
import com.desafiomv.desafiomv.utils.CpfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    private final ClientePessoaFisicaService clientePessoaFisicaService;
    private final ClientePessoaJuridicaService clientePessoaJuridicaService;

    public static final int QUANTIDADE_CPF = 11;

    @Autowired
    public ClienteController(ClienteService clienteService, ClientePessoaFisicaService clientePessoaFisicaService, ClientePessoaJuridicaService clientePessoaJuridicaService) {
        this.clienteService = clienteService;
        this.clientePessoaFisicaService = clientePessoaFisicaService;
        this.clientePessoaJuridicaService = clientePessoaJuridicaService;
    }

    @PostMapping("/{idEmpresa}")
    @Transactional
    public ResponseEntity<Cliente> criarCliente(@PathVariable Long idEmpresa, @RequestBody ClienteDto clienteDto) {
        return ResponseEntity.ok(clienteService.criarCliente(clienteDto, idEmpresa));
    }

    @GetMapping("/")
    public ResponseEntity<List<Cliente>> buscarTodosClientes() {
        return ResponseEntity.ok(clienteService.buscarTodosClientes());
    }

    @DeleteMapping("/{cnpjCpf}/idEmpresa/{idEmpresa}")
    @Transactional
    public ResponseEntity<Void> excluirCliente(@PathVariable String cnpjCpf, @PathVariable Long idEmpresa) {
        var cpfCnpjFormatado = (cnpjCpf.length() == 11) ? CpfUtil.formatCpf(cnpjCpf) : CnpjUtil.formatCnpj(cnpjCpf);
        var cliente = (cnpjCpf.length() == QUANTIDADE_CPF) ? clientePessoaFisicaService.buscarPorCpfEEmpresa(cpfCnpjFormatado, idEmpresa) : clientePessoaJuridicaService.buscarPorCnpjEEmpresa(cpfCnpjFormatado, idEmpresa);

        clienteService.excluirCliente(cliente);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{cnpjCpf}/idEmpresa/{idEmpresa}")
    @Transactional
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable String cnpjCpf, @PathVariable Long idEmpresa, @RequestBody ClienteDto clienteDto) {
        var cpfCnpjFormatado = (cnpjCpf.length() == QUANTIDADE_CPF) ? CpfUtil.formatCpf(cnpjCpf) : CnpjUtil.formatCnpj(cnpjCpf);
        var cliente = (cnpjCpf.length() == QUANTIDADE_CPF) ? clientePessoaFisicaService.buscarPorCpfEEmpresa(cpfCnpjFormatado, idEmpresa) : clientePessoaJuridicaService.buscarPorCnpjEEmpresa(cpfCnpjFormatado, idEmpresa);

        return ResponseEntity.ok(clienteService.atualizarCliente(cliente, clienteDto));
    }
}
