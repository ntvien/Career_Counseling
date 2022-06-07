import httpClient from '../httpClient';

export default chatApi = {
  getGroups: params => httpClient.get("/chat/groups", { params }),
  getMessagesByGroupId: (groupId, params) => httpClient.get(`/chat/groups/${groupId}/messages`, { params })
};
