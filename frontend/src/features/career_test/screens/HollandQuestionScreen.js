import React, { useEffect, useRef, useState, useMemo } from "react";
import { Alert, Animated, Dimensions, Text, View } from "react-native";
import { Button } from "react-native-elements";
import PagerView from "react-native-pager-view";
import hollandQuestionApi from "../../../api/http/career_test/hollandQuestionApi";
import routes from "../../../utils/enum/routes";
import QuestionProgressBar from "../components/QuestionProgressBar";
import RatingQuestion from "../components/RatingQuestion";
import { styles } from "./HollandQuestionScreen.styles";
const HollandQuestionScreen = ({ route, navigation }) => {
  const pagerView = useRef(null);
  const { _id } = route.params;
  const [questions, setQuestions] = useState([]);
  const [currentQuestion, setCurrentQuestion] = useState(0);
  const [stars, setStars] = useState([]);
  const [lastQuestion, setLastQuestion] = useState(false);
  const onPageSelected = useMemo(
    () =>
      Animated.event([], {
        listener: ({ nativeEvent: { position } }) => {
          setCurrentQuestion(position);
        },
        useNativeDriver: false,
      }),
    []
  );
  const onBeforeQuestion = () => {
    pagerView.current.setPage(currentQuestion - 1);
  };
  useEffect(async () => {
    try {
      const res = await hollandQuestionApi.getHollandQuestionList({
        codeId: _id,
      });
      setQuestions(res);
    } catch (e) {
      console.log(e);
    }
  }, []);
  useEffect(() => {
    if (currentQuestion == questions.length - 1) {
      setLastQuestion(true);
    } else {
      setLastQuestion(false);
    }
  }, [currentQuestion]);
  const onNextQuestion = () => {
    if (lastQuestion) {
      setCurrentQuestion((state) => state + 1);
      navigation.navigate(routes.HOLLAND_RESULT, {
        result: stars.reduce((a, b) => a + b, 0) + "/" + questions.length * 3,
      });
    } else {
      pagerView.current.setPage(currentQuestion + 1);
      setStars((state) => [...state.slice(0, currentQuestion), 0]);
    }
  };
  const onRatingPress = (rating) => {
    if (lastQuestion) {
      setCurrentQuestion((state) => state + 1);
      navigation.navigate(routes.HOLLAND_RESULT, {
        result: stars.reduce((a, b) => a + b, 0) + "/" + questions.length * 3,
      });
    } else {
      setStars((state) => [...state.slice(0, currentQuestion), rating]);
      pagerView.current.setPage(currentQuestion + 1);
    }
  };
  return (
    <View style={styles.container}>
      <View style={styles.questionContainer}>
        <PagerView
          scrollEnabled={false}
          ref={pagerView}
          onPageSelected={onPageSelected}
          style={styles.pagerView}
        >
          {questions.map((item, index) => (
            <View key={index}>
              <RatingQuestion
                number={index + 1}
                content={item.content}
                star={stars[index]}
                onRatingPress={onRatingPress}
              />
            </View>
          ))}
        </PagerView>
      </View>
      <QuestionProgressBar
        progress={currentQuestion}
        total={questions.length}
        width={Dimensions.get("window").width * 0.8}
      />
      <View style={styles.buttonContainer}>
        <Button
          icon={{
            name: "angle-left",
            size: 30,
            color: "black",
            type: "font-awesome-5",
          }}
          title="Câu hỏi trước"
          buttonStyle={[styles.button, styles.buttonLeft]}
          titleStyle={{ color: "black" }}
          onPress={onBeforeQuestion}
        />
        <Button
          icon={{
            name: "angle-right",
            size: 30,
            color: "white",
            type: "font-awesome-5",
          }}
          iconRight
          title={lastQuestion ? "Hoàn thành" : "Câu hỏi sau"}
          buttonStyle={[styles.button, styles.buttonRight]}
          onPress={onNextQuestion}
        />
      </View>
    </View>
  );
};

export default HollandQuestionScreen;
