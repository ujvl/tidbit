package com.thetidbitapp.feed;

import android.content.Context;

import com.thetidbitapp.adap.AbstractEventAdapter;
import com.thetidbitapp.adap.DefaultEventAdapter;
import com.thetidbitapp.model.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ujval on 5/30/15.
 */
public class PopularFragment extends AbstractEventsFragment {

    @Override
    public List<Event> getEvents() {
        ArrayList<Event> cards = new ArrayList<>();
        for (int i = 0; i < 15; i++)
            cards.add(new Event("" + i, "Hey buddy", new Date(), "Doe Library, VA", "Sushi", 293));
        return cards;
    }

	@Override
	public AbstractEventAdapter getEventAdapter(List<Event> events, Context context) {
		return new DefaultEventAdapter(events, context);
	}
}
