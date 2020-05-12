package com.example.bilclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class SignupActivity extends AppCompatActivity {

    DatabaseReference myRef;
    FirebaseDatabase database;
    Button btn;
    Switch aSwitch;
    EditText et3;
    EditText et4;
    EditText et5;
    EditText et7;
    EditText et8;
    EditText et9;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("accounts");
        textView = findViewById(R.id.textView2);
        textView.setVisibility(View.GONE);

        et5 = (EditText)findViewById(R.id.editText5);
        et5.setVisibility(View.GONE);
        et3 = (EditText) findViewById(R.id.editText3);
        et4 = (EditText)findViewById(R.id.editText4);
        et7 = (EditText)findViewById(R.id.editText7);
        et8 = (EditText)findViewById(R.id.editText8);
        et9 = (EditText)findViewById(R.id.editText9);
        aSwitch = findViewById(R.id.switch1);
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!aSwitch.isChecked())
                {
                    textView.setVisibility(View.GONE);
                    et5.setVisibility(View.GONE);
                }
                else
                {
                    textView.setVisibility(View.VISIBLE);
                    et5.setVisibility(View.VISIBLE);
                }
            }
        });
        btn = (Button)findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s1 = String.valueOf(et3.getText());
                String s2 = String.valueOf(et4.getText());
                String s3 = String.valueOf(et7.getText());
                String s4 = String.valueOf(et8.getText());
                String s5 = String.valueOf(et9.getText());
                Account a = new Account(s1, s2, s3, s4, s5, "", aSwitch.isChecked(), null, String.valueOf(et5.getText()));
                myRef.push().setValue(a);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot d: dataSnapshot.getChildren())
                        {
                            Account a = d.getValue(Account.class);
                            a.setKey(d.getKey());
                            Map<String, Object> upd = new HashMap<>();
                            upd.put("key", d.getKey());
                            myRef.child(d.getKey()).updateChildren(upd);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
