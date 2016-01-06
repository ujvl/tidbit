package com.thetidbitapp.adap;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

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
	private OnInteractionListener mInteractionListener;

	public interface OnInteractionListener {
		void onItemsChanged();
		void onNoConnectivityReported();
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
	public void setOnItemsChangeListener(OnInteractionListener listener) {
		mInteractionListener = listener;
	}

	/**
	 * Removes the view rightwards if it exists in the adapter
	 * @param view parent of button
	 * @param position position of view in adapter
	 */
	public synchronized void removeRightwards(View view, int position) {
		if (position != RecyclerView.NO_POSITION) {
			animateOut(view, position, R.anim.slide_out_right, 150);
		}
	}

	/**
	 * Removes the view leftwards if it exists in the adapter
	 * @param view parent of button
	 * @param position position of view in adapter
	 */
	public synchronized void removeLeftwards(View view, int position) {
		if (position != RecyclerView.NO_POSITION) {
			animateOut(view, position, R.anim.slide_out_left, 150);
		}
	}

	/**
	 * Gets an instance of the ViewHolder
	 * @param v the View that the ViewHolder controls
	 * @return an instance of the ViewHolder on v
	 */
	protected abstract E getViewHolder(View v);

	/**
	 * Removes the item at position
	 * @param position position to remove item from
	 */
	protected void removeItem(int position) {
		mEvents.remove(position);
		notifyItemRemoved(position);
		notifyItemRangeChanged(position, mEvents.size());
		mInteractionListener.onItemsChanged();
	}

	/**
	 * Out-animates the view
	 * USES HACKY FIX OF ANIMATION FLICKER
	 * @param view view to animate
	 * @param animId id of animation
	 * @param duration duration of animation
	 */
	protected void animateOut(final View view, final int position, int animId, final int duration) {
		Animation anim = AnimationUtils.loadAnimation(mContext, animId);
		anim.setDuration(duration);
		anim.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationRepeat(Animation animation) { }
			@Override
			public void onAnimationStart(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				view.setVisibility(View.INVISIBLE);
				removeItem(position);
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						view.setVisibility(View.VISIBLE);
					}
				}, duration + 100);
			}
		});
		view.startAnimation(anim);
	}

	/**
	 * Sets the animation for when a view comes in
	 * @param viewToAnimate view to animate
	 * @param position position of view in adapter
	 */
	private void setInAnimation(View viewToAnimate, int position) {
		if (position > mLastPosition) {
			Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
			viewToAnimate.startAnimation(animation);
			mLastPosition = position;
		}
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
		 * @return true if connected to internet, false otherwise
		 */
		protected boolean checkInternetConnectivity() {
			if (!InternetUtil.isOnline(mContext)) {
				mInteractionListener.onNoConnectivityReported();
				return false;
			}
			return true;
		}

	}

}