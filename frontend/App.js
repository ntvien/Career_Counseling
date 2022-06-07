import "react-native-gesture-handler";
import React from "react";
import Navigation from "./src/navigation";
import { LogBox } from "react-native";
const App = () => {
  LogBox.ignoreAllLogs();
  return <Navigation />;
};

export default App;
