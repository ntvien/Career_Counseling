import httpClient from "../httpClient";

const COLLECTION_PATH = "/resource/universities";

export default universityApi = {
  getUniversityList: ({ page, size, sortBy, keyword }) =>
    httpClient.get(COLLECTION_PATH, {
      params: { page, size, sortBy, keyword },
    }),
  getUniversity: (id) => httpClient.get(COLLECTION_PATH + "/" + id, {}),
  getUniversityNameList: ({ page, size }) =>
    httpClient.get(COLLECTION_PATH + "/name", { params: { page, size } }),
     updateView: ({ resourceId }) => httpClient.put("/resource/universities/:_id/view-number", { resourceId })
};
