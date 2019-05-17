package com.example.mobile.apagambarnya;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton btnSetting, btnShare;
    MediaPlayer mediaPlayer;
    private Button btnCaraMain, btnMulai, btnTentang, btnKirimSoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);

        if (sp.getInt("Sound", 0) == 0) {
            mediaPlayer = MediaPlayer.create(this, R.raw.bouncey);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }

        btnSetting = (ImageButton)findViewById(R.id.btn_setting);
        btnSetting.setOnClickListener(this);
        btnCaraMain = (Button)findViewById(R.id.btn_cara_bermain);
        btnCaraMain.setOnClickListener(this);
        btnMulai = (Button)findViewById(R.id.btn_mulai);
        btnMulai.setOnClickListener(this);
        btnTentang = (Button)findViewById(R.id.btn_tentang);
        btnTentang.setOnClickListener(this);
        btnKirimSoal = (Button)findViewById(R.id.btn_kirim_soal);
        btnKirimSoal.setOnClickListener(this);
        btnShare = (ImageButton) findViewById(R.id.btn_shared);
        btnShare.setOnClickListener(this);

        ImageButton button = findViewById(R.id.btn_ss);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenshoot();
            }
        });
    }

    private void screenshoot() {
        Date date = new Date();
        CharSequence now = android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);
        String filename = Environment.getExternalStorageDirectory() + "/ScreenShooter/" + now + ".jpg";

        View root = getWindow().getDecorView();
        root.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(root.getDrawingCache());
        root.setDrawingCacheEnabled(false);

        File file = new File(filename);
        file.getParentFile().mkdirs();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "image/*");
            startActivity(intent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Klik tombol BACK sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 4000);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.btn_cara_bermain:
                Intent moveCaraMain = new Intent(MainActivity.this, CaraMainActivity.class);
                startActivity(moveCaraMain);
                break;
            case R.id.btn_mulai:
                Intent moveMulai = new Intent(MainActivity.this, LevelActivity.class);
                startActivity(moveMulai);
                break;
            case R.id.btn_tentang:
                Intent moveTentang = new Intent(MainActivity.this, TentangActivity.class);
                startActivity(moveTentang);
                break;
            case R.id.btn_kirim_soal:
                Intent moveKirimSoal = new Intent(MainActivity.this, KirimSoalActivity.class);
                startActivity(moveKirimSoal);
                break;
            case R.id.btn_shared:
                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.setData(Uri.parse("mailto:"));
                intentShare.setType("text/plain");
                intentShare.putExtra(Intent.EXTRA_SUBJECT, "Aplikasi Apa Gambarnya");
                intentShare.putExtra(Intent.EXTRA_TEXT, ""+getText(R.string.deskripsi_bagikan));
                startActivity(Intent.createChooser(intentShare, "Bagikan Game Apa Gambarnya, melalui:"));
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        if (sp.getInt("Sound", 0) == 0)
            mediaPlayer.pause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        if (sp.getInt("Sound", 0) == 0)
            mediaPlayer.start();
    }
}
