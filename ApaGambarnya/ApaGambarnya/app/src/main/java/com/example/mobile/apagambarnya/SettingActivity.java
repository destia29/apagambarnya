package com.example.mobile.apagambarnya;

import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SharedPreferences sharedPreferences = getSharedPreferences("Score", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final Button sound = (Button)findViewById(R.id.play_sound);

        Button reset = (Button)findViewById(R.id.reset);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (sharedPreferences.getInt("Sound", 0) == 0) {
            sound.setText("Matikan Musik");
            mediaPlayer = MediaPlayer.create(this, R.raw.bouncey);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        } else
            sound.setText("Hidupkan Musik");

        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound.getText().equals("Hidupkan Musik")) {
                    editor.putInt("Sound", 0).commit();
                    sound.setText("Matikan Musik");
                    mediaPlayer = MediaPlayer.create(SettingActivity.this, R.raw.bouncey);
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                } else if (sound.getText().equals("Matikan Musik")) {
                    editor.putInt("Sound", 1).commit();
                    sound.setText("Hidupkan Musik");
                    mediaPlayer.stop();
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("Level 1", 0);
                editor.putInt("Level 2", 0);
                editor.putInt("Level 3", 0);
                editor.putInt("Level 4", 0);
                editor.putInt("Level 5", 0);
                editor.commit();
                Snackbar.make(v, "Nilai Tertinggi berhasil direset", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        if (sp.getInt("Sound", 0) == 0)
            mediaPlayer.pause();
    }

    protected void onRestart() {
        super.onRestart();
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        if (sp.getInt("Sound", 0) == 0)
            mediaPlayer.start();
    }
}
