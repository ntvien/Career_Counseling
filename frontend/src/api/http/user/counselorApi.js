import httpClient from '../httpClient';

export default counselorApi = {
  login: ({token}) =>
    httpClient.post('/user/counselors/login', {token}),
  refreshToken: ({refreshToken}) =>
    httpClient.post('/user/counselors/refresh-token', {refreshToken}),
  register: ({phone, email, universityId}) => httpClient.post('user/counselors/register', {phone, email, universityId}),
  getProfile: () => httpClient.get('user/counselors/profile'),
};
