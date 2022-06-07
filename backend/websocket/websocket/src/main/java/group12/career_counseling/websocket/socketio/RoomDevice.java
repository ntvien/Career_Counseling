package group12.career_counseling.websocket.socketio;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.store.MemoryStore;
import com.corundumstudio.socketio.store.Store;

import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RoomDevice {
    private final Store store =new MemoryStore();
    public void addDevice(String user_id,String User_Agent, SocketIOClient client){
        if(this.store.get(user_id) == null){
            Map<String,SocketIOClient> map = new HashMap<>();
            map.put(user_id,client);
            this.store.set(user_id, map);
        }
    }
    public Map<String, SocketIOClient> getListDeviceUser(String user_id){
        return this.store.get(user_id);
    }
    public SocketIOClient removeDevice(String user_id, String user_agent){
        Map<String, SocketIOClient> map = this.store.get(user_id);
        if(map !=null){
            SocketIOClient client = map.remove(user_agent);
            if(map.isEmpty()){
                this.store.del(user_id);
            }
        }
        return null;
    }
}
