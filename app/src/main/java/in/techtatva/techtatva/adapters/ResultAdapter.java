package in.techtatva.techtatva.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.activities.ResultActivity;
import in.techtatva.techtatva.fragments.ResultDetailsDialogFragment;
import in.techtatva.techtatva.models.results.ResultModel;
import in.techtatva.techtatva.resources.IconCollection;

/**
 * Created by Naman on 6/9/2016.
 */
public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder>{

    private List<ResultActivity.EventRound> eventRounds;
    private FragmentManager fm;
    private Context context;

    public ResultAdapter( FragmentManager fm, List<ResultActivity.EventRound> eventRounds, Context context){
        this.eventRounds = eventRounds;
        this.fm=fm;
        this.context = context;
    }

    @Override
    public ResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultAdapter.ViewHolder holder, int position) {
        ResultActivity.EventRound eventRound = eventRounds.get(position);
        holder.resultName.setText(eventRound.eventName);

        IconCollection icons = new IconCollection();
        holder.resultLogo.setImageResource(icons.getIconResource(context, eventRound.catName));
    }

    @Override
    public int getItemCount() {
        return eventRounds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView resultLogo;
        TextView resultName;

        public ViewHolder(View itemView) {
            super(itemView);

            resultLogo=(ImageView)itemView.findViewById(R.id.result_logo_image_view);
            resultName=(TextView)itemView.findViewById(R.id.result_name_text_view);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if(view.getId()==itemView.getId()){
                DialogFragment fragment= ResultDetailsDialogFragment.newInstance(eventRounds.get(getLayoutPosition()).result);
                fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

                Bundle bundle = new Bundle();
                bundle.putString("Event", resultName.getText().toString());

                fragment.setArguments(bundle);
                fragment.show(fm,"fragment_result_details_dialog");
            }
        }
    }

}
