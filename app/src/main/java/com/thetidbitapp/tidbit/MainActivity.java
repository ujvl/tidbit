package com.thetidbitapp.tidbit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.astuetz.PagerSlidingTabStrip;
import com.thetidbitapp.model.MainPagerAdapter;
import com.thetidbitapp.view.BlockedViewPager;

public class MainActivity extends ActionBarActivity implements OnLogoutListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BlockedViewPager pager = (BlockedViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        pager.setOffscreenPageLimit(2);

        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pagerSlidingTabStrip.setViewPager(pager);

    }

    @Override
    public void onLogout() {
        Intent intent = new Intent(this, InitialActivity.class);
        startActivity(intent);
        finish();
    }

}