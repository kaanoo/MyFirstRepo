package com.example.bilclub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ManagerProfileActivity extends AppCompatActivity {
    FirebaseStorage storage;
    StorageReference storageReference;
    TextView text;
    DatabaseReference myRef;
    FirebaseDatabase database;
    Account a;
    Button remove;
    Button button5;
    Button button6;
    ImageView view;
    Uri uri;
    String link;
    String url;
    static boolean c;
    RecyclerView rv;
    List<Account> userList;
    RVAdapter adapter;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_profile);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("accounts");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren())
                {
                    Account account = d.getValue(Account.class);
                    userList.add(account);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        adapter = new RVAdapter(this, userList);
        rv.setAdapter(adapter);
        view = findViewById(R.id.imageView9);
        c = false;

        remove = findViewById(R.id.button9);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, 1001);
                    }
                    else
                        pickImageFromGallery();
                }
                else
                    pickImageFromGallery();
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerProfileActivity.this, CreateEventActivity.class));
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove();
            }
        });
        Query q = myRef.orderByChild("key").equalTo(MainActivity.key);
        q.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren())
                {
                    a = d.getValue(Account.class);
                    text = findViewById(R.id.textView7);
                    text.setText("CLUB NAME: " + a.getClubName() + "\nMANAGER: " + a.getUserName());
                    if(!d.getValue(Account.class).getUri().equals("a")) {
                        Picasso.get().load(a.getUri()).into(view);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void pickImageFromGallery()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1000);
    }


    public void remove()
    {
        view = findViewById(R.id.imageView9);
        Map<String, Object> imageData = new HashMap<>();
        String k = MainActivity.key;
        imageData.put("uri", "a");
        myRef.child(k).updateChildren(imageData);
        c = true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1001:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    pickImageFromGallery();
                else
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == -1 && requestCode == 1000)
        {
            view.setImageURI(data.getData());
            final Uri file = data.getData();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            final StorageReference storageRef = storage.getReference().child("images");
            FirebaseUser user = mAuth.getCurrentUser();

            Map<String, Object> imageData = new HashMap<>();
            String k = a.getKey();
            imageData.put("uri", data.getData().toString());
            myRef.child(k).updateChildren(imageData);
            c = true;
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot d: dataSnapshot.getChildren())
                    {
                        Account account = d.getValue(Account.class);
                        userList.add(account);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            adapter = new RVAdapter(this, userList);
            rv.setAdapter(adapter);
        }

        }

}