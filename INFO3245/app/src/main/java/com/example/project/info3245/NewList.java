package com.example.project.info3245;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
        toolbar.setNavigationIcon(getDrawable(R.drawable.baseline_arrow_back_white_18dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED,returnIntent);
                finish();
            }
        });

        Button button = findViewById(R.id.button_add);
        array = new ArrayList<String>();

        Intent data = getIntent();
        if(data.getStringExtra("ACTION").equals("UPDATE")) {
            String title = data.getStringExtra("TITLE");
            ArrayList<String> items = data.getStringArrayListExtra("ITEMS");

            if (title != null) {
                EditText editText = findViewById(R.id.editText);
                editText.setText(title);
            }

            if (items != null)
                array.addAll(items);

            button.setText(getString(R.string.update));
        }

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
