package in.techtatva.techtatva.fragments;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import chipset.potato.Potato;
import in.techtatva.techtatva.R;
import in.techtatva.techtatva.activities.InstaFeedActivity;
import in.techtatva.techtatva.adapters.EventCardAdapter;
import in.techtatva.techtatva.models.events.EventModel;
import in.techtatva.techtatva.models.events.EventsListModel;
import in.techtatva.techtatva.network.EventsAPIClient;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AYUSH on 04-06-2016.
 */
public class DayFragment extends Fragment{
    private float x1, x2, y1, y2;
    private EventCardAdapter adapter;
    private RecyclerView eventsRecyclerView;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_day,container,false);
        setHasOptionsMenu(true);

        eventsRecyclerView = (RecyclerView)rootView.findViewById(R.id.day_recycler_view);

        if(Potato.potate(getActivity()).Utils().isInternetConnected())
        {
            prepareData();
        }
        else
        {
            final View noConnectionLayout = (View)rootView.findViewById(R.id.day_no_connection_layout);
            eventsRecyclerView.setVisibility(View.GONE);
            noConnectionLayout.setVisibility(View.VISIBLE);

            rootView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    ViewConfiguration vc = ViewConfiguration.get(getActivity());
                    int mSlop = vc.getScaledTouchSlop();
                    final int MAX_HORIZONTAL_SWIPE = 150;

                    switch (event.getAction()) {

                        case MotionEvent.ACTION_DOWN: {
                            y1 = event.getY();
                            x1 = event.getX();
                            break;
                        }

                        case MotionEvent.ACTION_UP: {
                            y2 = event.getY();
                            x2 = event.getX();

                            float deltaY = y2 - y1;
                            float deltaX = x2 - x1;

                            if (Math.abs(deltaY) > mSlop && deltaY > 0 && Math.abs(deltaX) < MAX_HORIZONTAL_SWIPE)
                                if (Potato.potate(getActivity()).Utils().isInternetConnected()) {
                                    noConnectionLayout.setVisibility(View.GONE);
                                    eventsRecyclerView.setVisibility(View.VISIBLE);
                                    prepareData();
                                }
                            break;
                        }
                    }

                    return true;
                }
            });
        }

        return rootView;
    }

    private void prepareData(){

        final ProgressDialog loading = ProgressDialog.show(getContext(), "Fetching Data", "Please wait...", false, false);

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
                adapter = new EventCardAdapter(eventsRecyclerView, events, getChildFragmentManager());
                eventsRecyclerView.setAdapter(adapter);
                eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure(Call<EventsListModel> call, Throwable t) {
                loading.dismiss();
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        android.support.v7.widget.SearchView search = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        search.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        search.setIconifiedByDefault(false);

        search.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filterData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.filterData(query);
                return false;
            }
        });
        search.setOnCloseListener(new android.support.v7.widget.SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adapter.filterData("");
                return false;
            }
        });
        search.setSubmitButtonEnabled(false);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.instagram:{
                Intent intent = new Intent (getActivity(),InstaFeedActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.trending:{
                break;
            }
        }


        return super.onOptionsItemSelected(item);
    }

}