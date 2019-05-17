package com.example.mobile.apagambarnya;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

/**
 * Created by ACER on 12/7/2018.
 */

public class level1 extends SQLiteOpenHelper {
    private static final String Database_path = "/data/data/com.example.mobile.apagambarnya/databases/";
    private static final String Database_name = "level1.db";
    private static final String Table_name = "level1";
    private static final String uid = "id";
    private static final String Pertanyaan = "Pertanyaan";
    private static final String Jawaban = "Jawaban";
    private static final int version = 1;
    public SQLiteDatabase sqlite;
    private Context context;

    public level1(Context context) {
        super(context, Database_name, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createDatabase() {
        createDB();
    }

    private void createDB() {
        boolean dbexist = DBexists();
        if (!dbexist){
            this.getReadableDatabase();
            copyDBfromResource();
        }
    }

    private void copyDBfromResource() {

        InputStream is;
        OutputStream os;
        String filePath = Database_path + Database_name;
        try {
            is = context.getAssets().open(Database_name);
            os = new FileOutputStream(filePath);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
            is.close();
            os.close();

        } catch (IOException e) {
            throw new Error("Problem copying database file:");
        }
    }

    public void openDatabase() throws SQLException {

        String myPath = Database_path + Database_name;
        sqlite = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private boolean DBexists() {
        SQLiteDatabase db = null;
        try {
            String databasePath = Database_path + Database_name;
            db = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);
            db.setLocale(Locale.getDefault());
            db.setVersion(1);
            db.setLockingEnabled(true);
        } catch (SQLException e) {
            Log.e("Sqlite", "Database not found");
        }
        if (db != null)
            db.close();
        return db != null ? true : false;

    }

    public String readUID(int i) {
        String Ans = "";
        Cursor c = sqlite.rawQuery("SELECT " + uid + " FROM " + Table_name + " WHERE " + uid + " = " + i + "", null);
        if (c.moveToFirst())
            Ans = c.getString(0);
        else
            Ans = "";
        return Ans;
    }

    public String readPertanyaan(int i) {
        String Ans = "";
        Cursor c = sqlite.rawQuery("SELECT " + Pertanyaan + " FROM " + Table_name + " WHERE " + uid + " = " + i + "", null);
        if (c.moveToFirst())
            Ans = c.getString(0);
        else
            Ans = "";
        return Ans;
    }

    public String readJawaban(int i) {

        String Ans = "";
        Cursor c = sqlite.rawQuery("SELECT " + Jawaban + " FROM " + Table_name + " WHERE " + uid + " = " + i + "", null);
        if (c.moveToFirst())
            Ans = c.getString(0);
        else
            Ans = "";
        return Ans;
    }
}
