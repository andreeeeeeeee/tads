import { Ionicons } from "@expo/vector-icons";
import { useRouter } from "expo-router";
import { useState } from "react";
import {
  Alert,
  KeyboardAvoidingView,
  Platform,
  Pressable,
  StyleSheet,
  Switch,
  Text,
  View,
} from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";

import { CoverPickerPlaceholder } from "@/components/spotify/CoverArt";
import { FormField } from "@/components/spotify/FormField";
import { SpotifyColors, SpotifySpacing } from "@/constants/spotify-theme";

export default function CriarPlaylistScreen() {
  const router = useRouter();
  const [name, setName] = useState("Minha playlist #1");
  const [description, setDescription] = useState("");
  const [isPrivate, setIsPrivate] = useState(false);
  const [error, setError] = useState("");

  const canSave = name.trim().length > 0;

  function handleCancel() {
    router.back();
  }

  function handleSave() {
    if (!name.trim()) {
      setError("Informe um nome para a playlist.");
      return;
    }

    setError("");
    Alert.alert(
      "Playlist criada",
      `"${name.trim()}" foi salva localmente${isPrivate ? " como particular" : ""}.`,
      [{ text: "OK", onPress: () => router.back() }],
    );
  }

  return (
    <SafeAreaView style={styles.container}>
      <KeyboardAvoidingView
        style={styles.flex}
        behavior={Platform.OS === "ios" ? "padding" : undefined}
      >
        <View style={styles.header}>
          <Pressable onPress={handleCancel} hitSlop={8}>
            <Text style={styles.cancel}>Cancelar</Text>
          </Pressable>
          <Text style={styles.title}>Nome e detalhes</Text>
          <Pressable onPress={handleSave} hitSlop={8} disabled={!canSave}>
            <Text style={[styles.save, !canSave && styles.saveDisabled]}>
              Salvar
            </Text>
          </Pressable>
        </View>

        <View style={styles.form}>
          <View style={styles.topRow}>
            <CoverPickerPlaceholder />
            <View style={styles.fields}>
              <FormField
                value={name}
                onChangeText={(text) => {
                  setName(text);
                  if (error) setError("");
                }}
                placeholder="Nome da playlist"
                autoFocus
              />
              <FormField
                value={description}
                onChangeText={setDescription}
                placeholder="Adicione uma descrição"
                multiline
              />
            </View>
          </View>

          {error ? <Text style={styles.error}>{error}</Text> : null}

          <Pressable
            style={styles.optionRow}
            onPress={() => setIsPrivate((value) => !value)}
          >
            <Ionicons
              name="lock-closed"
              size={20}
              color={SpotifyColors.white}
            />
            <Text style={styles.optionLabel}>Tornar particular</Text>
            <Switch
              value={isPrivate}
              onValueChange={setIsPrivate}
              trackColor={{
                false: SpotifyColors.surfaceElevated,
                true: SpotifyColors.green,
              }}
              thumbColor={SpotifyColors.white}
            />
          </Pressable>
        </View>
      </KeyboardAvoidingView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: SpotifyColors.background,
  },
  flex: {
    flex: 1,
  },
  header: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between",
    paddingHorizontal: SpotifySpacing.lg,
    paddingVertical: SpotifySpacing.md,
  },
  cancel: {
    color: SpotifyColors.white,
    fontSize: 15,
  },
  title: {
    color: SpotifyColors.white,
    fontSize: 15,
    fontWeight: "700",
  },
  save: {
    color: SpotifyColors.green,
    fontSize: 15,
    fontWeight: "700",
  },
  saveDisabled: {
    opacity: 0.4,
  },
  form: {
    padding: SpotifySpacing.lg,
    gap: SpotifySpacing.xl,
  },
  topRow: {
    flexDirection: "row",
    gap: SpotifySpacing.lg,
  },
  fields: {
    flex: 1,
    gap: SpotifySpacing.md,
  },
  error: {
    color: SpotifyColors.danger,
    fontSize: 13,
  },
  optionRow: {
    flexDirection: "row",
    alignItems: "center",
    gap: SpotifySpacing.md,
    paddingVertical: SpotifySpacing.sm,
  },
  optionLabel: {
    flex: 1,
    color: SpotifyColors.white,
    fontSize: 15,
  },
});
