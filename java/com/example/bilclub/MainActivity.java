package com.example.bilclub;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.MenuPopupWindow;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ProgressBar pb;
    ImageView v;
    DatabaseReference myRef;
    FirebaseDatabase database;
    Button b;
    static boolean c;
    static String key;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = findViewById(R.id.button4);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, b);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.action_i1:
                                return true;

                            case R.id.action_i2:
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                c = false;
                                finish();
                                return true;

                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("accounts");

        pb = (ProgressBar)findViewById(R.id.progressBar);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("   Bilclub");
        toolbar.setTitleTextColor(Color.BLACK);

        toolbar.setLogo(R.drawable.ic_contact_mail_black_24dp);
        setSupportActionBar(toolbar);
        v = findViewById(R.id.imageView4);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query q = myRef.orderByChild("key").equalTo(key);
                q.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot d: dataSnapshot.getChildren())
                        {
                            Account a = d.getValue(Account.class);
                            if(a.isManager)
                            {
                                startActivity(new Intent(MainActivity.this, ManagerProfileActivity.class));
                            }
                            else {

                            }



                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        // Write a message to the database

    }

}
