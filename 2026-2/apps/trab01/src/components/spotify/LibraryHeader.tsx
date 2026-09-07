import { Ionicons } from '@expo/vector-icons';
import { Pressable, StyleSheet, Text, View } from 'react-native';

import { SpotifyColors, SpotifySpacing } from '@/constants/spotify-theme';

type LibraryHeaderProps = {
  onSearchPress?: () => void;
  onCreatePress: () => void;
};

export function LibraryHeader({ onSearchPress, onCreatePress }: LibraryHeaderProps) {
  return (
    <View style={styles.container}>
      <View style={styles.left}>
        <View style={styles.avatar}>
          <Text style={styles.avatarText}>A</Text>
          <View style={styles.badge}>
            <Text style={styles.badgeText}>1</Text>
          </View>
        </View>
        <Text style={styles.title}>Sua Biblioteca</Text>
      </View>
      <View style={styles.actions}>
        <Pressable onPress={onSearchPress} hitSlop={8} style={styles.iconButton}>
          <Ionicons name="search" size={24} color={SpotifyColors.white} />
        </Pressable>
        <Pressable onPress={onCreatePress} hitSlop={8} style={styles.iconButton}>
          <Ionicons name="add" size={28} color={SpotifyColors.white} />
        </Pressable>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingHorizontal: SpotifySpacing.lg,
    paddingVertical: SpotifySpacing.md,
  },
  left: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: SpotifySpacing.md,
  },
  avatar: {
    width: 36,
    height: 36,
    borderRadius: 18,
    backgroundColor: '#E91E63',
    alignItems: 'center',
    justifyContent: 'center',
  },
  avatarText: {
    color: SpotifyColors.white,
    fontWeight: '700',
    fontSize: 16,
  },
  badge: {
    position: 'absolute',
    top: -2,
    right: -2,
    width: 16,
    height: 16,
    borderRadius: 8,
    backgroundColor: SpotifyColors.danger,
    alignItems: 'center',
    justifyContent: 'center',
    borderWidth: 2,
    borderColor: SpotifyColors.black,
  },
  badgeText: {
    color: SpotifyColors.white,
    fontSize: 9,
    fontWeight: '700',
  },
  title: {
    color: SpotifyColors.white,
    fontSize: 22,
    fontWeight: '700',
  },
  actions: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: SpotifySpacing.lg,
  },
  iconButton: {
    padding: 2,
  },
});
