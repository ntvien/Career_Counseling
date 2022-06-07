import React from "react";
import { StyleSheet, View, Text, Dimensions } from "react-native";
import { colors } from "../../../theme/colors";
import * as Progress from "react-native-progress";
import paddings from "../../../theme/paddings";

export default ProgressBar = ({ progress, total, width }) => {
  const rate = total? progress / total : 0;
  return (
    <View style={styles.container}>
      <Text>Tiến độ hoàn thành</Text>
      <Text style={styles.progressText}>{progress + "/" + total}</Text>
      <Progress.Bar
        progress={rate}
        width={width}
        height={30}
        color={colors.brand.primary}
        style={styles.progress}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: colors.bg.card,
    height: 100,
    padding: paddings.card
  },
  progress: {
    alignSelf: "center",
  },
  progressText: {
    alignSelf: "flex-end",
    fontWeight: "bold"
  },
});
