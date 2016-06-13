package in.techtatva.techtatva.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import in.techtatva.techtatva.fragments.EventDetailsFragment;
import in.techtatva.techtatva.fragments.InfoFragment;

/**
 * Created by Naman on 6/2/2016.
 */
public class EventFragmentPagerAdapter extends FragmentPagerAdapter{

    private int mCurrentPosition = -1;
    private String[] eventTabs={ "Event Details","Info"} ;

    public EventFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new EventDetailsFragment();
            case 1:
                return new InfoFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return eventTabs[position];
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);

        if (position != mCurrentPosition) {
            Fragment fragment = (Fragment) object;
            EventFragmentCustomPager pager = (EventFragmentCustomPager) container;

            if (fragment != null && fragment.getView() != null) {
                mCurrentPosition = position;
                pager.measureCurrentView(fragment.getView());
            }
        }
    }
}
