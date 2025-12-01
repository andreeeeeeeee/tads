
SET search_path TO public;

CREATE OR REPLACE FUNCTION contar_ingressos_vendidos(sessao_id_aux INTEGER)
RETURNS INTEGER AS $$
DECLARE
    qtde_vendidos INTEGER;
BEGIN
    SELECT COUNT(*) INTO qtde_vendidos
    FROM ingresso
    WHERE sessao_id = sessao_id_aux AND cpf IS NOT NULL;

    RETURN qtde_vendidos;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION assentos_disponiveis(sessao_id_aux INTEGER)
RETURNS TABLE(poltrona_id INTEGER, fileira CHAR, posicao INTEGER, tipo VARCHAR) AS $$
BEGIN
    RETURN QUERY
    SELECT p.id, p.fileira, p.posicao, p.tipo
    FROM poltrona p
    WHERE p.sala_id = (SELECT s.sala_id FROM sessao s WHERE s.id = sessao_id_aux)
    AND NOT EXISTS (
        SELECT 1 FROM ingresso i
        WHERE i.poltrona_id = p.id AND i.sessao_id = sessao_id_aux AND i.cpf IS NOT NULL
    );
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE vender_ingresso(
    cpf_aux CHAR(11),
    sessao_id_aux INTEGER,
    poltrona_id_aux INTEGER,
    valor_aux DOUBLE PRECISION
) AS $$
DECLARE
    capacidade_sala INTEGER;
    ingressos_vendidos INTEGER;
BEGIN
    IF EXISTS (SELECT 1 FROM ingresso WHERE sessao_id = sessao_id_aux AND poltrona_id = poltrona_id_aux AND cpf IS NOT NULL) THEN
        RAISE EXCEPTION 'Poltrona já ocupada para esta sessão';
    END IF;

    SELECT s.ocupacao INTO capacidade_sala
    FROM sala s
    JOIN sessao se ON se.sala_id = s.id
    WHERE se.id = sessao_id_aux;

    SELECT COUNT(*) INTO ingressos_vendidos
    FROM ingresso
    WHERE sessao_id = sessao_id_aux AND cpf IS NOT NULL;

    IF ingressos_vendidos >= capacidade_sala THEN
        RAISE EXCEPTION 'Capacidade da sala excedida';
    END IF;

    INSERT INTO ingresso (cpf, sessao_id, poltrona_id, valor)
    VALUES (cpf_aux, sessao_id_aux, poltrona_id_aux, valor_aux);

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION relatorio_ocupacao_sessao(sessao_id_aux INTEGER)
RETURNS TABLE(sessao_id INTEGER, titulo_filme TEXT, sala_id INTEGER, capacidade INTEGER, ocupados INTEGER, percentual NUMERIC) AS $$
DECLARE
    titulo_aux TEXT;
    sala_aux INTEGER;
    cap_aux INTEGER;
    ocup_aux INTEGER;
    perc_aux NUMERIC;
BEGIN
    SELECT f.titulo, s.sala_id, sa.ocupacao
    INTO titulo_aux, sala_aux, cap_aux
    FROM sessao s
    JOIN filme f ON f.id = s.filme_id
    JOIN sala sa ON sa.id = s.sala_id
    WHERE s.id = sessao_id_aux;

    SELECT COUNT(*) INTO ocup_aux
    FROM ingresso i
    WHERE i.sessao_id = sessao_id_aux AND i.cpf IS NOT NULL;

    perc_aux := (ocup_aux::NUMERIC / NULLIF(cap_aux, 0)::NUMERIC) * 100;

    RETURN QUERY SELECT sessao_id_aux, titulo_aux, sala_aux, cap_aux, ocup_aux, COALESCE(perc_aux, 0);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION impedir_venda_assento_ocupado()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM ingresso WHERE sessao_id = NEW.sessao_id AND poltrona_id = NEW.poltrona_id AND cpf IS NOT NULL AND id != NEW.id) THEN
        RAISE EXCEPTION 'Assento já ocupado';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_impedir_venda_assento_ocupado
BEFORE INSERT OR UPDATE ON ingresso
FOR EACH ROW
EXECUTE FUNCTION impedir_venda_assento_ocupado();

CREATE TABLE IF NOT EXISTS relatorio_ocupacao (
    id SERIAL PRIMARY KEY,
    sessao_id INTEGER REFERENCES sessao(id),
    data_atualizacao TIMESTAMP DEFAULT NOW(),
    percentual_ocupacao NUMERIC(5,2)
);

CREATE OR REPLACE FUNCTION atualizar_relatorio_ocupacao()
RETURNS TRIGGER AS $$
DECLARE
    perc NUMERIC;
    cap INTEGER;
    ocup INTEGER;
BEGIN
    SELECT sa.ocupacao INTO cap
    FROM sala sa
    JOIN sessao s ON s.sala_id = sa.id
    WHERE s.id = NEW.sessao_id;

    SELECT COUNT(*) INTO ocup
    FROM ingresso i
    WHERE i.sessao_id = NEW.sessao_id AND i.cpf IS NOT NULL;

    perc := (ocup::NUMERIC / cap::NUMERIC) * 100;

    INSERT INTO relatorio_ocupacao (sessao_id, percentual_ocupacao)
    VALUES (NEW.sessao_id, perc);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_atualizar_relatorio_ocupacao
AFTER INSERT OR UPDATE OR DELETE ON ingresso
FOR EACH ROW
EXECUTE FUNCTION atualizar_relatorio_ocupacao();
