import type { Track } from '@/types/spotify';

export const TRACKS: Track[] = [
  {
    id: 't1',
    title: 'No Brooklin',
    artist: 'Sabotage, Negra Li',
    coverColor: '#8B4513',
  },
  {
    id: 't2',
    title: 'Mun Rá',
    artist: 'Sabotage, Instituto',
    coverColor: '#4A3728',
  },
  {
    id: 't3',
    title: 'Cantando Pro Santo',
    artist: 'Criolo',
    coverColor: '#C45C26',
  },
  {
    id: 't4',
    title: 'Rap é Compromisso',
    artist: 'Sabotage',
    coverColor: '#2F4F4F',
  },
  {
    id: 't5',
    title: 'Hino Vira-Lata',
    artist: 'Emicida, Quinteto em Branco e Preto',
    coverColor: '#5C4033',
  },
  {
    id: 't6',
    title: 'Ainda Há Tempo',
    artist: 'Criolo',
    coverColor: '#1A1A2E',
  },
  {
    id: 't7',
    title: 'Gueto',
    artist: 'Racionais MC\'s',
    coverColor: '#3D0000',
  },
  {
    id: 't8',
    title: 'Negro Drama',
    artist: 'Racionais MC\'s',
    coverColor: '#1C1C1C',
  },
  {
    id: 't9',
    title: 'Boa Esperança',
    artist: 'Emicida',
    coverColor: '#0D7377',
  },
  {
    id: 't10',
    title: 'Principia',
    artist: 'Emicida, Pastor Alexandre',
    coverColor: '#14919B',
  },
  {
    id: 't11',
    title: 'The Adults Are Talking',
    artist: 'The Strokes',
    coverColor: '#00A5C6',
  },
  {
    id: 't12',
    title: 'Ode To The Mets',
    artist: 'The Strokes',
    coverColor: '#00A5C6',
  },
  {
    id: 't13',
    title: 'The Place Where He Inserted the Blade',
    artist: 'Black Country, New Road',
    coverColor: '#A68966',
  },
  {
    id: 't14',
    title: 'Concorde',
    artist: 'Black Country, New Road',
    coverColor: '#A68966',
  },
  {
    id: 't15',
    title: 'Blast Doors',
    artist: 'Everything Everything',
    coverColor: '#C2185B',
  },
  {
    id: 't16',
    title: 'No Reptiles',
    artist: 'Everything Everything',
    coverColor: '#C2185B',
  },
];

export function getTrackById(id: string): Track | undefined {
  return TRACKS.find((track) => track.id === id);
}

export function getTracksByIds(ids: string[]): Track[] {
  return ids
    .map((id) => getTrackById(id))
    .filter((track): track is Track => track !== undefined);
}
