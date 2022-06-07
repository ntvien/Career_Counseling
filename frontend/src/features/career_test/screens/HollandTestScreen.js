import React, { useEffect, useState } from "react";
import { View, Text, ScrollView, Image } from "react-native";
import hollandCodeApi from "../../../api/http/career_test/hollandCodeApi";
import navigation from "../../../navigation";
import { colors } from "../../../theme/colors";
import routes from "../../../utils/enum/routes";
import CustomStarRating from "../components/CustomStarRating";
import HollandCode from "../components/HollandCode";
import { styles } from "./HollandTestScreen.styles";

const HollandTestScreen = ({ navigation }) => {
  const [hollandCodes, setHollandCodes] = useState([]);
  useEffect(async () => {
    try {
      const res = await hollandCodeApi.getHollandCodeList();
      setHollandCodes(res);
    } catch (e) {
      console.log(e);
    }
  }, []);
  return (
    <ScrollView style={styles.container}>
      <Text style={styles.title}>Hướng dẫn</Text>
      <Text style={styles.text}>
        Bạn hãy chọn làm bài trắc nghiệm cho một nhóm tính cách bên dưới. Điểm
        càng cao thể hiện bạn có thiên hướng phù hợp với nhóm tính cách đó càng
        nhiều (Thang điểm 10).
      </Text>
      {hollandCodes.map((item, index) => (
        <HollandCode
          code={item.code}
          name={item.name}
          onPress={() =>
            navigation.navigate(routes.HOLLAND_QUESTION, { _id: item._id, name: item.name.split(" - ")[0] })
          }
          key={index}
        />
      ))}
    </ScrollView>
  );
};

export default HollandTestScreen;
