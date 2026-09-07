import { Pressable, StyleSheet, Text, View } from 'react-native';

import { SpotifyColors, SpotifySpacing } from '@/constants/spotify-theme';

type CoverArtProps = {
  color: string;
  colors?: string[];
  size: number;
  label?: string;
  borderRadius?: number;
};

export function CoverArt({
  color,
  colors,
  size,
  label,
  borderRadius = 4,
}: CoverArtProps) {
  const initial = (label ?? '').trim().charAt(0).toUpperCase() || '♪';

  if (colors && colors.length >= 4) {
    const cell = size / 2;
    return (
      <View style={[styles.grid, { width: size, height: size, borderRadius }]}>
        {colors.slice(0, 4).map((cellColor, index) => (
          <View
            key={`${cellColor}-${index}`}
            style={{ width: cell, height: cell, backgroundColor: cellColor }}
          />
        ))}
      </View>
    );
  }

  return (
    <View
      style={[
        styles.solid,
        {
          width: size,
          height: size,
          backgroundColor: color,
          borderRadius,
        },
      ]}>
      <Text style={[styles.initial, { fontSize: size * 0.35 }]}>{initial}</Text>
    </View>
  );
}

type CoverPickerPlaceholderProps = {
  onPress?: () => void;
};

export function CoverPickerPlaceholder({ onPress }: CoverPickerPlaceholderProps) {
  return (
    <Pressable onPress={onPress} style={styles.picker}>
      <Text style={styles.pickerIcon}>✎</Text>
    </Pressable>
  );
}

const styles = StyleSheet.create({
  grid: {
    overflow: 'hidden',
    flexDirection: 'row',
    flexWrap: 'wrap',
  },
  solid: {
    alignItems: 'center',
    justifyContent: 'center',
  },
  initial: {
    color: SpotifyColors.white,
    fontWeight: '700',
    opacity: 0.85,
  },
  picker: {
    width: 120,
    height: 120,
    borderRadius: 4,
    backgroundColor: SpotifyColors.surface,
    alignItems: 'center',
    justifyContent: 'center',
  },
  pickerIcon: {
    color: SpotifyColors.textSecondary,
    fontSize: 36,
  },
});
