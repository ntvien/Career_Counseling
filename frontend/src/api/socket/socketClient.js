const io = require("socket.io-client");
import env from '../../utils/env';
import EncryptedStorage from 'react-native-encrypted-storage';
import storageKeys from "../../utils/enum/storageKeys";

export default socketBuilder = async (nameSpace) => {
    var token = "";
    try {
        const accessToken = await EncryptedStorage.getItem(
            storageKeys.ACCESS_TOKEN,
          );
        if (accessToken!= undefined) {
            token = "Bearer " + accessToken;
        }
    } catch (e) {
        console.log(e);
    }
    const socketClient = io(env.HOST_SOCKET + nameSpace, {
        extraHeaders: {
            Authorization: token
        },
        withCredentials: true,
        reconnection: true,
        autoConnect: false,
        transports: ['websocket']

    });
    const tryReconnect = () => {
        setTimeout(() => {
            socketClient.open((err) => {
                if (err) {
                    tryReconnect();
                }
            });
        }, 5000);
    }
    socketClient.on("close", tryReconnect);


    return socketClient;
}