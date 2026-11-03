import { Stack } from 'expo-router';

import { Colors } from '@/constants/theme';

// Pilha interna de /anotacoes: a lista (index) e o detalhe ([id]).
// O layout do grupo (app) esconde o cabeçalho deste grupo para não
// empilhar duas barras.
export default function AnotacoesLayout() {
  return (
    <Stack
      screenOptions={{
        headerStyle: { backgroundColor: Colors.background },
        headerTintColor: Colors.text,
        contentStyle: { backgroundColor: Colors.background },
      }}>
      <Stack.Screen name="index" options={{ title: 'Minhas anotações' }} />
      <Stack.Screen name="[id]" options={{ title: 'Anotação' }} />
    </Stack>
  );
}
