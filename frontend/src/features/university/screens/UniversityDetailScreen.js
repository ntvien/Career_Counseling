import React, {
  useState,
  useEffect,
  useContext,
  useRef,
  useLayoutEffect,
} from "react";
import {
  Text,
  ScrollView,
  Image,
  StyleSheet,
  View,
  TouchableOpacity,
  Share,
} from "react-native";
import { Icon } from "react-native-elements";
import { SafeAreaView } from "react-native-safe-area-context";
import chatApi from "../../../api/http/Chat/chatApi";
import universityApi from "../../../api/http/resource/universityApi";
import CommunityResource from "../../../components/community/CommunityResource";
import { colors } from "../../../theme/colors";
import { fonts, fontSizes } from "../../../theme/fonts";
import TypeOfResource from "../../../utils/enum/resource/TypeOfResource";
import routes from "../../../utils/enum/routes";
import { AuthContext } from "../../../navigation";
import paddings from "../../../theme/paddings";

const UniversityDetailScreen = ({ route, navigation }) => {
  const { universityId } = route.params;
  const [university, setUniversity] = useState({});
  const {
    state: { profile, userType },
  } = useContext(AuthContext);
  useEffect(async () => {
    await fetchUniversity();
  }, []);
  useLayoutEffect(() => {
    navigation.setOptions({
      title: university ? university.name : "",
      headerRight: () => (
        <View style={{ marginRight: 12 }}>
          <Icon
            type="font-awesome-5"
            onPress={onPressChat}
            name="comment-dots"
            color="#ffffff"
            size={30}
            style={styles.icon}
          />
        </View>
      ),
      headerTintColor: colors.text.inverse,
      headerTitleStyle: {
        fontFamily: fonts.body,
        fontSize: fontSizes.h4,
      },
    });
  });
  const onPressShare = async () => {
    if (profile) {
      try {
        const result = await Share.share({
          message: `${university.contact.website}`,
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
        // communityApi.share({ userId: profile._id, resourceId: resourceId });
        // setCountLikes((old) => old + 1);
      } catch (error) {
        alert(error.message);
      }
    } else navigation.navigate(routes.AUTH_MODAL);
  };
  const onPressChat = async () => {
    if (!profile) {
      navigation.navigate(routes.AUTH_MODAL);
    } else {
      const group = await chatApi.getGroups({
        universityid: universityId,
        userid: profile._id,
      });
      navigation.navigate(routes.CHAT_ROOM, {
        user: { ...profile, role: userType },
        group: group,
      });
    }
  };
  const fetchUniversity = async () => {
    try {
      navigation.navigate(routes.LOADING_MODAL);
      const res = await universityApi.getUniversity(universityId);
      setUniversity(res);
      navigation.navigate(routes.UNIVERSITY_DETAIL, {
        universityId: universityId,
      });
    } catch (e) {
      console.log(":error fetch university,", e);
    }
  };
  return (
    <SafeAreaView style={{flex: 1}}>
      {university && (
        <ScrollView style={styles.container}>
          <View
            style={{
              flex: 1,
              justifyContent: "center",
              alignItems: "center",
              marginBottom: 5,
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
              {university.name}
            </Text>
          </View>

          <View style={{ marginHorizontal: 5 }}>
            <Image
              source={{ uri: university.image }}
              style={styles.image}
            ></Image>
          </View>

          <View style={{ marginTop: 8, marginBottom: 3 }}>
            <Text style={{ fontWeight: "bold", color: "red", fontSize: 19 }}>
              Sơ lược về trường
            </Text>
          </View>

          <Text>{university.description}</Text>

          <View style={{ marginTop: 8, marginBottom: 3 }}>
            <Text style={{ fontWeight: "bold", color: "red", fontSize: 19 }}>
              Thông tin liên hệ
            </Text>
          </View>

          <Text>
            Địa chỉ: {university.contact ? university.contact.address : ""}
          </Text>

          <Text>SĐT: {university.contact ? university.contact.phone : ""}</Text>

          <Text>
            Website: {university.contact ? university.contact.website : ""}
          </Text>

          <Text>
            Email: {university.contact ? university.contact.email : ""}
          </Text>

          <View style={{ marginTop: 7 }}>
            <CommunityResource
              resource={university}
              typeOfResource={TypeOfResource.UNIVERSITY}
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
    padding: paddings.container
  },
  image: {
    height: 120,
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
  inputSearch: {
    flex: 1,
    marginHorizontal: 5,
  },
  icon: {
    marginRight: 10,
  },
});

export default UniversityDetailScreen;
