import React, { useEffect, useContext, useState, useRef } from 'react';
import { View, Text, ScrollView, Image, FlatList } from 'react-native';
import { styles } from './CommentListScreen.styles';
import InputBox from "../../components/inputBox/InputBox";
import CommunitySocket from '../../api/socket/communitySocket';
import communityApi from '../../api/http/community/communityApi';
import { AuthContext } from '../../navigation';
import CommentCard from '../../components/cards/CommentCard';
import { colors } from '../../theme/colors';
import { fontSizes } from '../../theme/fonts';
import { fonts } from '../../theme/fonts';
const CommentListScreen = ({ route, navigation }) => {
    const { resourceId } = route.params;
    const [statesComments, setStatesComments] = useState({ comments: [], offset: 0, limit: 10 });
    const [inputComment, setInputComment] = useState('');
    const [loading, setLoading] = useState(true);
    const [communitySocket, setCommunitySocket] = useState(null);
    const flatListRef = useRef(null);
    const { state: { profile } } = useContext(AuthContext);
    useEffect(async () => {
        navigation.setOptions({
            title: "Bình luận",
            headerTintColor: colors.text.inverse,
            headerTitleStyle: {
                fontFamily: fonts.body,
                fontSize: fontSizes.h4,
            },

        });
        await fetchComments();

    }, []);
    useEffect( async() => {
        const socket =  await CommunitySocket.getInstance();
        setCommunitySocket(socket);
    }, [])
    useEffect(() => {
        if (communitySocket != null) {
            communitySocket.onComment(listenerComment)
            communitySocket.emitJoin({ room: resourceId });
            return () => {
                communitySocket.emitLeave({ room: resourceId });
                communitySocket.removeListenerEventComment(listenerComment);
            }
        }
    }, [communitySocket])
    const fetchComments = async () => {
        try {
            setLoading(true);
            const { comments, offset, limit } = statesComments;
            const newComments = await communityApi.getComments({ resourceId: resourceId, offset: offset, limit: limit });
            if (newComments.length > 0) {
                setStatesComments({
                    comments: [...comments, ...newComments],
                    offset: offset + newComments.length,
                    limit: limit
                });
            } else {
                setLoading(false);
            }
        } catch (e) {
            console.log("fetch comments err: ", e);
        }
    }
    const listenerComment = (comment) => {
        if (comment.resourceId == resourceId) {
            setStatesComments((oldStatesComments) => ({
                comments: [...oldStatesComments.comments, comment],
                offset: oldStatesComments.offset + 1,
                limit: oldStatesComments.limit
            }
            ));

        }
    }
    const onSubmit = () => {
        if (!profile) {
            navigation.navigate(routes.AUTH_MODAL);
        }
        else if (inputComment != "") {
            const comment = {
                resourceId: resourceId,
                userId: profile._id,
                typeOfResource: "university",
                contentComment: inputComment,
                createdTime: (new Date()).getTime().toString()
            }
            if (communitySocket != null) {
                communitySocket.emitComment(comment)

            }
            setInputComment('');
            flatListRef.current.scrollToEnd({ animated: true });
        }
    }

    return (
        <View style={styles.container}>
            <FlatList
                style={styles.listComment}
                data={statesComments.comments}
                ref={flatListRef}
                onEndReached={fetchComments}
                onEndReachedThreshold={0.01}
                extraData={statesComments}
                renderItem={({ item }) => <CommentCard
                    comment={item}
                    profile={profile}
                />}
                keyExtractor={item => item.createdTime}
            />
            <InputBox message={inputComment} setMessage={setInputComment} onSubmit={onSubmit} />
        </View>
    );
}
export default CommentListScreen;