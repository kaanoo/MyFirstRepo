package com.example.bilclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class LoginActivity extends AppCompatActivity {

    DatabaseReference myRef;
    StorageReference mStorageRef;
    EditText et1;
    EditText et2;
    Button btn2;
    Button btn;
    boolean c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn2 = (Button)findViewById(R.id.button2);
        btn = (Button)findViewById(R.id.button);
        et1 = (EditText)findViewById(R.id.editText);
        et2 = (EditText)findViewById(R.id.editText6);

        myRef = FirebaseDatabase.getInstance().getReference("accounts");
        mStorageRef = FirebaseStorage.getInstance().getReference("accounts");
        if(MainActivity.c)
        {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }




        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        c = false;
                        for(DataSnapshot d: dataSnapshot.getChildren())
                        {
                            Account a = d.getValue(Account.class);
                            String s1 = String.valueOf(et1.getText());
                            String s2 = String.valueOf(et2.getText());
                            if(s1.equals(a.getUserName()) && s2.equals(a.getPass()))
                            {
                                c = true;
                                MainActivity.key = a.getKey();
                            }
                        }
                        if(c)
                        {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            MainActivity.c = true;
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Wrong data!", Toast.LENGTH_LONG).show();
                            MainActivity.c = false;
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

}
