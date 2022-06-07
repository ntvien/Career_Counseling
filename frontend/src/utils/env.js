const localHost = "http://localhost:8080";
const remoteHost = "http://tubku.ddns.net";
const localHostSocket = "http://localhost:9092";
const remoteHostSocket = "http://tubku.ddns.net:9092";
const isLocalServer = process.env["SERVER"] == 'local';

export default env = {
    HOST: isLocalServer ? localHost : remoteHost,
    HOST_SOCKET: remoteHostSocket,
};
