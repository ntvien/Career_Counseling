import React, { useEffect, useMemo, useState } from 'react';
import { View, Text, StyleSheet, Image } from 'react-native';
import { colors } from '../../../theme/colors';
import moment from "moment";
import Roles from '../../../utils/enum/user/Roles';
import { TypingAnimation } from 'react-native-typing-animation';
import { Icon } from "react-native-elements/dist/icons/Icon";
const ChatMessage = ({ message, user, group, typing }) => {
    const [chatSocket, setChatSocket] = useState(null);
    const isMyMessage = useMemo(() => message.userId == user._id || (user.role === Roles.COUNSELOR && message.isCounselor), [message, user]);
    // const isRead = useMemo(() => message.userReads != null && message.userReads.some(userRead => userRead.userId == user._id), [message]);
    const [messageState, setMessageStates] = useState(message);
    const listenerReadMessage = (readMessage) => {
        if (readMessage.messageId == messageState._id) {
            setMessageStates({ ...messageState, userReads: [...messageState, userReads, readMessage] })
        }
    }
    return (
        <View style={styles.container}>
            <View style={[{ flexDirection: isMyMessage ? 'row-reverse' : 'row' }]}>

                {!isMyMessage && <Image source={{ uri: group.university.uriAvatar }} style={styles.avatar} />}
                <View style={[
                    styles.messageBox, {
                        backgroundColor: isMyMessage ? colors.message.myMessage : colors.message.otherMessage,
                        marginLeft: isMyMessage ? 50 : 0,
                        marginRight: isMyMessage ? 0 : 50
                    }]}>
                    {typing != null ? (<View style={styles.typing} ><TypingAnimation /></View>) :
                        (<View>
                            <Text>{message.contentMessage}</Text>
                            <Text style={styles.text_unread}> {moment(parseInt(message.createdTime)).format('h:mm')}</Text>

                        </View>)}

                </View>

            </View>
        </View>

    );
}

const styles = StyleSheet.create({
    container: {
        padding: 10,
        width: '100%',
    },
    messageBox: {
        marginRight: 50,
        borderRadius: 20,
        padding: 10,
        width: 'auto',
        minWidth: 50,

    },
    avatar: {
        borderRadius: 50,
        height: 20,
        width: 20,
        marginRight: 5,
    },
    typing: {
        marginBottom: 20
    },
    avatarRead: {
        marginRight: -5
    }
})

export default ChatMessage;