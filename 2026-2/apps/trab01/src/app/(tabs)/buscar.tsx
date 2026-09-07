import { StyleSheet, Text, View } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';

import { SpotifyColors, SpotifySpacing } from '@/constants/spotify-theme';

export default function BuscarScreen() {
  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.content}>
        <Text style={styles.title}>Buscar</Text>
        <Text style={styles.subtitle}>
          Tela placeholder. A busca da biblioteca fica no ícone da barra superior.
        </Text>
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: SpotifyColors.black,
  },
  content: {
    flex: 1,
    padding: SpotifySpacing.xl,
    justifyContent: 'center',
    gap: SpotifySpacing.sm,
  },
  title: {
    color: SpotifyColors.white,
    fontSize: 28,
    fontWeight: '700',
  },
  subtitle: {
    color: SpotifyColors.textSecondary,
    fontSize: 15,
    lineHeight: 22,
  },
});
