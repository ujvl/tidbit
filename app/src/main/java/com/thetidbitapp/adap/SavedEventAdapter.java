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
public class SavedEventAdapter extends AbstractEventAdapter<SavedEventAdapter.SavedEventViewHolder> {

	private Context mContext;

	public SavedEventAdapter(List<Event> events, Context context) {
		super(events, context);
		mContext = context;
	}


	@Override
	protected SavedEventViewHolder getViewHolder(View itemView) {
		return new SavedEventViewHolder(itemView);
	}

	public class SavedEventViewHolder extends AbstractEventAdapter.EventViewHolder {

		public SavedEventViewHolder(View itemView) {
			super(itemView);
			btnOne.setTextColor(mContext.getResources().getColor(R.color.green));
			btnOne.setTypeface(null, Typeface.BOLD);
		}

		@Override
		protected void onFirstBtnClick() { /* Do nothing */ }

		@Override
		protected void onSecondBtnClick() {
			super.onSecondBtnClick();
		}
	}
}