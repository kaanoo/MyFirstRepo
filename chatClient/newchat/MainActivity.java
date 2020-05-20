package me.borakostem.newchat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import me.borakostem.newchat.chatClient.Client;
import me.borakostem.newchat.chatClient.ServerListener;

public class MainActivity extends AppCompatActivity {
    //Properties
    static String user = "Bora Köstem";
    static final int PORT = 6666;
    public static Handler handler;
    boolean started = false;
    Client client1;
    EditText messageInput;
    Button sendTxt;
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        messageInput = findViewById(R.id.message_input);
        sendTxt = findViewById(R.id.send_text);

        message = findViewById(R.id.message_view);
        message.setMovementMethod(new ScrollingMovementMethod());
        message.setText("Please Enter Your Name:");

        client1 = new Client(user,PORT);
        client1.execute();
        buttonListener();
        handler = new Handler() {
            public void handleMessage(Message msg) {
                String messageText = (String) msg.obj;
                message.append("\n" + messageText);
            }
        };
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        client1.terminate();
    }

    @Override
    protected void onStop(){
        super.onStop();
        client1.terminate();
    }


    public void buttonListener(){
        sendTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageInput.getText().toString();
                messageInput.setText("");
                if(!message.matches("")) {
                    client1.sendMsg(message);
                    if (started == false) {
                        new ServerListener(client1.getSocket()).start();
                        //started = true;
                    }
                }
            }
        });
    }

    public String getUser(){
        return user;
    }

    public void setUser(String user){
        this.user = user;
    }


}
