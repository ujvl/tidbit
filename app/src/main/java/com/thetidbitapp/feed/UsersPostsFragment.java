package com.thetidbitapp.feed;

import com.thetidbitapp.model.Tidbit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ujval on 5/30/15.
 */
public class UsersPostsFragment extends EventListFragment {

	@Override
	public List<Tidbit> getCards() {
		ArrayList<Tidbit> cards = new ArrayList<>();
		for (int i = 0; i < 15; i++)
			cards.add(new Tidbit("" + i, "Hey buddy", new Date(), "Doe Library, VA", "Sushi", 293));
		return cards;
	}

}
