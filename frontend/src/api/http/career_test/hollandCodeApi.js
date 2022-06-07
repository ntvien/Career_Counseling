import httpClient from "../httpClient";

const COLLECTION_PATH = "/career-test/holland-codes";

export default majorApi = {
  getHollandCodeList: () => httpClient.get(COLLECTION_PATH, {}),
};
