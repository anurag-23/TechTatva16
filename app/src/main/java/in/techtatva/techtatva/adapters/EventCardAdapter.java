package in.techtatva.techtatva.adapters;

import android.graphics.Rect;
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
    private RecyclerView eventsRecyclerView;
    private List<EventFragmentPagerAdapter> adaptersList;

    public EventCardAdapter(RecyclerView recyclerView, List<EventModel> events,FragmentManager fm){
        eventsRecyclerView = recyclerView;
        this.events = events;
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
        viewHolder.eventName.setText(event.getEvent_name());


    }

    @Override
    public int getItemCount() {
        return events.size();
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

                    int position =this.getLayoutPosition();
                    EventModel event= events.get(position);
                    EventFragmentPagerAdapter adapter = new EventFragmentPagerAdapter(fm,event.getVenue(),event.getStart_time(),event.getDate(),event.getEvent_max_team_number(),event.getContact_number(),event.getDescription());
                    adaptersList.add(adapter);
                    eventFragmentPager.setAdapter(adapter);
                    eventTabLayout.setupWithViewPager(eventFragmentPager);
                    eventFragmentPager.setId(getLayoutPosition()+1);

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
