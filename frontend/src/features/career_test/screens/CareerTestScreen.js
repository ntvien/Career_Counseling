import React, { useContext } from "react";
import { View, Text, Button } from "react-native";
import { Avatar } from "react-native-elements/dist/avatar/Avatar";
import RedButton from "../../../components/button/RedButton";
import { AuthContext } from "../../../navigation";
import routes from "../../../utils/enum/routes";
import { styles } from "./CareerTestScreen.styles";

const CareerTestScreen = ({ navigation }) => {
  const {
    state: { profile },
  } = useContext(AuthContext);

  return (
    <View style={styles.container}>
      <Avatar
        size="xlarge"
        rounded
        source={
          profile?.avatar
            ? { uri: profile.avatar }
            : require("../../../assets/avatar.png")
        }
        containerStyle={styles.avatar}
      />
      <Text style={styles.profileText}><Text style={styles.fieldText}>Tên đăng nhập:</Text> {profile?.userName}</Text>
      <Text style={styles.profileText}><Text style={styles.fieldText}>Tên người dùng:</Text> {profile?.fullName}</Text>
      <RedButton style={styles.button}
        title="Trắc nghiệm nghề nghiệp (HOLLAND)"
        onPress={() => navigation.navigate(routes.HOLLAND_TEST)}
      />
      <RedButton style={styles.button}
        title="Tư vấn cùng hệ chuyên gia"
        onPress={() => navigation.navigate(routes.MBTI)}
      />
    </View>
  );
};

export default CareerTestScreen;
