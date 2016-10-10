package in.techtatva.techtatva.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.applications.TechTatva16;
import io.realm.Realm;

/**
 * Created by anurag on 10/10/16.
 */
public class RatingDialogFragment extends DialogFragment {

    private Realm ratedEventsDatabase;
    private static View clickedView;

    public RatingDialogFragment(){

    }

    public static RatingDialogFragment newInstance(View v){
        RatingDialogFragment fragment = new RatingDialogFragment();
        clickedView = v;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating_dialog, container, false);

        TextView rateTextView = (TextView)view.findViewById(R.id.rate_button_text_view);
        TextView cancelTextView = (TextView)view.findViewById(R.id.cancel_button_text_view);
        TextView rateEventName = (TextView)view.findViewById(R.id.rate_event_name_text_view);
        final RatingBar rating = (RatingBar)view.findViewById(R.id.rating_bar);

        rateEventName.setText(getArguments().getString("eventName"));

        rateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(TechTatva16.RATING_DATA, Context.MODE_PRIVATE).edit();
                editor.putFloat(getArguments().getString("eventName"), rating.getRating());
                editor.apply();
                dismiss();
                Snackbar.make(clickedView, "Rating saved!", Snackbar.LENGTH_SHORT).show();
            }
        });

        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return view;
    }

}
