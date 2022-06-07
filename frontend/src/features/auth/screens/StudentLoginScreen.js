import React, { useContext, useEffect, useState } from "react";
import { View, Text, Button, Image, Alert, Keyboard } from "react-native";
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
import userApi from "../../../api/http/user/userApi";
import messaging from "@react-native-firebase/messaging";
import { ScrollView } from "react-native-gesture-handler";
const StudentLoginScreen = ({ navigation }) => {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [disabledButton, setDisabledButton] = useState(true);
  const { dispatch } = useContext(AuthContext);
  const onLogin = async () => {
    navigation.navigate(routes.LOADING_MODAL);
    Keyboard.dismiss();
    try {
      const { accessToken, refreshToken } = await studentApi.login({
        userName,
        password,
      });
      const promise1 = EncryptedStorage.setItem(
        storageKeys.ACCESS_TOKEN,
        accessToken
      );
      const promise2 = EncryptedStorage.setItem(
        storageKeys.REFRESH_TOKEN,
        refreshToken
      );
      const promise3 = EncryptedStorage.setItem(
        storageKeys.USER_TYPE,
        userTypes.STUDENT
      );
      await Promise.all(promise1, promise2, promise3);
      navigation.navigate(routes.MAIN_NAVIGATION);
      const profile = await studentApi.getProfile();
      const tokenDevice = await messaging().getToken();
      console.log("token: ", tokenDevice);
      userApi.addUserDevice(tokenDevice);
      dispatch(actionCreators.login({ profile, userType: userTypes.STUDENT }));
    } catch (e) {
      navigation.pop();
      Alert.alert("Tài khoản không tồn tại");
      console.log(e);
    }
  };
  useEffect(() => {
    if (userName && password) {
      setDisabledButton(false);
    } else {
      setDisabledButton(true);
    }
  }, [userName, password]);
  return (
    <ScrollView>
      <View style={styles.container}>
        <Image source={require("../../../assets/red-logo.png")} />
        <Text style={styles.title}>Đăng nhập</Text>
        <View style={styles.textContainer}>
          <Text>Chào mừng đến với Career Counseling</Text>
          <Text>với vai trò học sinh</Text>
        </View>
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
          title="Đăng nhập"
          style={styles.button}
          onPress={onLogin}
          disabled={disabledButton}
        />
        <Text>
          Chưa có tài khoản?{" "}
          <Text
            onPress={() => navigation.navigate(routes.STUDENT_REGISTER)}
            style={styles.buttonText}
          >
            Đăng ký
          </Text>
        </Text>
      </View>
    </ScrollView>
  );
};

export default StudentLoginScreen;
