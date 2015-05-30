package com.thetidbitapp.adap;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thetidbitapp.model.Tidbit;
import com.thetidbitapp.tidbit.R;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by Ujval
 */
public class TidbitCard extends Card {

    private Tidbit mTidbit;

    static int count;

    public TidbitCard(Context context, Tidbit tidbit) {
        super(context, R.layout.card_content);
        mTidbit = tidbit;
        mContext = context;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        ImageView ivCover = (ImageView) parent.findViewById(R.id.card_cover_pic);
        TextView tvTitle = (TextView) parent.findViewById(R.id.card_event_title);
        TextView tvLoc = (TextView) parent.findViewById(R.id.card_loc);
        TextView tvDate = (TextView) parent.findViewById(R.id.card_date);

        if (count == 0)
            ivCover.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.test_picture_2));
        tvTitle.setText(mTidbit.eventName());
        tvLoc.setText(mTidbit.location());
        tvDate.setText(mTidbit.datetime());

        count++;

    }

}
