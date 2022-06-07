import {createNativeStackNavigator} from '@react-navigation/native-stack';
import React, {useContext} from 'react';
import {AuthContext} from '../contexts/AuthContext';
import routes from '../utils/enum/routes';
import GettingStartedScreen from '../features/startup/screens/GettingStartedScreen';
const Stack = createNativeStackNavigator();

const AuthNavigation = () => {
  const {state} = useContext(AuthContext);
  return (
    <Stack.Navigator screenOptions={{headerShown: false}}>
      {state.isFirst && (
        <Stack.Screen
          name={routes.GETTING_STARTED}
          component={GettingStartedScreen}
        />
      )}
    </Stack.Navigator>
  );
};

export default AuthNavigation;
