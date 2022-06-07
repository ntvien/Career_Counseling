import React from "react";
import PagerView from "react-native-pager-view";
import { SlidingDot } from "react-native-animated-pagination-dots";
import { StyleSheet } from "react-native";
import { Dimensions, Animated, View } from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";
const AnimatedPagerView = Animated.createAnimatedComponent(PagerView);

export default ExpandingDotPager = React.forwardRef(
  ({ children, style, dotHidden, ...props }, ref) => {
    const width = Dimensions.get("window").width;
    const scrollOffsetAnimatedValue = React.useRef(new Animated.Value(0))
      .current;
    const positionAnimatedValue = React.useRef(new Animated.Value(0)).current;
    const inputRange = [0, children ? children.length : 0];
    const scrollX = Animated.add(
      scrollOffsetAnimatedValue,
      positionAnimatedValue
    ).interpolate({
      inputRange,
      outputRange: [0, (children ? children.length : 0) * width],
    });
    const onPageScroll = Animated.event(
      [
        {
          nativeEvent: {
            offset: scrollOffsetAnimatedValue,
            position: positionAnimatedValue,
          },
        },
      ],
      { useNativeDriver: false }
    );
    return (
      <SafeAreaView style={style}>
        <PagerView
          onPageScroll={onPageScroll}
          ref={ref}
          style={styles.pager}
          {...props}
        >
          {children}
        </PagerView>
        {dotHidden || (
          <SlidingDot
            marginHorizontal={3}
            scrollX={scrollX}
            dotSize={12}
            data={children || []}
            containerStyle={styles.dotContainer}
            dotStyle={styles.dot}
            slidingIndicatorStyle={styles.slidingIndicator}
          />
        )}
      </SafeAreaView>
    );
  }
);

const styles = StyleSheet.create({
  pager: {
    flex: 1,
    marginBottom: 5,
  },
  dotContainer: {
    position: "relative",
    top: 10,
    left: 0,
    marginTop: 5,
  },
  dot: {
    backgroundColor: "#D0CBCB",
  },
  slidingIndicator: {
    backgroundColor: "#000000",
  },
});
