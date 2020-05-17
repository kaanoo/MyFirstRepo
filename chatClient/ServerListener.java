package me.borakostem.newchat.chatClient;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import me.borakostem.newchat.MainActivity;


public class ServerListener extends Thread{
    //Properties
    Socket socket;
    BufferedReader response;
    public ServerListener (Socket socket){
        this.socket = socket;
        try {
            response = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e){
            System.out.println("Error getting input stream: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void run(){
        while(true){
            try {
                String message = response.readLine();
                Message msg = Message.obtain();
                msg.obj = message;
                MainActivity.handler.sendMessage(msg);

                //System.out.println(message);
            }catch (IOException e){
                System.out.println("Error reading from server: " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }
    }
}
