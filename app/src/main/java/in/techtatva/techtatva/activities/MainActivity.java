package in.techtatva.techtatva.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import in.techtatva.techtatva.adapters.DayFragmentPagerAdapter;
import in.techtatva.techtatva.fragments.DayFragment;
import in.techtatva.techtatva.R;

public class MainActivity extends AppCompatActivity {

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mNavigationView = (NavigationView)findViewById(R.id.main_navigation_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);

        final ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                mDrawerLayout.closeDrawers();

                switch (item.getItemId()) {
                    case R.id.drawer_menu_favourites: {
                        Intent intent = new Intent(MainActivity.this, FavouritesActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.animation_fade_in, R.anim.hold);
                        break;
                    }
                    case R.id.drawer_menu_online: {
                        break;
                    }
                    case R.id.drawer_menu_results: {
                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.animation_fade_in, R.anim.hold);
                        break;
                    }
                    case R.id.drawer_menu_register: {
                        break;
                    }
                    case R.id.drawer_menu_insta: {
                        Intent intent = new Intent(MainActivity.this, InstaFeedActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.animation_fade_in, R.anim.hold);
                        break;
                    }
                    case R.id.drawer_menu_developers: {
                        Intent intent = new Intent(MainActivity.this, DevelopersActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.drawer_menu_about: {
                        Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.animation_fade_in, R.anim.hold);
                        break;
                    }

                }
                return true;
            }
        });

        DayFragmentPagerAdapter dayFragmentPagerAdapter = new DayFragmentPagerAdapter(getSupportFragmentManager());
        dayFragmentPagerAdapter.addFragment(new DayFragment(),getString(R.string.day_1), null);
        dayFragmentPagerAdapter.addFragment(new DayFragment(),getString(R.string.day_2), null);
        dayFragmentPagerAdapter.addFragment(new DayFragment(),getString(R.string.day_3), null);
        dayFragmentPagerAdapter.addFragment(new DayFragment(), getString(R.string.day_4), null);

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
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            }

            case R.id.trending:{
                Intent intent = new Intent (MainActivity.this, TrendingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
