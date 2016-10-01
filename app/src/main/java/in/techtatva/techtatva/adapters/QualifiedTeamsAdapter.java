package in.techtatva.techtatva.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.models.results.ResultModel;

/**
 * Created by Naman on 6/10/2016.
 */
public class QualifiedTeamsAdapter extends RecyclerView.Adapter<QualifiedTeamsAdapter.ViewHolder> {

    private List<ResultModel> eventResult;

    public QualifiedTeamsAdapter(List<ResultModel> eventResult) {
        this.eventResult = eventResult;
    }

    @Override
    public QualifiedTeamsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.qualified_teams_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QualifiedTeamsAdapter.ViewHolder holder, int position) {
        ResultModel team = eventResult.get(position);
        holder.qualifiedTeamsTeamID.setText("Team ID: "+team.getTeamID());
    }

    @Override
    public int getItemCount() {
        return eventResult.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView qualifiedTeamsTeamID;
        public ViewHolder(View itemView) {
            super(itemView);
            qualifiedTeamsTeamID=(TextView)itemView.findViewById(R.id.teamID_text_view);
        }
    }
}
