import { StyleSheet, Dimensions } from "react-native";
import { styles as authStyles } from "./AuthScreen.styles";

export const styles = StyleSheet.create({
  itemText: {
    fontSize: 15,
    margin: 2,
  },
  autocompleteContainer: {
    flex: null,
    overflow: "hidden",
    zIndex: 1,
  },
  ...authStyles,
  logo: {
    flex: 1,
    // height: Dimensions.get("screen").height / 6,
    resizeMode: "contain"
  },
});
