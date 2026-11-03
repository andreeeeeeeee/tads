import { StyleSheet, Text, View } from 'react-native';

import { formatarDataHora } from '@/components/formatar-data';
import { Colors, Spacing } from '@/constants/theme';
import type { Anotacao } from '@/models/usuario';

interface AnotacaoDetalheProps {
  anotacao: Anotacao;
}

// O que a tela /anotacoes/[id] mostra depois que o servidor respondeu.
// O texto veio da API, não de um parâmetro de navegação.
export function AnotacaoDetalhe({ anotacao }: AnotacaoDetalheProps) {
  return (
    <View style={styles.cartao}>
      <Text style={styles.texto}>{anotacao.texto}</Text>
      <Text style={styles.data}>{formatarDataHora(anotacao.criadaEm)}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  cartao: {
    gap: Spacing.sm,
    padding: Spacing.md,
    borderRadius: Spacing.sm,
    borderWidth: 1,
    borderColor: Colors.border,
    backgroundColor: Colors.surface,
  },
  texto: {
    fontSize: 18,
    lineHeight: 26,
    color: Colors.text,
  },
  data: {
    fontSize: 14,
    color: Colors.textSecondary,
  },
});
