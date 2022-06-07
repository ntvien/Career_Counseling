import React, { useContext, useEffect, useState } from "react";
import { View, Text, Button, Image, Alert } from "react-native";
import { Icon, Input } from "react-native-elements";
import { styles } from "./AuthScreen.styles";
import FontAwesome5 from "react-native-vector-icons/FontAwesome5";
import { Fumi } from "react-native-textinput-effects";
import { colors } from "../../../theme/colors";
import routes from "../../../utils/enum/routes";
import { AuthContext } from "../../../navigation";
import auth from "@react-native-firebase/auth";
import RedButton from "../../../components/button/RedButton";

const CounselorLoginScreen = ({ navigation }) => {
  const [phone, setPhone] = useState("");
  const [disabledButton, setDisabledButton] = useState(true);
  const { dispatch } = useContext(AuthContext);
  const onLogin = () => {
    navigation.navigate(routes.OTP_AUTH, { phone });
  };
  const onResgiter = () => navigation.navigate(routes.COUNSELOR_REGISTER);
  const onChangePhone = (value) => {
    setPhone(value);
  };
  useEffect(() => {
    if (
      (phone.charAt(0) == "0" && phone.length != 10) ||
      (phone.charAt(0) != "0" && phone.length != 9)
    ) {
      setDisabledButton(true);
    } else {
      setDisabledButton(false);
    }
  }, [phone]);
  return (
    <View style={styles.container}>
      <Image source={require("../../../assets/red-logo.png")} />
      <Text style={styles.title}>Đăng nhập</Text>
      <View style={styles.textContainer}>
        <Text>Chào mừng đến với Career Counseling</Text>
        <Text>với vai trò tư vấn viên</Text>
      </View>
      <Fumi
        label={"Số điện thoại"}
        iconClass={FontAwesome5}
        iconName={"phone"}
        iconColor={colors.brand.primary}
        iconSize={20}
        inputStyle={styles.inputText}
        style={styles.inputTextContainer}
        onChangeText={onChangePhone}
        inputPadding={20}
        autoComplete="tel-device"
        keyboardType="number-pad"
      />
      <RedButton
        title="Đăng nhập"
        style={styles.button}
        onPress={onLogin}
        disabled={disabledButton}
      />
      <Text>
        Chưa có tài khoản?{" "}
        <Text onPress={onResgiter} style={styles.buttonText}>
          Đăng ký
        </Text>
      </Text>
    </View>
  );
};

export default CounselorLoginScreen;
