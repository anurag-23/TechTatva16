package in.techtatva.techtatva.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.adapters.EventFragmentPagerAdapter;


public class EventDetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event_details, container, false);
        return view;
    }

}
