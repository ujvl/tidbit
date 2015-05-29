package com.thetidbitapp.adap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.thetidbitapp.model.Tidbit;
import com.thetidbitapp.tidbit.R;

import java.util.ArrayList;

/**
 * Created by Ujval on 2/22/15.
 */
public class TidbitAdapter extends ArrayAdapter<Tidbit> {

    private boolean mEnabled;

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
        TextView tvAtt = (TextView) convertView.findViewById(R.id.tidbit_attendees);

        // Populate the data into the template view using the data object
        tvName.setText(tidbit.eventName());
        tvTime.setText(tidbit.datetime());
        tvLoc.setText(tidbit.location());
        tvAtt.setText(tidbit.numberAttending() + " going");

        return convertView;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return mEnabled;
    }

    public void setAllItemsEnabled(boolean enabled) {
        mEnabled = enabled;
    }

}
