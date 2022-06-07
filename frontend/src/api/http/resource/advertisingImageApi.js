import httpClient from '../httpClient';

const COLLECTION_PATH = "/resource/advertising-images";

export default advertisingImageApi = {
  getAdvertisingImages: () =>
    httpClient.get(COLLECTION_PATH, {}),
};
