package com.example.project.info3245;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;

public class SettingsActivity extends AppCompatActivity {

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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

        // open the database
        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursorNotifications = dbManager.fetchNotificationPreference();

        //check if user has notifications on or off
        boolean notifications = false;
        if(cursorNotifications.getString(cursorNotifications.getColumnIndex("NOTIFICATIONS")).equals("YES"))
            notifications = true;

        // switch ref
        Switch switchNotifications = (Switch)findViewById(R.id.switchNotifications);
        if(notifications)
            switchNotifications.toggle();

        // button ref
        Button buttonDeleteAllTasks = findViewById(R.id.button3);

        // switch event handler
        switchNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    dbManager.updateNotifications(true);
                    Toast.makeText(SettingsActivity.this, "Turned on notifications", Toast.LENGTH_SHORT).show();
                }
                else {
                    dbManager.updateNotifications(false);
                    Toast.makeText(SettingsActivity.this, "Turned off notifications", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // creating the dialog for deleting all tasks
        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        dbManager.open();
                        Cursor cursor = dbManager.fetch();
                        if(cursor.moveToFirst()){
                            do {
                                dbManager.delete(cursor.getInt(cursor.getColumnIndex("ID")));
                            }while(cursor.moveToNext());
                        }
                        Toast.makeText(SettingsActivity.this, "Deleted all tasks", Toast.LENGTH_SHORT).show();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.cancel();
                        break;
                }
            }
        };

        // event handler for the delete all tasks button
        buttonDeleteAllTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setMessage("Are you sure you want to delete ALL tasks?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }
}
