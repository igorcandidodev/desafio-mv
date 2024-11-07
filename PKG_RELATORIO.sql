CREATE OR REPLACE PACKAGE SYSTEM.RELATORIO IS

  PROCEDURE RELATORIO_SALDO_CLIENTE(p_id_empresa        NUMBER,
                                    p_id_cliente        NUMBER,
                                    p_relatorio_json    OUT CLOB);

END RELATORIO;
/

CREATE OR REPLACE PACKAGE BODY SYSTEM.RELATORIO IS

  PROCEDURE RELATORIO_SALDO_CLIENTE(p_id_empresa IN NUMBER,
                                    p_id_cliente IN NUMBER,
                                    p_relatorio_json OUT CLOB)
    IS
      CURSOR C_BUSCAR_CONTAS_CLIENTE(p_cliente NUMBER) IS
        SELECT c.id, c.saldo
        FROM SYSTEM.CONTAS c
        WHERE c.CLIENTE_ID = p_cliente;

      CURSOR C_BUSCAR_MOVIMENTACOES_CLIENTE(p_conta NUMBER) IS
        SELECT m.TIPO_MOVIMENTACAO, m.VALOR, m.DATA_CRIACAO, m.DESCRICAO
        FROM SYSTEM.MOVIMENTACOES m
        WHERE m.CONTA_ID = p_conta;

      CURSOR C_BUSCAR_DADOS_CLIENTE(p_cliente NUMBER,
                                    p_empresa NUMBER) IS
        SELECT c.DATA_CADASTRO,
               c.NOME,
               e.LOGRADOURO,
               e.NUMERO,
               e.COMPLEMENTO,
               e.BAIRRO,
               e.CIDADE,
               e.ESTADO,
               e.CEP
        FROM SYSTEM.CLIENTES c, SYSTEM.ENDERECOS e, SYSTEM.EMPRESA_CLIENTES ec
        WHERE c.ID = p_cliente
        AND c.ID = ec.CLIENTE_ID
        AND ec.EMPRESA_ID = p_empresa
        AND c.ENDERECO_ID = e.ID;

      v_relatorio_json          CLOB   := '{';
      v_contador_debito         NUMBER := 0;
      v_contador_credito        NUMBER := 0;
      v_contador_taxa           NUMBER := 0;
      v_contador_saldo_atual    NUMBER := 0;
      v_contador_saldo_sem_taxa NUMBER := 0;

    BEGIN
        FOR conta IN C_BUSCAR_CONTAS_CLIENTE(p_id_cliente)
        LOOP
            v_contador_saldo_atual := v_contador_saldo_atual + conta.saldo;

            FOR movimentacao IN C_BUSCAR_MOVIMENTACOES_CLIENTE(conta.ID)
            LOOP
                IF movimentacao.TIPO_MOVIMENTACAO = 0 THEN
                    v_contador_credito := v_contador_credito + 1;
                    v_contador_saldo_sem_taxa := v_contador_saldo_sem_taxa + movimentacao.VALOR;
                ELSIF movimentacao.TIPO_MOVIMENTACAO = 1 and movimentacao.DESCRICAO != 'Taxa de serviço por movimentação' THEN
                    v_contador_debito := v_contador_debito + 1;
                END IF;

                IF movimentacao.DESCRICAO LIKE 'Taxa de serviço por movimentação' THEN
                    v_contador_taxa := v_contador_taxa + 1;
                END IF;

            END LOOP;

        END LOOP;

        FOR cliente IN C_BUSCAR_DADOS_CLIENTE(p_id_cliente, p_id_empresa)
        LOOP
            v_relatorio_json := v_relatorio_json || '"dataCadastro": "' || cliente.DATA_CADASTRO || '",';
            v_relatorio_json := v_relatorio_json || '"nome": "' || cliente.NOME || '",';
            v_relatorio_json := v_relatorio_json || '"logradouro": "' || cliente.LOGRADOURO || '",';
            v_relatorio_json := v_relatorio_json || '"numero": "' || cliente.NUMERO || '",';
            v_relatorio_json := v_relatorio_json || '"complemento": "' || cliente.COMPLEMENTO || '",';
            v_relatorio_json := v_relatorio_json || '"bairro": "' || cliente.BAIRRO || '",';
            v_relatorio_json := v_relatorio_json || '"cidade": "' || cliente.CIDADE || '",';
            v_relatorio_json := v_relatorio_json || '"estado": "' || cliente.ESTADO || '",';
            v_relatorio_json := v_relatorio_json || '"cep": "' || cliente.CEP || '",';
        END LOOP;

        v_relatorio_json := v_relatorio_json || '"movimentacao_credito": ' || v_contador_credito || ',';
        v_relatorio_json := v_relatorio_json || '"movimentacao_debito": ' || v_contador_debito || ',';
        v_relatorio_json := v_relatorio_json || '"saldo_inicial": ' || v_contador_saldo_sem_taxa || ',';
        v_relatorio_json := v_relatorio_json || '"saldo_final": ' || v_contador_saldo_atual || ',';
        v_relatorio_json := v_relatorio_json || '"taxa_servico": ' || v_contador_taxa || '}';

        p_relatorio_json := v_relatorio_json;

    EXCEPTION
        WHEN OTHERS THEN
            p_relatorio_json := '{"error": "An error occurred"}';
    END RELATORIO_SALDO_CLIENTE;

END RELATORIO;
/