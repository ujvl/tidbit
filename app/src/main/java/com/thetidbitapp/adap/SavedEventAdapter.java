package com.thetidbitapp.adap;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

import com.thetidbitapp.model.Event;
import com.thetidbitapp.tidbit.R;

import java.util.List;

/**
 * Created by Ujval on 6/9/15
 */
public class SavedEventAdapter extends BaseEventAdapter<SavedEventAdapter.SavedEventViewHolder> {

    public SavedEventAdapter(List<Event> events, Context context) {
        super(events, context);
    }


    @Override
    protected SavedEventViewHolder createViewHolder(View itemView) {
        return new SavedEventViewHolder(itemView);
    }

    public class SavedEventViewHolder extends BaseEventAdapter.EventViewHolder {

        public SavedEventViewHolder(View itemView) {
            super(itemView);
            btnOne.setTextColor(mContext.getResources().getColor(R.color.theme_light_gray));
            btnOne.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }

        @Override
        protected void onFirstBtnClick() { /* Do nothing */ }

        @Override
        protected void onSecondBtnClick() {
            if (checkInternetConnectivity()) {
                removeLeftwards(itemView, getAdapterPosition());
            }
        }
    }
}