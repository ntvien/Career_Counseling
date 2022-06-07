import React, { useContext, useState } from "react";
import routes from "../utils/enum/routes";
import { createDrawerNavigator, DrawerItem } from "@react-navigation/drawer";
import HomeScreen from "../features/home/HomeScreen";
import ChatListScreen from "../features/chat/screens/ChatListScreen";
import MajorListScreen from "../features/major/screens/MajorListScreen";
import UniversityListScreen from "../features/university/screens/UniversityListScreen";
import { colors } from "../theme/colors";
import {
  StyleSheet,
  Modal,
  View,
  Text,
  Pressable,
  Button,
  Image,
} from "react-native";
import CareerTestScreen from "../features/career_test/screens/CareerTestScreen";
import { Icon } from "react-native-elements";
import { AuthContext } from ".";
import {
  DrawerContentScrollView,
  DrawerItemList,
} from "@react-navigation/drawer";
import { actionCreators } from "../reducers/authReducer";
import CustomDrawerContent from "./components/CustomDrawerContent";
import UserTypeModal from "../features/modals/UserTypeModal";
import userTypes from "../utils/enum/userTypes";

const Drawer = createDrawerNavigator();

const MainNavigation = () => {
  const {
    state: { profile, userType },
  } = useContext(AuthContext);
  return (
    <Drawer.Navigator
      drawerContent={(props) => <CustomDrawerContent {...props} />}
      initialRouteName={routes.HOME}
      screenOptions={({ navigation }) => ({
        headerStyle: {
          backgroundColor: colors.brand.primary,
        },
        drawerStyle: {
          backgroundColor: colors.bg.drawer,
        },
        drawerLabelStyle: {
          color: colors.text.primary,
        },
        drawerItemStyle: {
          borderBottomWidth: 1,
          borderBottomColor: "#D0CBCB",
          marginVertical: 0,
        },
        headerTintColor: colors.text.inverse,
        headerRight: () =>
          !profile && (
            <View style={{ marginRight: 8 }}>
              <Icon
                containerStyle={styles.buttonRight}
                color={colors.text.inverse}
                name="sign-in-alt"
                onPress={() => {
                  navigation.navigate(routes.AUTH_MODAL);
                }}
                type="font-awesome-5"
                size={30}
              />
            </View>

          ),
      })}
    >
      <Drawer.Screen
        name={routes.HOME}
        component={HomeScreen}
        options={{
          title: "Trang chủ",
          drawerIcon: () => (
            <Icon
              type="font-awesome-5"
              name="home"
              color={colors.brand.primary}
              iconStyle={{ width: 30 }}
            />
          ),
        }}
      />

      <Drawer.Screen
        name={routes.CAREER_TEST}
        component={profile ? CareerTestScreen : UserTypeModal}
        options={{
          title: "Hiểu mình",
          drawerIcon: () => (
            <Icon
              type="font-awesome-5"
              name="lightbulb"
              color={colors.brand.primary}
              iconStyle={{ width: 30 }}
            />
          ),
        }}
      />
      <Drawer.Screen
        name={routes.MAJOR}
        component={MajorListScreen}
        options={{
          title: "Hiểu ngành",
          drawerIcon: () => (
            <Icon
              type="font-awesome-5"
              name="book-open"
              color={colors.brand.primary}
              iconStyle={{ width: 30 }}
            />
          ),
        }}
      />
      <Drawer.Screen
        name={routes.UNIVERSITY}
        component={UniversityListScreen}
        options={{
          title: "Hiểu trường",
          drawerIcon: () => (
            <Icon
              type="font-awesome-5"
              name="school"
              color={colors.brand.primary}
              iconStyle={{ width: 30 }}
            />
          ),
        }}
      />
      <Drawer.Screen
        name={routes.CHAT}
        component={profile ? ChatListScreen : UserTypeModal}
        options={{
          title: "Tư vấn",
          drawerIcon: () => (
            <Icon
              type="font-awesome-5"
              name="comments"
              color={colors.brand.primary}
              iconStyle={{ width: 30 }}
            />
          ),
        }}
      />
    </Drawer.Navigator>
  );
};

const styles = StyleSheet.create({
  buttonRight: {
    padding: 5,
  },
});
export default MainNavigation;
