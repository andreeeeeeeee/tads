import { useEffect, useState } from 'react';
import { FlatList, StyleSheet, View } from 'react-native';

import { AnotacaoModeracaoItem } from '@/components/anotacao-moderacao-item';
import { EmptyState } from '@/components/empty-state';
import { Header } from '@/components/header';
import { MensagemErro } from '@/components/mensagem-erro';
import { Note } from '@/components/note';
import { PrimaryButton } from '@/components/primary-button';
import { Colors, Spacing } from '@/constants/theme';
import { useAuth } from '@/contexts/auth-context';
import type { AnotacaoModeracao } from '@/models/usuario';
import { ApiError, api } from '@/services/api';

// MODERAÇÃO — src/app/(app)/moderacao.tsx  ->  rota "/moderacao"
//
// O Stack.Protected do layout só monta esta tela para moderador e admin.
// A lista, porém, vem de GET /moderacao/anotacoes: se o token não tiver
// um desses papéis, o servidor responde 403 e a mensagem aparece aqui,
// sem deslogar.
export default function ModeracaoScreen() {
  const { token } = useAuth();

  const [anotacoes, setAnotacoes] = useState<AnotacaoModeracao[]>([]);
  const [carregando, setCarregando] = useState(true);
  const [erro, setErro] = useState<string | null>(null);

  useEffect(() => {
    carregar();
  }, []);

  async function carregar() {
    setErro(null);
    setCarregando(true);
    try {
      setAnotacoes(await api.listarAnotacoesModeracao(token));
    } catch (e) {
      setErro(e instanceof ApiError ? e.message : 'Não foi possível carregar.');
    } finally {
      setCarregando(false);
    }
  }

  return (
    <FlatList
      style={styles.tela}
      contentContainerStyle={styles.conteudo}
      data={anotacoes}
      keyExtractor={(item) => item.id}
      renderItem={({ item }) => <AnotacaoModeracaoItem anotacao={item} />}
      ItemSeparatorComponent={() => <View style={styles.separador} />}
      ListHeaderComponent={
        <View style={styles.cabecalho}>
          <Header
            title="Moderação"
            subtitle="Todas as anotações, com o nome de quem escreveu"
          />
          <MensagemErro>{erro}</MensagemErro>
          <PrimaryButton
            label={carregando ? 'Atualizando…' : 'Atualizar'}
            variant="secondary"
            onPress={carregar}
            disabled={carregando}
          />
        </View>
      }
      ListEmptyComponent={
        carregando ? <EmptyState titulo="Carregando…" /> : erro ? null : (
          <EmptyState titulo="Nenhuma anotação no servidor" />
        )
      }
      ListFooterComponent={
        <View style={styles.rodape}>
          <Note>
            A Ana não chega nesta tela. Com o token dela, a mesma rota
            responde 403 — a proteção do app só esconde o caminho.
          </Note>
        </View>
      }
    />
  );
}

const styles = StyleSheet.create({
  tela: {
    flex: 1,
    backgroundColor: Colors.background,
  },
  conteudo: {
    padding: Spacing.lg,
  },
  cabecalho: {
    gap: Spacing.md,
    marginBottom: Spacing.lg,
  },
  separador: {
    height: Spacing.sm,
  },
  rodape: {
    marginTop: Spacing.lg,
  },
});
