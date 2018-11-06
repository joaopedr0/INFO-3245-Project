package com.example.project.info3245;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<List> {

    private int resourceLayout;
    private ArrayList<List> list;
    Context mContext;

    public CustomAdapter(ArrayList<List> list, int resource, Context context) {
        super(context, resource, list);
        this.resourceLayout = resource;
        this.list = list;
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

        List list = getItem(position);

        if (list != null) {
            TextView textView = (TextView) v.findViewById(R.id.textView);
            TextView textView2 = (TextView) v.findViewById(R.id.textView2);

            if (textView != null) {
                textView.setText(list.getTitle());
            }

            if (textView2 != null) {
                textView2.setText(list.getItems().get(0));
            }
        }

        return v;
    }

}
