package com.sqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sqlitedatabase.database.DatabaseHelper;

import java.util.ArrayList;

public class WordsActivity extends AppCompatActivity {

    private DatabaseHelper myDbHelper;
    private WordsAdapter adapter;
    private ArrayList<Model> modelData;
    private ArrayList<String> vocabularyData = new ArrayList<>();
    private ArrayList<String> vocabularyid = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        myDbHelper = new DatabaseHelper(this);

        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        modelData = myDbHelper.getAllcategoryAndId();

        for (Model model : modelData) {
            vocabularyData.add(model.getWord());
            vocabularyid.add(String.valueOf(model.getId()));
        }

        ListView listView = findViewById(R.id.list_item);
        adapter = new WordsAdapter(this, vocabularyData, vocabularyid);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String word = vocabularyData.get(position);
                String word_id = vocabularyid.get(position);
                Intent intent = new Intent(WordsActivity.this, DetailsActivity.class);
                intent.putExtra("word", word);
                intent.putExtra("id", word_id);
                Toast.makeText(WordsActivity.this, " " + word_id, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });


    }
}