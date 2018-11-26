package com.example.project.info3245;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.sqlcipher.database.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private DBManager dbManager;
    ArrayList<Task> taskArray;
    private static CustomAdapter adapter;
    private Task clickedTask;
    private int clickedPosition;
    public final int DELETE = 2;
    boolean notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase.loadLibs(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, NewList.class);
                intent.putExtra("ACTION", "ADD");
                startActivityForResult(intent,1);
            }
        });

        dbManager = new DBManager(this);
        dbManager.open();
        final Cursor cursor = dbManager.fetchByPriority();

        taskArray = new ArrayList<Task>();
        SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy");

        if(cursor.moveToFirst()){
            do {
                Date date;
                Boolean completed = false;
                try {
                    date = format.parse(cursor.getString(cursor.getColumnIndex("DATE")));
                } catch (ParseException e) {
                    date = null;
                }
                if (cursor.getString(cursor.getColumnIndex("COMPLETED")).equals("YES")) {
                    completed = true;
                }
                Task task = new Task(cursor.getInt(cursor.getColumnIndex("ID")),
                        cursor.getString(cursor.getColumnIndex("TITLE")), date,
                        cursor.getInt(cursor.getColumnIndex("PRIORITY")), completed);
                taskArray.add(task);
            }while(cursor.moveToNext());
        }

        Collections.sort(taskArray, new Comparator<Task>() {
            @Override public int compare(Task t1, Task t2) {
                return t2.getPriority()- t1.getPriority();
            }
        });

        final ListView listView = findViewById(R.id.listView);
        adapter = new CustomAdapter(taskArray, R.layout.listview_item, getApplicationContext());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, NewList.class);
                Cursor cursorNotifications = dbManager.fetchNotificationPreference();
                boolean notifications = false;
                if(cursorNotifications.getString(cursorNotifications.getColumnIndex("NOTIFICATIONS")).equals("YES"))
                    notifications = true;
                clickedTask = taskArray.get(position);
                clickedPosition = position;
                intent.putExtra("ACTION", "UPDATE");
                intent.putExtra("TITLE", clickedTask.getTitle());
                SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy");
                intent.putExtra("DATE", format.format(clickedTask.getDate()));
                intent.putExtra("PRIORITY", clickedTask.getPriority());
                intent.putExtra("COMPLETED", clickedTask.isComplete());
                intent.putExtra("NOTIFICATIONS", notifications);
                startActivityForResult(intent,2);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskArray.clear();
        SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy");
        dbManager = new DBManager(this);
        dbManager.open();
        final Cursor cursor = dbManager.fetchByPriority();
        if(cursor.moveToFirst()){
            do {
                Date date;
                Boolean completed = false;
                try {
                    date = format.parse(cursor.getString(cursor.getColumnIndex("DATE")));
                } catch (ParseException e) {
                    date = null;
                }
                if (cursor.getString(cursor.getColumnIndex("COMPLETED")).equals("YES")) {
                    completed = true;
                }
                Task task = new Task(cursor.getInt(cursor.getColumnIndex("ID")),
                        cursor.getString(cursor.getColumnIndex("TITLE")), date,
                        cursor.getInt(cursor.getColumnIndex("PRIORITY")), completed);
                taskArray.add(task);
            }while(cursor.moveToNext());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String title = data.getStringExtra("TITLE");
                SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy");
                Date date;
                try{
                    date = format.parse(data.getStringExtra("DATE"));
                } catch (ParseException e) {
                    date = null;
                }
                int priority = data.getIntExtra("PRIORITY", 0);
                dbManager.insert(title, data.getStringExtra("DATE"), priority);
                Cursor cursor = dbManager.fetch();
                cursor.moveToLast();
                int id = cursor.getInt(cursor.getColumnIndex("ID"));
                Task newTask = new Task(id, title, date, priority, false);
                taskArray.add(newTask);
                Collections.sort(taskArray, new Comparator<Task>() {
                    @Override public int compare(Task t1, Task t2) {
                        return t2.getPriority()- t1.getPriority();
                    }
                });
                adapter.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == 2) {
            if(resultCode == Activity.RESULT_OK){
                String title = data.getStringExtra("TITLE");
                SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy");
                Date date;
                try{
                    date = format.parse(data.getStringExtra("DATE"));
                } catch (Exception e) {
                    date = null;
                }
                int priority = data.getIntExtra("PRIORITY", 0);
                Boolean completed = false;
                if(data.getStringExtra("COMPLETED") != null && data.getStringExtra("COMPLETED").equals("YES"))
                    completed = true;
                dbManager.update(clickedTask.getId(), title, data.getStringExtra("DATE"), priority, completed);
                clickedTask.setTitle(title);
                clickedTask.setDate(date);
                clickedTask.setPriority(priority);
                clickedTask.setCompleted(completed);
                taskArray.remove(clickedPosition);
                taskArray.add(clickedPosition, clickedTask);
                Collections.sort(taskArray, new Comparator<Task>() {
                    @Override public int compare(Task t1, Task t2) {
                        return t2.getPriority()- t1.getPriority();
                    }
                });
                adapter.notifyDataSetChanged();
            }
            if (resultCode == DELETE) {
                dbManager.delete(clickedTask.getId());
                taskArray.remove(clickedPosition);
                adapter.notifyDataSetChanged();
            }
        }
    }

    // menu items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // menu event handlers
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.action_settings:
                // open the settings activity
                // startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            case R.id.task_progress:
                // open the progress activity
                startActivity(new Intent(MainActivity.this, ProgressActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
