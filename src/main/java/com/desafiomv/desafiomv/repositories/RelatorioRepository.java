package com.desafiomv.desafiomv.repositories;

import com.desafiomv.desafiomv.dtos.ClienteSaldoRelatorioDto;

import java.sql.Connection;
import java.sql.SQLException;

public interface RelatorioRepository {

    ClienteSaldoRelatorioDto buscarClienteSaldoRelatorio(Connection connection, Long idEmpresa, Long idCliente) throws SQLException;
}
