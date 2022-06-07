import React, { useState, useEffect, useContext, useRef } from "react";
import {
  View,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Image,
  ActivityIndicator,
  Text,
  FlatList,
} from "react-native";
import { colors } from "../../../theme/colors";
import { SafeAreaView } from "react-native-safe-area-context";
import ExpandingDotPager from "../../../components/pager/ExpandingDotPager";
import ResourceCard from "../../../components/cards/ResourceCard";
import { SearchBar } from "react-native-elements";
import routes from "../../../utils/enum/routes";
import AsyncStorage from "@react-native-async-storage/async-storage";
import storageKeys from "../../../utils/enum/storageKeys";
import majorApi from "../../../api/http/resource/majorApi";
import TypeOfResource from "../../../utils/enum/resource/TypeOfResource";
import AdvertisementCard from "../../../components/cards/AdvertisementCard";
import paddings from "../../../theme/paddings";

const MajorListScreen = () => {
  const [searchValue, setSearchValue] = useState("");
  const [outstandingMajor, setOutstandingMajor] = useState({
    majors: [],
    page: 0,
    size: 5,
    hasNext: true,
  });
  const [majorsStates, setMajorsStates] = useState({
    majors: [],
    page: 0,
    size: 5,
    has_next: true,
  });
  const [loading, setLoading] = useState(false);
  const updateSearch = (search) => {
    setSearchValue(search);
  };
  const [advertisingImages, setAdvertisingImages] = useState([]);

  useEffect(async () => {
    Promise.all(fetchMajor(), fetchOutstandingMajor());
  }, []);
  const fetchOutstandingMajor = async () => {
    try {
      if (outstandingMajor.majors.length < 5) {
        const { majors, page, size, hasNext } = outstandingMajor;
        const res = await majorApi.getMajorList({
          page: page,
          size: size,
          sortBy: "viewNumber",
          keyword: "",
        });
        setOutstandingMajor({
          majors: res.items,
          page: res.nextPage,
          size: size,
          hasNext: res.hasNext,
        });
      }
    } catch (e) {
      console.log("error ", e);
    }
  };
  const fetchMajor = async () => {
    try {
      const { majors, page, size, has_next } = majorsStates;
      if (has_next && !loading) {
        setLoading(true);
        const res = await majorApi.getMajorList({
          page: page,
          size: size,
          sortBy: "",
          keyword: "",
        });
        if (res) {
          setMajorsStates({
            majors: [...majors, ...res.items],
            page: res.nextPage,
            size: size,
            has_next: res.hasNext,
          });
        }
      }
      setLoading(false);
    } catch (e) {
      console.log("fetch majors err: ", e);
    }
  };
  const isCloseToBottom = ({
    layoutMeasurement,
    contentOffset,
    contentSize,
  }) => {
    const paddingToBottom = 20;
    return (
      layoutMeasurement.height + contentOffset.y >=
      contentSize.height - paddingToBottom
    );
  };
  const renderMajor = (major) => (
    <AdvertisementCard
      resource={major.item}
      typeOfResource={TypeOfResource.MAJOR}
    />
  );
  return (
    <SafeAreaView style={styles.container}>
      <ScrollView
        onScroll={async ({ nativeEvent }) => {
          if (isCloseToBottom(nativeEvent) && !loading) {
            await fetchMajor();
          }
        }}
        scrollEventThrottle={1}
        style={{ flex: 1, padding: paddings.container }}
      >
        {/* <SearchBar
                    placeholder="Nhập tên ngành ..."
                    value={searchValue}
                    onChangeText={updateSearch}
                    containerStyle={styles.ContainerSearchBar}
                    inputContainerStyle={styles.inputContainerStyle}
                /> */}
        <View style={{ marginTop: 10 }}>
          <Text style={{ fontWeight: "bold", color: "red", fontSize: 19 }}>
            Top 5 ngành xem nhiều nhất
          </Text>
        </View>
        <View style={{ marginTop: 8, marginBottom: 12 }}>
          <FlatList
            horizontal
            data={outstandingMajor.majors}
            renderItem={renderMajor}
            keyExtractor={(item) => item._id}
          />
        </View>
        <View style={{ marginTop: 2, marginBottom: 3 }}>
          <Text style={{ fontWeight: "bold", color: "red", fontSize: 19 }}>
            Dách sách các ngành
          </Text>
        </View>
        {majorsStates.majors.map((major, index) => (
          <View style={{width: "100%" }} key={index}>
            <ResourceCard
              resource={major}
              typeOfResource={TypeOfResource.MAJOR}
            />
          </View>
        ))}
      </ScrollView>
      {loading && <ActivityIndicator size="large" />}
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
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
  pager: {
    width: "100%",
    height: 200,
    margin: 10,
  },
  AdvertisementUniversity: {
    marginTop: 8,
    marginBottom: 25,
    width: "100%",
    height: 180,
  },
  loading: {
    position: "absolute",
    top: "50%",
    left: "50%",
  },
});

export default MajorListScreen;
