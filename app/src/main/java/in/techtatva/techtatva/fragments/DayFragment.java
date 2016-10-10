package in.techtatva.techtatva.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import chipset.potato.Potato;
import in.techtatva.techtatva.R;
import in.techtatva.techtatva.activities.EasterEggActivity;
import in.techtatva.techtatva.adapters.EventCardAdapter;
import in.techtatva.techtatva.models.events.EventDetailsModel;
import in.techtatva.techtatva.models.events.EventModel;
import in.techtatva.techtatva.models.events.EventsListModel;
import in.techtatva.techtatva.models.events.ScheduleListModel;
import in.techtatva.techtatva.models.events.ScheduleModel;
import in.techtatva.techtatva.network.APIClient;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
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
    private Realm eventsDatabase;
    private List<EventModel> eventsList = new ArrayList<>();
    private List<EventModel> allEventsList = new ArrayList<>();
    private View progressDialog;
    private View noConnectionLayout;
    private boolean searching = false;
    private final String LOAD_EVENTS = "load";
    private final String UPDATE_EVENTS = "update";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventsDatabase = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_day, container, false);
        setHasOptionsMenu(true);

        eventsRecyclerView = (RecyclerView)rootView.findViewById(R.id.day_recycler_view);
        progressDialog = rootView.findViewById(R.id.day_progress_dialog);

        adapter = new EventCardAdapter(getActivity(), eventsRecyclerView, eventsList, allEventsList, getChildFragmentManager(), eventsDatabase);
        eventsRecyclerView.setAdapter(adapter);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        noConnectionLayout = rootView.findViewById(R.id.day_no_connection_layout);
        noConnectionLayout.setOnTouchListener(new View.OnTouchListener() {
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
                                loadEvents(LOAD_EVENTS);
                            }
                            else{
                                Toast.makeText(getActivity(), "Check internet connection!", Toast.LENGTH_SHORT).show();
                            }
                        break;
                    }
                }

                return true;
            }
        });

        RealmResults<ScheduleModel> scheduleResults = eventsDatabase.where(ScheduleModel.class).equalTo("day", String.valueOf(getArguments().getString("title").charAt(4))).findAll();

        if (!scheduleResults.isEmpty()){
            displayData();
            loadEvents(UPDATE_EVENTS);
        }
        else
        {
            eventsRecyclerView.setVisibility(View.GONE);
            noConnectionLayout.setVisibility(View.VISIBLE);
        }
            //loadEvents(LOAD_EVENTS);

        return rootView;
    }

    void displayData() {
        eventsList.clear();
        RealmResults<ScheduleModel> scheduleResults = eventsDatabase.where(ScheduleModel.class).equalTo("day", String.valueOf(getArguments().getString("title").charAt(4))).findAllSorted("startTime", Sort.ASCENDING, "catName", Sort.ASCENDING);

        if(!scheduleResults.isEmpty()){
            for (ScheduleModel schedule : eventsDatabase.copyFromRealm(scheduleResults)) {
                EventDetailsModel eventDetail = eventsDatabase.where(EventDetailsModel.class).equalTo("eventID", schedule.getEventID()).findFirst();

                if (eventDetail!=null) {
                    EventModel event = new EventModel();
                    if (schedule.getRound().equalsIgnoreCase("f"))
                        event.setEventName(eventDetail.getEventName());
                    else
                        event.setEventName(eventDetail.getEventName() + " (Round " + schedule.getRound() + ")");

                    event.setEventId(eventDetail.getEventID());
                    event.setDescription(eventDetail.getDescription());
                    event.setEventMaxTeamNumber(eventDetail.getMaxTeamSize());
                    event.setCatName(eventDetail.getCatName());
                    event.setCatId(eventDetail.getCatID());
                    event.setContactName(eventDetail.getContactName());
                    event.setContactNumber(eventDetail.getContactNo());
                    event.setVenue(schedule.getVenue());
                    event.setDay(schedule.getDay());
                    event.setDate(schedule.getDate());
                    event.setStartTime(schedule.getStartTime());
                    event.setEndTime(schedule.getEndTime());
                    event.setHashtag1(eventDetail.getHs1());
                    event.setHashtag2(eventDetail.getHs2());
                    eventsList.add(event);
                }
            }
        }
        Collections.sort(eventsList, new Comparator<EventModel>() {
            @Override
            public int compare(EventModel o1, EventModel o2) {
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");

                try {
                    Date d1 = sdf.parse(o1.getStartTime());
                    Date d2 = sdf.parse(o2.getStartTime());

                    Calendar c1 = Calendar.getInstance();
                    c1.setTime(d1);
                    Calendar c2 = Calendar.getInstance();
                    c2.setTime(d2);

                    long diff = c1.getTimeInMillis() - c2.getTimeInMillis();

                    if (diff>0) return 1;
                    else if (diff<0) return -1;
                    else{
                        int catDiff = o1.getCatName().compareTo(o2.getCatName());

                        if (catDiff>0) return 1;
                        else if (catDiff<0) return -1;
                        else {
                            int eventDiff = o1.getEventName().compareTo(o2.getEventName());

                            if (eventDiff>0) return 1;
                            else if (eventDiff<0) return -1;
                            else return 0;
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });

        allEventsList.clear();
        allEventsList.addAll(eventsList);
        adapter.notifyDataSetChanged();
    }


    public void loadEvents(final String operation){

        if (operation.equals(LOAD_EVENTS)){
            eventsRecyclerView.setVisibility(View.GONE);
            progressDialog.setVisibility(View.VISIBLE);
        }

        Call<EventsListModel> call1 = APIClient.getInterface().getEvents();
        Call<ScheduleListModel> call2 = APIClient.getInterface().getSchedule();

        call1.enqueue(new Callback<EventsListModel>() {

            @Override
            public void onResponse(Call<EventsListModel> call, Response<EventsListModel> response) {
                if (operation.equals(LOAD_EVENTS)) {
                    progressDialog.setVisibility(View.GONE);
                    eventsRecyclerView.setVisibility(View.VISIBLE);
                }

                if (eventsDatabase.isClosed()) eventsDatabase = Realm.getDefaultInstance();

                eventsDatabase.beginTransaction();
                //eventsDatabase.where(EventModel.class).equalTo("day", String.valueOf(getArguments().getString("title").charAt(4))).findAll().deleteAllFromRealm();
                eventsDatabase.where(EventDetailsModel.class).findAll().deleteAllFromRealm();
                eventsDatabase.copyToRealm(response.body().getEvents());
                eventsDatabase.commitTransaction();

            }

            @Override
            public void onFailure(Call<EventsListModel> call, Throwable t) {
                if (operation.equals(LOAD_EVENTS)) {
                    progressDialog.setVisibility(View.GONE);
                    noConnectionLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        call2.enqueue(new Callback<ScheduleListModel>() {
            @Override
            public void onResponse(Call<ScheduleListModel> call, Response<ScheduleListModel> response) {
                eventsDatabase.beginTransaction();
                eventsDatabase.where(ScheduleModel.class).findAll().deleteAllFromRealm();
                eventsDatabase.copyToRealm(response.body().getData());
                eventsDatabase.commitTransaction();

                displayData();
            }

            @Override
            public void onFailure(Call<ScheduleListModel> call, Throwable t) {
                if (operation.equals(LOAD_EVENTS)) {
                    progressDialog.setVisibility(View.GONE);
                    noConnectionLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        /*for (int i = 0; i < eventDetailsList.size(); i++) {
            if (response.body().getEvents().get(i).getDay().charAt(0) == getArguments().getString("title").charAt(4)) {
                eventDetailsList.add(response.body().getEvents().get(i));
            }
        }*/


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_day, menu);

        final MenuItem searchItem = menu.findItem(R.id.search);
        final android.support.v7.widget.SearchView search = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        search.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        search.setIconifiedByDefault(false);

        search.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                searching = true;
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                if (adapter != null)
                    adapter.filterData("");
                if (searching) searching = false;
            }
        });

        search.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (adapter != null)
                    adapter.filterData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query.equalsIgnoreCase("harambe")){
                    Intent intent = new Intent(getActivity(), EasterEggActivity.class);
                    startActivity(intent);
                    searchItem.collapseActionView();
                }
                else if (adapter != null)
                    adapter.filterData(query);
                return false;
            }
        });
        search.setSubmitButtonEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();

        RealmResults<ScheduleModel> eventsResults = eventsDatabase.where(ScheduleModel.class).equalTo("day", String.valueOf(getArguments().getString("title").charAt(4))).findAll();

        if(!eventsResults.isEmpty() && !searching)
           displayData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventsDatabase.close();
    }


}