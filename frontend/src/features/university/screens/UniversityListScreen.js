import React, { useState, useEffect, useContext, useRef } from "react";
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  Image,
  ActivityIndicator,
  RefreshControl,
  FlatList,
} from "react-native";
import { colors } from "../../../theme/colors";
import { SafeAreaView } from "react-native-safe-area-context";
import ExpandingDotPager from "../../../components/pager/ExpandingDotPager";
import { SearchBar } from "react-native-elements";
import { useNavigation } from "@react-navigation/native";
import routes from "../../../utils/enum/routes";
import universityApi from "../../../api/http/resource/universityApi";
import AdvertisementCard from "../../../components/cards/AdvertisementCard";
import AsyncStorage from "@react-native-async-storage/async-storage";
import storageKeys from "../../../utils/enum/storageKeys";
import ResourceCard from "../../../components/cards/ResourceCard";
import typeOfResource from "../../../utils/enum/resource/TypeOfResource";
import NetInfo from "@react-native-community/netinfo";
import paddings from "../../../theme/paddings";
const UniversityListScreen = () => {
  const [searchValue, setSearchValue] = useState("");
  const updateSearch = (search) => {
    setSearchValue(search);
  };
  const [outstandingUniversities, setOutstandingUniversities] = useState({
    universities: [],
    page: 0,
    size: 5,
    hasNext: true,
  });
  const [universitiesStates, setUniversitiesStates] = useState({
    universities: [],
    page: 0,
    size: 5,
    hasNext: true,
  });
  const [loading, setLoading] = useState(false);
  const [advertisingImages, setAdvertisingImages] = useState([]);
  const [refreshing, setRefreshing] = React.useState(false);
  useEffect(async () => {
    const unsubscribe = NetInfo.addEventListener((state) => {
      if (state.isConnected) {
        Promise.all(fetchUniversity(), fetchOutstandingUniversities());
      }
    });
    return unsubscribe;
  }, []);
  const onRefresh = async () => {
    setRefreshing(true);
    setOutstandingUniversities({
      universities: [],
      page: 0,
      size: 5,
      hasNext: true,
    });
    setUniversitiesStates({
      universities: [],
      page: 0,
      size: 5,
      hasNext: true,
    });
    try {
      await Promise.all(fetchUniversity(), fetchOutstandingUniversities());
      setRefreshing(false);
    } catch (e) {
      console.log(e);
    }
  };
  const fetchOutstandingUniversities = async () => {
    try {
      const { universities, page, size, hasNext } = outstandingUniversities;
      const res = await universityApi.getUniversityList({
        page: page,
        size: size,
        sortBy: "viewNumber",
        keyword: "",
      });
      setOutstandingUniversities({
        universities: res.items,
        page: page,
        size: size,
        hasNext: res.hasNext,
      });
    } catch (e) {
      console.log("error ", e);
    }
  };

  const fetchUniversity = async () => {
    try {
      const { universities, page, size, hasNext } = universitiesStates;
      if (hasNext && !loading) {
        setLoading(true);
        const res = await universityApi.getUniversityList({
          page: page,
          size: size,
          sortBy: "",
          keyword: "",
        });
        setUniversitiesStates({
          universities: [...universities, ...res.items],
          page: res.nextPage,
          size: res.pageSize,
          hasNext: res.hasNext,
        });
      }
      setLoading(false);
    } catch (e) {
      console.log("fetch university err: ", e);
    }
  };
  const isCloseToBottom = ({
    layoutMeasurement,
    contentOffset,
    contentSize,
  }) => {
    const paddingToBottom = 10;
    return (
      layoutMeasurement.height + contentOffset.y >=
      contentSize.height - paddingToBottom
    );
  };
  const renderUniversity = (university) => (
    <AdvertisementCard
      resource={university.item}
      typeOfResource={typeOfResource.UNIVERSITY}
    />
  );
  return (
    <SafeAreaView style={styles.container}>
      <ScrollView
        onScroll={async ({ nativeEvent }) => {
          if (isCloseToBottom(nativeEvent) && !loading) {
            await fetchUniversity();
          }
        }}
        scrollEventThrottle={1}
        refreshControl={
          <RefreshControl refreshing={refreshing} onRefresh={onRefresh} />
        }
        style={{padding: paddings.container}}
      >
        {/* <SearchBar
                    placeholder="Nhập tên trường ..."
                    value={searchValue}
                    onChangeText={updateSearch}
                    containerStyle={styles.ContainerSearchBar}
                    inputContainerStyle={styles.inputContainerStyle}
                /> */}

        <View style={{ marginTop: 10 }}>
          <Text style={{ fontWeight: "bold", color: "red", fontSize: 19 }}>
            Top 5 trường xem nhiều nhất
          </Text>
        </View>
        <FlatList
          horizontal
          data={outstandingUniversities.universities}
          renderItem={renderUniversity}
          keyExtractor={(item) => item._id}
        />

        <View style={{ marginTop: 2, marginBottom: 10 }}>
          <Text style={{ fontWeight: "bold", color: "red", fontSize: 19 }}>
            Dách sách các trường
          </Text>
        </View>

        {universitiesStates.universities.map((university, index) => (
          <View style={{  width: "100%" }} key={index}>
            <ResourceCard
              resource={university}
              typeOfResource={typeOfResource.UNIVERSITY}
            />
          </View>
        ))}
      </ScrollView>

      {loading && <ActivityIndicator size="large"  />}
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
  AdvertisementUniversity: {
    marginTop: 8,
    marginBottom: 25,
    width: "100%",
    height: 190,
  },
  loading: {
    position: "absolute",
    top: "50%",
    left: "50%",
  },
});

export default UniversityListScreen;
