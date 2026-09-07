import { Stack } from "expo-router";
import { StatusBar } from "expo-status-bar";

import { SpotifyColors } from "@/constants/spotify-theme";

export default function RootLayout() {
  return (
    <>
      <StatusBar style="light" />
      <Stack
        screenOptions={{
          headerStyle: { backgroundColor: SpotifyColors.black },
          headerTintColor: SpotifyColors.white,
          contentStyle: { backgroundColor: SpotifyColors.black },
        }}
      >
        <Stack.Screen name="(tabs)" options={{ headerShown: false }} />
        <Stack.Screen
          name="playlist/[id]"
          options={{ headerShown: false, title: "Playlist" }}
        />
        <Stack.Screen
          name="playlist/criar"
          options={{
            presentation: "modal",
            headerShown: false,
            title: "Criar playlist",
          }}
        />
      </Stack>
    </>
  );
}
