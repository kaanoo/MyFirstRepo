package me.borakostem.chatServer.threads;


import me.borakostem.chatServer.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Threads extends Thread {
    //Properties
    private Socket socket;
    private Main send =new Main();
    private String name;
    private int clientIndex;
    private boolean isAdmin = false;
    //Constructor
    public Threads(Socket socket) {
        this.socket = socket;
    }

    //Input - Output Loop
    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Please enter your name!");
            name = input.readLine();
            send.clientAddName(name);
            System.out.println("Clients name is " + name + "!");
            while(true) {
                String echoString = input.readLine();
                if(echoString != null){
                    if(!isAdmin){
                        if(echoString.equals("Server.login")){
                            output.println("SERVER: Please enter your username!");
                            String userName = input.readLine();
                            if(send.isUser(userName)){
                                output.println("SERVER: Please enter your password!");
                                String pass = input.readLine();
                                if(send.passCorrect(userName,pass)){
                                    output.println("SERVER: You logged in as an admin!");
                                    isAdmin = true;
                                }
                                else{
                                    output.println("SERVER: Wrong password!");
                                }
                            }
                            else{
                                output.println("SERVER: Incorrect user!");
                            }
                        }
                        else{
                            send.broadcast(echoString,name,isAdmin);
                        }
                    }
                    else{
                        if(echoString.equals("Server.clear")){
                            send.clearChat();
                            output.println("SERVER: Chat cleared!");
                        }
                        else if(echoString.equals("Server.clients")){
                            ArrayList<Socket> clientList = send.getClients();
                            for(int i = 0;i < clientList.size();i++){
                                output.println(i + ": " + clientList.get(i));
                            }
                        }
                        else if(echoString.equals("Server.names")){
                            ArrayList<String> clientNames = send.getNames();
                            for(int i = 0;i < clientNames.size();i++){
                                output.println(i + ": " + clientNames.get(i));
                            }
                        }
                        else if(echoString.equals("Server.kick")){
                            output.println("SERVER: Please enter the index of the client:");
                            String index = input.readLine();
                            if(Integer.parseInt(index) < send.getClients().size()){
                                send.removeIndex(Integer.parseInt(index));
                                output.println("SERVER: Client removed successfully");
                            }
                            else{
                                output.println("SERVER: Error index is bigger than clients number!");
                            }
                        }
                        else if(echoString.equals("Server.broadcast")){
                            output.println("SERVER: Please enter a message to broadcast");
                            String broadcast = input.readLine();
                            send.broadcastMessage(broadcast);
                        }
                        else if(echoString.equals("Server.login")){
                            output.println("SERVER: You already logged in!");
                        }
                        else if(echoString.equals("Server.commands")){
                            output.println("Server Command List \n - Server.broadcast = Broadcast a message \n - Server.clear = Clear chat history \n - Server.clients = List Clients \n - Server.names = List Client Names \n - Server.addAdmin = create new admin account");
                        }
                        else if(echoString.equals("Server.addAdmin")){
                            output.println("SERVER: Please enter a username for the admin account");
                            String username = input.readLine();
                            output.println("SERVER: Please enter a password for the admin account");
                            String pass = input.readLine();
                            send.createAdmin(username,pass);
                            output.println("SERVER: Successfully created new admin account with name " + username + " and password " + pass);
                        }
                        else{
                            send.broadcast(echoString,name,isAdmin);
                        }
                    }
                }
                else{
                    System.out.println("INPUT CANNOT BE NULL");
                    socket.close();
                    send.remove(socket);
                }
            }

        } catch(IOException e) {
             System.out.println("Thread exception: " + e.getMessage());
             send.remove(socket);
        }

    }
}
