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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.thetidbitapp.model.Tidbit;
import com.thetidbitapp.tidbit.R;

import java.util.List;

/**
 * Created by Ujval on 6/3/15
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

	private List<Tidbit> mEvents;
	private int mLastPosition = -1;

	private Context mContext;
	private View.OnClickListener mListener, mGoingListener, mNotGoingListener;

	public EventAdapter(List<Tidbit> events, Context c) {
		mEvents = events;
		mContext = c;
	}

	@Override
	public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tidbit_card, parent, false);
		view.setOnClickListener(mListener);
		EventViewHolder evh = new EventViewHolder(view);
		return evh;
	}

	public void setOnItemClickListener(View.OnClickListener listener) {
		mListener = listener;
	}

	public void setOnClickGoingListener(View.OnClickListener listener) {
		mGoingListener = listener;
	}

	public void setOnClickNotGoingListener(View.OnClickListener listener) {
		mNotGoingListener = listener;
	}

	@Override
	public void onBindViewHolder(EventViewHolder holder, int position) {

		if (position == 0)
			holder.ivCover.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.test_picture_2));

		holder.tvTitle.setText(mEvents.get(position).eventName());
		holder.tvLoc.setText(mEvents.get(position).location());
		holder.tvDate.setText(mEvents.get(position).datetime());

		holder.btnGoing.setOnClickListener(mGoingListener);
		holder.btnNotGoing.setOnClickListener(mNotGoingListener);

		setAnimation(holder.cv, position);

	}

	private void setAnimation(View viewToAnimate, int position) {
		if (position > mLastPosition) {
			Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
			viewToAnimate.startAnimation(animation);
			mLastPosition = position;
		}
	}

	public void setAllItemsEnabled(boolean enable){
		boolean mAllEnabled = enable;
		notifyItemRangeChanged(0, getItemCount());
	}

	@Override
	public int getItemCount() {
		return mEvents.size();
	}

	public static class EventViewHolder extends RecyclerView.ViewHolder {

		private CardView cv;

		private ImageView ivCover;
		private TextView tvTitle;
		private TextView tvLoc;
		private TextView tvDate;
		private Button btnGoing;
		private Button btnNotGoing;

		public EventViewHolder(View itemView) {
			super(itemView);
			cv = (CardView) itemView.findViewById(R.id.card_layout);
			ivCover = (ImageView) itemView.findViewById(R.id.card_cover_pic);
			tvTitle = (TextView) itemView.findViewById(R.id.card_event_title);
			tvLoc = (TextView) itemView.findViewById(R.id.card_loc);
			tvDate = (TextView) itemView.findViewById(R.id.card_date);
			btnGoing = (Button) itemView.findViewById(R.id.card_going);
			btnNotGoing = (Button) itemView.findViewById(R.id.card_not_going);
		}

	}

}