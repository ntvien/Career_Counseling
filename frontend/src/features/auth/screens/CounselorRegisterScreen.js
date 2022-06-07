import React, { useContext, useEffect, useState } from "react";
import {
  View,
  Text,
  Button,
  Image,
  Alert,
  TouchableOpacity,
  KeyboardAvoidingView,
  TouchableWithoutFeedback,
  Keyboard,
  ScrollView,
  Dimensions,
} from "react-native";
import { styles } from "./CounselorRegisterScreen.styles";
import FontAwesome5 from "react-native-vector-icons/FontAwesome5";
import { Fumi } from "react-native-textinput-effects";
import { colors } from "../../../theme/colors";
import routes from "../../../utils/enum/routes";
import RedButton from "../../../components/button/RedButton";
import counselorApi from "../../../api/http/user/counselorApi";
import Autocomplete from "react-native-autocomplete-input";
import universityApi from "../../../api/http/resource/universityApi";

const CounselorRegisterScreen = ({ navigation }) => {
  const [email, setEmail] = useState("");
  const [universityId, setUniversityId] = useState(null);
  const [phone, setPhone] = useState("");
  const [disabledButton, setDisabledButton] = useState(true);
  const [universities, setUniversities] = useState([]);
  const [query, setQuery] = useState(null);
  const isLoading = !universities.length;
  const queriedUniversities = query
    ? universities.filter(
        (item) => item.name.search(new RegExp(`${query.trim()}`, "i")) >= 0
      )
    : [];

  const onRegister = async () => {
    try {
      navigation.navigate(routes.LOADING_MODAL);
      await counselorApi.register({
        phone,
        email,
        universityId,
      });
      navigation.navigate(routes.COUNSELOR_LOGIN);
    } catch (e) {
      console.log(e);
    }
  };
  const onLogin = () => {
    navigation.navigate(routes.COUNSELOR_LOGIN);
  };
  useEffect(async () => {
    let page = 1;
    const size = 30;
    setUniversities([]);
    try {
      while (true) {
        const { hasNext, items } = await universityApi.getUniversityNameList({
          page: page,
          size: size,
        });
        setUniversities((state) => [...state, ...items]);
        if (!hasNext) break;
        else page++;
      }
    } catch (e) {
      console.log(e);
    }
  }, []);
  useEffect(() => {
    if (!email?.match(/^[^\s@]+@[^\s@]+\.[^\s@]+$/)) {
      setDisabledButton(true);
    } else if (
      !phone ||
      phone.length > 10 ||
      phone.length < 9 ||
      (phone.charAt(0) == "0" && phone.length < 10)
    ) {
      setDisabledButton(true);
    } else if (!universityId) {
      setDisabledButton(true);
    } else {
      setDisabledButton(false);
    }
  }, [phone, email, universityId]);
  return (
    <ScrollView>
      <KeyboardAvoidingView behavior={"padding"} style={{ flex: 1 }}>
        <View style={styles.container}>
          <Image source={require("../../../assets/red-logo.png")} />
          <Text style={styles.title}>Đăng ký</Text>
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
            onChangeText={(value) => setPhone(value)}
            inputPadding={20}
            keyboardType="number-pad"
          />
          <Fumi
            label={"Email của bạn"}
            iconClass={FontAwesome5}
            iconName={"envelope"}
            iconColor={colors.brand.primary}
            iconSize={20}
            inputStyle={styles.inputText}
            style={styles.inputTextContainer}
            onChangeText={(value) => setEmail(value)}
            inputPadding={20}
          />
          <Autocomplete
            editable={!isLoading}
            autoCorrect={false}
            data={
              queriedUniversities?.length > 0 &&
              queriedUniversities[0].name.toLowerCase() == query.toLowerCase()
                ? []
                : queriedUniversities
            }
            renderTextInput={() => (
              <Fumi
                label={"Trường đại học"}
                iconClass={FontAwesome5}
                iconName={"school"}
                iconColor={colors.brand.primary}
                iconSize={20}
                inputStyle={styles.inputText}
                style={styles.inputTextContainer}
                value={query}
                onChangeText={setQuery}
                inputPadding={20}
              />
            )}
            containerStyle={styles.autocompleteContainer}
            inputContainerStyle={{ borderWidth: 0 }}
            flatListProps={{
              keyboardShouldPersistTaps: "always",
              keyExtractor: (item) => item._id,
              renderItem: ({ item: { name, _id } }) => (
                <TouchableOpacity
                  onPress={() => {
                    Keyboard.dismiss();
                    setUniversityId(_id);
                    setQuery(name);
                  }}
                >
                  <Text style={styles.itemText}>{name}</Text>
                </TouchableOpacity>
              ),
            }}
          />

          <RedButton
            title="Đăng ký"
            style={styles.button}
            onPress={onRegister}
            disabled={disabledButton}
          />
          <Text>
            Chưa có tài khoản?{" "}
            <Text onPress={onLogin} style={styles.buttonText}>
              Đăng nhập
            </Text>
          </Text>
        </View>
      </KeyboardAvoidingView>
    </ScrollView>
  );
};

export default CounselorRegisterScreen;
