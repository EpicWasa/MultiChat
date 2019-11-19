package ServerSide;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;



public class Main {

    public static LinkedList<ServerInstance> serverList = new LinkedList<>();

    public static void main(String[] args) {

        try(ServerSocket socket = new ServerSocket(3345)){

            System.out.println("Server has been launched");

            while (true){
                Socket client = socket.accept();
                System.out.println("Client connected"+client.getInetAddress());
                try {
                    //serverList.add(new ServerInstance(client));
                    ServerInstance instance =  new ServerInstance(client);
                    serverList.add(instance);
                    instance.start();
                }catch (Exception e){
                    e.printStackTrace();
                    //    client.close();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}



