import httpClient from '../httpClient';

export default userApi = {
    getProfile: () => httpClient.get('/user/profile'),
    addUserDevice : (token)=> httpClient.post('/user/add-device',{token:token}),
    removeUserDevice : (token)=> httpClient.post('/user/remove-device',{token:token})
};