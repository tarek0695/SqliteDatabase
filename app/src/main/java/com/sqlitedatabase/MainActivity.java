package com.sqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sqlitedatabase.database.DatabaseHelper;
import com.sqlitedatabase.database.LoadDatabaseAsync;

public class MainActivity extends AppCompatActivity {

    static DatabaseHelper myDbHelper;
    static boolean databaseOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDbHelper = new DatabaseHelper(this);

        if (myDbHelper.checkDataBase()) {
            openDatabase();

        } else {
            LoadDatabaseAsync task = new LoadDatabaseAsync(MainActivity.this);
            task.execute();
        }

        TextView wordList = findViewById(R.id.go_to_wordlist);
        TextView bookmark = findViewById(R.id.go_to_bookmark);

        wordList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WordsActivity.class);
                startActivity(intent);
            }
        });

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookmarkActivity.class);
                startActivity(intent);
            }
        });

    }

    public static void openDatabase() {
        try {
            myDbHelper.openDataBase();
            databaseOpened = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}