package in.techtatva.techtatva.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.techtatva.techtatva.R;

/**
 * Created by AYUSH on 04-06-2016.
 */
public class DayFragment extends Fragment {
    public static final String Arg_Day = "Arg_day";

    private int mPage;
    public static DayFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(Arg_Day,page);
        DayFragment fragment = new DayFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(Arg_Day);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_details, container, false);
        TextView textView = (TextView) view;
        textView.setText("Fragment #" + mPage);
        return view;
    }
}