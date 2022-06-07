import React from "react";
import { StyleSheet, Text, TouchableOpacity } from "react-native";
import { colors } from "../../../theme/colors";
import { fontSizes } from "../../../theme/fonts";
import paddings from "../../../theme/paddings";

const MBTITypeButton = ({ name, type, onPress }) => {
  return (
    <TouchableOpacity style={styles.container} onPress={() => onPress(type)}>
      <Text style={styles.type}>{type}</Text>
      <Text style={styles.name}>{name}</Text>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    width: 120,
    height: 100,
    margin: 5,
    borderColor: colors.brand.primary,
    borderWidth: 2,
    borderRadius: 10,
    alignItems: "center",
    justifyContent: "center",
    padding: paddings.card
  },
  type: {
    color: colors.brand.primary,
    fontSize: fontSizes.h3,
  },
  name: {
    fontSize: fontSizes.body,
    textAlign: "center",
    fontWeight: "500"
  },
});

export default MBTITypeButton;
