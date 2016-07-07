package in.techtatva.techtatva.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.techtatva.techtatva.R;


public class InfoFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info, container, false);

        TextView info = (TextView)view.findViewById(R.id.event_info_text_view);
        info.setText(getArguments().getString("info"));

        return view;
    }

}
