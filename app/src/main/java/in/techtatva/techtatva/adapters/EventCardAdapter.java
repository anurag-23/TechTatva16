package in.techtatva.techtatva.adapters;

import android.graphics.Color;
import android.graphics.Rect;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.models.FavouritesModel;
import in.techtatva.techtatva.models.events.EventModel;
import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by Naman on 6/2/2016.
 */
public class EventCardAdapter extends RecyclerView.Adapter<EventCardAdapter.ViewHolder>{

    private FragmentManager fm;
    private List<EventModel> events;
    private List<EventModel> allEvents;
    private RecyclerView eventsRecyclerView;
    private List<EventFragmentPagerAdapter> adaptersList;
    private Map<String, Boolean> isExpanded;
    private Realm eventsDatabase;
    private int id=0;

    public EventCardAdapter(RecyclerView recyclerView, List<EventModel> events,FragmentManager fm, Realm eventsDatabase){
        eventsRecyclerView = recyclerView;
        this.events = events;
        allEvents = new ArrayList<>();
        allEvents.addAll(this.events);
        this.fm = fm;
        this.eventsDatabase = eventsDatabase;

        adaptersList = new ArrayList<>();
        isExpanded = new HashMap<>();

        for (int i=0; i<events.size(); i++)
            isExpanded.put(events.get(i).getEventName(), false);
    }

    @Override
    public EventCardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventCardAdapter.ViewHolder viewHolder, int position) {

        EventModel event = events.get(position);

        RealmResults<FavouritesModel> favouritesResults = eventsDatabase.where(FavouritesModel.class).equalTo("eventName", event.getEventName()).findAll();

        if(!favouritesResults.isEmpty()) {
            viewHolder.favoriteButton.setColorFilter(Color.parseColor("#f1c40f"));
            viewHolder.favoriteButton.setTag("Selected");
        }
        else{
            viewHolder.favoriteButton.setColorFilter(Color.parseColor("#CCCCCC"));
            viewHolder.favoriteButton.setTag("Deselected");
        }

        if(isExpanded.get(event.getEventName()))
            viewHolder.linearLayout.setVisibility(View.VISIBLE);
        else if(!isExpanded.get(event.getEventName()))
            viewHolder.linearLayout.setVisibility(View.GONE);

        viewHolder.eventName.setText(event.getEventName());

        if (adaptersList.size() < position+1) {
            EventFragmentPagerAdapter adapter = new EventFragmentPagerAdapter(fm, event.getVenue(), event.getStartTime(), event.getEndTime(), event.getDate(), event.getEventMaxTeamNumber(), event.getContactNumber(), event.getContactName(), event.getCatName(), event.getDescription());
            adaptersList.add(adapter);
            viewHolder.eventFragmentPager.setAdapter(adapter);
            viewHolder.eventTabLayout.setupWithViewPager(viewHolder.eventFragmentPager);
            viewHolder.eventFragmentPager.setId(++id);
        }
        else {
            viewHolder.eventFragmentPager.setAdapter(adaptersList.get(position));
            viewHolder.eventTabLayout.setupWithViewPager(viewHolder.eventFragmentPager);
            viewHolder.eventFragmentPager.setId(++id);
        }

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void filterData(String query){
        events.clear();

        if(query.length()==0)
            events.addAll(allEvents);

        else
            for (EventModel event : allEvents)
                    if (event.getEventName().toLowerCase().contains(query.toLowerCase()))
                        events.add(event);

        notifyDataSetChanged();
    }

    public void addOrRemoveFavourites(EventModel event, String operation){

        if(operation.equals("add")) {
            FavouritesModel favourite = new FavouritesModel();

            favourite.setEventName(event.getEventName());
            favourite.setVenue(event.getVenue());
            favourite.setDate(event.getDate());
            favourite.setStartTime(event.getStartTime());
            favourite.setEndTime(event.getEndTime());
            favourite.setParticipants(event.getEventMaxTeamNumber());
            favourite.setContactName(event.getContactName());
            favourite.setContactNumber(event.getContactNumber());
            favourite.setCatName(event.getCatName());
            favourite.setDescription(event.getDescription());

            eventsDatabase.beginTransaction();
            eventsDatabase.copyToRealm(favourite);
            eventsDatabase.commitTransaction();
        }
        else if (operation.equals("remove")){
            eventsDatabase.beginTransaction();
            eventsDatabase.where(FavouritesModel.class).equalTo("eventName", event.getEventName()).findAll().deleteAllFromRealm();
            eventsDatabase.commitTransaction();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView eventLogo;
        TextView eventName;
        ImageButton favoriteButton;
        LinearLayout linearLayout;
        EventFragmentCustomPager eventFragmentPager;
        TabLayout eventTabLayout;
        CardView eventCardView;

        public ViewHolder(View itemView) {
            super(itemView);

            eventCardView = (CardView)itemView.findViewById(R.id.event_card);
            eventLogo = (ImageView)itemView.findViewById(R.id.event_logo_image_view);
            eventName = (TextView) itemView.findViewById(R.id.event_name_text_view);
            favoriteButton = (ImageButton) itemView.findViewById(R.id.favorite_image_button);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.description_linear_layout);

            eventFragmentPager = (EventFragmentCustomPager)itemView.findViewById(R.id.event_view_pager);
            eventTabLayout = (TabLayout)itemView.findViewById(R.id.event_tab_layout);

            itemView.setOnClickListener(this);
            favoriteButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(view.getId()==eventCardView.getId()){

                if(linearLayout.getVisibility()==View.VISIBLE){
                    linearLayout.setVisibility(View.GONE);
                    isExpanded.remove(eventName.getText().toString());
                    isExpanded.put(eventName.getText().toString(), false);
                }
                else if(linearLayout.getVisibility()==View.GONE){
                    linearLayout.setVisibility(View.VISIBLE);
                    isExpanded.remove(eventName.getText().toString());
                    isExpanded.put(eventName.getText().toString(), true);

                    eventsRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {

                            Rect scrollBounds = new Rect();
                            eventsRecyclerView.getDrawingRect(scrollBounds);

                            float top = itemView.getTop();
                            float bottom = top + itemView.getHeight() ;

                            if (scrollBounds.bottom < bottom)
                                eventsRecyclerView.smoothScrollBy(0, (int) bottom - scrollBounds.bottom);
                        }
                    });


                }

            }

            if(view.getId()==favoriteButton.getId()){

                if(favoriteButton.getTag().toString().equals("Deselected")) {
                    favoriteButton.setColorFilter(Color.parseColor("#f1c40f"));
                    favoriteButton.setTag("Selected");
                    addOrRemoveFavourites(events.get(getLayoutPosition()), "add");
                    Snackbar.make(view, eventName.getText().toString() + " added to favourites!", Snackbar.LENGTH_SHORT).show();
                }
                else if(favoriteButton.getTag().toString().equals("Selected")) {
                    favoriteButton.setColorFilter(Color.parseColor("#cccccc"));
                    favoriteButton.setTag("Deselected");
                    addOrRemoveFavourites(events.get(getLayoutPosition()), "remove");
                    Snackbar.make(view, eventName.getText().toString() + " removed from favourites!", Snackbar.LENGTH_SHORT).show();
                }

            }

        }


    }


}
