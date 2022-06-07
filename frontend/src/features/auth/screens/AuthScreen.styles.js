import { StyleSheet } from "react-native";
import { colors } from "../../../theme/colors";
import { fontSizes } from "../../../theme/fonts";
import paddings from "../../../theme/paddings";

export const styles = StyleSheet.create({
  container: {
    padding: paddings.container,
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
  title: {
    color: colors.brand.primary,
    fontWeight: "bold",
    fontSize: fontSizes.h2,
    marginVertical: 5,
  },
  textContainer: {
    alignItems: "center",
    marginBottom: 20,
  },
  buttonText: {
    color: colors.brand.primary,
  },
  inputText: {
    color: "#000000",
    textDecorationLine: "none",
    fontSize: fontSizes.body
  },
  inputTextContainer: {
    borderRadius: 20,
    width: 320,
    marginVertical: 10,
    backgroundColor: colors.bg.input,
  },
  button: {
    marginVertical: 10,
  },
});
