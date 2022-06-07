/**
 * @format
 */
import {AppRegistry} from 'react-native';
import App from './App';
import {name as appName} from './app.json';
import messaging from '@react-native-firebase/messaging';



// async function onMessageReceived(message) {
//  // Create a channel
//  const channelId = await notifee.createChannel({
//     id: 'default',
//     name: 'Default Channel',
//   });
//   console.log(message);
//   // Display a notification
//   await notifee.displayNotification({
//     title: message.notification.title,
//     body: message.notification.body,
//     android: {
//       channelId,
//       smallIcon: "@mipmap/logo",
//     },
    
//   });
// }
// messaging().onMessage(onMessageReceived);
// messaging().setBackgroundMessageHandler(onMessageReceived);
AppRegistry.registerComponent(appName, () => App);
