import React, { useEffect, useState } from "react";
import { StyleSheet, View, Text } from "react-native";
import { colors } from "../../../theme/colors";
import { Button } from "react-native-elements";
import { AirbnbRating } from "react-native-elements";
import CustomStarRating from "./CustomStarRating";

export default Question = ({ question, onAnswer }) => {
  const [content, type] = question ? question.split("-") : ["", ""];
  if (!question) return null;
 
  return (
    <View style={styles.container}>
      <Text style={styles.content}>{content}</Text>
      {type == "polar" ? (
        <View style={styles.answerContainer}>
          <Button
            title="A. Yes"
            buttonStyle={styles.answerButton}
            titleStyle={styles.answerText}
            type="outline"
            onPress={() => onAnswer(1)}
          />
          <Button
            title="B. No"
            buttonStyle={styles.answerButton}
            titleStyle={styles.answerText}
            type="outline"
            onPress={() => onAnswer(0)}
          />
        </View>
      ) : (
        <View style={styles.rating}>
          <CustomStarRating onRatingPress={onAnswer}  />
        </View>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    height: 200,
  },
  answerContainer: {
    flex: 1,
    justifyContent: "space-evenly",
  },
  answerText: {
    color: colors.text.primary,
    fontSize: 20,
  },
  answerButton: {
    backgroundColor: colors.text.inverse,
    paddingLeft: 10,
    borderRadius: 10,
    borderColor: colors.brand.primary,
    borderWidth: 2
  },
  content: {
    fontSize: 20,
    fontWeight: "bold",
  },
  rating: {
    flex: 1,
    borderRadius: 20,
    justifyContent: "center",
  },
});
