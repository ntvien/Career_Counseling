import axios from 'axios';
import { Alert } from 'react-native';
import env from '../../utils/env';
import storageKeyEnum from '../../utils/enum/storageKeys';
import statusCodes, * as Stat from './statusCodes';
import storageKeys from '../../utils/enum/storageKeys';
import EncryptedStorage from 'react-native-encrypted-storage';
const queryString = require('query-string');
const httpClient = axios.create({
  baseURL: `${env.HOST}`,
  timeout: 5000,
  headers: {
    'content-type': 'application/json'
  },
  paramsSerializer: params => queryString.stringify(params),
});
httpClient.interceptors.request.use(async config => {
  try {
    const accessToken = await EncryptedStorage.getItem(
      storageKeyEnum.ACCESS_TOKEN,
    );
    if (accessToken) {
      config.headers.Authorization =  `Bearer ${accessToken}`;
      return config;
    }
  } catch (e) {
    console.log("Config request err: ",e);
  }
  return config;
});

httpClient.interceptors.response.use(
  function (response) {
    if (response && response.data!==undefined) {
      return response.data;
    }
    Alert.alert('Kết nối không ổn định');
    return {};
  },
  async function (error) {
    try {
      if (error && error.response) {
        switch (error.response.status) {
          case statusCodes.UNAUTHORIZED:
            await processUnauthoried(error.response.config);
            break;
          case statusCodes.FORBBIDEN:
            Alert.alert('Bạn không có quyền truy cập');
            break;
          case statusCodes.NOT_FOUND:
            Alert.alert('Tài nguyên không tồn tại');
            break;
          case statusCodes.BAD_REQUEST:
            Alert.alert('Dữ liệu không hợp lệ');
            break;
          default:
            Alert.alert('Hệ thống đang bảo trì');
        }
      }
    } catch (e) {
      console.log("Response err: ",e);
    }
    return Promise.reject(error);
  },
);

const processUnauthoried = async config => {
  const refreshToken = await EncryptedStorage.getItem(
    storageKeys.REFRESH_TOKEN,
  );
  if (refreshToken) {
    await EncryptedStorage.removeItem(storageKeys.ACCESS_TOKEN);
    await EncryptedStorage.removeItem(storageKeys.REFRESH_TOKEN);
    axios.post(`${env.HOST}/user/refresh-token`, { refreshToken: refreshToken }).then((res) => {
      if (res && res.data) {
        const promise1 = EncryptedStorage.setItem(
          storageKeys.ACCESS_TOKEN,
          res.data.token,
        );
        const promise2 = EncryptedStorage.setItem(
          storageKeys.REFRESH_TOKEN,
          res.data.refresh_token,
        );
        Promise.all([promise1, promise2]).then(() => {
          return;
        });
      }
    }).catch((err) => {
      console.log("Refresh Token: ", err);
    })
  }
};

export default httpClient;
