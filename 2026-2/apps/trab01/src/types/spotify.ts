export type LibraryFilter = 'playlists' | 'podcasts' | 'albums' | 'artists' | 'downloaded';

export type LibraryItemType = 'playlist' | 'album' | 'podcast' | 'artist';

export interface Track {
  id: string;
  title: string;
  artist: string;
  coverColor: string;
}

export interface Playlist {
  id: string;
  name: string;
  description: string;
  owner: string;
  coverColor: string;
  coverColors?: string[];
  isPrivate: boolean;
  durationLabel: string;
  trackIds: string[];
  type: LibraryItemType;
  subtitle: string;
}

export interface NowPlaying {
  title: string;
  artist: string;
  coverColor: string;
  liked: boolean;
}
