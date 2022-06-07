import { StyleSheet } from "react-native";
import { colors } from "../../../theme/colors";
import { fontSizes } from "../../../theme/fonts";
import paddings from "../../../theme/paddings";
import { styles as authStyles } from "./AuthScreen.styles";

export const styles = {
  OTPContainer: {
    width: "80%",
    height: 100,
  },
  underlineStyleBase: {
    width: 40,
    height: 50,
    borderWidth: 0,
    borderBottomWidth: 2,
    borderColor: "#000",
    color: "#000",
    fontSize: 22,
  },
  underlineStyleHighLighted: {
    borderColor: colors.ui.success,
  },
  OTPError: {
    borderColor: colors.ui.error,
    color: colors.ui.error,
  },
  errorText: {
    color: colors.ui.error,
    fontSize: fontSizes.body,
  },
  message: {
    marginTop: 10,
    fontSize: fontSizes.caption,
    textAlign: "center",
  },
  ...authStyles,
};
