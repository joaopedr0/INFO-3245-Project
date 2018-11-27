package com.example.project.info3245;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewList extends AppCompatActivity {

    ArrayList<String> array;
    Calendar calendar;
    Date date;
    TextView deadline;
    DatePickerDialog.OnDateSetListener datePicker;
    SimpleDateFormat dateFormat;
    public final int DELETE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getDrawable(R.drawable.baseline_arrow_back_white_18dp));  // back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED,returnIntent);
                finish();
            }
        });

        Button buttonUpdate = findViewById(R.id.button_add);

        Button button_delete = findViewById(R.id.button);
        Button button_complete = findViewById(R.id.button2);

        array = new ArrayList<String>();
        deadline = findViewById(R.id.textView5);
        dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        calendar = Calendar.getInstance();
        deadline.setText(dateFormat.format(calendar.getTime()));
        datePicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                date = calendar.getTime();
                deadline.setText(dateFormat.format(date));
            }

        };
        deadline.setOnClickListener(new View.OnClickListener() {    // opens DatePicker control
            @Override
            public void onClick(View v) {
                new DatePickerDialog(NewList.this, datePicker, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Intent data = getIntent();
        if(data.getStringExtra("ACTION").equals("UPDATE")) {
            String title = data.getStringExtra("TITLE");
            String dateString = data.getStringExtra("DATE");
            int priority = data.getIntExtra("PRIORITY", 0);

            if (title != null) {
                EditText editText = findViewById(R.id.editText);
                editText.setText(title);
            }

            Date date = Calendar.getInstance().getTime();
            if (dateString != null){
                deadline.setText(dateString);
                SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy");
                try{
                    date = format.parse(data.getStringExtra("DATE"));
                    calendar.setTime(date);
                } catch (ParseException e) {
                    date = null;
                }
            }

            Spinner spinner = findViewById(R.id.spinner);
            spinner.setSelection(priority);

            buttonUpdate.setText(getString(R.string.update));

            button_delete.setVisibility(View.VISIBLE);

            boolean completed = data.getBooleanExtra("COMPLETED", false);
            if(!completed) {
                button_complete.setVisibility(View.VISIBLE);

                if(data.getBooleanExtra("NOTIFICATIONS", false)) {
                    Calendar now = Calendar.getInstance();
                    now.set(Calendar.HOUR_OF_DAY, 0);
                    now.set(Calendar.MINUTE, 0);
                    now.set(Calendar.SECOND, 0);
                    now.set(Calendar.MILLISECOND, 0);
                    Calendar taskDue = Calendar.getInstance();
                    taskDue.setTime(date);
                    long diff = now.getTimeInMillis() - taskDue.getTimeInMillis(); //result in millis
                    long days = (diff / (24 * 60 * 60 * 1000));
                    Toast toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
                    if (days == 0) {
                        String message = "This task is due today!";
                        toast.setText(message);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else if (days < 0) {
                        days = days * -1;
                        String message;
                        if (days == 1)
                            message = "This task is due tomorrow!";
                        else
                            message = "This task is due in " + days + " days.";
                        toast.setText(message);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        String message;
                        if (days == 1)
                            message = "This task was due yesterday.";
                        else
                            message = "This task was due " + days + " days ago.";
                        toast.setText(message);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
            }
        }

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                EditText editText = findViewById(R.id.editText);
                String title = editText.getText().toString();
                SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy");
                date = calendar.getTime();
                String deadline = format.format(date);
                Spinner spinner = findViewById(R.id.spinner);
                int priority = spinner.getSelectedItemPosition();

                returnIntent.putExtra("TITLE", title);
                returnIntent.putExtra("DATE", deadline);
                returnIntent.putExtra("PRIORITY", priority);

                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent returnIntent = new Intent();
                        setResult(DELETE,returnIntent);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.cancel();
                        break;
                }
            }
        };

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewList.this);
                builder.setMessage("Are you sure you want to delete this task?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });


        button_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                EditText editText = findViewById(R.id.editText);
                String title = editText.getText().toString();
                SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy");
                date = calendar.getTime();
                String deadline = format.format(date);
                Spinner spinner = findViewById(R.id.spinner);
                int priority = spinner.getSelectedItemPosition();

                returnIntent.putExtra("TITLE", title);
                returnIntent.putExtra("DATE", deadline);
                returnIntent.putExtra("PRIORITY", priority);
                returnIntent.putExtra("COMPLETED", "YES");

                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

    }
}
