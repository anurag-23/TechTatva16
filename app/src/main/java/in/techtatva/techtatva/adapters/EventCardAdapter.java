package in.techtatva.techtatva.adapters;

import android.support.v4.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    public void onBindViewHolder(EventCardAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView eventName;
        ImageButton favoriteButton;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            eventName = (TextView) itemView.findViewById(R.id.eventName);
            favoriteButton = (ImageButton) itemView.findViewById(R.id.favorite_button);
            relativeLayout=(RelativeLayout) itemView.findViewById(R.id.description);

            ViewPager eventViewPager = (ViewPager) itemView.findViewById(R.id.event_view_pager);
            eventViewPager.setAdapter(new EventFragmentPagerAdapter(fm));

            TabLayout tabLayout = (TabLayout) itemView.findViewById(R.id.event_tab_layout);
            tabLayout.setupWithViewPager(eventViewPager);

            itemView.setOnClickListener(this);



        }

        @Override
        public void onClick(View view) {
            if(view.getId()==itemView.getId()){
                if(relativeLayout.getVisibility()==View.VISIBLE){
                    relativeLayout.setVisibility(View.GONE);

                }
                else if(relativeLayout.getVisibility()==View.GONE){
                    relativeLayout.setVisibility(View.VISIBLE);
                }


            }
        }
    }


}
