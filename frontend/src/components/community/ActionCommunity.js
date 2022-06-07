import React, { useContext, useState, useEffect } from "react";
import { StyleSheet } from "react-native";
import { View, Text, TouchableOpacity, Share } from "react-native";
import { Icon } from 'react-native-elements';
import { useNavigation } from "@react-navigation/native";
import { AuthContext } from '../../navigation';
import routes from "../../utils/enum/routes";
import communityApi from "../../api/http/community/communityApi";
import universityApi from "../../api/http/resource/universityApi";
import majorApi from "../../api/http/resource/majorApi";
import TypeOfResource from "../../utils/enum/resource/TypeOfResource";
const ActionCommunity = ({ resourceId, typeOfResource, view,onShare }) => {
    const navigation = useNavigation();
    const { state: { profile } } = useContext(AuthContext);
    const [countLikes, setCountLikes] = useState(0);
    const [countComment, setCountComment] = useState(0);
    const [countShare, setCountShare] = useState(0);
    const [isLike, setIsLike] = useState(false);

    useEffect(() => {
        if (resourceId) {
            Promise.all(
                fetchCountLike(),
                fetchCountComment(),
                fetchCountShare(),

            );
            if (profile)
                Promise.all(fetchCheckUserLike());

            pushView();
        }

    }, [resourceId])
    const pushView = () => {
        if (typeOfResource == TypeOfResource.MAJOR)
            communityApi.pushViewMajor(resourceId);
        else
            communityApi.pushViewUniversity(resourceId);
    }

    const fetchCheckUserLike = async () => {
        try {
            const isLike = await communityApi.checkUserLike({ resourceId: resourceId });
            setIsLike(isLike);
        } catch (e) {
            console.log("fetch user like err: ", e);
        }
    }

    const fetchCountLike = async () => {
        try {
            const res = await communityApi.getLikes({ resourceId: resourceId });
            setCountLikes(res);
        } catch (e) {
            console.log("fetch like err: ", e);
        }
    }

    const fetchCountComment = async () => {
        try {
            const res = await communityApi.getComments({ resourceId: resourceId, isCount: true });
            setCountComment(res.count)
        } catch (e) {
            console.log("fetch comment err: ", e);
        }
    }
    const fetchCountShare = async () => {
        try {
            const res = await communityApi.getShares({ resourceId: resourceId, isCount: true });
            setCountShare(res);
        } catch (e) {
            console.log("fetch share err: ", e);
        }
    }

    const onPressLike = () => {
        if (!profile) {
            navigation.navigate(routes.AUTH_MODAL);
        } else if (!isLike) {
            try {
                communityApi.like({ userId: profile._id, resourceId: resourceId });
                setCountLikes(old => old + 1);
                setIsLike(true);
            } catch (e) {
                console.log("like errorL: ", e);
            }
        }
    }

    const onPressComment = () => {
        if (!profile) {
            navigation.navigate(routes.AUTH_MODAL);
        }
        else
            navigation.navigate(routes.COMMENT, { resourceId: resourceId });
    }

    

    return (
        <View style={styles.container}>
            <View style={styles.top}>
                <View style={styles.containerIconTop}>
                    <View style={styles.groupIcon}>
                        <Text>  </Text>
                        <Icon type="font-awesome-5" name="thumbs-up" color="#0277BD" size={20} style={styles.icon} />
                        <Text style={styles.textIcon}>{countLikes.toString()}</Text>
                    </View>
                    <View style={styles.groupIcon}>
                        <Icon type="font-awesome-5" name="eye" color="#404040" size={22} style={styles.icon} />
                        <Text style={styles.textIcon}>{view}</Text>
                    </View>
                </View>
                <View style={styles.containerTextTop}>
                    <Text style={styles.text}>{countComment} bình luận</Text>
                    <Text style={styles.text}>{countShare} lượt chia sẻ</Text>
                </View>
            </View>
            <View style={styles.bottom}>
                <TouchableOpacity
                    onPress={onPressLike}
                >
                    <View style={styles.groupIconBottom}>
                        <Icon type="font-awesome-5" name="thumbs-up" color={isLike ? "#0277BD" : ''} size={25} style={styles.icon} />
                        <Text style={styles.textIcon}>Thích</Text>
                    </View>
                </TouchableOpacity>
                <TouchableOpacity
                    onPress={onPressComment}
                >
                    <View style={styles.groupIconBottom}>
                        <Icon type="font-awesome-5" name="comment-dots" color="#404040" size={25} style={styles.icon} />
                        <Text style={styles.textIcon}>Bình luận</Text>
                    </View>
                </TouchableOpacity>
                <TouchableOpacity
                    onPress={onShare}>
                    <View style={styles.groupIconBottom}>
                        <Icon type="font-awesome-5" name="share" color="#404040" size={25} style={styles.icon} />
                        <Text style={styles.textIcon}>Chia sẻ</Text>
                    </View>
                </TouchableOpacity>
            </View>
        </View >
    );
}
const styles = StyleSheet.create({
    container: {
        flexDirection: 'column'
    },
    top: {
        borderBottomWidth: 0.5,
        flexDirection: 'row',
        paddingHorizontal: 5,
        paddingVertical: 5,
        justifyContent: 'space-between'
    },
    bottom: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        paddingHorizontal: 5,
        paddingVertical: 5,

    },
    containerIconTop: {
        flexDirection: 'row',

    },
    containerTextTop: {
        flexDirection: 'row',
        alignSelf: 'flex-start',
        paddingTop: 10,
    },
    groupIconBottom: {
        flexDirection: 'row',
    },
    groupIcon: {
        flexDirection: 'row',

        marginRight: 20,
    }, text: {
        marginLeft: 15,
    },
    textIcon: {
        textAlign: 'center',
        textAlignVertical: "center"
    },
    icon: {
        marginRight: 10
    }
});
export default ActionCommunity;