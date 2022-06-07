import React, { useState } from "react";
import { StyleSheet, View } from "react-native";
import { AirbnbRating, Rating } from "react-native-ratings";
import { colors } from "../../../theme/colors";
import StarRating from "react-native-star-rating";
export default CustomStarRating = ({ style, onRatingPress, star }) => {
  const [rating, setRating] = useState(star ? star : 0);
  const onStarClick = (rating) => {
    setRating(rating);
    setTimeout(() => {
      setRating(0);
      onRatingPress(rating);
    }, 500);
  };
  return (
    <StarRating
      rating={rating}
      buttonStyle={styles.star}
      fullStarColor={colors.brand.primary}
      containerStyle={[styles.rating, style]}
      maxStars={3}
      selectedStar={onStarClick}
    />
  );
};

const styles = StyleSheet.create({
  star: {},
  rating: {
    width: 150,
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 4,
    },
    shadowOpacity: 0.32,
    shadowRadius: 5.46,

    elevation: 9,
    alignSelf: "center",
  },
});
