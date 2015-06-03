package com.thetidbitapp.adap;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.thetidbitapp.model.Tidbit;
import com.thetidbitapp.tidbit.R;

import java.util.List;

/**
 * Created by Ujval on 6/3/15.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>{

	private List<Tidbit> mEvents;
	private boolean mAllEnabled;
	private int mLastPosition = -1;

	private Context mContext;

	public EventAdapter(List<Tidbit> events, Context c) {
		mEvents = events;
		mContext = c;
	}

	@Override
	public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tidbit_card, parent, false);
		EventViewHolder evh = new EventViewHolder(v);
		return evh;
	}

	@Override
	public void onBindViewHolder(EventViewHolder holder, int position) {
		if (position == 0)
			holder.ivCover.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.test_picture_2));
		holder.tvTitle.setText(mEvents.get(position).eventName());
		holder.tvLoc.setText(mEvents.get(position).location());
		holder.tvDate.setText(mEvents.get(position).datetime());

		setAnimation(holder.cv, position);
	}

	private void setAnimation(View viewToAnimate, int position) {
		if (position > mLastPosition) {
			Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
			viewToAnimate.startAnimation(animation);
			mLastPosition = position;
		}
	}

	@Override
	public int getItemCount() {
		return mEvents.size();
	}

	public void setAllItemsEnabled(boolean enable){
		mAllEnabled = enable;
		notifyItemRangeChanged(0, getItemCount());
	}

	public static class EventViewHolder extends RecyclerView.ViewHolder {

		CardView cv;

		ImageView ivCover;
		TextView tvTitle;
		TextView tvLoc;
		TextView tvDate;

		public EventViewHolder(View itemView) {
			super(itemView);
			cv = (CardView) itemView.findViewById(R.id.card_layout);
			ivCover = (ImageView) itemView.findViewById(R.id.card_cover_pic);
			tvTitle = (TextView) itemView.findViewById(R.id.card_event_title);
			tvLoc = (TextView) itemView.findViewById(R.id.card_loc);
			tvDate = (TextView) itemView.findViewById(R.id.card_date);
		}

	}

}
