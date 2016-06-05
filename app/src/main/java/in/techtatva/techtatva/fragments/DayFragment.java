package in.techtatva.techtatva.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.adapters.EventCardAdapter;
import in.techtatva.techtatva.adapters.EventFragmentPagerAdapter;
import in.techtatva.techtatva.models.Event;

/**
 * Created by AYUSH on 04-06-2016.
 */
public class DayFragment extends Fragment {
    /*public static final String Arg_Day = "Arg_day";

    private int mPage;
    public static DayFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(Arg_Day,page);
        DayFragment fragment = new DayFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(Arg_Day);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);

        /*TextView textView = (TextView) view;
        textView.setText("Fragment #" + mPage);*/

        RecyclerView eventsRecyclerView = (RecyclerView) view.findViewById(R.id.day_recycler_view);
        EventCardAdapter adapter = new EventCardAdapter(getEventsList(),getChildFragmentManager());
        eventsRecyclerView.setAdapter(adapter);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    public List<Event> getEventsList() {
        int[] text = {R.string.event_01, R.string.event_02, R.string.event_03, R.string.event_04, R.string.event_05, R.string.event_06, R.string.event_07, R.string.event_08};

        List<Event> list = new ArrayList<>();
        for (int i = 0; i < text.length; i++) {
            Event model = new Event();
            model.setText(text[i]);

            list.add(model);
        }

        return list;

    }
}