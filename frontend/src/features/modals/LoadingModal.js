import React from "react";
import { View, StyleSheet, ActivityIndicator, Text } from "react-native";
import { fontSizes } from "../../theme/fonts";
export default LoadingModal = () => {
  return (
    <View style={styles.container}>
      <View style={styles.modalView}>
        <Text style={styles.text}>Đang xử lý...</Text>
        <ActivityIndicator animating={true} size="large" />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#00000025",
    justifyContent: "center",
    alignItems: "center",
  },
  modalView: {
    backgroundColor: "white",
    borderRadius: 20,
    alignItems: "center",
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5,
    width: 200,
    height: 120,
    justifyContent: "center",
    alignItems: "center",
  },
  text: {
    fontSize: fontSizes.body,
    marginBottom: 5
  }
});
