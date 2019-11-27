package ServerSide;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;


public class ServerInstance extends Thread {

    private String userName;
    private BufferedWriter out;
    private BufferedReader in;
    private Socket socket;

    public ServerInstance(Socket s){
        this.socket = s;
        this.userName = "Guest";
    }

    @Override
    public void run(){
        String word;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Thread.sleep(1000);
            this.send("Welcome to chat! \nTo register, type /register name password, to login - /login name password.\nOtherwise another users see you as a guest.");
            while (true) {
                word = in.readLine();
                System.out.println("readed: "+word);
                if (word.equals("stop")) {
                    break;
                }

                if(word.charAt(0) == '/'){
                    executeCommand(word);
                }
                else {
                    for (ServerInstance vr : Main.serverList) {

                        LocalDateTime myDateObj = LocalDateTime.now();
                        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                        String formattedDate = myDateObj.format(myFormatObj);

                        vr.send("[" + this.userName + " " + formattedDate + " ]: " + word);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void executeCommand(String s) {
        String command = " ";
        StringTokenizer tokenizer = new StringTokenizer(s);
        try {
            command = tokenizer.nextToken();
        } catch (Exception e) {
            this.send("Unknown command");
        }
        try {
            switch (command) {
                case "/register":
                    try {
                        String username = tokenizer.nextToken();
                        boolean res = Main.registerUser(username, tokenizer.nextToken());
                        if(res){
                            this.userName = username;
                            this.send("Success, your username is"+this.userName);
                        }
                        else{
                            this.send("Something is wrong");
                        }
                    }catch (ExistingUserException e){
                        this.send("Such user already exists");
                    }
                    break;
                case "/login":
                    String username = tokenizer.nextToken();
                    try {
                        boolean res = Main.loginUser(username, tokenizer.nextToken());
                        if(res){
                            this.userName=username;
                            this.send("Success");
                        }
                        else{
                            this.send("Something is wrong");
                        }
                    }catch (NoSuchUserException e){
                        this.send("Wrong username/password");
                    }
                    break;
                default:
                    this.send("Unknown command");
            }
        }catch(Exception e){
            this.send("Can`t execute this command");
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
