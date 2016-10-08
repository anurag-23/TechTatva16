package in.techtatva.techtatva.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
    private boolean trendingEnabled = false;

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
            public boolean onNavigationItemSelected(@NonNull final MenuItem item) {



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch (item.getItemId()) {
                            case R.id.drawer_menu_favourites: {
                                Intent intent = new Intent(MainActivity.this, FavouritesActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.animation_fade_in, R.anim.hold);
                                break;
                            }
                            case R.id.drawer_menu_online: {
                                Intent intent=new Intent(MainActivity.this, OnlineEventsActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.animation_fade_in, R.anim.hold);
                                break;
                            }
                            case R.id.drawer_menu_results: {
                                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.animation_fade_in, R.anim.hold);
                                break;
                            }
                            case R.id.drawer_menu_register: {
                                Intent intent=new Intent(MainActivity.this, RegisterActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.animation_fade_in, R.anim.hold);
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
                    }
                }, 200);

                mDrawerLayout.closeDrawers();

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

	if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1){
            daysViewPager.setId(View.generateViewId());
        }

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());

        switch(formattedDate){

            case "12-10-2016":{
                daysViewPager.setCurrentItem(0);
                trendingEnabled = true;
                break;
            }
            case "13-10-2016":{
                daysViewPager.setCurrentItem(1);
                trendingEnabled = true;
                break;
            }
            case "14-10-2016":{
                daysViewPager.setCurrentItem(2);
                trendingEnabled = true;
                break;
            }
            case "15-10-2016":{
                daysViewPager.setCurrentItem(3);
                trendingEnabled = true;
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
                if (trendingEnabled) {
                    Intent intent = new Intent(MainActivity.this, TrendingActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                else{
                    Snackbar.make(mDrawerLayout, "Trending Categories - Coming Soon!", Snackbar.LENGTH_SHORT).show();
                }
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
