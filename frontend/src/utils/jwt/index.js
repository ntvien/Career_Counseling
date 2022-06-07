import jwt_decode from "jwt-decode";
import EncryptedStorage from 'react-native-encrypted-storage';
import AsyncStorage from '@react-native-async-storage/async-storage';
export const getUserInfoInToken = (token) => {
    decoded = jwt_decode(token);
    console.log(decoded);
    return {
        id: decoded.UserId,
        groups: decodeURI.groups
    }

}