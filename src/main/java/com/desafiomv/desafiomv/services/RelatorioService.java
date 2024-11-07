package com.desafiomv.desafiomv.services;

import com.desafiomv.desafiomv.dtos.ClienteSaldoRelatorioDto;
import com.desafiomv.desafiomv.entities.Cliente;
import com.desafiomv.desafiomv.entities.Empresa;
import com.desafiomv.desafiomv.exceptions.ChamadaProcedureException;
import com.desafiomv.desafiomv.repositories.EmpresaRepository;
import com.desafiomv.desafiomv.repositories.RelatorioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RelatorioService {

    private final RelatorioRepository relatorioRepository;
    private final EmpresaRepository empresaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public RelatorioService(RelatorioRepository relatorioRepository, EmpresaRepository empresaRepository) {
        this.relatorioRepository = relatorioRepository;
        this.empresaRepository = empresaRepository;
    }

    public List<ClienteSaldoRelatorioDto> buscarClienteSaldoRelatorio(Long idEmpresa) {
        Session session = entityManager.unwrap(Session.class);
        Connection connection = session.doReturningWork(conn -> conn.unwrap(Connection.class));

        var empresa = empresaRepository.findById(idEmpresa).orElseThrow(() -> new ObjectNotFoundException("Empresa não encontrada", Empresa.class));

        List<ClienteSaldoRelatorioDto> relatorio = new ArrayList<>();

        for(Cliente cliente : empresa.getClientes()) {
            try {
                relatorio.add(relatorioRepository.buscarClienteSaldoRelatorio(connection, empresa.getId(), cliente.getId()));
            } catch (SQLException e) {
                throw new ChamadaProcedureException("Erro ao buscar relatório de saldo do cliente " + e.getMessage());
            }
        }

        return relatorio;
    }
}
