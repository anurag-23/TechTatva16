package in.techtatva.techtatva.adapters;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.activities.EasterEggActivity;
import in.techtatva.techtatva.models.FavouritesModel;
import in.techtatva.techtatva.models.events.EventModel;
import in.techtatva.techtatva.receivers.NotificationReceiver;
import in.techtatva.techtatva.resources.IconCollection;
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
    private Map<String, EventFragmentPagerAdapter> adaptersMap;
    private Map<String, Boolean> isExpanded;
    private Realm eventsDatabase;
    private Context context;
    private int id=0;
    private final int ADD_FAVOURITE = 0;
    private final int REMOVE_FAVOURITE = 1;
    private final int CREATE_NOTIFICATION = 0;
    private final int CANCEL_NOTIFICATION = 1;

    public EventCardAdapter(Context context, RecyclerView recyclerView, List<EventModel> events, List<EventModel> allEvents, FragmentManager fm, Realm eventsDatabase){
        eventsRecyclerView = recyclerView;
        this.context = context;
        this.events = events;
        this.allEvents = allEvents;
        this.fm = fm;
        this.eventsDatabase = eventsDatabase;

        adaptersMap = new HashMap<>();
        isExpanded = new HashMap<>();

        for (int i=0; i<events.size(); i++) {
            adaptersMap.put(events.get(i).getEventName(), null);
            isExpanded.put(events.get(i).getEventName(), false);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        EventModel event = events.get(position);

        RealmResults<FavouritesModel> favouritesResults = eventsDatabase.where(FavouritesModel.class).equalTo("eventName", event.getEventName()).equalTo("date", event.getDate()).findAll();

        if(!favouritesResults.isEmpty()) {
            viewHolder.favoriteButton.setColorFilter(Color.parseColor("#f1c40f"));
            viewHolder.favoriteButton.setTag("Selected");
        }
        else{
            viewHolder.favoriteButton.setColorFilter(Color.parseColor("#CCCCCC"));
            viewHolder.favoriteButton.setTag("Deselected");
        }

        if(isExpanded.containsKey(event.getEventName()) && isExpanded.get(event.getEventName()))
            viewHolder.linearLayout.setVisibility(View.VISIBLE);
        else if(isExpanded.containsKey(event.getEventName()) && !isExpanded.get(event.getEventName()))
            viewHolder.linearLayout.setVisibility(View.GONE);

        viewHolder.eventName.setText(event.getEventName());

        IconCollection icons = new IconCollection();
        viewHolder.eventLogo.setImageResource(icons.getIconResource(context, event.getCatName()));

        if (adaptersMap.get(event.getEventName())==null) {
            EventFragmentPagerAdapter adapter = new EventFragmentPagerAdapter(fm, event.getVenue(), event.getStartTime(), event.getEndTime(), event.getDate(), event.getEventMaxTeamNumber(), event.getContactNumber(), event.getContactName(), event.getCatName(), event.getDescription());
            adaptersMap.remove(event.getEventName());
            adaptersMap.put(event.getEventName(), adapter);
            viewHolder.eventFragmentPager.setAdapter(adapter);
            viewHolder.eventTabLayout.setupWithViewPager(viewHolder.eventFragmentPager);
            viewHolder.eventFragmentPager.setId(++id);
        }
        else {
            viewHolder.eventFragmentPager.setAdapter(adaptersMap.get(event.getEventName()));
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
        Log.d("Submit query here", query);

        if(query.length()==0)
            events.addAll(allEvents);

        else
            for (EventModel event : allEvents) {
                Log.d("Query: "+query, "Event Name: "+event.getEventName());
                if (event.getEventName().toLowerCase().contains(query.toLowerCase())) {
                    events.add(event);
                    Log.d(event.getEventName(),"added");
                }
            }

        notifyDataSetChanged();
    }

    public void addOrRemoveFavourites(EventModel event, int operation){

        if(operation == ADD_FAVOURITE) {
            FavouritesModel favourite = new FavouritesModel();

            favourite.setId(event.getEventId());
            favourite.setCatID(event.getCatId());
            favourite.setEventName(event.getEventName());
            favourite.setVenue(event.getVenue());
            favourite.setDate(event.getDate());
            favourite.setDay(event.getDay());
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

            editNotification(event, CREATE_NOTIFICATION);
        }
        else if (operation == REMOVE_FAVOURITE){
            eventsDatabase.beginTransaction();
            eventsDatabase.where(FavouritesModel.class).equalTo("eventName", event.getEventName()).equalTo("day", event.getDay()).findAll().deleteAllFromRealm();
            eventsDatabase.commitTransaction();

            editNotification(event, CANCEL_NOTIFICATION);
        }

    }

    public void editNotification(EventModel event, int operation){
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("eventName", event.getEventName());
        intent.putExtra("startTime", event.getStartTime());
        intent.putExtra("eventVenue", event.getVenue());
        intent.putExtra("eventID", event.getEventId());

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, Integer.parseInt(event.getEventId()), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, Integer.parseInt(event.getCatId()+event.getEventId()), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (operation==CREATE_NOTIFICATION){
            StringBuilder dateStringBuilder = new StringBuilder();
            StringBuilder hourStringBuilder = new StringBuilder();
            StringBuilder minuteStringBuilder = new StringBuilder();

            for (int i=0; event.getDate().charAt(i)!='-'; i++){
                dateStringBuilder.append(event.getDate().charAt(i));
            }
            for (int i=0; event.getStartTime().charAt(i)!=':'; i++){
                hourStringBuilder.append(event.getStartTime().charAt(i));
            }
            int startHour = event.getStartTime().indexOf(':')+1;
            for (int i=startHour; i<startHour+2; i++){
                minuteStringBuilder.append(event.getStartTime().charAt(i));
            }

            int eventDate = Integer.parseInt(dateStringBuilder.toString());
            int eventHour = Integer.parseInt(hourStringBuilder.toString());
            int eventMinute = Integer.parseInt(minuteStringBuilder.toString());

            Log.d("Date: ", dateStringBuilder.toString());
            Log.d("Hour: ", hourStringBuilder.toString());
            Log.d("Minute: ", minuteStringBuilder.toString());

            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(Calendar.SECOND, 0);
            calendar1.set(Calendar.MINUTE, eventMinute);
            calendar1.set(Calendar.HOUR, eventHour - 1);
            calendar1.set(Calendar.AM_PM, Calendar.PM);
            calendar1.set(Calendar.MONTH, Calendar.OCTOBER);
            calendar1.set(Calendar.YEAR, 2016);
            calendar1.set(Calendar.DATE, eventDate);

            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(Calendar.SECOND, 0);
            calendar2.set(Calendar.MINUTE, 0);
            calendar2.set(Calendar.HOUR, 0);
            calendar2.set(Calendar.AM_PM, Calendar.AM);
            calendar2.set(Calendar.MONTH, Calendar.OCTOBER);
            calendar2.set(Calendar.YEAR, 2016);
            calendar2.set(Calendar.DATE, eventDate);

            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent1);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pendingIntent2);
        }
        else if (operation==CANCEL_NOTIFICATION){
            alarmManager.cancel(pendingIntent1);
            alarmManager.cancel(pendingIntent2);
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
            linearLayout = (LinearLayout) itemView.findViewById(R.id.event_description_linear_layout);

            eventFragmentPager = (EventFragmentCustomPager)itemView.findViewById(R.id.event_view_pager);
            eventTabLayout = (TabLayout)itemView.findViewById(R.id.events_tab_layout);

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
                    addOrRemoveFavourites(events.get(getLayoutPosition()), ADD_FAVOURITE);
                    Snackbar.make(view, eventName.getText().toString().toUpperCase() + " added to favourites!", Snackbar.LENGTH_SHORT).show();
                }
                else if(favoriteButton.getTag().toString().equals("Selected")) {
                    favoriteButton.setColorFilter(Color.parseColor("#cccccc"));
                    favoriteButton.setTag("Deselected");
                    addOrRemoveFavourites(events.get(getLayoutPosition()), REMOVE_FAVOURITE);
                    Snackbar.make(view, eventName.getText().toString().toUpperCase() + " removed from favourites!", Snackbar.LENGTH_SHORT).show();
                }

            }

        }


    }


}