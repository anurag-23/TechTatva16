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

import java.text.SimpleDateFormat;
import java.util.Calendar;

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

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());

        switch(formattedDate){

            case "12-10-2016":{
                daysViewPager.setCurrentItem(0);
                break;
            }
            case "13-10-2016":{
                daysViewPager.setCurrentItem(1);
                break;
            }
            case "14-10-2016":{
                daysViewPager.setCurrentItem(2);
                break;
            }
            case "15-10-2016":{
                daysViewPager.setCurrentItem(3);
                break;
            }
            default: daysViewPager.setCurrentItem(0);

        }

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
                Intent intent = new Intent (this, TrendingActivity.class);
                startActivity(intent);
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
