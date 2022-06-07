import React from 'react';
import styles from './SplashScreen.styles';
import { View, Image } from 'react-native';

const SplashScreen = () => {
  return (
    <View style={styles.container}>
      <Image source={require('../../../assets/logo.png')} />
    </View>
  );
};

export default SplashScreen;
