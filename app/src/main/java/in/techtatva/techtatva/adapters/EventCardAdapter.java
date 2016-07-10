package in.techtatva.techtatva.adapters;

import android.graphics.Rect;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.models.events.EventModel;

/**
 * Created by Naman on 6/2/2016.
 */
public class EventCardAdapter extends RecyclerView.Adapter<EventCardAdapter.ViewHolder>{

    private FragmentManager fm;
    private List<EventModel> events;
    private List<EventModel> allEvents;
    private RecyclerView eventsRecyclerView;
    private List<EventFragmentPagerAdapter> adaptersList;
    private int id=0;

    public EventCardAdapter(RecyclerView recyclerView, List<EventModel> events,FragmentManager fm){
        eventsRecyclerView = recyclerView;
        this.events = events;
        allEvents = new ArrayList<>();
        allEvents.addAll(this.events);
        this.fm = fm;

        adaptersList = new ArrayList<>();
    }

    @Override
    public EventCardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventCardAdapter.ViewHolder viewHolder, int position) {

        EventModel event = events.get(position);
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
            favoriteButton.setTag("Deselected");
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
                }
                else if(linearLayout.getVisibility()==View.GONE){
                    linearLayout.setVisibility(View.VISIBLE);

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
                    favoriteButton.setImageResource(R.drawable.ic_fav_selected);
                    favoriteButton.setTag("Selected");
                    Toast.makeText(view.getContext(), eventName.getText().toString() + " added to favourites!", Toast.LENGTH_SHORT).show();
                }
                else if(favoriteButton.getTag().toString().equals("Selected")) {
                    favoriteButton.setImageResource(R.drawable.ic_fav_deselected);
                    favoriteButton.setTag("Deselected");
                    Toast.makeText(view.getContext(), eventName.getText().toString() + " removed from favourites!", Toast.LENGTH_SHORT).show();
                }

            }

        }


    }


}
