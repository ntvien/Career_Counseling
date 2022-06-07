import { DefaultTransition } from "@react-navigation/stack/lib/typescript/src/TransitionConfigs/TransitionPresets";
import { StyleSheet } from "react-native";
import { colors } from "../../theme/colors";
import { fontSizes } from "../../theme/fonts";
import paddings from "../../theme/paddings";

export const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: paddings.container,
    flexGrow: 1,
  },
  pager: {
    width: "100%",
    height: 170,
    margin: 10,
  },
  text: {
    alignSelf: "center",
    color: colors.brand.primary,
    fontSize: fontSizes.body,
  },
  features: {
    flexDirection: "row",
    flexWrap: "wrap",
    justifyContent: "space-around",
    marginTop: 8,
  },
  bottomContainer: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between",
  },
  moreButton: {
    borderColor: colors.brand.primary,
    padding: 2,
    borderRadius: 20,
  },
  moreContainer: {
    width: 80,
  },
  moreTitle: {
    color: colors.text.primary,
    fontSize: 14,
  },
  universitiesTitle: {
    fontSize: fontSizes.h4,
    color: colors.brand.primary,
    fontWeight: "bold",
  },
  AdvertisementUniversity: {
    marginTop: 8,
    width: "100%",
    height: 300,
  },
});
