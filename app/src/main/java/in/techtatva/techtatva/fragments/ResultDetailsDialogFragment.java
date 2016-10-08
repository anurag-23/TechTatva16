package in.techtatva.techtatva.fragments;


import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.adapters.QualifiedTeamsAdapter;
import in.techtatva.techtatva.models.results.ResultModel;


public class ResultDetailsDialogFragment extends DialogFragment {

    private static ImageView plusImageView;
    private List<ResultModel> eventResult;

    public ResultDetailsDialogFragment() {
    }

    public static ResultDetailsDialogFragment newInstance(List<ResultModel> eventResult, ImageView plus) {
        ResultDetailsDialogFragment fragment = new ResultDetailsDialogFragment();
        fragment.setEventResult(eventResult);
        plusImageView = plus;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_result_details_dialog, container,false);

        TextView eventName = (TextView)view.findViewById(R.id.result_name_text_view);
        eventName.setText(getArguments().getString("Event"));

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.qualified_teams_recycler_view);
        QualifiedTeamsAdapter adapter = new QualifiedTeamsAdapter(eventResult);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        return view;


    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        if (plusImageView!=null){
            plusImageView.setImageResource(R.drawable.ic_plus);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get field from view

    }

    public void setEventResult(List<ResultModel> eventResult){
        this.eventResult = eventResult;
    }

}