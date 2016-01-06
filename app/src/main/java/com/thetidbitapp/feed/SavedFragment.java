package com.thetidbitapp.feed;

import com.thetidbitapp.adap.BaseEventAdapter;
import com.thetidbitapp.adap.SavedEventAdapter;
import com.thetidbitapp.model.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ujval on 5/30/15
 */
public class SavedFragment extends BaseEventsFragment {

	@Override
	public synchronized List<Event> getEvents() {
		ArrayList<Event> cards = new ArrayList<>();
		for (int i = 0; i < 15; i++)
			cards.add(new Event("" + i, "Startup Fair", new Date(), "RSF, Bancroft Way", "Papa Johns", 293));
		return cards;
	}

	@Override
	public BaseEventAdapter getEventAdapter(List<Event> events) {
		return new SavedEventAdapter(events, getActivity());
	}

	@Override
	public int getViewPagerPosition() {
		return 2;
	}

}