import { ActivityIndicator, StyleSheet, Text, View } from 'react-native';

import { PrimaryButton } from '@/components/primary-button';
import { Colors, Spacing } from '@/constants/theme';

interface TelaDeEsperaProps {
  mensagem: string;
  onTentarNovamente?: () => void;
}

// Cobre a tela inteira enquanto um login, um cadastro ou a restauração
// da sessão ainda não terminou. O botão só aparece quando a espera é um
// erro de conexão — aí a pessoa pode repetir o GET /auth/me.
export function TelaDeEspera({ mensagem, onTentarNovamente }: TelaDeEsperaProps) {
  return (
    <View style={styles.tela}>
      <ActivityIndicator size="large" color={Colors.primary} />
      <Text style={styles.mensagem}>{mensagem}</Text>
      {onTentarNovamente ? (
        <PrimaryButton label="Tentar de novo" onPress={onTentarNovamente} />
      ) : null}
    </View>
  );
}

const styles = StyleSheet.create({
  tela: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    gap: Spacing.md,
    padding: Spacing.lg,
    backgroundColor: Colors.background,
  },
  mensagem: {
    fontSize: 16,
    textAlign: 'center',
    color: Colors.text,
  },
});
