import { StyleSheet } from "react-native";
import { colors } from "../../../theme/colors";
import { fontSizes } from "../../../theme/fonts";
import paddings from "../../../theme/paddings";

export const styles = StyleSheet.create({
  container: {
    padding: paddings.container,
    marginBottom: 10
  },
  topContainer: {
    height: 400,
    alignItems: "center",
    justifyContent: "center",
  },
  text: {
    fontSize: fontSizes.body,
    marginBottom: 10,
  },
  title: {
    fontSize: fontSizes.h4,
    fontWeight: "bold",
  },
});
