package in.techtatva.techtatva.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import in.techtatva.techtatva.R;
import in.techtatva.techtatva.adapters.DayFragmentPagerAdapter;
import in.techtatva.techtatva.fragments.CategoryInfoDialogFragment;
import in.techtatva.techtatva.fragments.DayFragment;
import in.techtatva.techtatva.fragments.SelectedCategoryDayFragment;
import io.realm.Realm;

public class SelectedCategoryActivity extends AppCompatActivity {

    private Realm categoryDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_category);

        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("Category Name"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DayFragmentPagerAdapter dayFragmentPagerAdapter = new DayFragmentPagerAdapter(getSupportFragmentManager());
        dayFragmentPagerAdapter.addFragment(new SelectedCategoryDayFragment(),getString(R.string.day_1), getIntent().getStringExtra("Category ID"));
        dayFragmentPagerAdapter.addFragment(new SelectedCategoryDayFragment(), getString(R.string.day_2), getIntent().getStringExtra("Category ID"));
        dayFragmentPagerAdapter.addFragment(new SelectedCategoryDayFragment(), getString(R.string.day_3), getIntent().getStringExtra("Category ID"));
        dayFragmentPagerAdapter.addFragment(new SelectedCategoryDayFragment(), getString(R.string.day_4), getIntent().getStringExtra("Category ID"));

        ViewPager daysViewPager = (ViewPager) findViewById(R.id.category_day_viewpager);
        daysViewPager.setAdapter(dayFragmentPagerAdapter);
        daysViewPager.setId(R.id.category_day_viewpager);

        TabLayout daysTabLayout = (TabLayout)findViewById(R.id.category_tab_layout);
        daysTabLayout.setupWithViewPager(daysViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_selected_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.category_info) {
            DialogFragment fragment = CategoryInfoDialogFragment.newInstance();
            fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

            Bundle bundle = new Bundle();
            bundle.putString("Category Name", getIntent().getStringExtra("Category Name"));
            bundle.putString("Description", getIntent().getStringExtra("Description"));

            fragment.setArguments(bundle);
            fragment.show(getSupportFragmentManager(), "fragment_category_info_dialog");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
