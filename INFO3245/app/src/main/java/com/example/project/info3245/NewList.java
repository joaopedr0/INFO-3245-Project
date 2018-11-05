package com.example.project.info3245;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewList extends AppCompatActivity {

    ArrayList<String> array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        array = new ArrayList<String>();

        final ListView listView = findViewById(R.id.listView2);
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.to_do_list_item, R.id.checkBox, array);
        listView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                array.add("item");
                adapter.notifyDataSetChanged();
            }
        });

        Button button = findViewById(R.id.button_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                String title;
                EditText editText = findViewById(R.id.editText);
                title = editText.getText().toString();
                returnIntent.putExtra("TITLE", title);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

    }
}
