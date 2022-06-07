import React, {
  useState,
  useEffect,
  useContext,
  useRef,
  useLayoutEffect,
} from "react";
import { View, Text, ScrollView, Image, Share, FlatList } from "react-native";
import { StyleSheet } from "react-native";
import { Button, Icon } from "react-native-elements";
import { SafeAreaView } from "react-native-safe-area-context";
import majorApi from "../../../api/http/resource/majorApi";
import CommunityResource from "../../../components/community/CommunityResource";
import { colors } from "../../../theme/colors";
import TypeOfResource from "../../../utils/enum/resource/TypeOfResource";
import routes from "../../../utils/enum/routes";
import AdvertisementCard from "../../../components/cards/AdvertisementCard";
import { fonts, fontSizes } from "../../../theme/fonts";
import { AuthContext } from "../../../navigation";
const MajorDetailScreen = ({ route, navigation }) => {
  const { majorId } = route.params;
  const [major, setMajor] = useState({});
  const {
    state: { profile, userType },
  } = useContext(AuthContext);
  useEffect(async () => {
    await fetchMajor();
  }, []);
  const fetchMajor = async () => {
    try {
      navigation.navigate(routes.LOADING_MODAL);
      try {
        const res = await majorApi.getMajor(majorId);
        navigation.setOptions({
          title: major ? res.name : "",
          headerTintColor: colors.text.inverse,
          headerTitleStyle: {
            fontFamily: fonts.body,
            fontSize: fontSizes.h4,
          },
        });
        setMajor(res);
      } catch (e) {
        console.log(e);
      }
      navigation.pop();
    } catch (e) {
      console.log(":error fetch major,", e);
    }
  };

  const onPressShare = async () => {
    if (profile) {
      try {
        const result = await Share.share({
          message: `${major.name}`,
        });
        if (result.action === Share.sharedAction) {
          if (result.activityType) {
            // shared with activity type of result.activityType
          } else {
            // shared
          }
        } else if (result.action === Share.dismissedAction) {
          // dismissed
        }
        communityApi.share({ userId: profile._id, resourceId: resourceId });
        setCountLikes((old) => old + 1);
      } catch (error) {
        alert(error.message);
      }
    } else navigation.navigate(routes.AUTH_MODAL);
  };
  const onPressMoreUniversity = () => {
    navigation.navigate(routes.UNIVERSITY);
  };
  const renderUniversity = (university) => (
    <AdvertisementCard
      resource={university.item}
      typeOfResource={TypeOfResource.UNIVERSITY}
    />
  );
  return (
    <SafeAreaView style={{flex: 1}}>
      {major != {} && (
        <ScrollView style={styles.container}>
          <View
            style={{
              flex: 1,
              justifyContent: "center",
              alignItems: "center",
              marginBottom: 7,
            }}
          >
            <Text
              style={{
                textAlignVertical: "center",
                textAlign: "center",
                fontWeight: "bold",
                color: "red",
                fontSize: 21,
              }}
            >
              {major.name}
            </Text>
          </View>

          <View style={{ marginHorizontal: 5 }}>
            <Image source={{ uri: major.image }} style={styles.image}></Image>
          </View>

          <View style={{ marginTop: 8, marginBottom: 3 }}>
            <Text style={{ fontWeight: "bold", color: "red", fontSize: 19 }}>
              Sơ lược về ngành
            </Text>
          </View>

          <Text style={styles.description}>{major.description}</Text>

          <View style={{ marginTop: 8, marginBottom: 3 }}>
            <View style={styles.bottomContainer}>
              <Text style={styles.universitiesTitle}>Các trường liên quan</Text>
              {/* <Button
                title="Xem thêm"
                type="outline"
                buttonStyle={styles.moreButton}
                titleStyle={styles.moreTitle}
                containerStyle={styles.moreContainer}
                onPress={onPressMoreUniversity}
              /> */}
            </View>
          </View>

          <View style={{ marginTop: 8, marginBottom: 12 }}>
            <FlatList
              horizontal
              data={major.universities}
              renderItem={renderUniversity}
              keyExtractor={(item) => item._id}
            />
          </View>

          <View style={{ marginTop: 8 }}>
            <CommunityResource
              resource={major}
              typeOfResource={TypeOfResource.MAJOR}
              onShare={onPressShare}
            />
          </View>
        </ScrollView>
      )}
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 11,
  },
  containerNameMajor: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    textAlign: "center",
  },
  nameMajor: {
    fontWeight: "bold",
    marginTop: 0,
    width: 200,
  },
  ContainerSearchBar: {
    flexDirection: "row",
    backgroundColor: colors.bg.input,
    borderTopWidth: 0,
    borderBottomWidth: 0,
    borderRadius: 30,
    justifyContent: "center",
    alignItems: "flex-end",
    height: 50,
  },
  inputContainerStyle: {
    backgroundColor: colors.bg.input,
    borderRadius: 30,
    height: 27,
  },
  image: {
    height: 120,
  },
  inputSearch: {
    flex: 1,
    marginHorizontal: 5,
  },
  pager: {
    width: "100%",
    height: 200,
    margin: 10,
  },
  bottomContainer: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between",
  },
  universitiesTitle: {
    fontSize: 19,
    color: colors.brand.primary,
    fontWeight: "bold",
  },
  moreButton: {
    borderColor: colors.brand.primary,
    padding: 2,
    borderRadius: 20,
  },
  moreContainer: {
    width: 80,
  },
  moreTitle: {
    color: colors.text.primary,
    fontSize: 14,
  },
  AdvertisementUniversity: {
    marginTop: 5,
    marginBottom: 5,
    width: "100%",
    height: 190,
  },
  description: {
  }
});

export default MajorDetailScreen;
