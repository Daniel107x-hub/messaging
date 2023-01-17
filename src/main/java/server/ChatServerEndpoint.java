package server;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.util.HashMap;
import java.util.Map;

@ServerEndpoint(value = "/app")
public class ChatServerEndpoint {
    private Map<String, String> users = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username){
        System.out.println("---Connected user: " + username + " with session id: " + session.getId());
        users.put(session.getId(), username);
    }

    @OnMessage
    public String onMessage(String message, Session session){
        System.out.println("---User " + users.get(session.getId()) + " says: " + message);
        return message;
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason){
        System.out.println("---Session: " + session.getId() + " of user: " + users.get(session.getId()) + " closed because: " + closeReason);
        users.remove(session.getId());
    }
}
