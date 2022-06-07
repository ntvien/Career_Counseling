import React, { useState, useEffect, useRef, useLayoutEffect } from "react";
import { View, Text, FlatList, StyleSheet, ActivityIndicator } from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";
import { colors } from "../../../theme/colors";
import ChatMessage from "../components/ChatMessage";
import InputBox from "../../../components/inputBox/InputBox";
import ChatSocket from "../../../api/socket/ChatSocket";
import TypingAction from "../../../utils/enum/socket/TypingAction";
import Roles from "../../../utils/enum/user/Roles";
import { TypingAnimation } from 'react-native-typing-animation';
import ActiveStatus from "../../../utils/enum/socket/ActiveStatus";
import { fonts, fontSizes } from '../../../theme/fonts';
const ChatRoomScreen = ({ route, navigation }) => {
    const { user, group } = route.params;
    const flatListRef = useRef(null);
    const myStateTyping = useRef(false);
    const [typing, setTyping] = useState(null);
    const [message, setMessage] = useState('');
    const [statesMessages, setStatesMessage] = useState({ messages: [], offset: 0, limit: 10 });
    const [activeStatus, setActiveStatus] = useState(ActiveStatus.OFF);
    const [chatSocket, setChatSocket] = useState(null);
    const [loading, setLoading] = useState(true);
    useEffect(async () => {
        await fetchMessages();
        const socket = await ChatSocket.getInstance()
        setChatSocket(socket);

    }, []);
    useEffect(async () => {
        const socket = await ChatSocket.getInstance()
        setChatSocket(socket);
    }, []);
    useEffect(() => {
        if (chatSocket != null) {
            chatSocket.onMessage(listenerMessage);
            chatSocket.onTyping(listenerTyping)
            chatSocket.onActiveStatus(listenerActiveStatus);

            return () => {
                chatSocket.removeListenerEventMessage(listenerMessage);
                chatSocket.removeListenerEventTyping(listenerTyping);
                chatSocket.removeListenerEventActiveStatus(listenerActiveStatus);
            }
        }
    }, [chatSocket])
    useEffect(() => {
        setLoading(false);
    }, [statesMessages])
    useEffect(() => {
        if (chatSocket != null) {
            if (!myStateTyping.current && message) {
                myStateTyping.current = true;
                chatSocket.emitTyping(user._id, group._id, TypingAction.TYPING, user.role === Roles.COUNSELOR)
            }
            else if (myStateTyping.current && !message) {
                myStateTyping.current = false;
                chatSocket.emitTyping(user._id, group._id, TypingAction.STOP_TYPING, user.role === Roles.COUNSELOR)
            }
        }
    }, [message])
    useLayoutEffect(() => {
        navigation.setOptions({
            title: user.role == Roles.STUDENT ? group.university.fullName : group.student.fullName,
            headerTintColor: colors.text.inverse,
            headerTitleStyle: {
                fontFamily: fonts.body,
                fontSize: fontSizes.h4,

            },
            headerRight: () => { activeStatus == ActiveStatus.ON && <View style={styles.activity} /> },

        });
    }, [activeStatus])
    const fetchMessages = async () => {
        try {
            setLoading(true);
            const { messages, offset, limit } = statesMessages;
            const newMessages = await chatApi.getMessagesByGroupId(group._id, { offset: offset, limit: limit });
            if (newMessages.length > 0) {
                setStatesMessage({
                    messages: [...newMessages.reverse(), ...messages.reverse()].reverse(),
                    offset: offset + newMessages.length,
                    limit: limit
                });
            } else {
                setLoading(false);
            }
        } catch (e) {
            console.log("fetch messages err: ", e);
        }
    }
    const listenerActiveStatus = (data) => {
        if ((user.role == Roles.STUDENT && data.userId == group.university.id) ||
            ((user.role == Roles.COUNSELOR && data.userId == group.student.id)))
            if (data.status == ActiveStatus.ON && activeStatus == ActiveStatus.OFF)
                setActiveStatus(ActiveStatus.ON);
            else if (data.status == ActiveStatus.OFF && activeStatus == ActiveStatus.OFF)
                setActiveStatus(ActiveStatus.OFF);
    }
    const listenerTyping = (data) => {
        if (data.groupId == group._id) {
            if (data.action == TypingAction.TYPING)
                setTyping(data);
            else if (data.action == TypingAction.STOP_TYPING)
                setTyping(data);
        }
    }
    const listenerMessage = (message) => {
        if (message.groupId == group._id) {
            setStatesMessage((oldStatesMessages) => ({
                messages: [message, ...oldStatesMessages.messages],
                offset: oldStatesMessages.offset + 1,
                limit: oldStatesMessages.limit
            }
            ));

        }
    }

    const onSubmit = () => {
        if (chatSocket)
            if (message != '') {
                const newMessage = {
                    userId: user._id,
                    groupId: group._id,
                    contentMessage: message.toString(),
                    isCounselor: user.role === Roles.COUNSELOR,
                    createdTime: (new Date()).getTime().toString()
                }
                chatSocket.emitMessage(newMessage);
                setMessage('');
            }
    }
    return (
        <SafeAreaView style={styles.container}>
            {loading && <ActivityIndicator size="large" />}
            <FlatList
                data={statesMessages.messages}
                ref={flatListRef}
                onEndReached={fetchMessages}
                inverted
                onEndReachedThreshold={0.01}
                extraData={statesMessages}
                // onLayout={() => flatListRef.current.scrollToOffset({ animated: true, offset: 0 })}
                renderItem={({ item, index }) => <ChatMessage
                    message={item}
                    user={user}
                    group={group}
                />}
                keyExtractor={item => item.createdTime}
            />
            {typing != null && typing.action == TypingAction.TYPING && <ChatMessage
                message={typing}
                user={user}
                group={group}
                typing={typing} />}

            <InputBox message={message} setMessage={setMessage} onSubmit={onSubmit} />
        </SafeAreaView>

    );
}
const styles = StyleSheet.create({
    container: {
        backgroundColor: colors.bg.secondary,
        height: '100%',
        padding: paddings.container,
    },
    typing: {
        marginBottom: 15,
        height: 0,
        width: '100%',
        flexWrap: 'wrap',
        paddingRight: 35,
        flexDirection: 'row',

    },
    activity: {
        width: 15,
        height: 15,
        borderRadius: 50,
        backgroundColor: colors.ui.online,
        position: 'absolute',
        left: 100,
        top: 20,
        zIndex: 1,
    },
})
export default ChatRoomScreen;