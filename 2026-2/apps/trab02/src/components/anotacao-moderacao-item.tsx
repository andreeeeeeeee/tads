import { StyleSheet, Text, View } from 'react-native';

import { formatarDataHora } from '@/components/formatar-data';
import { Colors, Spacing } from '@/constants/theme';
import type { AnotacaoModeracao } from '@/models/usuario';

interface AnotacaoModeracaoItemProps {
  anotacao: AnotacaoModeracao;
}

export function AnotacaoModeracaoItem({ anotacao }: AnotacaoModeracaoItemProps) {
  return (
    <View style={styles.item}>
      <Text style={styles.texto}>{anotacao.texto}</Text>
      <Text style={styles.meta}>
        {anotacao.autor} · {formatarDataHora(anotacao.criadaEm)}
      </Text>
    </View>
  );
}

const styles = StyleSheet.create({
  item: {
    gap: Spacing.xs,
    padding: Spacing.md,
    borderRadius: Spacing.sm,
    borderWidth: 1,
    borderColor: Colors.border,
    backgroundColor: Colors.surface,
  },
  texto: {
    fontSize: 16,
    color: Colors.text,
  },
  meta: {
    fontSize: 12,
    color: Colors.textSecondary,
  },
});
