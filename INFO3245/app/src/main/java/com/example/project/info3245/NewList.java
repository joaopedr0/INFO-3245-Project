package com.example.project.info3245;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
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
        deadline.setOnClickListener(new View.OnClickListener() {
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
            String date = data.getStringExtra("DATE");
            int priority = data.getIntExtra("PRIORITY", 0);

            if (title != null) {
                EditText editText = findViewById(R.id.editText);
                editText.setText(title);
            }

            if (date != null){
                deadline.setText(date);
            }

            Spinner spinner = findViewById(R.id.spinner);
            spinner.setSelection(priority);

            button.setText(getString(R.string.update));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                EditText editText = findViewById(R.id.editText);
                String title = editText.getText().toString();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
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

    }
}
