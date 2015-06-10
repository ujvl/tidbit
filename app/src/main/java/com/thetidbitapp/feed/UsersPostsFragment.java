package com.thetidbitapp.feed;

import android.content.Context;

import com.thetidbitapp.adap.BaseEventAdapter;
import com.thetidbitapp.adap.UserEventAdapter;
import com.thetidbitapp.model.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ujval on 5/30/15.
 */
public class UsersPostsFragment extends BaseEventsFragment {

	@Override
	public List<Event> getEvents() {
		ArrayList<Event> cards = new ArrayList<>();
		for (int i = 0; i < 15; i++)
			cards.add(new Event("" + i, "Hey buddy", new Date(), "Doe Library, VA", "Sushi", 293));
		return cards;
	}

	@Override
	public BaseEventAdapter getEventAdapter(List<Event> events, Context context) {
		return new UserEventAdapter(events, context);
	}

	@Override
	public int getViewPagerPosition() {
		return 3;
	}

}
