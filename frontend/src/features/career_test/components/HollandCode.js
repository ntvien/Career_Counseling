import React from "react";
import { StyleSheet, View, Text, TouchableOpacity } from "react-native";
import { colors } from "../../../theme/colors";
import { fontSizes } from "../../../theme/fonts";
import paddings from "../../../theme/paddings";

export default HollandCode = ({ code, name, onPress }) => {
  return (
    <TouchableOpacity onPress={onPress}>
      <View style={styles.container}>
        <View style={styles.codeContainer}>
          <Text style={styles.code}>{code}</Text>
        </View>
        <Text style={styles.name}>{name}</Text>
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    flexDirection: "row",
    alignItems: "center",
    backgroundColor: colors.bg.card,
    borderRadius: 10,
    padding: paddings.card,
    margin: 10,
  },
  codeContainer: {
    backgroundColor: colors.brand.primary,
    alignItems: "center",
    justifyContent: "center",
    borderRadius: 30,
    width: 50,
    height: 50,
    marginRight: 20,
  },
  code: {
    fontSize: 20,
    color: colors.text.inverse,
    fontWeight: "bold",
  },
  name: {
    fontWeight: "bold",
    fontSize: fontSizes.body,
    flex: 1
  },
});
