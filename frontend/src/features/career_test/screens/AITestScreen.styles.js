import { StyleSheet } from "react-native";
import { colors } from "../../../theme/colors";
import { fontSizes } from "../../../theme/fonts";

export const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 10,
    backgroundColor: colors.bg.primary,
  },
  suggestText: {
    marginTop: 20,
    marginBottom: 10,
    fontWeight: "bold",
    fontSize: 20,
    color: colors.brand.primary,
  },
  text: {
    fontSize: fontSizes.h3,
    color: colors.brand.primary,
    textAlign: "center",
    margin: 10,
    fontWeight: "bold"
  },
  backButton: {},
});
