    package com.example.firebaseupload;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;

    import android.app.ProgressDialog;
    import android.content.ContentResolver;
    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.os.Handler;
    import android.util.Log;
    import android.view.View;
    import android.webkit.MimeTypeMap;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.ProgressBar;
    import android.widget.TextView;
    import android.widget.Toast;


    import com.google.android.gms.tasks.Continuation;
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.OnProgressListener;
    import com.google.firebase.storage.StorageReference;
    import com.google.firebase.storage.StorageTask;
    import com.google.firebase.storage.UploadTask;
    import com.squareup.picasso.Picasso;

    import java.util.UUID;

    public class MainActivity extends AppCompatActivity {
        // variables
        private static final int PICK_IMAGE_REQUEST = 1;
        private Button mButtonChooseImage;
        private Button mButtonUpload;
        private TextView mTextViewShowUploads;
        private EditText mEditTextFileName;
        private ImageView mImageView;
        private ProgressBar mProgressBar;
        private Uri mImageUri;

        private StorageReference mStorageRef;
        private DatabaseReference mDatabaseRef;
        private StorageTask mUploadTask;
        private static final String TAG = "MainActivity";
        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mButtonChooseImage = findViewById(R.id.button_choose_image);
            mButtonUpload = findViewById(R.id.button_upload);
            mTextViewShowUploads = findViewById(R.id.text_view_show_uploads);
            mEditTextFileName = findViewById(R.id.edit_text_file_name);
            mImageView = findViewById(R.id.image_view);
            mProgressBar = findViewById(R.id.progress_bar);

            mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

            mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openFileChooser();
                }
            });

            mButtonUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mUploadTask != null && mUploadTask.isInProgress()) {
                        Toast.makeText(MainActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                    } else {
                        uploadFile();
                    }
                }
            });

            mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  openImagesActivity();



                }
            });
        }
        private void openFileChooser()
        {
            Intent intent = new Intent();
            intent.setType("image/*"); // for only see images
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,PICK_IMAGE_REQUEST);


        }

        //this method called when the file is picked.
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
        {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData() !=null)
            {
                mImageUri = data.getData();

                Picasso.get().load(mImageUri).into(mImageView);

            }
        }
        private String getFileExtension(Uri uri)
        {
            ContentResolver cR = getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            return mime.getExtensionFromMimeType(cR.getType(uri));

        }
        private void uploadFile()
        {

            if (mImageUri != null) {
                final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                        + "." + getFileExtension(mImageUri));

                fileReference.putFile(mImageUri).continueWithTask(
                        new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw task.getException(); }
                                return fileReference.getDownloadUrl();
                            } })
                        .addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) { Uri downloadUri = task.getResult();
                                    Upload upload = new Upload(mEditTextFileName.getText().toString().trim(), downloadUri.toString());
                                    mDatabaseRef.push().setValue(upload);
                                    Toast.makeText(MainActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                                }
                                else { Toast.makeText(MainActivity.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            } else {
                Toast.makeText(this, "No file selected", Toast.LENGTH_LONG).show();
            }
        }




       private void openImagesActivity()
        {
           Intent intent = new Intent(this, ImagesActivity.class);
           startActivity(intent);
           finish();
        }
    }



