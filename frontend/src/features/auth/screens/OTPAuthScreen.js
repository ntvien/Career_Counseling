import React, { useContext, useEffect, useState } from "react";
import { View, Text, Button, Image, Alert } from "react-native";
import { styles } from "./OTPAuthScreen.styles";
import routes from "../../../utils/enum/routes";
import { AuthContext } from "../../../navigation";
import OTPInputView from "@twotalltotems/react-native-otp-input";
import messaging from "@react-native-firebase/messaging";
import RedButton from "../../../components/button/RedButton";
import auth from "@react-native-firebase/auth";
import counselorApi from "../../../api/http/user/counselorApi";
import userTypes from "../../../utils/enum/userTypes";
import userApi from "../../../api/http/user/userApi";
import EncryptedStorage from "react-native-encrypted-storage";
import storageKeys from "../../../utils/enum/storageKeys";
import { actionCreators } from "../../../reducers/authReducer";

const OTPAuthScreen = ({ route, navigation }) => {
  const { phone } = route.params;
  const [code, setCode] = useState("");
  const [error, setError] = useState(false);
  const [disabledButton, setDisabledButton] = useState(true);
  const [confirm, setConfirm] = useState(null);
  const { dispatch } = useContext(AuthContext);
  const onCodeFilled = (value) => {
    setCode(value);
  };
  const processAuth = async (token) => {
    try {
      const { accessToken, refreshToken } = await counselorApi.login({
        token,
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
        userTypes.COUNSELOR
      );
      await Promise.all(promise1, promise2, promise3);
      const profile = await counselorApi.getProfile();
      const tokenDevice = await messaging().getToken();
      userApi.addUserDevice(tokenDevice);
      dispatch(
        actionCreators.login({ profile, userType: userTypes.COUNSELOR })
      );
      navigation.navigate(routes.MAIN_NAVIGATION);
    } catch (error) {
      console.log(error);
      setError(true);
    }
  };

  const onConfirm = async () => {
    try {
      navigation.navigate(routes.LOADING_MODAL);
      await confirm.confirm(code);
    } catch (e) {
      console.log(e);
      navigation.goBack();
    }
  };

  const resendCode = async () => {
    if (confirm) {
      try {
        const confirmation = await auth().signInWithPhoneNumber("+84" + phone);
        setConfirm(confirmation);
      } catch (e) {
        console.log(e.code);
      }
    }
  };

  const onCodeChanged = (code) => {
    if (code.length == 5) setError(false);
  };
  useEffect(() => {
    if (confirm && code) {
      setDisabledButton(false);
    } else {
      setDisabledButton(true);
    }
  }, [code]);

  useEffect(async () => {
    try {
      const confirmation = await auth().signInWithPhoneNumber("+84" + phone);
      setConfirm(confirmation);
    } catch (e) {
      console.log(e.code);
    }
    const subscriber = auth().onAuthStateChanged(async (user) => {
      if (user) {
        const token = await user.getIdToken();
        await processAuth(token);
      }
    });
    return subscriber;
  }, []);
  return (
    <View style={styles.container}>
      <Image source={require("../../../assets/red-logo.png")} />
      <Text style={styles.title}>Xác thực mã OTP</Text>
      <View style={styles.textContainer}>
        <Text>Vui lòng nhập mã 6 chữ số được gửi đến</Text>
        <Text>{phone} qua SMS</Text>
      </View>
      {error && (
        <Text style={styles.errorText}>
          Mã không chính xác. Vui lòng thử lại.
        </Text>
      )}
      <OTPInputView
        style={styles.OTPContainer}
        pinCount={6}
        autoFocusOnLoad
        codeInputFieldStyle={[
          styles.underlineStyleBase,
          error && styles.OTPError,
        ]}
        codeInputHighlightStyle={styles.underlineStyleHighLighted}
        onCodeFilled={onCodeFilled}
        onCodeChanged={onCodeChanged}
        autoFocusOnLoad
      />
      <Text>
        Bạn không nhận được mã?{" "}
        <Text onPress={resendCode} style={styles.buttonText}>
          Gửi lại mã
        </Text>
      </Text>
      <RedButton
        title="Xác nhận"
        style={styles.button}
        onPress={onConfirm}
        disabled={disabledButton}
      />
      <Text style={styles.message}>
        Bằng cách tiếp tục, bạn cho biết rằng bạn chấp nhận Điều khoản sử dụng
        và Chính sách quyền riêng tư của chúng tôi
      </Text>
    </View>
  );
};

export default OTPAuthScreen;
