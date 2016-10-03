package in.techtatva.techtatva.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.adapters.EventCardAdapter;
import in.techtatva.techtatva.models.events.EventDetailsModel;
import in.techtatva.techtatva.models.events.EventModel;
import in.techtatva.techtatva.models.events.ScheduleModel;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class SelectedCategoryDayFragment extends android.support.v4.app.Fragment {

    private Realm categoryDatabase;
    private RecyclerView categoryDayRecyclerView;
    private LinearLayout noEventsLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryDatabase = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_selected_category_day, container, false);

        setHasOptionsMenu(true);

        categoryDayRecyclerView = (RecyclerView)rootView.findViewById(R.id.category_day_recycler_view);
        noEventsLayout = (LinearLayout)rootView.findViewById(R.id.no_events_layout);

        RealmResults<ScheduleModel> categoryDayResults = categoryDatabase.where(ScheduleModel.class).equalTo("day", String.valueOf(getArguments().getString("title").charAt(4))).equalTo("catID", getArguments().getString("categoryID")).findAllSorted("startTime", Sort.ASCENDING, "eventName", Sort.ASCENDING);
        List<EventModel> categoryDayList = new ArrayList<>();
        if (!categoryDayResults.isEmpty()){

            for (ScheduleModel schedule : categoryDatabase.copyFromRealm(categoryDayResults)){
                EventDetailsModel eventDetail = categoryDatabase.where(EventDetailsModel.class).equalTo("eventID", schedule.getEventID()).findFirst();

                if (eventDetail!=null){
                    EventModel event = new EventModel();
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
                    categoryDayList.add(event);
                }

            }

            EventCardAdapter adapter = new EventCardAdapter(getActivity(), categoryDayRecyclerView, categoryDayList, categoryDayList, getChildFragmentManager(), categoryDatabase);
            categoryDayRecyclerView.setAdapter(adapter);
            categoryDayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        }
        else{
            noEventsLayout.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        categoryDatabase.close();
    }
}
