CREATE TABLE IF NOT EXISTS cliente (
    cpf character(11) PRIMARY KEY CHECK(valida_cpf(cpf) = TRUE),
    nome varchar(100) NOT NULL,
    email varchar(150),
    telefone varchar(20),
    data_cadastro timestamp DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS ingresso_simulado (
    id serial PRIMARY KEY,
    cpf character(11) NOT NULL,
    sessao_id integer REFERENCES sessao(id),
    valor numeric(8,2) CHECK (valor >= 0),
    poltrona_id integer REFERENCES poltrona(id),
    data_simulacao timestamp DEFAULT NOW(),
    UNIQUE(sessao_id, poltrona_id)
);

CREATE TABLE IF NOT EXISTS log_remanejamento (
    id serial PRIMARY KEY,
    cpf character(11) NOT NULL,
    sessao_id integer REFERENCES sessao(id),
    poltrona_antiga integer REFERENCES poltrona(id),
    poltrona_nova integer REFERENCES poltrona(id),
    data_remanejamento timestamp DEFAULT NOW(),
    observacoes text
);

INSERT INTO cliente (cpf, nome, email, telefone) VALUES 
('17658586072', 'Joao Silva Santos', 'joao.silva@email.com', '(11) 99999-1234')
ON CONFLICT (cpf) DO NOTHING;

INSERT INTO cliente (cpf, nome, email, telefone) VALUES 
('11144477735', 'Pedro Almeida', 'pedro@email.com', '(11) 98888-1111'),
('25804321079', 'Ana Carolina', 'ana@email.com', '(11) 97777-2222'),
('32516794085', 'Carlos Eduardo', 'carlos@email.com', '(11) 96666-3333'),
('78965412378', 'Juliana Santos', 'juliana@email.com', '(11) 95555-4444'),
('95175346802', 'Roberto Lima', 'roberto@email.com', '(11) 94444-5555')
ON CONFLICT (cpf) DO NOTHING;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints 
        WHERE constraint_name = 'fk_ingresso_cliente'
    ) THEN
        ALTER TABLE ingresso 
        ADD CONSTRAINT fk_ingresso_cliente 
        FOREIGN KEY (cpf) REFERENCES cliente(cpf);
    END IF;
END $$;

DELETE FROM ingresso WHERE sessao_id IN (
    SELECT s1.id FROM sessao s1
    INNER JOIN sessao s2 ON s1.filme_id = s2.filme_id 
                        AND s1.sala_id = s2.sala_id
                        AND s1.data = s2.data 
                        AND s1.hora_inicio = s2.hora_inicio
                        AND s1.id > s2.id
);

DELETE FROM sessao WHERE id IN (
    SELECT s1.id FROM sessao s1
    INNER JOIN sessao s2 ON s1.filme_id = s2.filme_id 
                        AND s1.sala_id = s2.sala_id
                        AND s1.data = s2.data 
                        AND s1.hora_inicio = s2.hora_inicio
                        AND s1.id > s2.id
);

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM sessao 
        WHERE filme_id = 1 AND sala_id = 1 
        AND data = CURRENT_DATE + INTERVAL '1 day' 
        AND hora_inicio = '20:00:00'
    ) THEN
        INSERT INTO sessao (filme_id, sala_id, data, hora_inicio, hora_fim) VALUES 
        (1, 1, CURRENT_DATE + INTERVAL '1 day', '20:00:00', '22:00:00'),
        (2, 2, CURRENT_DATE + INTERVAL '1 day', '18:00:00', '20:00:00'),
        (1, 1, CURRENT_DATE + INTERVAL '2 days', '20:00:00', '22:00:00'),
        (2, 2, CURRENT_DATE + INTERVAL '2 days', '18:00:00', '20:00:00'),
        (1, 2, CURRENT_DATE + INTERVAL '3 days', '20:00:00', '22:00:00'),
        (2, 1, CURRENT_DATE + INTERVAL '3 days', '18:00:00', '20:00:00'),
        (1, 1, CURRENT_DATE + INTERVAL '4 days', '20:00:00', '22:00:00'),
        (2, 2, CURRENT_DATE + INTERVAL '4 days', '18:00:00', '20:00:00');
    END IF;
END $$;

INSERT INTO poltrona (fileira, posicao, tipo, sala_id) VALUES
('B', 1, 'simples', 1),
('B', 2, 'simples', 1),
('B', 3, 'luxo', 1),
('B', 4, 'simples', 1),
('B', 5, 'simples', 1),
('C', 1, 'simples', 1),
('C', 2, 'simples', 1),
('C', 3, 'simples', 1),
('C', 4, 'luxo', 1),
('C', 5, 'simples', 1),
('A', 2, 'simples', 2),
('A', 3, 'simples', 2),
('A', 4, 'luxo', 2),
('A', 5, 'simples', 2),
('B', 1, 'simples', 2),
('B', 2, 'simples', 2),
('B', 3, 'luxo', 2),
('B', 4, 'simples', 2),
('B', 5, 'simples', 2);

INSERT INTO ingresso (cpf, sessao_id, valor, poltrona_id) VALUES 
('17658586072', 5, 25.00, 8),
('17658586072', 6, 25.00, 9)
ON CONFLICT (sessao_id, poltrona_id) DO NOTHING;

INSERT INTO ingresso (cpf, sessao_id, valor, poltrona_id) VALUES 
('11144477735', 1, 25.00, 3),
('11144477735', 2, 25.00, 7),
('11144477735', 3, 25.00, 10),
('11144477735', 4, 25.00, 11),
('11144477735', 5, 25.00, 12),
('11144477735', 6, 25.00, 13)
ON CONFLICT (sessao_id, poltrona_id) DO NOTHING;

INSERT INTO ingresso (cpf, sessao_id, valor, poltrona_id) VALUES 
('25804321079', 1, 25.00, 4),
('25804321079', 3, 25.00, 14),
('25804321079', 5, 25.00, 15),
('25804321079', 7, 25.00, 16),
('25804321079', 2, 25.00, 17)
ON CONFLICT (sessao_id, poltrona_id) DO NOTHING;

INSERT INTO ingresso (cpf, sessao_id, valor, poltrona_id) VALUES 
('32516794085', 2, 25.00, 18),
('32516794085', 4, 25.00, 19),
('32516794085', 6, 25.00, 20),
('32516794085', 1, 25.00, 21),
('32516794085', 3, 25.00, 22)
ON CONFLICT (sessao_id, poltrona_id) DO NOTHING;

INSERT INTO ingresso (cpf, sessao_id, valor, poltrona_id) VALUES 
('78965412378', 3, 25.00, 23),
('78965412378', 5, 25.00, 24),
('78965412378', 7, 25.00, 25),
('78965412378', 8, 25.00, 26),
('78965412378', 4, 25.00, 27)
ON CONFLICT (sessao_id, poltrona_id) DO NOTHING;

CREATE OR REPLACE FUNCTION relatorio_clientes_frequentes()
RETURNS TABLE (
    cpf character(11),
    nome varchar(100),
    quantidade_ingressos bigint,
    total_gasto numeric,
    valor_medio_ingresso numeric
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        c.cpf,
        c.nome,
        COUNT(i.id) as quantidade_ingressos,
        SUM(i.valor) as total_gasto,
        AVG(i.valor) as valor_medio_ingresso
    FROM cliente c
    INNER JOIN ingresso i ON c.cpf = i.cpf
    GROUP BY c.cpf, c.nome
    HAVING COUNT(DISTINCT i.sessao_id) >= 2
    ORDER BY total_gasto DESC;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION verificar_sessoes_simultaneas()
RETURNS TABLE (
    sessao_id1 integer,
    sessao_id2 integer,
    filme_id integer,
    titulo_filme text,
    sala_id1 integer,
    sala_id2 integer,
    data_conflito date,
    hora_conflito time
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        s1.id as sessao_id1,
        s2.id as sessao_id2,
        s1.filme_id,
        f.titulo as titulo_filme,
        s1.sala_id as sala_id1,
        s2.sala_id as sala_id2,
        s1.data as data_conflito,
        s1.hora_inicio as hora_conflito
    FROM sessao s1
    INNER JOIN sessao s2 ON s1.filme_id = s2.filme_id 
                        AND s1.data = s2.data
                        AND s1.hora_inicio = s2.hora_inicio
                        AND s1.sala_id != s2.sala_id 
                        AND s1.id < s2.id
    INNER JOIN filme f ON s1.filme_id = f.id
    ORDER BY s1.data, s1.hora_inicio, f.titulo;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION simular_ocupacao_sessao(
    p_sessao_id integer,
    p_percentual numeric
) RETURNS integer AS $$
DECLARE
    v_total_poltronas integer;
    v_ocupadas integer;
    v_poltronas_para_simular integer;
    v_poltronas_simuladas integer := 0;
    v_poltrona_id integer;
BEGIN
    SELECT COUNT(p.id) INTO v_total_poltronas
    FROM poltrona p
    INNER JOIN sessao s ON p.sala_id = s.sala_id
    WHERE s.id = p_sessao_id;
    
    SELECT COUNT(*) INTO v_ocupadas
    FROM ingresso i
    WHERE i.sessao_id = p_sessao_id;
    
    v_poltronas_para_simular := CEIL(v_total_poltronas * p_percentual / 100.0) - v_ocupadas;
    
    IF v_poltronas_para_simular <= 0 THEN
        RETURN 0;
    END IF;
    
    FOR v_poltrona_id IN (
        SELECT p.id
        FROM poltrona p
        INNER JOIN sessao s ON p.sala_id = s.sala_id
        WHERE s.id = p_sessao_id 
        AND p.id NOT IN (
            SELECT poltrona_id FROM ingresso WHERE sessao_id = p_sessao_id
            UNION
            SELECT poltrona_id FROM ingresso_simulado WHERE sessao_id = p_sessao_id
        )
        ORDER BY p.fileira, p.posicao
        LIMIT v_poltronas_para_simular
    ) LOOP
        INSERT INTO ingresso_simulado (cpf, sessao_id, valor, poltrona_id)
        VALUES ('00000000000', p_sessao_id, 25.00, v_poltrona_id)
        ON CONFLICT (sessao_id, poltrona_id) DO NOTHING;
        
        v_poltronas_simuladas := v_poltronas_simuladas + 1;
    END LOOP;
    
    RETURN v_poltronas_simuladas;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION ajustar_precos_baixa_ocupacao()
RETURNS integer AS $$
DECLARE
    v_ingressos_ajustados integer := 0;
BEGIN
    UPDATE ingresso 
    SET valor = valor * 0.8
    WHERE sessao_id IN (
        SELECT s.id
        FROM sessao s
        WHERE s.data = CURRENT_DATE 
        AND s.hora_inicio BETWEEN CURRENT_TIME AND CURRENT_TIME + INTERVAL '1 hour'
        AND (
            SELECT COUNT(*)::FLOAT / (
                SELECT COUNT(*) FROM poltrona WHERE sala_id = s.sala_id
            ) * 100
            FROM ingresso i WHERE i.sessao_id = s.id
        ) < 30
    )
    AND valor > 0;
    
    GET DIAGNOSTICS v_ingressos_ajustados = ROW_COUNT;
    RETURN v_ingressos_ajustados;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION remanejar_poltrona(
    p_cpf character(11),
    p_sessao_id integer,
    p_poltrona_antiga integer,
    p_poltrona_nova integer
) RETURNS boolean AS $$
DECLARE
    v_ingresso_id integer;
    v_ocupada integer;
BEGIN
    SELECT id INTO v_ingresso_id
    FROM ingresso 
    WHERE cpf = p_cpf AND sessao_id = p_sessao_id AND poltrona_id = p_poltrona_antiga;
    
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Ingresso nao encontrado para este CPF e poltrona';
    END IF;
    
    SELECT COUNT(*) INTO v_ocupada
    FROM ingresso 
    WHERE sessao_id = p_sessao_id AND poltrona_id = p_poltrona_nova;
    
    IF v_ocupada > 0 THEN
        RAISE EXCEPTION 'Nova poltrona nao esta disponivel';
    END IF;
    
    UPDATE ingresso 
    SET poltrona_id = p_poltrona_nova 
    WHERE id = v_ingresso_id;
    
    INSERT INTO log_remanejamento (cpf, sessao_id, poltrona_antiga, poltrona_nova)
    VALUES (p_cpf, p_sessao_id, p_poltrona_antiga, p_poltrona_nova);
    
    RETURN TRUE;
EXCEPTION
    WHEN OTHERS THEN
        RETURN FALSE;
END;
$$ LANGUAGE plpgsql;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM sessao 
        WHERE filme_id = 1 AND sala_id = 1 
        AND data = '2025-12-15' 
        AND hora_inicio = '20:00:00'
    ) THEN
        INSERT INTO sessao (filme_id, sala_id, data, hora_inicio, hora_fim) VALUES 
        (1, 1, '2025-12-15', '20:00:00', '22:00:00'),
        (1, 2, '2025-12-15', '20:00:00', '22:00:00'), 
        (2, 1, '2025-12-16', '19:30:00', '21:30:00'),
        (2, 2, '2025-12-16', '19:30:00', '21:30:00');
    END IF;
END $$; 

SELECT 'Tabela cliente criada com sucesso!' as status WHERE EXISTS (
    SELECT 1 FROM information_schema.tables WHERE table_name = 'cliente'
);

SELECT 'Tabela ingresso_simulado criada com sucesso!' as status WHERE EXISTS (
    SELECT 1 FROM information_schema.tables WHERE table_name = 'ingresso_simulado'
);

SELECT 'Tabela log_remanejamento criada com sucesso!' as status WHERE EXISTS (
    SELECT 1 FROM information_schema.tables WHERE table_name = 'log_remanejamento'
);

SELECT 'Todas as funcoes foram criadas com sucesso!' as status WHERE EXISTS (
    SELECT 1 FROM pg_proc WHERE proname = 'relatorio_clientes_frequentes'
) AND EXISTS (
    SELECT 1 FROM pg_proc WHERE proname = 'verificar_sessoes_simultaneas'
) AND EXISTS (
    SELECT 1 FROM pg_proc WHERE proname = 'simular_ocupacao_sessao'
) AND EXISTS (
    SELECT 1 FROM pg_proc WHERE proname = 'ajustar_precos_baixa_ocupacao'
) AND EXISTS (
    SELECT 1 FROM pg_proc WHERE proname = 'remanejar_poltrona'
);

SELECT 'TESTE 1: Relatorio de Clientes Frequentes' as teste;
SELECT * FROM relatorio_clientes_frequentes();

SELECT 'TESTE 2: Verificar Sessoes Simultaneas' as teste;
SELECT * FROM verificar_sessoes_simultaneas();

SELECT 'TESTE 3: Simulacao de Ocupacao (70%)' as teste;
SELECT simular_ocupacao_sessao(1, 70.0) as poltronas_simuladas;

SELECT 'TESTE 4: Estrutura da Tabela de Log' as teste;
SELECT column_name, data_type FROM information_schema.columns 
WHERE table_name = 'log_remanejamento' 
ORDER BY ordinal_position;

SELECT 'TESTE 5: Teste de Remanejamento' as teste;
SELECT remanejar_poltrona('17658586072', 1, 1, 5) as resultado_remanejamento;

SELECT '==== SETUP CONCLUIDO COM SUCESSO! ====' as status;
