import React, { useEffect, useRef, useState } from "react";
import {
  View,
  Dimensions,
  Text,
  StyleSheet,
  TouchableNativeFeedback,
} from "react-native";
import { Image, Icon, Avatar, ListItem } from "react-native-elements";
import { colors } from "../../../theme/colors";
import { fontWeights } from "../../../theme/fonts";
import { useNavigation } from "@react-navigation/native";
import routes from "../../../utils/enum/routes";
import moment from "moment";
import TypingAction from "../../../utils/enum/socket/TypingAction";
import { TypingAnimation } from "react-native-typing-animation";
import ChatSocket from "../../../api/socket/ChatSocket";
import ActiveStatus from "../../../utils/enum/socket/ActiveStatus";
import Roles from "../../../utils/enum/user/Roles";
const ChatListItem = ({ user, group }) => {
  const navigation = useNavigation();
  const [groupStates, setGroupStates] = useState(group);

  const [typing, setTyping] = useState(false);
  const [activeStatus, setActiveStatus] = useState(ActiveStatus.OFF);
  const prevActiveStatus = useRef(ActiveStatus.OFF);
  const prevGroup = useRef(group);
  const [chatSocket, setChatSocket] = useState(null);
  useEffect(async () => {
    const socket = await ChatSocket.getInstance();
    setChatSocket(socket);
  }, []);
  useEffect(() => {
    prevActiveStatus.current = activeStatus;
  }, [activeStatus]);
  useEffect(() => {
    prevGroup.current = groupStates;
  }, [groupStates]);
  useEffect(() => {
    if (chatSocket != null) {
      chatSocket.onTyping(listerTyping);
      chatSocket.onMessage(listenerMessage);
      chatSocket.onActiveStatus(listenerActiveStatus);
      return () => {
        chatSocket.removeListenerEventTyping(listerTyping);
        chatSocket.removeListenerEventMessage(listenerMessage);
        chatSocket.removeListenerEventActiveStatus(listenerActiveStatus);
      };
    }
  }, [chatSocket]);
  const listenerActiveStatus = (data) => {
    if (
      (user.role == Roles.STUDENT && data.userId == group.university.id) ||
      (user.role == Roles.COUNSELOR && data.userId == group.student.id)
    ) {
      if (
        data.status == ActiveStatus.ON &&
        prevActiveStatus.current == ActiveStatus.OFF
      )
        setActiveStatus(ActiveStatus.ON);
      else if (
        data.status == ActiveStatus.OFF &&
        prevActiveStatus.current == ActiveStatus.ON
      )
        setActiveStatus(ActiveStatus.OFF);
    }
  };
  const listenerMessage = (message) => {
    if (message.groupId == prevGroup.current._id) {
      setGroupStates((oldGroup) => ({ ...oldGroup, lastMessage: message }));
    }
  };
  const listerTyping = (data) => {
    if (data.groupId == group._id) {
      if (data.action == TypingAction.TYPING) setTyping(true);
      else if (data.action == TypingAction.STOP_TYPING) setTyping(false);
    }
  };
  const onClick = () => {
    navigation.navigate(routes.CHAT_ROOM, {
      user: user,
      group: group,
    });
  };

  function truncate(str) {
    const n = (Dimensions.get("window").width - 200) / 8;
    return str.length > n ? str.substr(0, n - 1) + " ..." : str;
  }
  const handleTime = (timestamp) => {
    const date = moment(parseInt(timestamp));
    if (date.format("l") !== moment().format("l")) return date.format("Do MMM");
    return date.format("LT");
  };
  return (
    <TouchableNativeFeedback onPress={onClick}>
      <View style={styles.container}>
        <View style={styles.lefContainer}>
          <View>
            {user.role === Roles.COUNSELOR ? (
              <Icon
                type="font-awesome-5"
                name="user-circle"
                color={colors.brand.primary}
                size={40}
                style={styles.avatar}
              />
            ) : (
              <Image
                source={{ uri: groupStates.university.uriAvatar }}
                size={30}
                style={styles.avatar}
              />
            )}

            {activeStatus == ActiveStatus.ON && (
              <View style={styles.activity} />
            )}
          </View>
          <View style={styles.midContainer}>
            {user.role === Roles.COUNSELOR ? (
              <Text style={styles.title}>{groupStates.student.fullName}</Text>
            ) : (
              <Text style={styles.title}>
                {groupStates.university.fullName}
              </Text>
            )}
            {typing ? (
              <TypingAnimation />
            ) : (
              groupStates.lastMessage != null && (
                <View style={styles.lastMessContainer}>
                  <Text style={[styles.lastMessage, styles.textUnread]}>
                    {truncate(groupStates.lastMessage.contentMessage)}{" "}
                  </Text>
                  <Text style={styles.textUnread}> ·</Text>
                  <Text style={styles.textUnread}>
                    {" "}
                    {handleTime(groupStates.lastMessage.createdTime)}
                  </Text>
                </View>
              )
            )}
          </View>
        </View>
      </View>
    </TouchableNativeFeedback>
  );
};

const styles = StyleSheet.create({
  container: {
    flexDirection: "row",
    width: "100%",
    margin: 5,
    padding: 5,
    flex: 1,
    borderBottomWidth: 1,
    borderBottomColor: colors.border.primary
  },
  lefContainer: {
    flexDirection: "row",
    width: "100%",
  },
  midContainer: {
    flex: 1,
    alignSelf: "center"
  },
  lastMessContainer: {
    flexDirection: "row",
  },
  avatar: {
    borderRadius: 30,
    height: 60,
    width: 60,
    marginRight: 15,
  },
  activity: {
    width: 15,
    height: 15,
    borderRadius: 50,
    backgroundColor: colors.ui.online,
    position: "absolute",
    left: 45,
    top: 40,
    zIndex: 1,
  },
  textUnread: {
    color: colors.text.primary,
    fontWeight: fontWeights.bold,
  },
  textRead: {
    color: colors.text.primary,
    fontWeight: fontWeights.regular,
  },
  lastMessage: {
  },
  title: {
    color: colors.text.primary,
    fontWeight: fontWeights.bold,
  },
});
export default ChatListItem;
