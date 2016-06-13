package in.techtatva.techtatva.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.activities.ResultActivity;
import in.techtatva.techtatva.models.DrawerModel;


public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder>{

    private LayoutInflater inflater;
    private List<DrawerModel> list = new ArrayList<>();
    private Context context;

    public DrawerAdapter(Context context, List<DrawerModel> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.drawer_item, parent, false);

        DrawerViewHolder holder = new DrawerViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(DrawerViewHolder holder, int position) {

        DrawerModel model = list.get(position);
        holder.drawerItemIcon.setImageResource(model.getIcon());
        holder.drawerItemName.setText(model.getText());

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class DrawerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView drawerItemIcon;
        TextView drawerItemName;

        public DrawerViewHolder(View itemView) {
            super(itemView);

            drawerItemIcon = (ImageView)itemView.findViewById(R.id.drawer_item_image_view);
            drawerItemName = (TextView)itemView.findViewById(R.id.drawer_item_text_view);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (drawerItemName.getText().equals("Results")){
                Intent intent = new Intent(context, ResultActivity.class);
                context.startActivity(intent);
            }

        }
    }

}
