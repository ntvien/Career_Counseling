import React, { useContext } from "react";
import {
  DrawerContentScrollView,
  DrawerItemList,
  DrawerItem,
} from "@react-navigation/drawer";
import { Icon } from "react-native-elements";
import { Image, StyleSheet } from "react-native";
import { AuthContext } from "..";
import { colors } from "../../theme/colors";
import { Avatar } from "react-native-elements";
import EncryptedStorage from "react-native-encrypted-storage";
import { actionCreators } from "../../reducers/authReducer";
import userTypes from "../../utils/enum/userTypes";
import auth from "@react-native-firebase/auth";
function CustomDrawerContent(props) {
  const {
    dispatch,
    state: { profile, userType },
  } = useContext(AuthContext);
  const onLogout = async () => {
    try {
      const promise1 = EncryptedStorage.removeItem(storageKeys.ACCESS_TOKEN);
      const promise2 = EncryptedStorage.removeItem(storageKeys.REFRESH_TOKEN);
      const promise3 = EncryptedStorage.removeItem(storageKeys.USER_TYPE);
      const promise4 =
        userType == userTypes.COUNSELOR ? auth().signOut() : null;
      await Promise.all(promise1, promise2, promise3, promise4);
      dispatch(actionCreators.logout());
    } catch (e) {
      console.log(e);
    }
  };

  return (
    <DrawerContentScrollView {...props}>
      <Avatar
        size="xlarge"
        rounded
        source={
          userType == userTypes.STUDENT && profile?.avatar
            ? { uri: profile.avatar }
            : userType == userTypes.COUNSELOR && profile?.university?.logo
            ? { uri: profile.university.logo }
            : require("../../assets/avatar.png")
        }
        containerStyle={styles.avatar}
        imageProps={styles.image}
      />
      <DrawerItemList {...props} />
      <DrawerItem
        label="Đăng xuất"
        onPress={() => alert("Link to help")}
        icon={() => (
          <Icon
            type="font-awesome-5"
            name="sign-out-alt"
            color={colors.brand.primary}
            iconStyle={{ width: 30 }}
          />
        )}
        labelStyle={{ color: colors.text.primary }}
        style={{
          borderTopColor: "#D0CBCB",
        }}
        onPress={onLogout}
      />
    </DrawerContentScrollView>
  );
}
const styles = StyleSheet.create({
  avatar: {
    alignSelf: "center",
    marginBottom: 20,
  },
  image: {
    resizeMode: "stretch",
  },
});
export default CustomDrawerContent;
