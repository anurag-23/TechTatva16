package in.techtatva.techtatva.adapters;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chipset.potato.Potato;
import in.techtatva.techtatva.Manifest;
import in.techtatva.techtatva.R;
import in.techtatva.techtatva.activities.MainActivity;
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
    private Map<String, Boolean> isExpanded;
    private Realm eventsDatabase;
    private Activity activity;
    private int id=0;
    private final int ADD_FAVOURITE = 0;
    private final int REMOVE_FAVOURITE = 1;
    private final int CREATE_NOTIFICATION = 0;
    private final int CANCEL_NOTIFICATION = 1;
    private final int CALL_PERMISSION = 1;
    private float x1,x2,y1,y2;

    public EventCardAdapter(Activity activity, RecyclerView recyclerView, List<EventModel> events, List<EventModel> allEvents, FragmentManager fm, Realm eventsDatabase){
        eventsRecyclerView = recyclerView;
        this.activity = activity;
        this.events = events;
        this.allEvents = allEvents;
        this.fm = fm;
        this.eventsDatabase = eventsDatabase;

        isExpanded = new HashMap<>();

        for (int i=0; i<events.size(); i++) {
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
        viewHolder.eventLogo.setImageResource(icons.getIconResource(activity, event.getCatName()));

        String[] details = new String[5];
        details[0] = "Venue: "+event.getVenue();
        details[1] = "Time: "+event.getStartTime()+" to "+event.getEndTime();
        details[2] = "Date: "+event.getDate();
        details[3] = "Team of: "+event.getEventMaxTeamNumber();
        details[4] = "Contact: "+ event.getContactNumber()+" ("+event.getContactName()+")";

        final SpannableStringBuilder[] detailsStringBuilder = new SpannableStringBuilder[5];

        for (int i = 0; i<5; i++){
            detailsStringBuilder[i] = new SpannableStringBuilder(details[i]);
            detailsStringBuilder[i].setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 0, details[i].indexOf(":"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        detailsStringBuilder[4].setSpan(new UnderlineSpan(), details[4].indexOf(" ")+1, details[4].indexOf("(")-1, 0);

        viewHolder.venue.setText(detailsStringBuilder[0]);
        viewHolder.time.setText(detailsStringBuilder[1]);
        viewHolder.date.setText(detailsStringBuilder[2]);
        viewHolder.teamOf.setText(detailsStringBuilder[3]);
        viewHolder.contact.setText(detailsStringBuilder[4]);

        viewHolder.categoryName.setText(event.getCatName());
        viewHolder.description.setText(event.getDescription());
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
            for (EventModel event : allEvents) {
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
        Intent intent = new Intent(activity, NotificationReceiver.class);
        intent.putExtra("eventName", event.getEventName());
        intent.putExtra("startTime", event.getStartTime());
        intent.putExtra("eventVenue", event.getVenue());
        intent.putExtra("eventID", event.getEventId());

        AlarmManager alarmManager = (AlarmManager)activity.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(activity, Integer.parseInt(event.getEventId()), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(activity, Integer.parseInt(event.getCatId()+event.getEventId()), intent, PendingIntent.FLAG_UPDATE_CURRENT);

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
        TabLayout eventTabLayout;
        CardView eventCardView;
        ImageView expandEvent;
        TextView venue;
        TextView time;
        TextView date;
        TextView teamOf;
        TextView contact;
        TextView categoryName;
        TextView description;
        View detailsLayout, infoLayout;
        View detailsUnderline, infoUnderline;
        TextView detailsTitle, infoTitle;
        LinearLayout detailsTitleLayout, infoTitleLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            eventCardView = (CardView)itemView.findViewById(R.id.event_card);
            eventLogo = (ImageView)itemView.findViewById(R.id.event_logo_image_view);
            eventName = (TextView) itemView.findViewById(R.id.event_name_text_view);
            favoriteButton = (ImageButton) itemView.findViewById(R.id.favorite_image_button);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.event_description_linear_layout);
            expandEvent = (ImageView)itemView.findViewById(R.id.event_expand_image_view);

            eventTabLayout = (TabLayout)itemView.findViewById(R.id.events_tab_layout);

            detailsLayout = itemView.findViewById(R.id.event_details_layout);
            venue = (TextView)detailsLayout.findViewById(R.id.event_location_text_view);
            time = (TextView)detailsLayout.findViewById(R.id.event_time_text_view);
            date = (TextView)detailsLayout.findViewById(R.id.event_date_text_view);
            teamOf = (TextView)detailsLayout.findViewById(R.id.event_participants_text_view);
            contact = (TextView)detailsLayout.findViewById(R.id.event_contact_text_view);

            infoLayout = itemView.findViewById(R.id.event_info_layout);
            categoryName = (TextView)infoLayout.findViewById(R.id.event_category_text_view);
            description = (TextView)infoLayout.findViewById(R.id.event_info_text_view);
            
            detailsTitle = (TextView)itemView.findViewById(R.id.event_details_title);
            infoTitle = (TextView)itemView.findViewById(R.id.event_info_title);
            detailsUnderline = itemView.findViewById(R.id.event_details_underline);
            infoUnderline = itemView.findViewById(R.id.event_info_underline);

            detailsTitleLayout = (LinearLayout)itemView.findViewById(R.id.event_details_title_layout);
            infoTitleLayout = (LinearLayout)itemView.findViewById(R.id.event_info_title_layout);
            
            itemView.setOnClickListener(this);
            favoriteButton.setOnClickListener(this);
            contact.setOnClickListener(this);
            detailsTitleLayout.setOnClickListener(this);
            infoTitleLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            
            if (view.getId() == detailsTitleLayout.getId()){
                detailsLayout.setVisibility(View.VISIBLE);
                detailsTitle.setTextColor(ContextCompat.getColor(activity, R.color.color_primary));
                infoLayout.setVisibility(View.GONE);
                infoTitle.setTextColor(ContextCompat.getColor(activity, R.color.dark_grey));
                detailsUnderline.setVisibility(View.VISIBLE);
                infoUnderline.setVisibility(View.GONE);
            }

            if (view.getId() == infoTitleLayout.getId()){
                detailsLayout.setVisibility(View.GONE);
                detailsTitle.setTextColor(ContextCompat.getColor(activity, R.color.dark_grey));
                infoLayout.setVisibility(View.VISIBLE);
                infoTitle.setTextColor(ContextCompat.getColor(activity, R.color.color_primary));
                detailsUnderline.setVisibility(View.GONE);
                infoUnderline.setVisibility(View.VISIBLE);
            }
            
            if (view.getId() == contact.getId()){

                if (ContextCompat.checkSelfPermission(activity,
                        android.Manifest.permission.CALL_PHONE)
                        == PackageManager.PERMISSION_GRANTED && !events.get(getLayoutPosition()).getContactNumber().isEmpty()) {
                    new AlertDialog.Builder(activity)
                            .setMessage("Call " + events.get(getLayoutPosition()).getContactName() + "?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Potato.potate(activity).Intents().callIntent("+91" + events.get(getLayoutPosition()).getContactNumber());
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .create().show();
                }
            }

            if(view.getId()==eventCardView.getId()){
                expandEvent.setRotation(expandEvent.getRotation() + 180);

                if(linearLayout.getVisibility()==View.VISIBLE) {
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
