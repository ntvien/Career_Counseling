import React, { useStates } from "react";
import { StyleSheet, View } from "react-native";
import ActionCommunity from "./ActionCommunity";
import HighlightsComment from "./HighlightsComment";

const CommunityResource = ({ resource, typeOfResource, onShare}) => {
    return (
        <View style={styles.container}>
            <ActionCommunity resourceId={resource._id} typeOfResource={typeOfResource} view={resource ? resource.viewNumber : 0} onShare = {onShare} />
            <HighlightsComment resourceId={resource._id} />
        </View>
    );
}
const styles = StyleSheet.create({
    container: {
    }
});
export default CommunityResource;