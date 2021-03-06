package com.thetidbitapp.adap;

import android.content.Context;
import android.view.View;

import com.thetidbitapp.model.Event;

import java.util.List;

/**
 * Created by Ujval on 6/9/15
 */
public class DefaultEventAdapter extends BaseEventAdapter<DefaultEventAdapter.DefaultEventViewHolder> {

    public DefaultEventAdapter(List<Event> events, Context c) {
        super(events, c);
    }

    @Override
    protected DefaultEventViewHolder createViewHolder(View v) {
        return new DefaultEventViewHolder(v);
    }

    public class DefaultEventViewHolder extends BaseEventAdapter.EventViewHolder {

        public DefaultEventViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onFirstBtnClick() {
            if (checkInternetConnectivity()) {
                removeRightwards(itemView, getAdapterPosition());
            }
        }

        @Override
        protected void onSecondBtnClick() {
            if (checkInternetConnectivity()) {
                removeLeftwards(itemView, getAdapterPosition());
            }
        }

    }

}
