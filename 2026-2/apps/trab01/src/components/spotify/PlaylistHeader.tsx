import { Ionicons } from '@expo/vector-icons';
import { LinearGradient } from 'expo-linear-gradient';
import { Pressable, StyleSheet, Text, View } from 'react-native';
import { useSafeAreaInsets } from 'react-native-safe-area-context';

import { CoverArt } from '@/components/spotify/CoverArt';
import { SpotifyColors, SpotifySpacing } from '@/constants/spotify-theme';
import type { Playlist } from '@/types/spotify';

type PlaylistHeaderProps = {
  playlist: Playlist;
  onBack: () => void;
};

export function PlaylistHeader({ playlist, onBack }: PlaylistHeaderProps) {
  const insets = useSafeAreaInsets();

  return (
    <LinearGradient
      colors={[playlist.coverColor, SpotifyColors.black]}
      style={[styles.gradient, { paddingTop: insets.top + SpotifySpacing.sm }]}>
      <Pressable onPress={onBack} hitSlop={12} style={styles.backButton}>
        <Ionicons name="arrow-back" size={24} color={SpotifyColors.white} />
      </Pressable>

      <View style={styles.coverWrap}>
        <CoverArt
          color={playlist.coverColor}
          colors={playlist.coverColors}
          size={180}
          label={playlist.name}
          borderRadius={4}
        />
      </View>

      <Text style={styles.title}>{playlist.name}</Text>
      {playlist.description ? (
        <Text style={styles.description}>{playlist.description}</Text>
      ) : null}

      <View style={styles.ownerRow}>
        <View style={styles.ownerAvatar}>
          <Text style={styles.ownerInitial}>
            {playlist.owner.charAt(0).toUpperCase()}
          </Text>
        </View>
        <Text style={styles.ownerName}>{playlist.owner}</Text>
      </View>

      <View style={styles.metaRow}>
        <Ionicons
          name={playlist.isPrivate ? 'lock-closed' : 'globe-outline'}
          size={14}
          color={SpotifyColors.textSecondary}
        />
        <Text style={styles.duration}>{playlist.durationLabel}</Text>
      </View>
    </LinearGradient>
  );
}

const styles = StyleSheet.create({
  gradient: {
    paddingHorizontal: SpotifySpacing.lg,
    paddingBottom: SpotifySpacing.lg,
  },
  backButton: {
    alignSelf: 'flex-start',
    marginBottom: SpotifySpacing.md,
  },
  coverWrap: {
    alignItems: 'center',
    marginBottom: SpotifySpacing.xl,
  },
  title: {
    color: SpotifyColors.white,
    fontSize: 28,
    fontWeight: '700',
    marginBottom: SpotifySpacing.xs,
  },
  description: {
    color: SpotifyColors.textSecondary,
    fontSize: 13,
    marginBottom: SpotifySpacing.md,
  },
  ownerRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: SpotifySpacing.sm,
    marginBottom: SpotifySpacing.sm,
  },
  ownerAvatar: {
    width: 24,
    height: 24,
    borderRadius: 12,
    backgroundColor: '#E91E63',
    alignItems: 'center',
    justifyContent: 'center',
  },
  ownerInitial: {
    color: SpotifyColors.white,
    fontSize: 11,
    fontWeight: '700',
  },
  ownerName: {
    color: SpotifyColors.white,
    fontSize: 13,
    fontWeight: '600',
  },
  metaRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: SpotifySpacing.xs,
  },
  duration: {
    color: SpotifyColors.textSecondary,
    fontSize: 13,
  },
});
