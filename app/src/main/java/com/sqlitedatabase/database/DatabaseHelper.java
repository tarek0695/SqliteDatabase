package com.sqlitedatabase.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.sqlitedatabase.Model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    private String DB_PATH = null;
    private static String DB_NAME = "eng_dictionary.db";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
        Log.e("Path 1", DB_PATH);

    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (!dbExist) {

            this.getReadableDatabase();
            try {
                myContext.deleteDatabase(DB_NAME);
                copyDataBase();

            } catch (IOException e) {
                throw new Error("Error copying database");
            }

        }

    }

    public boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            //
        }
        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }


    private void copyDataBase() throws IOException {

        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
        Log.i("copyDataBase", "Database copied");

    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }


    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            this.getReadableDatabase();
            myContext.deleteDatabase(DB_NAME);
            copyDataBase();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    //get all vocabulary category
    public ArrayList<Model> getAllcategoryAndId() throws SQLException {
        ArrayList<Model> data = new ArrayList<>();
        String query = "SELECT _id, en_word from words";

        Cursor row = myDataBase.rawQuery(query, null);
        if (row != null) {
            row.moveToFirst();
            while (!row.isAfterLast()) {
                int word_id = row.getInt(row.getColumnIndexOrThrow("_id"));
                String category = row.getString(row.getColumnIndexOrThrow("en_word"));
                data.add(new Model(word_id, category));
                // do what ever you want here
                row.moveToNext();
            }

            row.close();
        } else
            data = null;

        return data;
    }

    public void bookmarkAdd(String text) {
        myDataBase.execSQL("INSERT INTO bookmark(word) VALUES(UPPER('" + text + "'))");

    }

    //bookmark retriving
    public ArrayList<Model> retriveBookmark() throws SQLException {
        ArrayList<Model> data = new ArrayList<>();
        String query = "SELECT word FROM bookmark";
        Cursor row = myDataBase.rawQuery(query, null);
        if (row != null) {
            row.moveToFirst();
            while (!row.isAfterLast()) {
                String word = row.getString(row.getColumnIndexOrThrow("word"));
                data.add(new Model(word));
                // do what ever you want here
                row.moveToNext();
            }

            row.close();
        } else
            data = null;

        return data;
    }
}

