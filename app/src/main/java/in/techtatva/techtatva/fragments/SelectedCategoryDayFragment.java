package in.techtatva.techtatva.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.adapters.EventCardAdapter;
import in.techtatva.techtatva.models.events.EventModel;
import io.realm.Realm;
import io.realm.RealmResults;

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

        RealmResults<EventModel> categoryDayResults = categoryDatabase.where(EventModel.class).equalTo("day", String.valueOf(getArguments().getString("title").charAt(4))).equalTo("catId", getArguments().getString("category")).findAll();

        if (!categoryDayResults.isEmpty()){

            List<EventModel>  categoryDayList = categoryDatabase.copyFromRealm(categoryDayResults);

            EventCardAdapter adapter = new EventCardAdapter(categoryDayRecyclerView, categoryDayList, getChildFragmentManager(), categoryDatabase);
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
