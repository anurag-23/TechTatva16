package in.techtatva.techtatva.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v4.widget.DrawerLayout;

import in.techtatva.techtatva.adapters.DayFragmentPagerAdapter;
import in.techtatva.techtatva.fragments.DayFragment;
import in.techtatva.techtatva.fragments.DrawerFragment;
import in.techtatva.techtatva.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.ic_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        DrawerFragment mDrawerFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerFragment.setUp(mDrawerLayout, toolbar);

        DayFragmentPagerAdapter dayFragmentPagerAdapter = new DayFragmentPagerAdapter(getSupportFragmentManager());
        dayFragmentPagerAdapter.addFragment(new DayFragment(),"Day 1");
        dayFragmentPagerAdapter.addFragment(new DayFragment(),"Day 2");
        dayFragmentPagerAdapter.addFragment(new DayFragment(),"Day 3");
        dayFragmentPagerAdapter.addFragment(new DayFragment(),"Day 4");

        ViewPager daysViewPager = (ViewPager) findViewById(R.id.day_viewpager);
        daysViewPager.setAdapter(dayFragmentPagerAdapter);

        TabLayout daysTabLayout = (TabLayout)findViewById(R.id.tab_layout);
        daysTabLayout.setupWithViewPager(daysViewPager);

    }

}
