import React from "react";
import { StyleSheet } from "react-native";
import { Button } from "react-native-elements";
import { colors } from "../../theme/colors";
import { fontSizes } from "../../theme/fonts";
import paddings from "../../theme/paddings";

export default RedButton = ({ style, disabledShadow, ...props }) => {
  return (
    <Button
      containerStyle={[
        styles.container,
        style,
        disabledShadow ? { shadowOpacity: 0, elevation: 0 } : {},
      ]}
      buttonStyle={styles.button}
      titleStyle={styles.title}
      {...props}
    />
  );
};

const styles = StyleSheet.create({
  container: {
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 5,
    },
    elevation: 11,
    shadowOpacity: 0.36,
    shadowRadius: 6.68,
  },
  button: {
    width: 300,   
    backgroundColor: colors.brand.primary,
    borderRadius: 10,
    padding: paddings.button
  },
  title: {
    color: colors.text.inverse,
    fontSize: fontSizes.button
  },
});
