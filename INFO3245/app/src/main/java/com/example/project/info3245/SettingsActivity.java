package com.example.project.info3245;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // head toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // switch refs
        Switch switchNotifications = (Switch)findViewById(R.id.switchNotifications);
        Switch switchDeleteAllTasks = (Switch)findViewById(R.id.switchDeleteAllTasks);
        Switch switchResetTaskCounter = (Switch)findViewById(R.id.switchResetTaskCounter);

        // switch event handlers
        switchNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something; isChecked will be true if switch is in On position

                Toast.makeText(SettingsActivity.this, "Turned off annoying notifications", Toast.LENGTH_SHORT).show();
            }
        });

        switchDeleteAllTasks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something; isChecked will be true if switch is in On position
                // other stuff to code: ask for user confirmation (popup "are you sure" menu) + set switch state to "off" after action completed

                Toast.makeText(SettingsActivity.this, "Deleted your mum", Toast.LENGTH_SHORT).show();
            }
        });

        switchResetTaskCounter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something; isChecked will be true if switch is in On position
                // other stuff to code: ask for user confirmation (popup "are you sure" menu) + set switch state to "off" after action completed

                Toast.makeText(SettingsActivity.this, "Resetted your butt HEHEXD", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
