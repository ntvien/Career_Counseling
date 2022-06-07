import React from "react";
import { StyleSheet, Modal, View, Text, Pressable } from "react-native";
import { fontSizes } from "../../theme/fonts";
import padding from "../../theme/paddings";
import { colors } from "../../theme/colors";
import routes from "../../utils/enum/routes";

const HollandResultModal = ({ navigation, route }) => {
  return (
    <View style={styles.container}>
      <View style={styles.modalView}>
        <View style={styles.titleContainer}>
          <Text style={styles.titleText}>Kết quả</Text>
        </View>

        <View style={styles.bottomContainer}>
          <Text style={styles.text}>{route.params.result}</Text>
          <Pressable
            style={styles.buttonUserType}
            onPress={() => {
              navigation.pop();
              navigation.pop();
            }}
          >
            <Text style={styles.buttonText}>Quay lại</Text>
          </Pressable>
        </View>
      </View>
    </View>
  );
}

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
    width: 300,
    height:200,
  },
  buttonUserType: {
    borderRadius: 10,
    padding: paddings.button,
    elevation: 2,
    backgroundColor: colors.button.lightgrey,
    marginHorizontal: 10,
    width: 130,
  },
  buttonText: {
    textAlign: "center",
    fontSize: fontSizes.button,
  },
  modalText: {
    textAlign: "center",
    fontSize: fontSizes.body,
    marginTop: 10,
  },
  bottomContainer: {
    justifyContent: "space-around",
    padding: 10,
    alignItems: "center",
  },
  titleContainer: {
    backgroundColor: colors.brand.primary,
    height: 50,
    alignItems: "center",
    borderTopLeftRadius: 20,
    borderTopRightRadius: 20,
    alignSelf: "stretch",
    justifyContent: "center",
  },
  titleText: {
    color: colors.text.inverse,
    fontSize: fontSizes.h4,
    fontWeight: "bold",
  },
  text: {
    fontSize: 20,
    fontWeight: "bold",
    marginBottom: 20,
  },
});

export default HollandResultModal;
