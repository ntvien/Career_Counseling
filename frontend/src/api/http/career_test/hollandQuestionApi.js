import httpClient from "../httpClient";

const COLLECTION_PATH = "/career-test/holland-questions";

export default majorApi = {
  getHollandQuestionList: ({ codeId }) =>
    httpClient.get(COLLECTION_PATH, { params: { codeId } }),
};
