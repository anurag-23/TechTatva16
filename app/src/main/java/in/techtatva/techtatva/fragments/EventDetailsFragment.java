package in.techtatva.techtatva.fragments;

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

        eventLocation.setText(getArguments().getString("location"));
        eventTime.setText(getArguments().getString("time"));
        eventDate.setText(getArguments().getString("date"));
        eventParticipants.setText(getArguments().getString("participants"));
        eventContact.setText(getArguments().getString("contact"));

        return view;
    }

}
