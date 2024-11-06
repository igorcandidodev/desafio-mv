package com.desafiomv.desafiomv.controllers;

import com.desafiomv.desafiomv.dtos.EnderecoDto;
import com.desafiomv.desafiomv.entities.Cliente;
import com.desafiomv.desafiomv.entities.Endereco;
import com.desafiomv.desafiomv.services.ClientePessoaFisicaService;
import com.desafiomv.desafiomv.services.ClientePessoaJuridicaService;
import com.desafiomv.desafiomv.services.EnderecoService;
import com.desafiomv.desafiomv.utils.CnpjUtil;
import com.desafiomv.desafiomv.utils.CpfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;
    private final ClientePessoaFisicaService clientePessoaFisicaService;
    private final ClientePessoaJuridicaService clientePessoaJuridicaService;

    public static final int QUANTIDADE_CPF = 11;

    @Autowired
    public EnderecoController(EnderecoService enderecoService, ClientePessoaFisicaService clientePessoaFisicaService, ClientePessoaJuridicaService clientePessoaJuridicaService) {
        this.enderecoService = enderecoService;
        this.clientePessoaFisicaService = clientePessoaFisicaService;
        this.clientePessoaJuridicaService = clientePessoaJuridicaService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Endereco>> buscarTodosEnderecos() {
        return ResponseEntity.ok(enderecoService.buscarTodosEnderecos());
    }

    @GetMapping("/buscaporcep/{cep}")
    public ResponseEntity<List<Endereco>> buscarEnderecoPorCep(@PathVariable String cep) {
        return ResponseEntity.ok(enderecoService.buscarEnderecosPorCep(cep));
    }

    @PostMapping("/{cpjCnpj}")
    @Transactional
    public ResponseEntity<Cliente> adicionarEndereco(@PathVariable String cpjCnpj, @RequestBody EnderecoDto enderecoDto) {
        var cpfCnpjFormatado = (QUANTIDADE_CPF == 11) ? CpfUtil.formatCpf(cpjCnpj) : CnpjUtil.formatCnpj(cpjCnpj);
        var cliente = (cpjCnpj.length() == QUANTIDADE_CPF) ? clientePessoaFisicaService.buscarPorCpf(cpfCnpjFormatado) : clientePessoaJuridicaService.buscarPorCnpj(cpfCnpjFormatado);

        var endereco = new Endereco(enderecoDto.logradouro(), enderecoDto.numero(), enderecoDto.complemento(), enderecoDto.bairro(), enderecoDto.cidade(), enderecoDto.estado(), enderecoDto.cep());

        return ResponseEntity.ok(enderecoService.adicionarEndereco(cliente, endereco));
    }

    @DeleteMapping("/{cpjCnpj}")
    @Transactional
    public ResponseEntity<Void> deletarEndereco(@PathVariable String cpjCnpj) {
        var cpfCnpjFormatado = (QUANTIDADE_CPF == 11) ? CpfUtil.formatCpf(cpjCnpj) : CnpjUtil.formatCnpj(cpjCnpj);
        var cliente = (cpjCnpj.length() == QUANTIDADE_CPF) ? clientePessoaFisicaService.buscarPorCpf(cpfCnpjFormatado) : clientePessoaJuridicaService.buscarPorCnpj(cpfCnpjFormatado);

        enderecoService.deletarEndereco(cliente);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{cpjCnpj}")
    @Transactional
    public ResponseEntity<Cliente> atualizarEndereco(@PathVariable String cpjCnpj, @RequestBody EnderecoDto enderecoDto) {
        var cpfCnpjFormatado = (QUANTIDADE_CPF == 11) ? CpfUtil.formatCpf(cpjCnpj) : CnpjUtil.formatCnpj(cpjCnpj);
        var cliente = (cpjCnpj.length() == QUANTIDADE_CPF) ? clientePessoaFisicaService.buscarPorCpf(cpfCnpjFormatado) : clientePessoaJuridicaService.buscarPorCnpj(cpfCnpjFormatado);

        var endereco = new Endereco(enderecoDto.logradouro(), enderecoDto.numero(), enderecoDto.complemento(), enderecoDto.bairro(), enderecoDto.cidade(), enderecoDto.estado(), enderecoDto.cep());

        return ResponseEntity.ok(enderecoService.atualizarEndereco(cliente, endereco));
    }
}
