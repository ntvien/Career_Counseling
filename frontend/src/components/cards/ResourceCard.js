import React from "react";
import { StyleSheet, View, Image, Text, TouchableOpacity } from "react-native";
import { Icon } from "react-native-elements";
import { colors } from "../../theme/colors";
import { fontSizes } from "../../theme/fonts";
import { useNavigation } from "@react-navigation/native";
import TypeOfResource from "../../utils/enum/resource/TypeOfResource";
import routes from "../../utils/enum/routes";
export default ResourceCard = ({ resource, typeOfResource }) => {
  const navigation = useNavigation();
  const onPressDetail = () => {
    if (typeOfResource == TypeOfResource.MAJOR)
      navigation.navigate(routes.MAJOR_DETAIL, { majorId: resource._id });
    else
      navigation.navigate(routes.UNIVERSITY_DETAIL, {
        universityId: resource._id,
      });
  };
  return (
    <TouchableOpacity onPress={onPressDetail}>
      <View style={styles.container}>
        <Image source={{ uri: resource.image }} style={styles.image}></Image>
        <View style={styles.bottomContainer}>
          {typeOfResource == TypeOfResource.UNIVERSITY && (
            <Image source={{ uri: resource.logo }} style={styles.logo}></Image>
          )}
          <Text style={styles.resourceText} lineBreakMode>
            {resource.name}
          </Text>
          <View style={styles.interactionContainer}>
            <View style={styles.iconContainer}>
              <Icon type="font-awesome-5" name="thumbs-up" size={20} />
              <Text style={{ fontSize: 18 }}> {resource.starNumber}</Text>
            </View>
            <View style={styles.iconContainer}>
              <Icon type="font-awesome-5" name="eye" size={18} />
              <Text style={{ fontSize: 16 }}> {resource.viewNumber}</Text>
            </View>
          </View>
        </View>
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    height: 160,
    borderRadius: 15,
    flexDirection: "column",
    marginBottom: 10
  },
  image: {
    height: 90,
    borderTopLeftRadius: 15,
    borderTopRightRadius: 15,
  },
  bottomContainer: {
    backgroundColor: colors.bg.card,
    flex: 1,
    flexDirection: "row",
    borderBottomLeftRadius: 15,
    borderBottomRightRadius: 15,
    alignItems: "center",
    justifyContent: "space-around",
  },
  icon: {
    width: 50,
    height: 50,
  },
  resourceText: {
    fontSize: fontSizes.body,
    width: 200,
    fontWeight: "bold",
  },
  iconContainer: {
    flexDirection: "row",
  },
  interactionContainer: {
    height: 50,
    marginRight: 1,
    justifyContent: "space-between",
  },
  logo: {
    height: 30,
    width: 30,
    borderTopLeftRadius: 5,
    borderTopRightRadius: 5,
    borderBottomLeftRadius: 5,
    borderBottomRightRadius: 5,
    marginTop: 12,
    marginLeft: 8,
    marginBottom: 10,
  },
});
