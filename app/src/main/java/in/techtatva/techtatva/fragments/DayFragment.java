package in.techtatva.techtatva.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import chipset.potato.Potato;
import in.techtatva.techtatva.R;
import in.techtatva.techtatva.adapters.EventCardAdapter;
import in.techtatva.techtatva.models.events.EventModel;
import in.techtatva.techtatva.models.events.EventsListModel;
import in.techtatva.techtatva.network.EventsAPIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        final View[] rootView = {inflater.inflate(R.layout.fragment_day, container, false)};
        final RecyclerView eventsRecyclerView = (RecyclerView)rootView[0].findViewById(R.id.day_recycler_view);
        eventsRecyclerView.setVisibility(View.GONE);

        if(Potato.potate(getActivity()).Utils().isInternetConnected())
        {
            prepareData(rootView[0]);
            return  rootView[0];
        }
        else
        {
            return inflater.inflate(R.layout.no_connection_layout, container, false);
        }

    }
    private void prepareData(View rootView)
    {
        final RecyclerView eventsRecyclerView = (RecyclerView)rootView.findViewById(R.id.day_recycler_view);
        final ProgressDialog loading = ProgressDialog.show(getContext(), "Fetching Data", "Please wait...", false, false);

        eventsRecyclerView.setVisibility(View.GONE);
        Call<EventsListModel> call = EventsAPIClient.getInterface().getEvents();
        call.enqueue(new Callback<EventsListModel>() {

            @Override
            public void onResponse(Call<EventsListModel> call, Response<EventsListModel> response) {
                loading.dismiss();
                List<EventModel> events = new ArrayList<EventModel>();
                for (int i = 0; i < response.body().getEvents().size(); i++) {
                    if (response.body().getEvents().get(i).getDay().charAt(0) == getArguments().getString("title").charAt(4)) {
                        events.add(response.body().getEvents().get(i));
                    }
                }

                EventCardAdapter adapter = new EventCardAdapter(eventsRecyclerView, events, getChildFragmentManager());
                eventsRecyclerView.setAdapter(adapter);
                eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                eventsRecyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<EventsListModel> call, Throwable t) {
                loading.dismiss();
            }
        });

    }



}