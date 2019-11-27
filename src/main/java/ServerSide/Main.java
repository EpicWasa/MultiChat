package ServerSide;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;



public class Main {

    public static LinkedList<ServerInstance> serverList = new LinkedList<>();
    protected static Connection connection;

    static private String URL ="jdbc:mysql://localhost:3306/users";
    private static String USERNAME = "root";
    private static String USERPASSWORD = "root";

    public static void main(String[] args) {

        try(ServerSocket socket = new ServerSocket(3345)){

            System.out.println("Server has been launched");
            Driver driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, USERPASSWORD);

            while (true){
                Socket client = socket.accept();
                System.out.println("Client connected"+client.getInetAddress());
                try {
                    ServerInstance instance =  new ServerInstance(client);
                    serverList.add(instance);
                    instance.start();
                }catch (Exception e){
                    e.printStackTrace();
                        client.close();
                }
            }
        }catch (IOException| SQLException e){
            e.printStackTrace();
        }

    }

    protected static Boolean registerUser(String username, String password)
    throws ExistingUserException{
        try {
            PreparedStatement statement = connection.prepareStatement("select * from users where user_name = ?");
            statement.setString(1, username);
            statement.execute();
            ResultSet set = statement.getResultSet();
             if(set.next()){
                 throw new ExistingUserException();
             }
             else{
                 statement = connection.prepareStatement("INSERT INTO users(user_name, user_password) VALUES (?,?)");
                 statement.setString(1, username);
                 statement.setString(2, password);
                 statement.execute();
                 return true;
             }


        }catch (ExistingUserException e){
            throw e;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    protected static Boolean loginUser(String name, String password)
    throws NoSuchUserException{
        try {
            PreparedStatement statement = connection.prepareStatement("select * from users where users.user_name = ? and users.user_password = ?");
            statement.setString(1, name);
            statement.setString(2, password);
            statement.execute();
            ResultSet set = statement.getResultSet();
            if(set.next()){
                return true;
            }
            else throw new NoSuchUserException();

        }catch (NoSuchUserException e){
            throw e;
        }
        catch (Exception e){
            return false;
        }
    }

}



