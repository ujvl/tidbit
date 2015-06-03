package com.thetidbitapp.feed;

import com.thetidbitapp.adap.TidbitCard;
import com.thetidbitapp.model.Tidbit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by Ujval on 5/30/15.
 */
public class SavedFragment extends EventListFragment {

	@Override
	public List<Tidbit> getCards() {
		ArrayList<Tidbit> cards = new ArrayList<>();
		for (int i = 0; i < 15; i++)
			cards.add(new Tidbit(0, "Hey buddy", new Date(), "Doe Library, VA", "Sushi", 293));
		return cards;
	}

}