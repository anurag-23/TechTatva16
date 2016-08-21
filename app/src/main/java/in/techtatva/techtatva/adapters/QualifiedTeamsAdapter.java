package in.techtatva.techtatva.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.techtatva.techtatva.R;

/**
 * Created by Naman on 6/10/2016.
 */
public class QualifiedTeamsAdapter extends RecyclerView.Adapter<QualifiedTeamsAdapter.ViewHolder> {

    private List<Integer> teamIDs;

    public QualifiedTeamsAdapter(List<Integer> teamIDs) {
        this.teamIDs = teamIDs;
    }

    @Override
    public QualifiedTeamsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.qualified_teams_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QualifiedTeamsAdapter.ViewHolder holder, int position) {
        int teamID = teamIDs.get(position);
        holder.qualifiedTeamsTeamID.setText("Team ID: "+teamID);
    }

    @Override
    public int getItemCount() {
        return teamIDs.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView qualifiedTeamsTeamID;
        public ViewHolder(View itemView) {
            super(itemView);
            qualifiedTeamsTeamID=(TextView)itemView.findViewById(R.id.teamID_text_view);
        }
    }
}
