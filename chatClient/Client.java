package me.borakostem.newchat.chatClient;

import android.os.Message;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import me.borakostem.newchat.MainActivity;

public class Client {
    //Properties
    static String user = "";
    static Socket socket;
    static int port;
    ServerListener listen;
    PrintWriter input;
    public Client(String userName, int port) {
        user = userName;
        this.port = port;
    }

    public Socket getSocket(){
        return socket;
    }

    public void execute(){
        Message msg = Message.obtain();
        try {
            //Client Properties
            Socket socket = new Socket("178.20.229.45", port);
            this.socket = socket;
            System.out.println("Connected to the server");
            input = new PrintWriter(socket.getOutputStream(), true);
            //new Send(socket,user).start();

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
            msg.obj = "Server not found: " + ex.getMessage();
            MainActivity.handler.sendMessage(msg);
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
            msg.obj = "I/O Error: " + ex.getMessage();
            MainActivity.handler.sendMessage(msg);
        }

    }

    public void terminate(){
        try{
            socket.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


    public void sendMsg(String send){
        if(send != null) {
            input.println(send);
        }

    }


}
