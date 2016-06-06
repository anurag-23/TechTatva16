package in.techtatva.techtatva.adapters;

import android.support.v4.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.models.Event;

/**
 * Created by Naman on 6/2/2016.
 */
public class EventCardAdapter extends RecyclerView.Adapter<EventCardAdapter.ViewHolder>{

    private FragmentManager fm;
    private List<Event> events;

    public EventCardAdapter(List<Event> events,FragmentManager fm){
        this.events=events;
        this.fm=fm;
    }

    @Override
    public EventCardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_item,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventCardAdapter.ViewHolder viewHolder, int position) {

        Event event = events.get(position);
        viewHolder.eventName.setText(event.getText());
        viewHolder.eventFragmentPager.setAdapter(new EventFragmentPagerAdapter(fm));
        viewHolder.eventTabLayout.setupWithViewPager(viewHolder.eventFragmentPager);
        viewHolder.eventFragmentPager.setId(position + 1);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView eventName;
        ImageButton favoriteButton;
        LinearLayout linearLayout;
        CardView eventCard;
        EventFragmentCustomPager eventFragmentPager;
        TabLayout eventTabLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            eventName = (TextView) itemView.findViewById(R.id.eventName);
            favoriteButton = (ImageButton) itemView.findViewById(R.id.favorite_button);
            favoriteButton.setTag("Deselected");
            linearLayout = (LinearLayout) itemView.findViewById(R.id.description);
            eventCard = (CardView)itemView.findViewById(R.id.event_card);

            eventFragmentPager = (EventFragmentCustomPager)itemView.findViewById(R.id.event_view_pager);
            eventTabLayout = (TabLayout)itemView.findViewById(R.id.event_tab_layout);


            itemView.setOnClickListener(this);
            favoriteButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(view.getId()==itemView.getId()){

                if(linearLayout.getVisibility()==View.VISIBLE){
                    linearLayout.setVisibility(View.GONE);
                }
                else if(linearLayout.getVisibility()==View.GONE){
                    linearLayout.setVisibility(View.VISIBLE);
                }

            }

            if(view.getId()==favoriteButton.getId()){
                if(favoriteButton.getTag().toString().equals("Deselected")){
                    favoriteButton.setImageResource(R.drawable.ic_fav_selected);
                    favoriteButton.setTag("Selected");
                }
                else if(favoriteButton.getTag().toString().equals("Selected")){
                    favoriteButton.setImageResource(R.drawable.ic_fav_deselected);
                    favoriteButton.setTag("Deselected");
                }

            }

        }


    }


}
