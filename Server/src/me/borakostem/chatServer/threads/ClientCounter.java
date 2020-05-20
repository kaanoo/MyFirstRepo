package me.borakostem.chatServer.threads;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ClientCounter extends Socket{
    //Properties
    ArrayList<String> oldMessages = new ArrayList <String>(100);
    ArrayList<Socket> clientSocket = new ArrayList<Socket>();
    ArrayList<String> clientNames = new ArrayList<String>();
    int size = 0;

    public ClientCounter(){ }

    public void add(Socket socket){
        clientSocket.add(socket);
        oldMessages(socket);
        size++;
    }

    public ArrayList<Socket> getClients(){
        return clientSocket;
    }

    public ArrayList<String> getNames(){
        return clientNames;
    }

    public void addName(String name){
        clientNames.add(name);
    }

    public void remove(Socket socket){
        int index = clientSocket.indexOf(socket);
        clientSocket.remove(index);
        clientNames.remove(index);
        try {
            socket.close();
        }
        catch (IOException e){
            System.out.println("Error");
            e.printStackTrace();
        }
        size--;
    }

    public void removeInt(int index){
        clientSocket.remove(index);
        clientNames.remove(index);
        try {
            clientSocket.get(index).close();
        }
        catch (IOException e){
            System.out.println("Error");
            e.printStackTrace();
        }
        size--;
    }

    public void broadcast(String input) {
        addMessage(input);
        for(int i = 0; i < size; i++) {
            try {
                System.out.println("Send: " + input + "to  " + clientSocket.get(i));
                PrintWriter output = new PrintWriter(clientSocket.get(i).getOutputStream(), true);
                output.println(input);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void addMessage(String message){
        if(oldMessages.size() < 100){
            System.out.println("Lower");
            oldMessages.add(message);
        }
        else{
            for(int i = 0; i < 99;i++){
                oldMessages.set(i,oldMessages.get(i + 1));
            }
            oldMessages.set(99,message);
        }
    }

    public void clearChat(){
        oldMessages.clear();
    }

    public void oldMessages(Socket socket){
        try{
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Size of the array is " + oldMessages.size());
            for(int i = 0; i < oldMessages.size();i++){
                output.println(oldMessages.get(i));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
