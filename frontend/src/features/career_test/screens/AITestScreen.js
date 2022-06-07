import React, { useEffect, useState } from "react";
import { View, Text } from "react-native";
import { Button } from "react-native-elements";
import { FlatList } from "react-native-gesture-handler";
import majorApi from "../../../api/http/resource/majorApi";
import ResourceCard from "../../../components/cards/ResourceCard";
import TypeOfResource from "../../../utils/enum/resource/TypeOfResource";
import Question from "../components/Question";
import { styles } from "./AITestScreen.styles";

const renderItem = ({ item }) => (
  <ResourceCard resource={item} typeOfResource={TypeOfResource.MAJOR}/>
);
const model = require("../../../utils/model/decisionTree.json");

const AITestScreen = ({ route }) => {
  const { MBTIType } = route.params;
  const [node, setNode] = useState(model);
  const [majors, setMajors] = useState([]);
  const [isFinsish, setIsFinnish] = useState(false);

  const getQuestionNode = (root) => {
    if (!root || !root["question"]) return false;
    if (root["question"].length == 1) {
      answer = MBTIType.includes(root["question"]) ? 1 : 0;
      return getQuestionNode(root[answer]);
    }
    return root;
  };

  const handleAnswer = async (answer) => {
    if (node[answer]) {
      const questionNode = getQuestionNode(node[answer]);
      if (!questionNode) setIsFinnish(true);
      setNode(questionNode);
      try {
        const res = await majorApi.getAllMajor(questionNode["values"]);
        setMajors(res);
      } catch (e) {
        console.log(e.request);
      }
    } else {
      setIsFinnish(true);
    }
  };
  useEffect(async () => {
    const questionNode = getQuestionNode(model);
    if (!questionNode) setIsFinnish(true);
    setNode(questionNode);
    setMajors([])
  }, []);

  return (
    <View style={styles.container}>
      {isFinsish ? (
        <Text style={styles.text}>
          Chúc mừng bạn! Bạn đã hoàn thành bài kiểm tra.
        </Text>
      ) : (
        <Question question={node.question} onAnswer={handleAnswer} />
      )}

      <Text style={styles.suggestText}>Các ngành phù hợp</Text>
      <FlatList
        data={majors}
        renderItem={renderItem}
        keyExtractor={(item) => item._id}
      />
    </View>
  );
};

export default AITestScreen;
