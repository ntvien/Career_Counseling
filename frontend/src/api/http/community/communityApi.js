import httpClient from '../httpClient';

export default communityApi = {
    //comment
    getComments: (params) => httpClient.get("/community/comments", { params }),
    evaluateComment: ({ commentId, typeOfEvaluate }) => httpClient.post("/community/comments/evaluate", { commentId, typeOfEvaluate }),

    //like
    getLikes: (params) => httpClient.get("/community/likes", { params }),
    checkUserLike: (params) => httpClient.get("/community/likes/check", { params }),
    like: ({ userId, resourceId }) => httpClient.post("/community/likes", { userId, resourceId }),

    //share
    getShares: (params) => httpClient.get("/community/shares", { params }),
    share: ({ userId, resourceId }) => httpClient.post("/community/shares", { userId, resourceId }),


    //view 
    pushViewUniversity: (id) => httpClient.put(`/resource/universities/${id}/view-number`),
    pushViewMajor: (id) => httpClient.put(`/resource/majors/${id}/view-number`),

}