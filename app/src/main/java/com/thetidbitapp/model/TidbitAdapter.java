package com.thetidbitapp.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.thetidbitapp.tidbit.R;

import java.util.ArrayList;

/**
 * Created by Ujval on 2/22/15.
 */
public class TidbitAdapter extends ArrayAdapter<Tidbit> {

    public TidbitAdapter(Context context, ArrayList<Tidbit> events) {
        super(context, 0, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Tidbit tidbit = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tidbit, parent, false);
        }

        // Lookup views for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tidbit_name);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tidbit_datetime);
        TextView tvLoc = (TextView) convertView.findViewById(R.id.tidbit_loc);

        // Populate the data into the template view using the data object
        tvName.setText(tidbit.getName());
        tvTime.setText(tidbit.getDate());
        tvLoc.setText(tidbit.getLocation());

        return convertView;
    }

}
