package ClientSide;

import sample.Main;

import java.io.*;
import java.net.Socket;

public class ClientMainServer {
    private static PrintWriter writer;
    private static BufferedReader reader;


    public static PrintWriter getWriter() {
        return writer;
    }

    public static BufferedReader getReader() {
        return reader;
    }

    private static BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
    private static Socket client;

    public static Socket getClient() {
        return client;
    }

    public ClientMainServer(String host, int port){
        try  {
            client = new Socket(host, port);
            writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            new ClientWriter().start();
            new ClientReader().start();
            System.out.println("Threads started");
        } catch (IOException e) {
            e.printStackTrace();
        }
       /* finally {
            try{
            client.close();
            }catch (IOException e){};
        }*/
    }

    static class ClientReader extends Thread {
        @Override
        public void run() {

            try {
                while (true) {
                    String str = reader.readLine();
                    System.out.println(str);
                    Main.getController().readMessage(str);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static class ClientWriter extends Thread {
        @Override
        public  void run() {
            try {
                while (true) {
                    String str = buffer.readLine();
                    System.out.println("Readed: "+str);
                    writer.println((String) str);
                    writer.flush();
                }

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void writeMSG(String s){
        writer.println((String) s);
        writer.flush();
    }

}
