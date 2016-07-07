package in.techtatva.techtatva.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.adapters.EventFragmentPagerAdapter;


public class EventDetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event_details, container, false);

        TextView eventLocation = (TextView)view.findViewById(R.id.event_location_text_view);
        TextView eventTime = (TextView)view.findViewById(R.id.event_time_text_view);
        TextView eventDate = (TextView)view.findViewById(R.id.event_date_text_view);
        TextView eventParticipants = (TextView)view.findViewById(R.id.event_participants_text_view);
        TextView eventContact = (TextView)view.findViewById(R.id.event_contact_text_view);

        eventLocation.setText("Venue : "+getArguments().getString("location"));
        eventTime.setText("Time : "+getArguments().getString("time"));
        eventDate.setText("Date : "+getArguments().getString("date"));
        eventParticipants.setText("Team of  "+getArguments().getString("participants"));
        String contact=getArguments().getString("contact_number")+" ( "+getArguments().getString("contact_name")+" )";
        eventContact.setText(contact);


        eventContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+getArguments().getString("contact_number")));
                getActivity().startActivity(intent);
            }
        });

        return view;
    }

}
