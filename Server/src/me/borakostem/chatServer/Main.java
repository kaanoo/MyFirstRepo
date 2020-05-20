package me.borakostem.chatServer;

import me.borakostem.chatServer.threads.ClientCounter;
import me.borakostem.chatServer.threads.ServerUI;
import me.borakostem.chatServer.threads.Threads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Main {
    //Properties
    final static int PORT = 6666;
    private static ClientCounter clients = new ClientCounter();
    private static ServerUI ui = new ServerUI();
    static HashMap <String,String> loginInfo = new HashMap<String, String>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    Date date = new Date();
    public static void main(String[] args) {
        ServerUI ui = new ServerUI();
        loginInfo.put("BoraKostem","sefermelek");
        loginInfo.put("AlperKandemir69","12345678");
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            while(true) {
                System.out.println("Waiting Client");
                ui.addText("\n"+"Waiting Client");
                Socket socket = serverSocket.accept();
                System.out.println("Server: Client Connected");
                ui.addText("\n"+"Server: Client Connected");
                Threads thread = new Threads(socket);
                thread.start();
                clients.add(socket);
            }
        }
        catch(IOException e){
            System.out.println("Server exception: " + e.getMessage());
            ui.addText("\n"+"Server exception: " + e.getMessage());
        }
    }

    public void clearChat(){
        clients.clearChat();
    }
    public boolean isUser(String user){
        if(loginInfo.containsKey(user))
            return true;
        return false;
    }

    public boolean passCorrect(String user,String password){
        if(loginInfo.get(user).equals(password))
            return true;
        return false;
    }

    public void broadcast(String input,String name,boolean isAdmin){
        if(isAdmin){
            clients.broadcast(dateFormat.format(date) + "| (Admin) " + name + ": " + input);
            ui.addText("\n"+"Message broadcast: " + dateFormat.format(date) + "| (Admin)" + name + ": " + input);
        }
        else{
            clients.broadcast(dateFormat.format(date) + "| " + name + ": " + input);
            ui.addText("\n"+"Message broadcast: " + dateFormat.format(date) + "| " + name + ": " + input);
        }
    }

    public void broadcastMessage(String input){
        clients.broadcast("BROADCAST: " + input);
        ui.addText("BROADCAST: " + input);
    }

    public void createAdmin(String user,String pass){
        loginInfo.put(user,pass);
    }
    public void remove(Socket socket){
        clients.remove(socket);
    }

    public void removeIndex(int index){
        clients.removeInt(index);
    }

    public void clientAddName(String name){
        clients.addName(name);
    }

    public ArrayList<Socket> getClients(){
        return clients.getClients();
    }

    public ArrayList<String> getNames(){
        return clients.getNames();
    }
}
