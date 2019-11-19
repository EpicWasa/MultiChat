package ServerSide;

import java.io.*;
import java.net.Socket;


public class ServerInstance extends Thread {

    private BufferedWriter out;
    private BufferedReader in;
    private Socket socket;

    public ServerInstance(Socket s){
        this.socket = s;
    }

    @Override
    public void run(){
        String word;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (true) {
                word = in.readLine();
                System.out.println("readed: "+word);
                if (word.equals("stop")) {
                    break;
                }
                for (ServerInstance vr : Main.serverList) {
                    System.out.println("Sended: "+word);
                    vr.send(word); // отослать принятое сообщение с
                    // привязанного клиента всем остальным включая его
                }

            }
        } catch (IOException e) {
        }
    }


    private void send(String s) {
        try {
            this.out.write(s+"\n");
            this.out.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
