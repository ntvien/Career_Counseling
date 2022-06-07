import httpClient from '../httpClient';

export default studentApi = {
  login: ({userName, password}) => httpClient.post('/user/students/login', {userName, password}),
  refreshToken: body => httpClient.post('/user/students/refresh-token', body),
  register: ({userName, password, fullName}) => httpClient.post('user/students/register', {userName, password, fullName}),
  getProfile: () => httpClient.get('user/students/profile')
};
