import { useRouter } from "expo-router";
import { useMemo, useState } from "react";
import { FlatList, ScrollView, StyleSheet, Text, View } from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";

import { FilterChip } from "@/components/spotify/FilterChip";
import { LibraryHeader } from "@/components/spotify/LibraryHeader";
import { LibraryItem } from "@/components/spotify/LibraryItem";
import { SpotifyColors, SpotifySpacing } from "@/constants/spotify-theme";
import { PLAYLISTS } from "@/data/playlists";
import type { LibraryFilter, Playlist } from "@/types/spotify";

const FILTERS: { key: LibraryFilter; label: string }[] = [
  { key: "playlists", label: "Playlists" },
  { key: "podcasts", label: "Podcasts" },
  { key: "albums", label: "Álbuns" },
  { key: "artists", label: "Artistas" },
  { key: "downloaded", label: "Baixado" },
];

function matchesFilter(item: Playlist, filter: LibraryFilter | null): boolean {
  if (!filter) return true;
  if (filter === "playlists") return item.type === "playlist";
  if (filter === "albums") return item.type === "album";
  if (filter === "podcasts") return item.type === "podcast";
  if (filter === "artists") return item.type === "artist";
  return false;
}

export default function BibliotecaScreen() {
  const router = useRouter();
  const [activeFilter, setActiveFilter] = useState<LibraryFilter | null>(null);

  const data = useMemo(
    () => PLAYLISTS.filter((item) => matchesFilter(item, activeFilter)),
    [activeFilter, PLAYLISTS],
  );

  function handleFilterPress(filter: LibraryFilter) {
    setActiveFilter((current) => (current === filter ? null : filter));
  }

  function openPlaylist(id: string) {
    router.push({ pathname: "/playlist/[id]", params: { id } });
  }

  function openCreate() {
    router.push("/playlist/criar");
  }

  return (
    <SafeAreaView style={styles.container} edges={["top"]}>
      <LibraryHeader onCreatePress={openCreate} />

      <View style={styles.chipsWrap}>
        <ScrollView
          horizontal
          showsHorizontalScrollIndicator={false}
          contentContainerStyle={styles.chips}
        >
          {FILTERS.map((filter) => (
            <FilterChip
              key={filter.key}
              label={filter.label}
              selected={activeFilter === filter.key}
              onPress={() => handleFilterPress(filter.key)}
            />
          ))}
        </ScrollView>
      </View>

      <FlatList
        data={data}
        keyExtractor={(item) => item.id}
        renderItem={({ item }) => (
          <LibraryItem item={item} onPress={openPlaylist} />
        )}
        contentContainerStyle={styles.list}
        showsVerticalScrollIndicator={false}
        ListEmptyComponent={
          <Text style={styles.empty}>Nenhum item neste filtro.</Text>
        }
      />
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: SpotifyColors.black,
  },
  chipsWrap: {
    marginBottom: SpotifySpacing.sm,
  },
  chips: {
    paddingHorizontal: SpotifySpacing.lg,
    paddingBottom: SpotifySpacing.sm,
  },
  list: {
    paddingBottom: 140,
    flexGrow: 1,
  },
  empty: {
    color: SpotifyColors.textSecondary,
    textAlign: "center",
    marginTop: SpotifySpacing.xxl,
    fontSize: 14,
  },
});
