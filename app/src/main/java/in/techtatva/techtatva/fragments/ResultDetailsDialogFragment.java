package in.techtatva.techtatva.fragments;


import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.adapters.QualifiedTeamsAdapter;


public class ResultDetailsDialogFragment extends DialogFragment {

    public ResultDetailsDialogFragment() {
    }

    public static ResultDetailsDialogFragment newInstance() {
        ResultDetailsDialogFragment fragment = new ResultDetailsDialogFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_result_details_dialog, container,false);

        TextView eventName = (TextView)view.findViewById(R.id.result_name_text_view);
        eventName.setText(getArguments().getString("Event"));

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.qualified_teams_recycler_view);
        QualifiedTeamsAdapter adapter = new QualifiedTeamsAdapter(getList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        return view;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Get field from view

    }
    public List<Integer> getList() {
        int[] teamIDs = {201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,218,219,220, 221,222,223,224};

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < teamIDs.length; i++) {
            int teamID;
            teamID=teamIDs[i];

            list.add(teamID);
        }

        return list;

    }

}