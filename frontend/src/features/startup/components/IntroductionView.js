import React from 'react';
import {View, Text, StyleSheet} from 'react-native';
import { colors } from '../../../theme/colors';

export default IntroductionView = ({key, content}) => {
  return (
    <View style={styles.container} key={key} collapsable={false}>
      <Text style={styles.logoText}>Career Counseling</Text>
      <Text style={styles.content}>{content}</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    width: '100%',
    height: '100%',
    padding: 10,
    justifyContent: 'space-evenly',
    alignItems: 'center',
  },
  logoText: {
    color: colors.text.inverse,
    fontSize: 60,
    textAlign: 'center',
    fontWeight: 'bold',
  },
  content: {
    color: colors.text.inverse,
    textAlign: 'center',
    fontSize: 20,
  },
});
