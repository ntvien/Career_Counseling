import {StyleSheet} from 'react-native';
import {colors} from '../../../theme/colors';
import paddings from '../../../theme/paddings';

export const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: paddings.container,
  },
  pagerView: {
    flex: 1,
  },
  buttonContainer: {
    flexDirection: 'row',
    marginTop: 10,
    flex: 1,
    flexWrap: 'wrap',
    justifyContent: 'space-around',
  },
  button: {
    width: 170,
    borderRadius: 20,
    borderWidth: 1,
    marginHorizontal: 5
  },
  buttonLeft: {
    backgroundColor: '#fff',
    borderColor: '#000',
    marginBottom: 10
  },
  buttonRight: {
    backgroundColor: colors.brand.primary,
    borderColor: colors.brand.primary,
  },
  questionContainer: {
    height: 150,
  },
});
