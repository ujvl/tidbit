package com.thetidbitapp.feed;

import java.util.ArrayList;

import com.thetidbitapp.adap.BaseEventAdapter;
import com.thetidbitapp.adap.DefaultEventAdapter;
import com.thetidbitapp.model.Event;

import java.util.Date;
import java.util.List;

/**
 * Created by Ujval on 5/30/15.
 */
public class PopularFragment extends BaseEventsFragment {

	@Override
	public List<Event> getEvents() {
		ArrayList<Event> cards = new ArrayList<>();
		for (int i = 0; i < 15; i++)
			cards.add(new Event("" + i, "Tesla Tech Talk", new Date(), "Soda Hall", "Sushi", 293));
		return cards;
	}

	@Override
	public BaseEventAdapter getEventAdapter(List<Event> events) {
		return new DefaultEventAdapter(events, getActivity());
	}

	@Override
	public int getViewPagerPosition() {
		return 1;
	}

}
