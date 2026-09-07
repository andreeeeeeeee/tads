import { Ionicons } from '@expo/vector-icons';
import { Tabs } from 'expo-router/js-tabs';
import { StyleSheet, View } from 'react-native';
import { useSafeAreaInsets } from 'react-native-safe-area-context';

import { MiniPlayer } from '@/components/spotify/MiniPlayer';
import { SpotifyColors } from '@/constants/spotify-theme';

export default function TabsLayout() {
  const insets = useSafeAreaInsets();
  const tabBarHeight = 56 + insets.bottom;

  return (
    <View style={styles.root}>
      <Tabs
        initialRouteName="biblioteca"
        screenOptions={{
          headerShown: false,
          tabBarStyle: {
            backgroundColor: SpotifyColors.black,
            borderTopColor: '#282828',
            borderTopWidth: StyleSheet.hairlineWidth,
            height: tabBarHeight,
            paddingBottom: insets.bottom > 0 ? insets.bottom : 8,
            paddingTop: 6,
          },
          tabBarActiveTintColor: SpotifyColors.white,
          tabBarInactiveTintColor: SpotifyColors.textSecondary,
          tabBarLabelStyle: {
            fontSize: 11,
            fontWeight: '600',
          },
        }}>
        <Tabs.Screen
          name="inicio"
          options={{
            title: 'Início',
            tabBarIcon: ({ color, size }) => (
              <Ionicons name="home" size={size} color={color} />
            ),
          }}
        />
        <Tabs.Screen
          name="buscar"
          options={{
            title: 'Buscar',
            tabBarIcon: ({ color, size }) => (
              <Ionicons name="search" size={size} color={color} />
            ),
          }}
        />
        <Tabs.Screen
          name="biblioteca"
          options={{
            title: 'Sua Biblioteca',
            tabBarIcon: ({ color, size }) => (
              <Ionicons name="library" size={size} color={color} />
            ),
          }}
        />
        <Tabs.Screen
          name="index"
          options={{
            href: null,
          }}
        />
      </Tabs>
      <View style={[styles.miniPlayerWrap, { bottom: tabBarHeight }]}>
        <MiniPlayer />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  root: {
    flex: 1,
    backgroundColor: SpotifyColors.black,
  },
  miniPlayerWrap: {
    position: 'absolute',
    left: 0,
    right: 0,
  },
});
