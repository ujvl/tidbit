package com.thetidbitapp.adap;

import android.content.Context;
import android.view.View;

import com.thetidbitapp.model.Event;

import java.util.List;

/**
 * Created by Ujval on 6/9/15
 */
public class DefaultEventAdapter extends BaseEventAdapter<BaseEventAdapter.EventViewHolder> {

	public DefaultEventAdapter(List<Event> events, Context c) {
		super(events, c);
	}

	@Override
	protected EventViewHolder getViewHolder(View v) {
		return new EventViewHolder(v);
	}

}
