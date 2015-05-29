package com.thetidbitapp.adap;

import android.content.Context;

import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;

/**
 * Created by Ujval on 5/29/15.
 */
public class EnablableCardAdapter extends CardArrayAdapter {

    private boolean mEnabled;

    public EnablableCardAdapter(Context context, List<Card> cards) {
        super(context, cards);
    }


    @Override
    public boolean areAllItemsEnabled() {
        return mEnabled;
    }

    public void setAllItemsEnabled(boolean enabled) {
        mEnabled = enabled;
    }

}
