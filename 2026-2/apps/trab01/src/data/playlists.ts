import type { NowPlaying, Playlist } from '@/types/spotify';

export const PLAYLISTS: Playlist[] = [
  {
    id: 'p1',
    name: 'RAP-AIZ',
    description: 'o rap é compromisso',
    owner: 'andre',
    coverColor: '#1A4A4A',
    coverColors: ['#8B4513', '#4A3728', '#C45C26', '#2F4F4F'],
    isPrivate: false,
    durationLabel: '40h 57min',
    trackIds: ['t1', 't2', 't3', 't4', 't5', 't6', 't7', 't8', 't9', 't10'],
    type: 'playlist',
    subtitle: 'Playlist • andre',
  },
  {
    id: 'p2',
    name: 'The New Abnormal',
    description: 'Álbum completo',
    owner: 'The Strokes',
    coverColor: '#00A5C6',
    // coverColors: ['#00A5C6', '#F5C518', '#1A1A1A', '#E8DFD0'],
    isPrivate: false,
    durationLabel: '45min',
    trackIds: ['t11', 't12'],
    type: 'album',
    subtitle: 'Álbum • The Strokes',
  },
  {
    id: 'p3',
    name: 'Ants From Up There',
    description: 'Álbum completo',
    owner: 'Black Country, New Road',
    coverColor: '#A68966',
    // coverColors: ['#A68966', '#B4A278', '#8C6A4A', '#D4C4A8'],
    isPrivate: false,
    durationLabel: '58min',
    trackIds: ['t13', 't14'],
    type: 'album',
    subtitle: 'Álbum • Black Country, New Road',
  },
  {
    id: 'p4',
    name: 'Get To Heaven (Deluxe)',
    description: 'Álbum completo',
    owner: 'Everything Everything',
    coverColor: '#C2185B',
    // coverColors: ['#F5C518', '#C2185B', '#2E3192', '#E91E63'],
    isPrivate: false,
    durationLabel: '1h 17min',
    trackIds: ['t15', 't16'],
    type: 'album',
    subtitle: 'Álbum • Everything Everything',
  },
  {
    id: 'p5',
    name: 'Músicas Curtidas',
    description: 'Suas faixas favoritas',
    owner: 'andre',
    coverColor: '#5038A0',
    isPrivate: true,
    durationLabel: '12h 30min',
    trackIds: ['t1', 't2', 't3', 't4', 't5', 't6', 't7', 't8'],
    type: 'playlist',
    subtitle: 'Playlist • andre',
  },
];

export const NOW_PLAYING: NowPlaying = {
  title: 'Hino Vira-Lata',
  artist: 'Emicida, Quinteto em Branco e Preto',
  coverColor: '#5C4033',
  liked: true,
};

export function getPlaylistById(id: string): Playlist | undefined {
  return PLAYLISTS.find((playlist) => playlist.id === id);
}
