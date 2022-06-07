import React from "react";
import { StyleSheet } from "react-native";
import { fontSizes } from "../../../theme/fonts";
import paddings from "../../../theme/paddings";

export default styles = StyleSheet.create({
  container: {
    padding: paddings.container,
  },
  title: {
    fontWeight: "bold",
    fontSize: fontSizes.h4,
    marginBottom: 5
  },
  MBTIContainer: {
    alignItems: "center",
    flexWrap: "wrap",
    flexDirection: "row",
    justifyContent: "space-around",
  },
  button: {
    marginBottom: 20,
    marginTop: 5,
    alignSelf: "center"
  }
})