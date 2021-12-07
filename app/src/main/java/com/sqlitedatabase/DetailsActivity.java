package com.sqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sqlitedatabase.database.DatabaseHelper;


public class DetailsActivity extends AppCompatActivity {

    private DatabaseHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView id_tv = findViewById(R.id.id_tv);
        TextView word_tv = findViewById(R.id.word_tv);
        ImageView bookmark = findViewById(R.id.book_mark);

        myDbHelper = new DatabaseHelper(this);

        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        Intent mIntent = getIntent();

        String word = mIntent.getStringExtra("word");
        String word_id = mIntent.getStringExtra("id");


        word_tv.setText(word);
        id_tv.setText(word_id);

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDbHelper.bookmarkAdd(word);

                Toast.makeText(DetailsActivity.this, "Bookmark added " + word, Toast.LENGTH_SHORT).show();

            }
        });


    }
}