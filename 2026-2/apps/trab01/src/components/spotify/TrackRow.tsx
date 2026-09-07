import { Ionicons } from '@expo/vector-icons';
import { Pressable, StyleSheet, Text, View } from 'react-native';

import { CoverArt } from '@/components/spotify/CoverArt';
import { SpotifyColors, SpotifySpacing } from '@/constants/spotify-theme';
import type { Track } from '@/types/spotify';

type TrackRowProps = {
  track: Track;
  onPress?: () => void;
};

export function TrackRow({ track, onPress }: TrackRowProps) {
  return (
    <Pressable style={styles.row} onPress={onPress}>
      <CoverArt color={track.coverColor} size={48} label={track.title} />
      <View style={styles.text}>
        <Text style={styles.title} numberOfLines={1}>
          {track.title}
        </Text>
        <Text style={styles.artist} numberOfLines={1}>
          {track.artist}
        </Text>
      </View>
      <Ionicons name="ellipsis-vertical" size={18} color={SpotifyColors.textSecondary} />
    </Pressable>
  );
}

const styles = StyleSheet.create({
  row: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: SpotifySpacing.lg,
    paddingVertical: SpotifySpacing.sm,
    gap: SpotifySpacing.md,
  },
  text: {
    flex: 1,
    gap: 2,
  },
  title: {
    color: SpotifyColors.white,
    fontSize: 15,
    fontWeight: '500',
  },
  artist: {
    color: SpotifyColors.textSecondary,
    fontSize: 13,
  },
});
