package com.example.project.info3245;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ArrayList<Task> taskArray;
    private static CustomAdapter adapter;
    private Task clickedTask;
    private int clickedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        taskArray = new ArrayList<Task>();

        final ListView listView = findViewById(R.id.listView);
        adapter = new CustomAdapter(taskArray, R.layout.listview_item, getApplicationContext());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, NewList.class);
                clickedTask = taskArray.get(position);
                clickedPosition = position;
                intent.putExtra("ACTION", "UPDATE");
                intent.putExtra("TITLE", clickedTask.getTitle());
                SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy");
                intent.putExtra("DATE", format.format(clickedTask.getDate()));
                intent.putExtra("PRIORITY", clickedTask.getPriority());
                startActivityForResult(intent,2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String title = data.getStringExtra("TITLE");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                Date date;
                try{
                    date = format.parse(data.getStringExtra("DATE"));
                } catch (ParseException e) {
                    date = null;
                }
                int priority = data.getIntExtra("PRIORITY", 0);
                Task newTask = new Task(title, date, priority);
                taskArray.add(newTask);
                adapter.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == 2) {
            if(resultCode == Activity.RESULT_OK){
                String title = data.getStringExtra("TITLE");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                Date date;
                try{
                    date = format.parse(data.getStringExtra("DATE"));
                } catch (Exception e) {
                    date = null;
                }
                int priority = data.getIntExtra("PRIORITY", 0);
                clickedTask.setTitle(title);
                clickedTask.setDate(date);
                clickedTask.setPriority(priority);
                taskArray.remove(clickedPosition);
                taskArray.add(clickedPosition, clickedTask);
                adapter.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
