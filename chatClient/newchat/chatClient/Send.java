package me.borakostem.newchat.chatClient;


import android.widget.EditText;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import me.borakostem.newchat.R;

public class Send extends Thread {
    //Properties
    Socket socket;
    PrintWriter input;
    String user;
    public Send (Socket socket,String user){
        this.user = user;
        this.socket = socket;
        try {
            input = new PrintWriter(socket.getOutputStream(), true);
        }catch (IOException e){
            System.out.println("Error getting output stream: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void run(){
        Scanner scan = new Scanner(System.in);
        String message = "";
        do{
            System.out.println("Please enter a message: ");
            message = scan.nextLine();
            input.println(message);
        }while (!message.equals("Server.stop"));

    }
}
