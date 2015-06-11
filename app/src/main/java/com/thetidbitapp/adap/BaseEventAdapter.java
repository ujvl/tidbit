package com.thetidbitapp.adap;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thetidbitapp.model.Event;
import com.thetidbitapp.tidbit.R;
import com.thetidbitapp.util.InternetUtil;

import java.util.List;

/**
 * Created by Ujval on 6/3/15
 */
public abstract class BaseEventAdapter<E extends BaseEventAdapter.EventViewHolder>
													extends RecyclerView.Adapter<E> {

	private List<Event> mEvents;
	private int mLastPosition = -1;

	protected Context mContext;
	private View.OnClickListener mClickListener;
	private OnItemsChangeListener mChangeListener;

	public interface OnItemsChangeListener {
		public void onItemsChanged();
	}

	public BaseEventAdapter(List<Event> events, Context c) {
		mEvents = events;
		mContext = c;
	}

	@Override
	public E onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tidbit_card, parent, false);
		view.setOnClickListener(mClickListener);
		E evh = getViewHolder(view);
		return evh;
	}

	@Override
	public void onBindViewHolder(E holder, int position) {

		holder.ivCover.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.test_picture_2));
		holder.tvTitle.setText(mEvents.get(position).eventName());
		holder.tvLoc.setText(mEvents.get(position).location());
		holder.tvDate.setText(mEvents.get(position).datetime());

		setInAnimation(holder.cv, position);

	}

	@Override
	public int getItemCount() {
		return mEvents.size();
	}

	/**
	 * Sets a listener on item clicks
	 * @param listener listener to assign to
	 */
	public void setOnItemClickListener(View.OnClickListener listener) {
		mClickListener = listener;
	}

	/**
	 * Sets a listener on changes in the Adapter's items
	 * @param listener listener to assign to
	 */
	public void setOnItemsChangeListener(OnItemsChangeListener listener) {
		mChangeListener = listener;
	}

	/**
	 * Gets an instance of the ViewHolder
	 * @param v the View that the ViewHolder controls
	 * @return an instance of the ViewHolder on v
	 */
	protected abstract E getViewHolder(View v);

	/**
	 * Sets the animation for when a view comes in
	 * @param viewToAnimate view to animate
	 * @param position position of view in adapter
	 */
	private void setInAnimation(View viewToAnimate, int position) {
		if (position > mLastPosition) {
			Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
			viewToAnimate.startAnimation(animation);
			mLastPosition = position;
		}
	}

	/**
	 * Removes the view rightwards
	 * @param view parent of button
	 * @param position position of view in adapter
	 */
	protected void removeRightwards(View view, int position) {
		removeItem(position);
		animateOut(view, android.R.anim.slide_out_right, 150);
	}

	/**
	 * Removes the view leftwards
	 * @param view parent of button
	 * @param position position of view in adapter
	 */
	protected void removeLeftwards(View view, int position) {
		removeItem(position);
		animateOut(view, R.anim.slide_out_left, 150);
	}

	/**
	 * Removes the item at position
	 * @param position position to remove item from
	 */
	protected void removeItem(int position) {
		mEvents.remove(position);
		notifyItemRemoved(position);
		notifyItemRangeChanged(position, mEvents.size());
		mChangeListener.onItemsChanged();
	}

	/**
	 * Out-animates the view
	 * @param view view to animate
	 * @param animId id of animation
	 * @param duration duration of animation
	 */
	protected void animateOut(View view, int animId, int duration) {
		Animation anim = AnimationUtils.loadAnimation(mContext, animId);
		anim.setDuration(duration);
		view.startAnimation(anim);
	}

	public abstract class EventViewHolder extends RecyclerView.ViewHolder {

		protected CardView cv;

		protected ImageView ivCover;
		protected TextView tvTitle;
		protected TextView tvLoc;
		protected TextView tvDate;
		protected TextView btnOne;
		protected TextView btnTwo;

		public EventViewHolder(View itemView) {

			super(itemView);
			cv = (CardView) itemView.findViewById(R.id.card_layout);
			ivCover = (ImageView) itemView.findViewById(R.id.card_cover_pic);
			tvTitle = (TextView) itemView.findViewById(R.id.card_event_title);
			tvLoc = (TextView) itemView.findViewById(R.id.card_loc);
			tvDate = (TextView) itemView.findViewById(R.id.card_date);

			btnOne = (TextView) itemView.findViewById(R.id.card_button_one);
			btnTwo = (TextView) itemView.findViewById(R.id.card_button_two);

			btnOne.setText(mContext.getString(R.string.going));
			btnTwo.setText(mContext.getString(R.string.not_going));

			btnOne.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onFirstBtnClick();
				}
			});

			btnTwo.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onSecondBtnClick();
				}
			});

		}

		protected abstract void onFirstBtnClick();

		protected abstract void onSecondBtnClick();

		/**
		 * Notifies user if not connected to internet
		 * @return true if connected to internet, false otherwise
		 */
		protected boolean checkInternetConnectivity() {
			if (!InternetUtil.isOnline(mContext)) {
				
				Toast.makeText(mContext, "Stahp, no internet.", Toast.LENGTH_LONG).show();
				return false;
			}
			return true;
		}

	}

}