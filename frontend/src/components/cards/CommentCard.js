import React, { useState, useEffect, useRef, useMemo } from "react";
import { View, Text, StyleSheet, TouchableOpacity, ToastAndroid } from "react-native";
import { fontSizes, fontWeights } from "../../theme/fonts";
import { Icon } from 'react-native-elements';
import { colors } from '../../theme/colors';
import { useNavigation } from "@react-navigation/native";
import communityApi from "../../api/http/community/communityApi";
import routes from "../../utils/enum/routes";
const CommentCard = ({ profile, comment }) => {
    const [commentStates, setCommentStates] = useState(comment);
    const navigation = useNavigation();
    const myEvaluation = useMemo(() => {
        if (commentStates.userEvaluates && profile)
            return commentStates.userEvaluates.find(userEvaluate => userEvaluate.userId === profile._id)
        return null;
    },
        [commentStates])
    const showToast = () => {
        ToastAndroid.show("Cám ơn đã đánh giá !", ToastAndroid.SHORT);
    }
    const onEvaluate = async (typeOfEvaluate) => {
        if (!profile) {
            navigation.navigate(routes.AUTH_MODAL);
        } else {
            showToast();
            const userEvaluates = {
                userId: profile._id,
                typeOfEvaluate: typeOfEvaluate,
            }

            setCommentStates(oldComment => ({ ...oldComment, userEvaluates: [userEvaluates] }))
            try {
                await communityApi.evaluateComment({ commentId: comment._id, typeOfEvaluate: typeOfEvaluate })
            } catch (e) {
                console.log("error evaluate", e);
            }
        }
    }
    return (
        <View style={style.container}>
            <Icon type="font-awesome-5" name="user-circle" color={colors.brand.primary} size={40} style={style.image} />
            <View style={style.body}>
                <Text style={style.username} >{comment.userComment.userName}</Text>
                <Text style={style.content} >{comment.contentComment}</Text>
                <View style={style.evaluation}>
                    <Text style={{flex: 1}}>Bình luận có ích không? </Text>
                    <TouchableOpacity
                        style={
                            [style.button,
                            { backgroundColor: myEvaluation && myEvaluation.typeOfEvaluate == 'helpful' ? '#D1E7FF' : '#C4C4C4' }]}
                        onPress={() => onEvaluate('helpful')}>
                        <Text style={style.buttonText}>Có</Text>
                    </TouchableOpacity>
                    <TouchableOpacity
                        style={[style.button,
                        { backgroundColor: myEvaluation && myEvaluation.typeOfEvaluate == 'unhelpful' ? '#D1E7FF' : '#C4C4C4' }]}
                        onPress={() => onEvaluate('unhelpful')}>
                        <Text style={style.buttonText}>Không</Text>
                    </TouchableOpacity>
                </View>
            </View>
        </View>
    );
}
const style = StyleSheet.create({
    container: {
        flexDirection: 'row',
        flexGrow: 1,
        marginTop: 5,
        marginBottom: 10,
        flex: 1

    },
    image: {
        height: 40,
        width: 40,
        marginRight: 5
    },
    body: {
        backgroundColor: '#ECEFF1',
        borderRadius: 15,
        padding: 10,
        flex: 1
    },
    username: {
        fontSize: fontSizes.h5,
        fontWeight: fontWeights.medium,
        marginBottom: 5

    },
    content: {
        marginBottom: 5
    },
    evaluation: {
        flexDirection: 'row',
        paddingLeft: 5
    },
    button: {
        backgroundColor: '#C4C4C4',
        borderRadius: 10,
        paddingHorizontal: 10,
        marginLeft: 10,
        justifyContent: "center"

    },
    buttonText: {
        textAlign: 'center',
    }

})
export default CommentCard;
