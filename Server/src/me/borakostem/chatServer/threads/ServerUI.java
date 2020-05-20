package me.borakostem.chatServer.threads;

import javax.swing.*;
import java.awt.*;

public class ServerUI{
    JTextArea text;
    public ServerUI (){
        JFrame frame = new JFrame("Server");
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        text = new JTextArea();
        text.append("Initializing...");
        panel.add(text);
        frame.add(panel);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void addText(String text){
        this.text.append(text);
    }

}
