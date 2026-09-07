import { StyleSheet, Text, TextInput, View, type TextInputProps } from 'react-native';

import { SpotifyColors, SpotifySpacing } from '@/constants/spotify-theme';

type FormFieldProps = TextInputProps & {
  label?: string;
  multiline?: boolean;
};

export function FormField({ label, style, multiline, ...rest }: FormFieldProps) {
  return (
    <View style={styles.wrapper}>
      {label ? <Text style={styles.label}>{label}</Text> : null}
      <TextInput
        placeholderTextColor={SpotifyColors.textMuted}
        style={[styles.input, multiline && styles.multiline, style]}
        multiline={multiline}
        {...rest}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  wrapper: {
    gap: SpotifySpacing.xs,
  },
  label: {
    color: SpotifyColors.textSecondary,
    fontSize: 12,
  },
  input: {
    backgroundColor: SpotifyColors.surface,
    color: SpotifyColors.white,
    borderRadius: 4,
    paddingHorizontal: SpotifySpacing.md,
    paddingVertical: SpotifySpacing.md,
    fontSize: 16,
  },
  multiline: {
    minHeight: 88,
    textAlignVertical: 'top',
  },
});
