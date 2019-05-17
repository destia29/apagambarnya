package com.example.mobile.apagambarnya;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class KirimSoalActivity extends AppCompatActivity implements View.OnClickListener {
    MediaPlayer mediaPlayer;
    private Button btnKirimSoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kirim_soal);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        //To play background sound
        if (sp.getInt("Sound", 0) == 0) {
            mediaPlayer = MediaPlayer.create(this, R.raw.bouncey);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }

        btnKirimSoal = (Button)findViewById(R.id.btn_kirim_soal);
        btnKirimSoal.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_kirim_soal:
                Intent intentKirimSoal = new Intent(Intent.ACTION_SEND);
                intentKirimSoal.setType("text/plain");
                intentKirimSoal.putExtra(Intent.EXTRA_EMAIL, new String[] {"nadilarizqi7@gmail.com"});
                intentKirimSoal.putExtra(Intent.EXTRA_SUBJECT, "Kirim Soal");
                intentKirimSoal.putExtra(Intent.EXTRA_TEXT, ""+getText(R.string.isi_email));
                startActivity(Intent.createChooser(intentKirimSoal, "Kirim melalui email"));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
