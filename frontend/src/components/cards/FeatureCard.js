import React from "react";
import { StyleSheet } from "react-native";
import { Icon } from "react-native-elements";
import { colors } from "../../theme/colors";
import { fontSizes } from "../../theme/fonts";
import paddings from "../../theme/paddings";
import { View, Text, TouchableOpacity } from "react-native";
export default FeatureCard = ({ icon, title, onPress }) => {
  return (
    <TouchableOpacity style={styles.container} onPress={onPress}>
      <Icon
        type="font-awesome-5"
        name={icon}
        color={colors.brand.primary}
        size={30}
      />
      <Text style={styles.title}>{title}</Text>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    padding: paddings.card,
    backgroundColor: colors.bg.card,
    width: 140,
    height: 100,
    marginVertical: 5,
    alignItems: 'center',
    justifyContent: 'center'
  },
  title: {
    fontSize: fontSizes.body,
    marginTop: 5,
  },
});
