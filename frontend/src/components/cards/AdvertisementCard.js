import React from "react";
import {
  StyleSheet,
  View,
  Image,
  Text,
  TouchableOpacity,
  Dimensions,
} from "react-native";
import { Icon, makeStyles } from "react-native-elements";
import { colors } from "../../theme/colors";
import { fontSizes } from "../../theme/fonts";
import { useNavigation } from "@react-navigation/native";
import TypeOfResource from "../../utils/enum/resource/TypeOfResource";
import routes from "../../utils/enum/routes";
import paddings from "../../theme/paddings";

export default AdvertisementCard = ({ resource, typeOfResource }) => {
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
    <TouchableOpacity onPress={onPressDetail} style={{ flex: 1 }}>
      <View style={styles.container}>
        <Image source={{ uri: resource.image }} style={styles.image}></Image>
        <View style={styles.bottomContainer}>
          {typeOfResource == TypeOfResource.UNIVERSITY && (
            <Image source={{ uri: resource.logo }} style={styles.logo}></Image>
          )}
          <View style={styles.interactionContainer}>
            <Text style={styles.majorText}>{resource.name}</Text>
            <View style={styles.iconContainer}>
              <View style={styles.iconInerContainer}>
                <Icon type="font-awesome-5" name="thumbs-up" size={22} />
                <Text style={{ fontSize: 16, marginHorizontal: 10 }}>
                  {resource.starNumber}
                </Text>
              </View>
              <View style={styles.iconInerContainer}>
                <Icon type="font-awesome-5" name="eye" size={22} />
                <Text style={{ fontSize: 16 }}> {resource.viewNumber}</Text>
              </View>
            </View>
          </View>
        </View>
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    marginRight: 10,
    flex: 1,
    borderRadius: 15,
    marginBottom: 20,
    marginTop: 10,
    width: 310,
  },
  icon: {
    width: 50,
    height: 50,
  },
  interactionContainer: {
  },
  image: {
    height: 130,
    borderTopRightRadius: 15,
    borderTopLeftRadius: 15,
  },
  logo: {
    height: 50,
    width: 50,
    borderTopLeftRadius: 15,
    borderTopRightRadius: 15,
    marginBottom: 10,
  },
  bottomContainer: {
    padding: paddings.card,
    backgroundColor: colors.bg.card,
    height: 100,
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
  majorText: {
    fontSize: fontSizes.title,
    fontWeight: "bold",
    width: 230,
    textAlign: "center",
    marginBottom: 5
  },
  iconContainer: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-evenly",
  },
  iconInerContainer: {
    flexDirection: "row",
  },
 
});
