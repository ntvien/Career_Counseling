import React, { useEffect, useState, createContext, useReducer } from "react";
import { NavigationContainer } from "@react-navigation/native";
import { navigationRef, RootNavigation } from "./RootNavigation";
import storageKeys from "../utils/enum/storageKeys";
import GettingStartedScreen from "../features/startup/screens/GettingStartedScreen";
import SplashScreen from "../features/startup/screens/SplashScreen";
import EncryptedStorage from "react-native-encrypted-storage";
import AsyncStorage from "@react-native-async-storage/async-storage";
import {
  reducer,
  intitialState,
  actionCreators,
} from "../reducers/authReducer";
import { colors } from "../theme/colors";
import userApi from "../api/http/user/userApi";
import userTypes from "../utils/enum/userTypes";
import studentApi from "../api/http/user/studentApi";
import counselorApi from "../api/http/user/counselorApi";
import { Image } from "react-native";
import NetInfo from "@react-native-community/netinfo";

export const AuthContext = createContext();
const myTheme = {
  dark: false,
  colors: {
    background: colors.bg.primary,
    card: colors.brand.primary,
  },
};
export default Navigation = () => {
  const [state, dispatch] = useReducer(reducer, intitialState);
  const [isLoading, setIsLoading] = useState(true);

  const checkStorage = async () => {
    try {
      const accessToken = await EncryptedStorage.getItem(
        storageKeys.ACCESS_TOKEN
      );
      const userType = await EncryptedStorage.getItem(storageKeys.USER_TYPE);
      if (accessToken && userType) {
        let profile = {};
        try {
          if (userType == userTypes.STUDENT) {
            profile = await studentApi.getProfile();
          } else {
            profile = await counselorApi.getProfile();
          }
        } catch (e) {
          console.log(e);
        }
        dispatch(
          actionCreators.restoreState({
            profile,
            userType,
          })
        );
      } else {
        const isOpen = await AsyncStorage.getItem(storageKeys.IS_OPEN);
        if (isOpen) {
          dispatch(actionCreators.open());
        }
      }

      const advertising_images = JSON.parse(
        await AsyncStorage.getItem(storageKeys.ADVERTISING_IMAGES)
      );
      if (advertising_images != null) {
        advertising_images.map((image) => Image.prefetch(image.uri));
      }
    } catch (e) {
      console.log(e);
    }
    setIsLoading(false);
  };

  useEffect(() => {
    checkStorage();
    const unsubscribe = NetInfo.addEventListener((state) => {
      if (state.isConnected) {
        checkStorage();
      }
    });
    return unsubscribe;
  }, []);

  if (isLoading) {
    return <SplashScreen />;
  }

  return (
    <AuthContext.Provider value={{ state, dispatch }}>
      <NavigationContainer ref={navigationRef} theme={myTheme}>
        {state.isOpen ? <RootNavigation /> : <GettingStartedScreen />}
      </NavigationContainer>
    </AuthContext.Provider>
  );
};
