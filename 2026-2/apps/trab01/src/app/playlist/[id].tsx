import { useMemo } from 'react';
import { FlatList, ScrollView, StyleSheet, Text, View } from 'react-native';
import { useLocalSearchParams, useRouter } from 'expo-router';

import {
  PlaylistActions,
  QuickActionPill,
} from '@/components/spotify/PlaylistActions';
import { PlaylistHeader } from '@/components/spotify/PlaylistHeader';
import { TrackRow } from '@/components/spotify/TrackRow';
import { SpotifyColors, SpotifySpacing } from '@/constants/spotify-theme';
import { getPlaylistById } from '@/data/playlists';
import { getTracksByIds } from '@/data/tracks';

export default function PlaylistDetailScreen() {
  const router = useRouter();
  const { id } = useLocalSearchParams<{ id: string }>();

  const playlist = useMemo(() => (id ? getPlaylistById(id) : undefined), [id]);
  const tracks = useMemo(
    () => (playlist ? getTracksByIds(playlist.trackIds) : []),
    [playlist],
  );

  if (!playlist) {
    return (
      <View style={styles.missing}>
        <Text style={styles.missingText}>Playlist não encontrada.</Text>
        <Text style={styles.backLink} onPress={() => router.back()}>
          Voltar
        </Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <FlatList
        data={tracks}
        keyExtractor={(item) => item.id}
        ListHeaderComponent={
          <View>
            <PlaylistHeader
              playlist={playlist}
              onBack={() => {
                if (router.canGoBack()) {
                  router.back();
                } else {
                  router.replace('/biblioteca');
                }
              }}
            />
            <PlaylistActions
              coverColor={playlist.coverColor}
              coverColors={playlist.coverColors}
              name={playlist.name}
            />
            <ScrollView
              horizontal
              showsHorizontalScrollIndicator={false}
              contentContainerStyle={styles.pills}>
              <QuickActionPill icon="add" label="Adicionar" />
              <QuickActionPill icon="shuffle" label="Mixar" />
              <QuickActionPill icon="videocam-outline" label="Vídeo" />
              <QuickActionPill icon="create-outline" label="Editar" />
            </ScrollView>
          </View>
        }
        renderItem={({ item }) => <TrackRow track={item} />}
        contentContainerStyle={styles.list}
        showsVerticalScrollIndicator={false}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: SpotifyColors.black,
  },
  pills: {
    paddingHorizontal: SpotifySpacing.lg,
    paddingBottom: SpotifySpacing.md,
  },
  list: {
    paddingBottom: 40,
  },
  missing: {
    flex: 1,
    backgroundColor: SpotifyColors.black,
    alignItems: 'center',
    justifyContent: 'center',
    gap: SpotifySpacing.md,
  },
  missingText: {
    color: SpotifyColors.white,
    fontSize: 16,
  },
  backLink: {
    color: SpotifyColors.green,
    fontSize: 15,
    fontWeight: '600',
  },
});
