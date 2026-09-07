import { Ionicons } from '@expo/vector-icons';
import { StyleSheet, Text, View } from 'react-native';

import { CoverArt } from '@/components/spotify/CoverArt';
import { SpotifyColors, SpotifySpacing } from '@/constants/spotify-theme';
import { NOW_PLAYING } from '@/data/playlists';

export function MiniPlayer() {
  return (
    <View style={styles.container}>
      <CoverArt
        color={NOW_PLAYING.coverColor}
        size={40}
        label={NOW_PLAYING.title}
        borderRadius={4}
      />
      <View style={styles.info}>
        <Text style={styles.title} numberOfLines={1}>
          {NOW_PLAYING.title}
        </Text>
        <Text style={styles.artist} numberOfLines={1}>
          {NOW_PLAYING.artist}
        </Text>
      </View>
      <Ionicons name="phone-portrait-outline" size={20} color={SpotifyColors.white} />
      <Ionicons
        name={NOW_PLAYING.liked ? 'checkmark-circle' : 'ellipse-outline'}
        size={24}
        color={NOW_PLAYING.liked ? SpotifyColors.green : SpotifyColors.white}
      />
      <Ionicons name="play" size={24} color={SpotifyColors.white} />
      <View style={styles.progress} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    marginHorizontal: SpotifySpacing.sm,
    marginBottom: SpotifySpacing.sm,
    backgroundColor: SpotifyColors.miniPlayer,
    borderRadius: 8,
    flexDirection: 'row',
    alignItems: 'center',
    padding: SpotifySpacing.sm,
    gap: SpotifySpacing.sm,
    overflow: 'hidden',
  },
  info: {
    flex: 1,
    minWidth: 0,
  },
  title: {
    color: SpotifyColors.white,
    fontSize: 13,
    fontWeight: '600',
  },
  artist: {
    color: SpotifyColors.textSecondary,
    fontSize: 11,
  },
  progress: {
    position: 'absolute',
    left: 8,
    right: 8,
    bottom: 0,
    height: 2,
    backgroundColor: SpotifyColors.white,
    opacity: 0.9,
    borderRadius: 1,
  },
});
