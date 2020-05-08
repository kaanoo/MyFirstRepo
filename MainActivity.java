package com.example.bilkentradio;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

  Button b_play;
  MediaPlayer mediaPlayer;
  Button b_pause;
  boolean prepared = false;
  boolean started = false;


  String stream = "http://stream.radioreklama.bg/radio1rock128";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b_play = (Button) findViewById(R.id.b_play);
        b_play.setEnabled(false);
        b_play.setText("LOADING");


        b_pause = (Button) findViewById(R.id.b_pause);
        b_pause.setEnabled(false);
        b_pause.setText("LOADING");


        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        new PlayerTask().execute(stream);

        b_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                started = false;
            mediaPlayer.pause();
            b_play.setText("PLAY");

            }

        });


        b_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    started = true;
                    mediaPlayer.start();
                    b_play.setText("PAUSE");


            }

        });

    }


    class PlayerTask  extends AsyncTask<String, Void, Boolean > {


        @Override
        protected Boolean doInBackground(String... strings) {
try {
    mediaPlayer.setDataSource(strings[0]);
    mediaPlayer.prepare();
    prepared = true;
} catch (IOException e) {
    e.printStackTrace();
}

     return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            b_play.setEnabled(true);
            b_play.setText("PLAY");



        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(started){
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(started)
            mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(prepared){
            mediaPlayer.release();
        }
    }
}
