import React, { useContext } from "react";
import { createNavigationContainerRef } from "@react-navigation/native";
import { createStackNavigator } from "@react-navigation/stack";
import routes from "../utils/enum/routes";
import MainNavigation from "../navigation/MainNavigation";
import ChatRoomScreen from "../features/chat/screens/ChatRoomScreen";
import { colors } from "../theme/colors";
import AITestScreen from "../features/career_test/screens/AITestScreen";
import { AuthContext } from ".";
import UserTypeModal from "../features/modals/UserTypeModal";
import StudentLoginScreen from "../features/auth/screens/StudentLoginScreen";
import StudentRegisterScreen from "../features/auth/screens/StudentRegisterScreen";
import HollandTestScreen from "../features/career_test/screens/HollandTestScreen";
import CommentListScreen from "../features/comment/CommentListScreen";
import CounselorLoginScreen from "../features/auth/screens/CounselorLoginScreen";
import CounselorRegisterScreen from "../features/auth/screens/CounselorRegisterScreen";
import OTPAuthScreen from "../features/auth/screens/OTPAuthScreen";
import UniversityDetailScreen from "../features/university/screens/UniversityDetailScreen";
import MajorDetailScreen from "../features/major/screens/MajorDetailScreen";
import LoadingModal from "../features/modals/LoadingModal";
import HollandQuestionScreen from "../features/career_test/screens/HollandQuestionScreen";
import HollandResultModal from "../features/modals/HollandResultModal";
import MBTIScreen from "../features/career_test/screens/MBTIScreen";
export const navigationRef = createNavigationContainerRef();

export function navigate(name, params) {
  if (navigationRef.isReady()) {
    navigationRef.navigate(name, params);
  }
}

export function RootNavigation() {
  const Stack = createStackNavigator();
  const {
    state: { profile },
  } = useContext(AuthContext);
  return (
    <Stack.Navigator>
      <Stack.Screen
        options={{ headerShown: false }}
        name={routes.MAIN_NAVIGATION}
        component={MainNavigation}
      />
      <Stack.Group
        screenOptions={{
          headerStyle: {
            backgroundColor: colors.brand.primary,
          },
          headerTintColor: colors.text.inverse,
        }}
      >
        <Stack.Screen name={routes.CHAT_ROOM} component={ChatRoomScreen} />
        {/* CareerTest */}
        <Stack.Screen
          name={routes.HOLLAND_TEST}
          component={HollandTestScreen}
          options={{ title: "HOLLAND" }}
        />
        <Stack.Screen
          name={routes.MBTI}
          component={MBTIScreen}
          options={{ title: "Tư vấn cùng hệ chuyên gia" }}
        />
        <Stack.Screen
          name={routes.AI_TEST}
          component={AITestScreen}
          options={{ title: "Tư vấn cùng hệ chuyên gia" }}
        />
        <Stack.Screen
          name={routes.HOLLAND_QUESTION}
          component={HollandQuestionScreen}
          options={({ route }) => ({ title: route.params.name })}
        />
        {/* Auth */}
        {!profile && (
          <>
            <Stack.Screen
              name={routes.STUDENT_LOGIN}
              component={StudentLoginScreen}
              options={{
                headerShown: false,
              }}
            />
            <Stack.Screen
              name={routes.STUDENT_REGISTER}
              component={StudentRegisterScreen}
              options={{
                headerShown: false,
              }}
            />
            <Stack.Screen
              name={routes.COUNSELOR_LOGIN}
              component={CounselorLoginScreen}
              options={{
                headerShown: false,
              }}
            />
            <Stack.Screen
              name={routes.COUNSELOR_REGISTER}
              component={CounselorRegisterScreen}
              options={{
                headerShown: false,
              }}
            />
            <Stack.Screen
              name={routes.OTP_AUTH}
              component={OTPAuthScreen}
              options={{
                headerShown: false,
              }}
            />
          </>
        )}
        {/* Modal */}
        <Stack.Screen
          name={routes.AUTH_MODAL}
          component={UserTypeModal}
          options={{
            presentation: "transparentModal",
            headerShown: false,
          }}
        />
         <Stack.Screen
          name={routes.HOLLAND_RESULT}
          component={HollandResultModal}
          options={{
            presentation: "transparentModal",
            headerShown: false,
          }}
        />
        <Stack.Screen name={routes.COMMENT} component={CommentListScreen} />
        <Stack.Screen
          name={routes.UNIVERSITY_DETAIL}
          component={UniversityDetailScreen}
          options={{ title: "Đang tải..." }}
        />
        <Stack.Screen
          name={routes.MAJOR_DETAIL}
          component={MajorDetailScreen}
          options={{ title: "Đang tải..." }}
        />
        <Stack.Screen
          name={routes.LOADING_MODAL}
          component={LoadingModal}
          options={{
            presentation: "transparentModal",
            headerShown: false,
          }}
        />
      </Stack.Group>
    </Stack.Navigator>
  );
}
