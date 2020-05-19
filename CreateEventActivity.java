package com.example.bilclub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class CreateEventActivity extends AppCompatActivity {
    Button choose, create;
    ImageView view;
    EditText et10, et2, et11, et12;
    String s1, s2, s3;
    Date d;
    Time t;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseStorage storage;
    StorageReference ref;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        storage = FirebaseStorage.getInstance();
        ref = storage.getReference();

        et10 = findViewById(R.id.editText10);
        et2 = findViewById(R.id.editText2);
        et11 = findViewById(R.id.editText11);
        et12 = findViewById(R.id.editText12);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("events");
        create = findViewById(R.id.button8);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = et2.getText().toString();
                s1 = String.valueOf(et10.getText());
                s2 = et11.getText().toString();
                s3 = String.valueOf(et12.getText());
                String link = UUID.randomUUID().toString();

                Event event = new Event(s1, string, s2, s3, link);
                myRef.push().setValue(event);
                StorageReference sref = ref.child("images/" + link);
                sref.putFile(uri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(CreateEventActivity.this, "Uploaded", Toast.LENGTH_LONG).show();
                            }
                        });
                startActivity(new Intent(CreateEventActivity.this, ManagerProfileActivity.class));

            }
        });
        view = findViewById(R.id.imageView11);
        choose = findViewById(R.id.button7);
        choose.setOnClickListener(new View.OnClickListener() {
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
    }

    public void pickImageFromGallery()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1000);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == -1 && requestCode == 1000)
        {
            view.setImageURI(data.getData());
            uri = data.getData();
        }
    }
}
