import { Ionicons } from '@expo/vector-icons';
import { Pressable, StyleSheet, Text, View } from 'react-native';

import { CoverArt } from '@/components/spotify/CoverArt';
import { SpotifyColors, SpotifySpacing } from '@/constants/spotify-theme';

type ActionIconProps = {
  name: keyof typeof Ionicons.glyphMap;
  color?: string;
  size?: number;
  onPress?: () => void;
};

export function ActionIcon({
  name,
  color = SpotifyColors.textSecondary,
  size = 24,
  onPress,
}: ActionIconProps) {
  return (
    <Pressable onPress={onPress} hitSlop={8} style={styles.icon}>
      <Ionicons name={name} size={size} color={color} />
    </Pressable>
  );
}

type PlaylistActionsProps = {
  coverColor: string;
  coverColors?: string[];
  name: string;
  shuffleOn?: boolean;
};

export function PlaylistActions({
  coverColor,
  coverColors,
  name,
  shuffleOn = true,
}: PlaylistActionsProps) {
  return (
    <View style={styles.row}>
      <View style={styles.left}>
        <CoverArt
          color={coverColor}
          colors={coverColors}
          size={28}
          label={name}
          borderRadius={2}
        />
        <ActionIcon name="arrow-down-circle-outline" />
        <ActionIcon name="share-outline" />
        <ActionIcon name="ellipsis-horizontal" />
      </View>
      <View style={styles.right}>
        <ActionIcon
          name="shuffle"
          color={shuffleOn ? SpotifyColors.green : SpotifyColors.textSecondary}
          size={26}
        />
        <Pressable style={styles.playButton}>
          <Ionicons name="play" size={28} color={SpotifyColors.black} />
        </Pressable>
      </View>
    </View>
  );
}

type QuickActionPillProps = {
  icon: keyof typeof Ionicons.glyphMap;
  label: string;
  onPress?: () => void;
};

export function QuickActionPill({ icon, label, onPress }: QuickActionPillProps) {
  return (
    <Pressable onPress={onPress} style={styles.pill}>
      <Ionicons name={icon} size={16} color={SpotifyColors.white} />
      <Text style={styles.pillLabel}>{label}</Text>
    </Pressable>
  );
}

const styles = StyleSheet.create({
  row: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingHorizontal: SpotifySpacing.lg,
    paddingVertical: SpotifySpacing.md,
  },
  left: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: SpotifySpacing.lg,
  },
  right: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: SpotifySpacing.lg,
  },
  icon: {
    padding: 2,
  },
  playButton: {
    width: 52,
    height: 52,
    borderRadius: 26,
    backgroundColor: SpotifyColors.green,
    alignItems: 'center',
    justifyContent: 'center',
    paddingLeft: 3,
  },
  pill: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: SpotifySpacing.xs,
    backgroundColor: SpotifyColors.surface,
    paddingHorizontal: SpotifySpacing.md,
    paddingVertical: SpotifySpacing.sm,
    borderRadius: 20,
    marginRight: SpotifySpacing.sm,
  },
  pillLabel: {
    color: SpotifyColors.white,
    fontSize: 13,
    fontWeight: '600',
  },
});
