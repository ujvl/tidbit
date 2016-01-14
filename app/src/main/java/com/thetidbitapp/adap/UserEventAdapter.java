package com.thetidbitapp.adap;

import android.content.Context;
import android.view.View;

import com.thetidbitapp.model.Event;
import com.thetidbitapp.tidbit.R;

import java.util.List;

/**
 * Created by Ujval on 6/9/15
 */
public class UserEventAdapter extends BaseEventAdapter<UserEventAdapter.UserEventViewHolder> {

	public UserEventAdapter(List<Event> events, Context context) {
		super(events, context);
	}

	@Override
	protected UserEventViewHolder createViewHolder(View itemView) {
		return new UserEventViewHolder(itemView);
	}

	public class UserEventViewHolder extends BaseEventAdapter.EventViewHolder {

		public UserEventViewHolder(View itemView) {
			super(itemView);
			btnOne.setText(mContext.getString(R.string.cancel_ev));
			btnOne.setTextColor(
					mContext.getResources().getColorStateList(R.color.flat_button_text_color_negative)
			);
			btnTwo.setVisibility(View.GONE);
		}

		@Override
		protected void onFirstBtnClick() {
			if (checkInternetConnectivity()) {
				removeRightwards(itemView, getAdapterPosition());
			}
		}

		@Override
		protected void onSecondBtnClick() { /* Do nothing */ }
	}
}
