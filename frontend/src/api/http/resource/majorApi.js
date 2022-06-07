import httpClient from "../httpClient";

const COLLECTION_PATH = "/resource/majors";

export default majorApi = {
  getMajorList: ({ page, size, sortBy, keyword }) =>
    httpClient.get(COLLECTION_PATH, { params: { page, size, sortBy, keyword } }),
  getMajor: id => httpClient.get(`${COLLECTION_PATH}/${id}`, {}),
  updateView: ({ resourceId }) => httpClient.put("/resource/majors/:_id/view-number", { resourceId }),
  getAllMajor: (ids) => httpClient.get(`${COLLECTION_PATH}/all`, {params:{ids: ids.toString()}}),
};
