package com.thetidbitapp.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rey.material.widget.TextView;
import com.thetidbitapp.model.Tidbit;
import com.thetidbitapp.tidbit.R;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by Ujval
 */
public class TidbitCard extends Card {

    private Tidbit mTidbit;

    public TidbitCard(Context context, Tidbit tidbit) {
        super(context, R.layout.tidbit_card);
        mTidbit = tidbit;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        ImageView ivCover = (ImageView) view.findViewById(R.id.card_cover_pic);
        TextView tvTitle = (TextView) view.findViewById(R.id.card_event_title);
        TextView tvLoc = (TextView) view.findViewById(R.id.card_loc);
        TextView tvDate = (TextView) view.findViewById(R.id.card_date);

        //ivCover.setImageBitmap(mTidbit.cover());
        tvTitle.setText(mTidbit.eventName());
        tvLoc.setText(mTidbit.location());
        tvDate.setText(mTidbit.datetime());

    }

}
