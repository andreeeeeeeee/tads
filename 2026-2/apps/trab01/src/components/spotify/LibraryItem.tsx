import { Pressable, StyleSheet, Text, View } from 'react-native';

import { CoverArt } from '@/components/spotify/CoverArt';
import { SpotifyColors, SpotifySpacing } from '@/constants/spotify-theme';
import type { Playlist } from '@/types/spotify';

type LibraryItemProps = {
  item: Playlist;
  onPress: (id: string) => void;
};

export function LibraryItem({ item, onPress }: LibraryItemProps) {
  return (
    <Pressable
      style={styles.row}
      onPress={() => onPress(item.id)}
      accessibilityRole="button"
      accessibilityLabel={`${item.name}, ${item.subtitle}`}>
      <CoverArt
        color={item.coverColor}
        colors={item.coverColors}
        size={64}
        label={item.name}
      />
      <View style={styles.textBlock}>
        <Text style={styles.name} numberOfLines={1}>
          {item.name}
        </Text>
        <Text style={styles.subtitle} numberOfLines={1}>
          {item.subtitle}
        </Text>
      </View>
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
  textBlock: {
    flex: 1,
    gap: 2,
  },
  name: {
    color: SpotifyColors.white,
    fontSize: 16,
    fontWeight: '600',
  },
  subtitle: {
    color: SpotifyColors.textSecondary,
    fontSize: 13,
  },
});
