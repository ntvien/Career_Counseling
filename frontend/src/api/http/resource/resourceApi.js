import httpClient from '../httpClient';

export default resourceApi = {
    createUniversity: body => httpClient.post("/resource/universitys/", body),
    getUniversities: (params)=> httpClient.get("/resource/universitys/",{params}),
    getUniversitiesById: (id)=> httpClient.get(`/resource/universitys/${id}`),
    getListNameUniversities:()=>httpClient.get("/resource/universitys/listnameuniversity")
};
