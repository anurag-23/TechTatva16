package in.techtatva.techtatva.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

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
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        DrawerFragment mDrawerFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.main_navigation_drawer);

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);

        mDrawerFragment.setUp(mDrawerLayout, toolbar);

        DayFragmentPagerAdapter dayFragmentPagerAdapter = new DayFragmentPagerAdapter(getSupportFragmentManager());
        dayFragmentPagerAdapter.addFragment(new DayFragment(),getString(R.string.day_1), null);
        dayFragmentPagerAdapter.addFragment(new DayFragment(),getString(R.string.day_2), null);
        dayFragmentPagerAdapter.addFragment(new DayFragment(),getString(R.string.day_3), null);
        dayFragmentPagerAdapter.addFragment(new DayFragment(),getString(R.string.day_4), null);

        ViewPager daysViewPager = (ViewPager) findViewById(R.id.event_day_viewpager);
        daysViewPager.setAdapter(dayFragmentPagerAdapter);
        daysViewPager.setId(R.id.event_day_viewpager);

        TabLayout daysTabLayout = (TabLayout)findViewById(R.id.events_tab_layout);
        daysTabLayout.setupWithViewPager(daysViewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.category_view:{
                Intent intent = new Intent (this, CategoriesActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.trending:{
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
