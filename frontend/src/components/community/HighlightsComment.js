import React, { useEffect, useState, useContext } from "react";
import { StyleSheet } from "react-native";
import { View, FlatList, Text } from "react-native";
import { Icon } from "react-native-elements";
import { TouchableOpacity } from "react-native-gesture-handler";
import communityApi from "../../api/http/community/communityApi";
import { colors } from "../../theme/colors";
import { fontSizes, fontWeights } from "../../theme/fonts";
import CommentCard from "../cards/CommentCard";
import { useNavigation } from "@react-navigation/native";
import routes from "../../utils/enum/routes";
import { AuthContext } from '../../navigation';
const HighlightsComment = ({ resourceId }) => {
    const [comments, setComments] = useState([]);
    const navigation = useNavigation();
    const { state: { profile, userType } } = useContext(AuthContext);
    useEffect(async () => {
        if (resourceId) {
            await fetchComment();
        }
    }, [resourceId])
    const fetchComment = async () => {
        try {

            const newComments = await communityApi.getComments({ resourceId: resourceId, offset: 0, limit: 2 });
            setComments(newComments);
        } catch (e) {
            console.log("fetch comments err: ", e);
        }
    }
    const onSeeMoreComment = () => {
        navigation.navigate(routes.COMMENT, { resourceId: resourceId })
    }
    return (
        <View style={styles.container}>
            <View style={styles.rowTop}>
                <Text style={styles.highCommentText}>Bình luận nổi bật</Text>
                {/* <TouchableOpacity
                    onPress={onSeeMoreComment}>
                    <View style={styles.ContainerViewMoreText}>
                        <Text style={styles.viewMoreText}>Xem thêm</Text>
                    </View>
                </TouchableOpacity> */}
            </View>
            <View style={styles.rowBottom}>
                {comments.map(comment =>
                    <CommentCard
                        comment={comment}
                        resourceId={resourceId}
                        profile={profile}
                        key={comment.createdTime} />
                )}
                {comments.length >= 2 &&
                    <TouchableOpacity
                        onPress={onSeeMoreComment}>
                        <View style={styles.viewMoreBottom}>
                            <Text style={styles.viewMoreText}>Xem thêm</Text>
                            <Icon type="font-awesome-5" name="angle-down" color="#404040" size={20} style={styles.icon} />
                        </View>
                    </TouchableOpacity>}
            </View>

        </View>
    );
}
const styles = StyleSheet.create({
    container: {
        flex: 1,
        paddingTop: 20,
    },
    rowTop: {
        flexDirection: 'row',
        marginHorizontal: 5,
        justifyContent: 'space-between'
    },
    ContainerViewMoreText: {
        paddingHorizontal: 10,
        paddingVertical: 5,
        borderRadius: 20,
        borderColor: colors.brand.primary,
        borderWidth: 1
    },
    viewMoreText: {
        textAlignVertical: "center",
    },
    highCommentText: {
        textAlignVertical: "center",
        color: colors.brand.primary,
        fontSize: fontSizes.h4,
        fontWeight: fontWeights.bold,
    },
    rowBottom: {
        flex: 1,
        paddingVertical: 20,
    },
    viewMoreBottom: {
        justifyContent: 'center',
        flexDirection: 'column',
        alignSelf: 'center'
    }
});
export default HighlightsComment;