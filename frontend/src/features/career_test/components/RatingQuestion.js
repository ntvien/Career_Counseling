import React from "react";
import { StyleSheet, View, Text } from "react-native";
import { fontSizes } from "../../../theme/fonts";
import CustomStarRating from "./CustomStarRating";

export default RatingQuestion = ({
  number,
  content,
  onRatingPress,
  star,
}) => {
  return (
    <View style={styles.container}>
      <View style={styles.questionContainer}>
        <Text style={styles.questionNumber}>{number}</Text>
        <Text style={styles.questionContent}>{content}</Text>
      </View>
      <CustomStarRating onRatingPress={onRatingPress} star={star}/>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
  },
  questionContainer: {
    flexDirection: "row",
    alignItems: "center",
    marginBottom: 20,
  },
  questionNumber: {
    fontSize: 30,
    marginRight: 5
  },
  questionContent: {
    fontSize: fontSizes.body,
    flex: 1
  },
});
