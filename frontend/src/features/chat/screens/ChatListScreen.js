import React, { useState, useEffect, useContext, useRef } from "react";
import { View, Text, Button, StyleSheet, FlatList, TextInput, ActivityIndicator, RefreshControl } from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";

import ChatListItem from "../components/ChatListItem";
import { SearchBar } from "react-native-elements";
import { colors } from "../../../theme/colors";
import EncryptedStorage from 'react-native-encrypted-storage';
import storageKeys from "../../../utils/enum/storageKeys";
import { getUserInfoInToken } from "../../../utils/jwt";
import Roles from "../../../utils/enum/user/Roles";
import userApi from "../../../api/http/user/userApi";
import chatApi from "../../../api/http/Chat/chatApi";
import { AuthContext } from '../../../navigation';
import paddings from "../../../theme/paddings";
import routes from "../../../utils/enum/routes";
import NetInfo from "@react-native-community/netinfo";
const ChatListScreen = ({ navigation }) => {
    const [searchValue, setSearchValue] = useState('');
    const { state: { profile, userType } } = useContext(AuthContext);
    const [statesListGroup, setStatesListGroup] = useState({ groups: [], offset: 0, limit: 10 });
    const [loading, setLoading] = useState(true);
    const [refreshing, setRefreshing] = React.useState(false);
    useEffect(async () => {
        const unsubscribe = NetInfo.addEventListener(async (state) => {
            if (profile) {
                if (state.isConnected) {
                    navigation.navigate(routes.LOADING_MODAL);
                    await fetchGroups(profile._id);
                    navigation.navigate(routes.CHAT);
                }
                return unsubscribe;
            }
        })
    }, []);
    const updateSearch = (search) => {
        setSearchValue(search);
    }
    const fetchGroups = async () => {
        try {
            setLoading(true);
            const { groups, offset, limit } = statesListGroup;
            const newGroups = await chatApi.getGroups({ offset: offset, limit: limit, userid: profile._id });
            setStatesListGroup({
                groups: [...groups, ...newGroups],
                offset: offset + newGroups.length,
                limit: limit
            });
            setLoading(false);
        } catch (err) {
            console.log(err);
        }
    }
    const onRefresh = async () => {
        setRefreshing(true);
        setStatesListGroup({
            groups: [],
            offset: 0,
            limit: 10,
        });
        await fetchGroups();
        setRefreshing(false);
    }
    return (
        <SafeAreaView style={styles.container}>
            {/* <SearchBar
                placeholder="Tìm kiếm ..."
                value={searchValue}
                onChangeText={updateSearch}
                containerStyle={styles.ContainerSearchBar}
                inputContainerStyle={styles.inputContainerStyle}
            /> */}
            <FlatList
                data={statesListGroup.groups}
                onEndReached={fetchGroups}
                refreshControl={<RefreshControl
                    refreshing={refreshing}
                    onRefresh={onRefresh}
                />}
                onEndReachedThreshold={1}
                extraData={statesListGroup}
                renderItem={({ item }) => <ChatListItem
                    user={({ ...profile, role: userType })}
                    group={item} />}
                keyExtractor={item => item._id}
            />
            {loading && <ActivityIndicator size="large" />}
        </SafeAreaView>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
        // padding: 11,
        padding: paddings.container,

    },
    ContainerSearchBar: {
        flexDirection: 'row',
        backgroundColor: colors.bg.input,
        borderTopWidth: 0,
        borderBottomWidth: 0,
        borderRadius: 30,
        justifyContent: 'center',
        alignItems: 'flex-end',
        height: 50
    },
    inputContainerStyle: {
        backgroundColor: colors.bg.input,
        borderRadius: 30,
        height: 27,
    },
    inputSearch: {
        flex: 1,
        marginHorizontal: 5
    }
})

export default ChatListScreen;
