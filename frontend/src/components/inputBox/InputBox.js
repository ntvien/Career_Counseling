import React from "react";
import { View, Text, TextInput, StyleSheet, TouchableOpacity } from "react-native";
import { Icon } from "react-native-elements/dist/icons/Icon";
import { colors } from "../../theme/colors";
import sizes from "../../theme/sizes";

const InputBox = ({message, setMessage, onSubmit}) => {
    return (
        <View style={styles.container}>
            <TextInput
                placeholder={"Type a message"}
                style={styles.textInput}
                multiline
                value={message}
                onChangeText={(text) => { setMessage(text) }}
            />
            <TouchableOpacity onPress={onSubmit}>
                <Icon
                    name="paper-plane"
                    type='font-awesome-5'
                    size={sizes[2]}
                    iconStyle={styles.buttonSend}

                />
            </TouchableOpacity>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flexDirection: 'row',
        backgroundColor: colors.bg.primary,
        borderColor: colors.brand.primary,
        borderWidth: 0.5,
        justifyContent: 'center',
        alignItems: 'flex-end',
        paddingHorizontal: 10
    },
    textInput: {
        flex: 1,
        marginHorizontal: 5
    },
    buttonSend: {
        color: colors.brand.primary,
        marginBottom: 10,
        marginLeft: 10

    },
    buttonClear: {
        marginBottom: 10,
        marginHorizontal: 10
    }
});
export default InputBox;