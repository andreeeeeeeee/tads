import { Link } from 'expo-router';
import { Pressable, StyleSheet, Text } from 'react-native';

import { formatarDataHora } from '@/components/formatar-data';
import { Colors, Spacing } from '@/constants/theme';
import type { Anotacao } from '@/models/usuario';

interface AnotacaoItemProps {
  anotacao: Anotacao;
}

// O toque abre /anotacoes/[id] só com o id. O texto é buscado de novo
// na tela de detalhe — não vai no parâmetro da rota.
export function AnotacaoItem({ anotacao }: AnotacaoItemProps) {
  return (
    <Link href={{ pathname: '/anotacoes/[id]', params: { id: anotacao.id } }} asChild>
      <Pressable style={({ pressed }) => [styles.item, pressed && styles.pressed]}>
        <Text style={styles.texto}>{anotacao.texto}</Text>
        <Text style={styles.meta}>{formatarDataHora(anotacao.criadaEm)}</Text>
      </Pressable>
    </Link>
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
  pressed: {
    opacity: 0.7,
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
