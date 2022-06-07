import React, { useState, useRef, useMemo, useContext, useEffect } from 'react';
import { View, Text, Image } from 'react-native';
import { Button, Icon } from 'react-native-elements';
import PagerView from 'react-native-pager-view';
import ExpandingDotPager from '../../../components/pager/ExpandingDotPager';
import contents from '../../../utils/enum/contents';
import IntroductionView from '../components/IntroductionView';
import styles from './GettingStartedScreen.styles';
import { Dimensions, Animated } from 'react-native';
import { SlidingBorder, SlidingDot } from 'react-native-animated-pagination-dots';
import { AuthContext } from '../../../navigation';
import { actionCreators } from '../../../reducers/authReducer';
import storageKeys from '../../../utils/enum/storageKeys';
import AsyncStorage from '@react-native-async-storage/async-storage';

const GettingStartedScreen = ({ }) => {
  const pagerView = useRef(null);
  const [activePage, setActivePage] = useState(0);
  const { dispatch } = useContext(AuthContext);
  const onPageSelected = useMemo(
    () =>
      Animated.event([], {
        listener: ({ nativeEvent: { position } }) => {
          setActivePage(position);
        },
        useNativeDriver: false,
      }),
    [],
  );
  const onNextPress = () => {
    if (activePage == contents.introduction.length - 1) {
      onSkipPress();
    } else {
      pagerView.current.setPage(activePage + 1);
    }
  };
  const onSkipPress = async () => {
    try {
      await AsyncStorage.setItem(storageKeys.IS_OPEN, 'true');
      dispatch(actionCreators.open());
    } catch (e) {
      console.log(e);
    }
  };
  useEffect(async () => {
    try {
      const res = await advertisingImageApi.getAdvertisingImages();
      AsyncStorage.setItem(storageKeys.ADVERTISING_IMAGES, JSON.stringify(res));
      res.map(item => Image.prefetch(item.uri));
    } catch (e) {
      console.log(e);
    }
  }, []);
  return (
    <View style={styles.container}>
      {activePage != contents.introduction.length - 1 && (
        <Button
          containerStyle={styles.skipContainer}
          buttonStyle={styles.skipButton}
          title="Bỏ qua "
          type="solid"
          titleStyle={styles.skipText}
          disabledStyle={false}
          icon={
            <Icon
              name="angle-double-right"
              type="font-awesome-5"
              size={15}
              color="red"
            />
          }
          iconRight
          onPress={onSkipPress}
        />
      )}
      <ExpandingDotPager
        ref={pagerView}
        onPageSelected={onPageSelected}
        style={styles.pager}>
        {contents.introduction.map((item, index) => (
          <IntroductionView key={index} content={item} />
        ))}
      </ExpandingDotPager>
      <Button
        title={
          activePage == contents.introduction.length - 1
            ? 'Bắt đầu'
            : 'Tiếp tục'
        }
        buttonStyle={styles.nextButton}
        containerStyle={styles.nextContainer}
        titleStyle={styles.nextText}
        onPress={onNextPress}
      />
    </View>
  );
};

export default GettingStartedScreen;
