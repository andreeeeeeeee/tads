import { Pressable, StyleSheet, Text } from 'react-native';

import { SpotifyColors, SpotifySpacing } from '@/constants/spotify-theme';

type FilterChipProps = {
  label: string;
  selected: boolean;
  onPress: () => void;
};

export function FilterChip({ label, selected, onPress }: FilterChipProps) {
  return (
    <Pressable
      onPress={onPress}
      style={[styles.chip, selected && styles.chipSelected]}
      accessibilityRole="button"
      accessibilityState={{ selected }}>
      <Text style={[styles.label, selected && styles.labelSelected]}>{label}</Text>
    </Pressable>
  );
}

const styles = StyleSheet.create({
  chip: {
    paddingHorizontal: SpotifySpacing.lg,
    paddingVertical: SpotifySpacing.sm,
    borderRadius: 20,
    backgroundColor: SpotifyColors.chip,
    marginRight: SpotifySpacing.sm,
  },
  chipSelected: {
    backgroundColor: SpotifyColors.chipActive,
  },
  label: {
    color: SpotifyColors.white,
    fontSize: 13,
    fontWeight: '600',
  },
  labelSelected: {
    color: SpotifyColors.chipActiveText,
  },
});
