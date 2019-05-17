package com.example.mobile.apagambarnya;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Soal1Activity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton pilA, pilB, pilC, pilD;
    MediaPlayer mediaPlayer;
    TextView soal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soal1);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences shared = getSharedPreferences("Score", Context.MODE_PRIVATE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        soal = (TextView)findViewById(R.id.pertanyaan);
        pilA = (ImageButton) findViewById(R.id.pilihan_a);
        pilA.setOnClickListener(this);
        pilB = (ImageButton) findViewById(R.id.pilihan_b);
        pilB.setOnClickListener(this);
        pilC = (ImageButton) findViewById(R.id.pilihan_c);
        pilC.setOnClickListener(this);
        pilD = (ImageButton) findViewById(R.id.pilihan_d);
        pilD.setOnClickListener(this);

        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        //To play background sound
        if (sp.getInt("Sound", 0) == 0) {
            mediaPlayer = MediaPlayer.create(this, R.raw.bouncey);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }

    }

    public void onClick(View v) {
        final SharedPreferences shared = getSharedPreferences("Score", Context.MODE_PRIVATE);
        switch (v.getId()) {
            case R.id.pilihan_a:
                Intent move2a = new Intent(Soal1Activity.this, Soal2Activity.class);
                startActivity(move2a);
                Snackbar.make(v, "Salah\t      Jawaban benar : Basket", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.pilihan_b:
                Intent move2b = new Intent(Soal1Activity.this, Soal2Activity.class);
                startActivity(move2b);
                Snackbar.make(v, "          Benar ☺", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.pilihan_c:
                Intent move2c = new Intent(Soal1Activity.this, Soal2Activity.class);
                startActivity(move2c);
                Snackbar.make(v, "Salah\t      Jawaban benar : Basket", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.pilihan_d:
                Intent move2d = new Intent(Soal1Activity.this, Soal2Activity.class);
                startActivity(move2d);
                Snackbar.make(v, "Salah\t      Jawaban benar : Basket", Snackbar.LENGTH_SHORT).show();
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
