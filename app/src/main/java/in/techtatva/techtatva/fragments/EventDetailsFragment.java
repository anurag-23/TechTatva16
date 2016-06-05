package in.techtatva.techtatva.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.adapters.EventCardAdapter;
import in.techtatva.techtatva.models.Event;


public class EventDetailsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event_details, container, false);
        RecyclerView eventsRecyclerView = (RecyclerView) view.findViewById(R.id.events_recycler_view);
        EventCardAdapter adapter = new EventCardAdapter(getEventslist(),getFragmentManager());
        eventsRecyclerView.setAdapter(adapter);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    public List<Event> getEventslist() {
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
