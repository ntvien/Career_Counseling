package group12.career_counseling.websocket.modules.community;


import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.namespace.Namespace;
import group12.career_counseling.web_service.ddd.community.comment.Comment;
import group12.career_counseling.web_service.ddd.community.comment.CommentRequest.CommentRequest;
import group12.career_counseling.web_service.ddd.community.comment.service.ICommentService;
import group12.career_counseling.web_service.ddd.community.like.Like;
import group12.career_counseling.web_service.ddd.community.like.request.LikeRequest;
import group12.career_counseling.web_service.ddd.community.like.service.ILikeService;
import group12.career_counseling.web_service.ddd.resource.university.service.IUniversityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static group12.career_counseling.websocket.enumeration.CommunityEventName.*;


@Component
public class CommunityModule {
    private static final Logger logger = LoggerFactory.getLogger(CommunityModule.class);
    private final SocketIOServer server;
    private final Namespace namespace;
    private ICommentService commentService;
    private ILikeService likeService;
    private IUniversityService universityService;

    @Autowired
    public CommunityModule(SocketIOServer server, ICommentService commentService, ILikeService likeService, IUniversityService universityService) {
        this.server = server;
        this.commentService = commentService;
        this.likeService = likeService;
        this.universityService = universityService;
        this.namespace = (Namespace) this.server.addNamespace(NameOfNameSpace);
        this.namespace.addEventListener(COMMENT_EVENT, CommentRequest.class, onCommentReceived());
        this.namespace.addEventListener(LIKE_EVENT, LikeRequest.class, onLikeReceived());
        this.namespace.addEventListener(JOIN_ROOM, JoinRoom.class, onJoinRoom());
        this.namespace.addEventListener(LEAVE_ROOM, JoinRoom.class, onLeaveRoom());
    }

    private DataListener<CommentRequest> onCommentReceived() {
        return ((client, data, ackSender) -> {
            Comment comment = this.commentService.insertComment(data);
            this.namespace.getRoomOperations(data.getResourceId()).sendEvent(COMMENT_EVENT, comment);
        });
    }

    private DataListener<LikeRequest> onLikeReceived() {
        return ((client, data, ackSender) -> {
            Like like = this.likeService.insertLike(data);
            this.namespace.getRoomOperations(data.getResourceId()).sendEvent(LIKE_EVENT, like);
        });
    }
    private DataListener<JoinRoom> onJoinRoom() {
        return ((client, data, ackSender) -> {
            client.joinRoom(data.getRoom());
        });
    }
    private DataListener<JoinRoom> onLeaveRoom() {
        return ((client, data, ackSender) -> {
            client.leaveRoom(data.getRoom());
        });
    }
}
