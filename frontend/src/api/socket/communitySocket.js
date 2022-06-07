import CommunityEvent from "../../utils/enum/socket/CommunityEvent";
import nameSpaceSocket from "../../utils/enum/socket/nameSpaceSocket";

const CommunitySocket = function () {
    var instance;

    async function init() {
        const socket = await socketBuilder(nameSpaceSocket.COMMUNITY);
        socket.connect();
        socket.on("connect", () => {
            console.log("socket id: ", socket.id);
            console.log(socket.connected)
        })
        const onComment = (listener) => {
            socket.on(CommunityEvent.COMMENT, listener);
        }
        const onLike = (listener) => {
            socket.on(CommunityEvent.LIKE, listener);
        }
        const emitComment = ({ resourceId, userId, typeOfResource, contentComment, createdTime }) => {
            console.log({ resourceId, userId, typeOfResource, contentComment, createdTime });
            socket.emit(CommunityEvent.COMMENT,
                {
                    resourceId: resourceId,
                    userId: userId,
                    typeOfResource: typeOfResource,
                    contentComment: contentComment,
                    createdTime: createdTime
                });
        }
        const emitLike = ({ userId, resourceId, typeOfResource }) => {
            socket.emit(CommunityEvent.LIKE, { userId, resourceId, typeOfResource });
        }
        const emitJoin = ({ room }) => {
            socket.emit(CommunityEvent.JOIN_ROOM, {
                room
            });
        }
        const emitLeave = ({ room }) => {
            socket.emit(CommunityEvent.LEAVE_ROOM, {
                room
            });
        }
        const removeListenerEventComment = (listener) => {
            socket.off(CommunityEvent.COMMENT, listener);
        }
        const removeListenerEventReadLike = (listener) => {
            socket.off(CommunityEvent.LIKE, listener);
        }

        return {
            onComment,
            onLike,
            emitComment,
            emitLike,
            emitJoin,
            emitLeave,
            removeListenerEventComment,
            removeListenerEventReadLike,
        }
    }
    return {
        getInstance: async function () {
            if (!instance) {
                instance = await init();
            }
            return instance;
        }
    }
}();

export default CommunitySocket;