package in.techtatva.techtatva.adapters;

import android.os.Bundle;
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
    String location,startTime,endTime,date,participants, contactNumber, contactName,catName,info;

    public EventFragmentPagerAdapter(FragmentManager fm,String location,String startTime,String endTime,String date,String participants,String contactNumber,String contactName,String catName,String info) {
        super(fm);
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.participants = participants;
        this.contactNumber = contactNumber;
        this.contactName = contactName;
        this.catName = catName;
        this.info=info;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment fragment=new EventDetailsFragment();
                Bundle bundle=new Bundle();
                bundle.putString("location",location);
                bundle.putString("startTime",startTime);
                bundle.putString("endTime", endTime);
                bundle.putString("date",date);
                bundle.putString("participants",participants);
                bundle.putString("contactNumber", contactNumber);
                bundle.putString("contactName", contactName);
                fragment.setArguments(bundle);
                return fragment;
            case 1:
                fragment=new InfoFragment();
                bundle=new Bundle();
                bundle.putString("catName", catName);
                bundle.putString("info",info);
                fragment.setArguments(bundle);
                return fragment;
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
