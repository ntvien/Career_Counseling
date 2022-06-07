import React, { useContext, useEffect, useState } from "react";
import { View, Text, Button, Image, Alert, ScrollView } from "react-native";
import { Icon, Input } from "react-native-elements";
import { styles } from "./AuthScreen.styles";
import FontAwesome5 from "react-native-vector-icons/FontAwesome5";
import { Fumi } from "react-native-textinput-effects";
import { colors } from "../../../theme/colors";
import studentApi from "../../../api/http/user/studentApi";
import routes from "../../../utils/enum/routes";
import { AuthContext } from "../../../navigation";
import { actionCreators } from "../../../reducers/authReducer";
import storageKeys from "../../../utils/enum/storageKeys";
import userTypes from "../../../utils/enum/userTypes";
import EncryptedStorage from "react-native-encrypted-storage";
import RedButton from "../../../components/button/RedButton";

const StudentRegisterScreen = ({ navigation }) => {
  const [fullName, setFullName] = useState("");
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [disabledButton, setDisabledButton] = useState(true);
  const { dispatch } = useContext(AuthContext);
  const onRegister = async () => {
    try {
      await studentApi.register({
        userName,
        password,
        fullName,
      });
      navigation.navigate(routes.STUDENT_LOGIN);
    } catch (e) {
      Alert.alert("Tài khoản đã tồn tại");
      console.log(e);
    }
  };
  useEffect(() => {
    if (userName && password && fullName) {
      setDisabledButton(false);
    } else {
      setDisabledButton(true);
    }
  }, [userName, password, fullName]);
  return (
    <ScrollView>
      <View style={styles.container}>
        <Image source={require("../../../assets/red-logo.png")} />
        <Text style={styles.title}>Đăng ký</Text>
        <View style={styles.textContainer}>
          <Text>Chào mừng đến với Career Counseling</Text>
          <Text>với vai trò học sinh</Text>
        </View>
        <Fumi
          label={"Tên của bạn"}
          iconClass={FontAwesome5}
          iconName={"address-card"}
          iconColor={colors.brand.primary}
          iconSize={20}
          inputStyle={styles.inputText}
          style={styles.inputTextContainer}
          onChangeText={(value) => setFullName(value)}
          inputPadding={20}
        />
        <Fumi
          label={"Tên đăng nhập"}
          iconClass={FontAwesome5}
          iconName={"user"}
          iconColor={colors.brand.primary}
          iconSize={20}
          inputStyle={styles.inputText}
          style={styles.inputTextContainer}
          onChangeText={(value) => setUserName(value)}
          inputPadding={20}
        />
        <Fumi
          label={"Mật khẩu"}
          iconClass={FontAwesome5}
          iconName={"key"}
          iconColor={colors.brand.primary}
          iconSize={20}
          inputStyle={styles.inputText}
          style={styles.inputTextContainer}
          inputPadding={20}
          onChangeText={(value) => setPassword(value)}
          secureTextEntry
        />
        <RedButton
          title="Đăng ký"
          style={styles.button}
          onPress={onRegister}
          disabled={disabledButton}
        />
        <Text>
          Chưa có tài khoản?{" "}
          <Text
            onPress={() => navigation.navigate(routes.STUDENT_LOGIN)}
            style={styles.buttonText}
          >
            Đăng nhập
          </Text>
        </Text>
      </View>
    </ScrollView>
  );
};

export default StudentRegisterScreen;
