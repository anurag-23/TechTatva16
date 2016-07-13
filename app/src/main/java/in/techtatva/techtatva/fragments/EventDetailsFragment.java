package in.techtatva.techtatva.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import chipset.potato.Potato;
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

        String[] details = new String[5];
        details[0] = "Venue: "+getArguments().getString("location");
        details[1] = "Time: "+getArguments().getString("startTime")+" to "+getArguments().getString("endTime");
        details[2] = "Date: "+getArguments().getString("date");
        details[3] = "Team of: "+getArguments().getString("participants");
        details[4] = "Contact: "+ getArguments().getString("contactNumber")+" ("+getArguments().getString("contactName")+")";

        final SpannableStringBuilder[] detailsStringBuilder = new SpannableStringBuilder[5];

        for (int i = 0; i<5; i++){
            detailsStringBuilder[i] = new SpannableStringBuilder(details[i]);
            detailsStringBuilder[i].setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 0, details[i].indexOf(":"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        detailsStringBuilder[4].setSpan(new UnderlineSpan(), details[4].indexOf(" ")+1, details[4].indexOf(" ")+11, 0);

        eventLocation.setText(detailsStringBuilder[0]);
        eventTime.setText(detailsStringBuilder[1]);
        eventDate.setText(detailsStringBuilder[2]);
        eventParticipants.setText(detailsStringBuilder[3]);
        eventContact.setText(detailsStringBuilder[4]);

        eventContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("Call " + getArguments().getString("contactName") + "?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Potato.potate(getActivity()).Intents().callIntent("+91" + getArguments().getString("contactNumber"));
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .create().show();
            }
        });


        return view;
    }

}
