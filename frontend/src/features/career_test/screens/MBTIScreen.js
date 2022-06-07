import React from "react";
import { Linking, ScrollView, Text, View } from "react-native";
import { Button } from "react-native-elements/dist/buttons/Button";
import RedButton from "../../../components/button/RedButton";
import routes from "../../../utils/enum/routes";
import MBTITypeButton from "../components/MBTITypeButton";
import styles from "./MBTIScreen.styles";

const MBTITypes = [
  {
    type: "ENFJ",
    name: "Người chỉ dạy",
  },
  {
    type: "ENFP",
    name: "Người truyền cảm hứng",
  },
  {
    type: "ENTJ",
    name: "Nhà điều hành",
  },
  {
    type: "ENTP",
    name: "Người có tầm nhìn xa",
  },
  {
    type: "ESFJ",
    name: "Người quan tâm",
  },
  {
    type: "ESFP",
    name: "Người trình diễn",
  },
  {
    type: "ESTJ",
    name: "Người giám sát",
  },
  {
    type: "ESTP",
    name: "Người thực thi",
  },
  {
    type: "INFJ",
    name: "Người che chở",
  },
  {
    type: "INFP",
    name: "Người duy tâm",
  },
  {
    type: "INTJ",
    name: "Nhà khoa học",
  },
  {
    type: "INTP",
    name: "Nhà tư duy",
  },
  {
    type: "ISFJ",
    name: "Người nuôi dưỡng",
  },
  {
    type: "ISFP",
    name: "Người nghệ sĩ",
  },
  {
    type: "ISTJ",
    name: "Người trách nhiệm",
  },
  {
    type: "ISTP",
    name: "Nhà cơ học",
  },
];
const MBTIScreen = ({ navigation }) => {
  const hanldePress = (type) => {
    navigation.navigate(routes.AI_TEST, { MBTIType: type });
  };
  const handlePress = () => {
    Linking.openURL("https://mbti.vn/");
  };
  return (
    <ScrollView style={styles.container}>
      <Text style={styles.title}>Bạn thuộc nhóm tính cách nào?</Text>
      <View style={styles.MBTIContainer}>
        {MBTITypes.map((item) => (
          <MBTITypeButton onPress={hanldePress} {...item} key={item.type} />
        ))}
      </View>
      <RedButton
        title="Kiểm tra ngay"
        style={styles.button}
        onPress={handlePress}
      />
    </ScrollView>
  );
};

export default MBTIScreen;
