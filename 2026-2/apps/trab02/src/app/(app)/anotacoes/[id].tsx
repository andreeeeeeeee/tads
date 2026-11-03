import { useLocalSearchParams } from 'expo-router';
import { useEffect, useState } from 'react';
import { ScrollView, StyleSheet } from 'react-native';

import { AnotacaoDetalhe } from '@/components/anotacao-detalhe';
import { Header } from '@/components/header';
import { MensagemErro } from '@/components/mensagem-erro';
import { TelaDeEspera } from '@/components/tela-de-espera';
import { Colors, Spacing } from '@/constants/theme';
import { useAuth } from '@/contexts/auth-context';
import type { Anotacao } from '@/models/usuario';
import { ApiError, api } from '@/services/api';

// DETALHE — src/app/(app)/anotacoes/[id].tsx  ->  rota "/anotacoes/abc"
//
// O id vem do caminho. O texto NÃO veio junto: esta tela pede a anotação
// de novo ao servidor. Se o id for de outra pessoa, a resposta é 404.
export default function AnotacaoScreen() {
  const { id: idParam } = useLocalSearchParams<{ id: string }>();
  const id = typeof idParam === 'string' ? idParam : idParam?.[0];
  const { token } = useAuth();

  const [anotacao, setAnotacao] = useState<Anotacao | null>(null);
  const [carregando, setCarregando] = useState(true);
  const [erro, setErro] = useState<string | null>(null);

  useEffect(() => {
    if (!id) {
      setCarregando(false);
      setErro('Anotação não encontrada.');
      return;
    }

    const anotacaoId = id;
    let ativo = true;

    async function carregar() {
      setCarregando(true);
      setErro(null);
      try {
        const dados = await api.buscarAnotacao(token, anotacaoId);
        if (ativo) setAnotacao(dados);
      } catch (e) {
        if (!ativo) return;
        setAnotacao(null);
        setErro(e instanceof ApiError ? e.message : 'Não foi possível carregar.');
      } finally {
        if (ativo) setCarregando(false);
      }
    }

    void carregar();
    return () => {
      ativo = false;
    };
  }, [id, token]);

  if (carregando) {
    return <TelaDeEspera mensagem="Carregando anotação…" />;
  }

  return (
    <ScrollView style={styles.tela} contentContainerStyle={styles.conteudo}>
      <Header title="Anotação" subtitle="Buscada no servidor pelo id da rota" />
      {erro ? <MensagemErro>{erro}</MensagemErro> : null}
      {anotacao ? <AnotacaoDetalhe anotacao={anotacao} /> : null}
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  tela: {
    flex: 1,
    backgroundColor: Colors.background,
  },
  conteudo: {
    padding: Spacing.lg,
    gap: Spacing.lg,
  },
});
