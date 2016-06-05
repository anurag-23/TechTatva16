package in.techtatva.techtatva.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import in.techtatva.techtatva.adapters.DayFragmentPagerAdapter;
import in.techtatva.techtatva.adapters.EventFragmentPagerAdapter;
import in.techtatva.techtatva.fragments.DrawerFragment;
import in.techtatva.techtatva.R;
import in.techtatva.techtatva.fragments.EventDetailsFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        DrawerFragment mDrawerFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerFragment.setUp(mDrawerLayout, toolbar);

        DayFragmentPagerAdapter dayFragmentPagerAdapter = new DayFragmentPagerAdapter(getSupportFragmentManager());
        dayFragmentPagerAdapter.addFragment(new EventDetailsFragment(),"Day 01");
        dayFragmentPagerAdapter.addFragment(new EventDetailsFragment(),"Day 02");
        dayFragmentPagerAdapter.addFragment(new EventDetailsFragment(),"Day 03");
        dayFragmentPagerAdapter.addFragment(new EventDetailsFragment(),"Day 04");
        //ViewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.day_viewpager);
        viewPager.setAdapter(dayFragmentPagerAdapter);
        //DaysTabLayout
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }



}
