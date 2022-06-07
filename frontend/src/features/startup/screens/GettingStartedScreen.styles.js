import { StyleSheet } from 'react-native';
import { colors } from '../../../theme/colors';

export default styles = StyleSheet.create({
  container: {
    backgroundColor: colors.brand.primary,
    flex: 1,
    alignItems: 'center',
  },
  pager: {
    flex: 1,
  },
  skipContainer: {
    margin: 10,
    alignSelf: 'flex-end',
  },
  skipButton: {
    borderRadius: 20,
    backgroundColor: colors.bg.primary,
    borderColor: colors.border.primary,
    borderWidth: 2,
    paddingHorizontal: 12,
    paddingVertical: 5,
  },
  skipText: {
    color: colors.brand.primary,
    fontSize: 15,
  },
  nextText: {
    fontWeight: 'bold',
    color: colors.text.primary,
  },
  nextButton: {
    borderRadius: 20,
    backgroundColor: colors.bg.primary,
  },
  nextContainer: {
    height: 50,
    margin: 20,
    width: 200,
  },
});
