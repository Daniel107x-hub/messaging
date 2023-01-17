package server;

import org.glassfish.tyrus.server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MessagingServer {
    public static void main(String[] args) {
        runServer();
    }

    public static void runServer(){
        Server server = new Server("localhost", 10000, "/websockets", null, ChatServerEndpoint.class);
        try{
            server.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while(true){}
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            server.stop();
        }
    }
}
