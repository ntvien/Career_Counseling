import chatEvent from "../../utils/enum/socket/chatEvent";
import nameSpaceSocket from "../../utils/enum/socket/nameSpaceSocket";
import socketBuilder from "./socketClient";


const ChatSocket = function () {
    var instance;

    async function init() {
        const socket = await socketBuilder(nameSpaceSocket.CHAT);
        socket.connect();
        socket.on("connect", () => {
            console.log("socket id: ", socket.id);
            console.log(socket.connected)
        })
        const onMessage = (listener) => {
            socket.on(chatEvent.MESSAGE, listener);
        }
        const onReadMessage = (listener) => {
            socket.on(chatEvent.READ_MESSAGE, listener);
        }
        const onTyping = (listener) => {
            socket.on(chatEvent.TYPING, listener);
        }
        const onActiveStatus = (listener) => {
            socket.on(chatEvent.ACTIVE_STATUS, listener);
        }
        const emitMessage = ({ userId, groupId, contentMessage, isCounselor, createTime }) => {
            socket.emit(chatEvent.MESSAGE,
                {
                    userId: userId,
                    groupId: groupId,
                    contentMessage: contentMessage,
                    isCounselor: isCounselor,
                    createdTime: createTime
                });
        }
        const emitTyping = (userId, groupId, action, isCounselor) => {
            socket.emit(chatEvent.TYPING,
                {
                    userId: userId,
                    groupId: groupId,
                    isCounselor: isCounselor,
                    action: action
                });
        }
        const emitReadMessage = (userId, groupId, messageId, createdTime) => {
            socket.emit(chatEvent.READ_MESSAGE,
                {
                    userId: userId,
                    groupId: groupId,
                    messageId: messageId,
                    createdTime: createdTime
                });
        }
        const removeListenerEventMessage = (listener) => {
            socket.off(chatEvent.MESSAGE, listener);
        }
        const removeListenerEventReadMessage = (listener) => {
            socket.off(chatEvent.READ_MESSAGE, listener);
        }
        const removeListenerEventTyping = (listener) => {
            socket.off(chatEvent.TYPING, listener);
        }
        const removeListenerEventActiveStatus = (listener) => {
            socket.off(chatEvent.ACTIVE_STATUS, listener);
        }

        return {
            onMessage,
            onTyping,
            onActiveStatus,
            onReadMessage,
            emitMessage,
            emitTyping,
            emitReadMessage,

            removeListenerEventReadMessage,
            removeListenerEventActiveStatus,
            removeListenerEventTyping,
            removeListenerEventMessage
        }
    }
    return {
        getInstance: async function () {
            if (!instance) {
                instance= await init();
            }
            return instance;
        }
    }
}();

export default ChatSocket;