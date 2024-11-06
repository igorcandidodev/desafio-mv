package com.desafiomv.desafiomv.carga;

import com.desafiomv.desafiomv.dtos.*;
import com.desafiomv.desafiomv.entities.Empresa;
import com.desafiomv.desafiomv.entities.enums.TipoMovimentacao;
import com.desafiomv.desafiomv.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Component
public class Config implements CommandLineRunner {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url}")
    private String apiUrl;


    @Override
    public void run(String... args) throws Exception {
        List<ContaDto> contas = List.of(
                new ContaDto("001", "1111", "1", "12222", "6", null)
        );
        EmpresaDto master = new EmpresaDto("XPTO", "xpto@gmail.com", "00.623.904/0001-73", contas);

        String urlEmpresa = apiUrl + "api/empresas/";
        String urlClientes = apiUrl + "api/clientes/1";

        List<MovimentacaoDto> movimentacoesX = List.of(
                new MovimentacaoDto("Depósito", TipoMovimentacao.CREDITO, BigDecimal.valueOf(1000))
        );

        List<MovimentacaoDto> movimentacoesY = List.of(
                new MovimentacaoDto("Depósito", TipoMovimentacao.CREDITO, BigDecimal.valueOf(2000))
        );

        List<MovimentacaoDto> movimentacoesZ = List.of(
                new MovimentacaoDto("Depósito", TipoMovimentacao.CREDITO, BigDecimal.valueOf(2000))
        );

        ClienteDto clienteX = getClienteX(movimentacoesX);
        ClienteDto clienteY = getClienteY(movimentacoesY);
        ClienteDto clienteZ = getClienteZ(movimentacoesZ);


        restTemplate.postForLocation(urlEmpresa, master);
        restTemplate.postForLocation(urlClientes, clienteX);
        restTemplate.postForLocation(urlClientes, clienteY);
        restTemplate.postForLocation(urlClientes, clienteZ);
    }

    private static ClienteDto getClienteX(List<MovimentacaoDto> movimentacoes) {
        List<ContaDto> contas = List.of(
                new ContaDto("001", "1456", "1", "12345", "6", movimentacoes)
        );

        EnderecoDto endereco = new EnderecoDto("Rua X", "Logradouro X", "Complemento X", "Bairro X", "Cidade X", "Estado X", "12345-678");
        return new ClienteDto("Cliente X", "123.456.789-00", null, "clientex@gmail.com", contas, endereco, ZonedDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")));
    }

    private static ClienteDto getClienteY(List<MovimentacaoDto> movimentacoes) {
        List<ContaDto> contas = List.of(
                new ContaDto("001", "2454", "1", "12346", "6", movimentacoes)
        );

        EnderecoDto endereco = new EnderecoDto("Rua Y", "Logradou Y", "Complemento Y", "Bairro Y", "Cidade Y", "Estado Y", "12345-679");
        return new ClienteDto("Cliente Y", null, "00.623.904/0001-73", "clientey@gmail.com", contas, endereco, null);
    }

    private static ClienteDto getClienteZ(List<MovimentacaoDto> movimentacoes) {
        List<ContaDto> contas = List.of(
                new ContaDto("001", "3546", "1", "12344", "6", movimentacoes)
        );

        EnderecoDto endereco = new EnderecoDto("Rua Z", "Logradou Z", "Complemento Z", "Bairro Z", "Cidade Z", "Estado Z", "12345-680");
        return new ClienteDto("Cliente Z", null, "00.623.904/0001-74", "clientez@gmail.com", contas, endereco, null);
    }
}
