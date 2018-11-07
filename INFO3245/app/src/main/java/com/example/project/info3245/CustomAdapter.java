package com.example.project.info3245;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Task> {

    private int resourceLayout;
    private ArrayList<Task> task;
    Context mContext;

    public CustomAdapter(ArrayList<Task> task, int resource, Context context) {
        super(context, resource, task);
        this.resourceLayout = resource;
        this.task = task;
        this.mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        Task task = getItem(position);

        if (task != null) {
            TextView textView = (TextView) v.findViewById(R.id.textView);
            TextView textView2 = (TextView) v.findViewById(R.id.textView2);

            if (textView != null) {
                textView.setText(task.getTitle());
            }

            if (textView2 != null && task.getDate() != null) {
                textView2.setText(task.getDateFormatted(task.getDate()));
            }
        }

        return v;
    }

}
