package client;

import jakarta.websocket.*;
import org.glassfish.tyrus.client.ClientManager;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

@ClientEndpoint
public class ChatClientEndpoint {
    private static final Scanner scanner = new Scanner(System.in);

    @OnOpen
    public void onOpen(Session session){
        System.out.println("---Connected to server with session id: " + session.getId());
        try{
            session.getBasicRemote().sendText("start");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @OnMessage
    public String onMessage(String message, Session session){
        System.out.println("---Received message from server: " + message);
        return scanner.nextLine();
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason){
        System.out.println("---Closed connection with session id: " + session.getId() + " with reason: " + closeReason);
    }

    public static void main(String[] args) {
        ClientManager clientManager = ClientManager.createClient();
        try{
            System.out.print("Please type your username: ");
            String username = scanner.nextLine();
            URI uri = new URI("ws://localhost:10000/websockets/app?username=" + username);
            clientManager.connectToServer(ChatClientEndpoint.class, uri);
            while(true){}
        }catch(DeploymentException | URISyntaxException | IOException e){
            e.printStackTrace();
        }
    }
}
