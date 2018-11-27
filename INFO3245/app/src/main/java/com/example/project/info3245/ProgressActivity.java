package com.example.project.info3245;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import net.sqlcipher.database.SQLiteDatabase;

import java.text.ParseException;
import java.util.Date;

public class ProgressActivity extends AppCompatActivity {

    private DBManager dbManager;
    public int total = 0;
    public int completed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        // head toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

        // opening the database and retrieving all tasks
        SQLiteDatabase.loadLibs(this);
        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        // counts total tasks and completed tasks
        if(cursor.moveToFirst()){
            do {
                if(cursor.getString(cursor.getColumnIndex("COMPLETED")).equals("YES"))
                    completed++;
                total++;
            }while(cursor.moveToNext());
        }

        // updates counter
        TextView taskCounter = findViewById(R.id.textViewTaskCounter);
        taskCounter.setText(String.valueOf(completed) + "/" + String.valueOf(total));

    }
}
