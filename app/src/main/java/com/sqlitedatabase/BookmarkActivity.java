package com.sqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.SQLException;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sqlitedatabase.Model;
import com.sqlitedatabase.R;
import com.sqlitedatabase.database.DatabaseHelper;


import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity {
    private DatabaseHelper myDbHelper;
    private ArrayList<Model> modelData;
    private ArrayList<String> vocabularyData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        myDbHelper = new DatabaseHelper(this);

        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        modelData = myDbHelper.retriveBookmark();

        for (Model model : modelData) {
            vocabularyData.add(model.getWord());
        }

        ListView listView = findViewById(R.id.list_item);

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vocabularyData);

        listView.setAdapter(itemsAdapter);
    }
}