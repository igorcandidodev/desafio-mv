package com.desafiomv.desafiomv.repositories.impl;

import com.desafiomv.desafiomv.dtos.ClienteSaldoRelatorioDto;
import com.desafiomv.desafiomv.repositories.RelatorioRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.text.SimpleDateFormat;

@Repository
public class RelatorioRepositoryImpl implements RelatorioRepository {

    @Override
    public ClienteSaldoRelatorioDto buscarClienteSaldoRelatorio(Connection connection, Long idEmpresa, Long idCliente) throws SQLException {
        String sql = "{call RELATORIO.RELATORIO_SALDO_CLIENTE(?, ?, ?)}";
        try (CallableStatement callableStatement = connection.prepareCall(sql)) {

            callableStatement.setLong(1, idEmpresa);
            callableStatement.setLong(2, idCliente);

            callableStatement.registerOutParameter(3, Types.CLOB);

            callableStatement.execute();

            Clob jsonClob = callableStatement.getClob(3);
            String jsonString = jsonClob.getSubString(1, (int) jsonClob.length());

            return convertJsonToClienteSaldoRelatorioDto(jsonString);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private ClienteSaldoRelatorioDto convertJsonToClienteSaldoRelatorioDto(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yy HH:mm:ss,SSSSSS Z"));
        return objectMapper.readValue(jsonString, ClienteSaldoRelatorioDto.class);
    }
}
