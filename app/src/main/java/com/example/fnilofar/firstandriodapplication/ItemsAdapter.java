package com.example.fnilofar.firstandriodapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by fnilofar on 8/14/2015.
 */
public class ItemsAdapter extends ArrayAdapter<Items> {

    private static class ViewHolder {
        TextView itemDesc;
        TextView dueDate;
        ImageView priority;
    }

    public ItemsAdapter(Context context, ArrayList<Items> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Items item = getItem(position);

        ViewHolder viewHolder; // view lookup cache stored in tag

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.items, parent, false);
            viewHolder.itemDesc = (TextView) convertView.findViewById(R.id.itemDesc);
            viewHolder.dueDate = (TextView) convertView.findViewById(R.id.dueDateField);
            viewHolder.priority = (ImageView) convertView.findViewById(R.id.priority);
            convertView.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.itemDesc.setText(item.text);
        viewHolder.dueDate.setText(item.dueDate);
        if(item.priority == 1) {
            viewHolder.priority.setImageResource(R.drawable.reddot);
        }
        if(item.priority == 2) {
            viewHolder.priority.setImageResource(R.drawable.bluedot);
        }
        if(item.priority == 3) {
            viewHolder.priority.setImageResource(R.drawable.greendot);
        }
        // Lookup view for data population
       // TextView textDesc = (TextView) convertView.findViewById(R.id.itemDesc);
        //textDesc.setText(item.text);

        return  convertView;




    }
}
