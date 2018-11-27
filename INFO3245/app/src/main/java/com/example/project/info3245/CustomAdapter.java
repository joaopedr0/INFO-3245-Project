package com.example.project.info3245;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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
    public void notifyDataSetChanged()
    {
        super.notifyDataSetChanged();

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

            // set the first textview as the title of the task
            if (textView != null) {
                textView.setText(task.getTitle());
                // if the task is completed, change the color of the title to gray
                if(task.isComplete())
                    textView.setTextColor(Color.parseColor("#A9A9A9"));
                else
                    textView.setTextColor(Color.parseColor("#000000"));
            }

            // set the second textview as the deadline
            if (textView2 != null && task.getDate() != null) {
                textView2.setText(task.getDateFormatted(task.getDate()));
            }

            LinearLayout layout = (LinearLayout) v.findViewById(R.id.linearLayout);

            // change the color of the task based on the priority
            switch (task.getPriority()){
                case 0:
                    layout.setBackground(v.getContext().getDrawable(R.drawable.border_red));
                    break;
                case 1:
                    layout.setBackground(v.getContext().getDrawable(R.drawable.border_yellow));
                    break;
                case 2:
                    layout.setBackground(v.getContext().getDrawable(R.drawable.border));
                    break;
            }
        }

        return v;
    }

}
