import { StyleSheet } from "react-native";
import { fontSizes } from "../../../theme/fonts";
import paddings from "../../../theme/paddings";

export const styles = StyleSheet.create({
  container: {
    padding: paddings.container,
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
  avatar: {
    marginBottom: 20
  },
  profileText: {
    marginVertical: 10,
    alignSelf: 'flex-start',
    fontSize: fontSizes.h5,
  },
  fieldText: {
    fontWeight: "bold"
  },
  button: {
    marginVertical: 10
  }
});
