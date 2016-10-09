package in.techtatva.techtatva.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chipset.potato.Potato;
import in.techtatva.techtatva.R;
import in.techtatva.techtatva.models.FavouritesModel;
import in.techtatva.techtatva.resources.IconCollection;
import io.realm.Realm;
import io.realm.Sort;

/**
 * Created by anurag on 24/7/16.
 */
public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder> {

    private Context context;
    private List<FavouritesModel> favouritesList;
    private Realm favouritesDatabase;
    private Map<FavouritesModel, Boolean> isExpanded;
    private RecyclerView favouritesRecyclerView;
    private LinearLayout noFavouritesLayout;

    public FavouritesAdapter (List<FavouritesModel> favouritesList, Context context, Realm favouritesDatabase, RecyclerView favouritesRecyclerView, LinearLayout noFavouritesLayout){

        this.favouritesList = favouritesList;
        this.context = context;
        this.favouritesDatabase = favouritesDatabase;
        this.favouritesRecyclerView = favouritesRecyclerView;
        this.noFavouritesLayout = noFavouritesLayout;

        isExpanded = new HashMap<>();

        for (int i=0; i<this.favouritesList.size(); i++){
            isExpanded.put(this.favouritesList.get(i), false);
        }

    }


    @Override
    public FavouritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.favourites_item, parent, false);
        return new FavouritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavouritesAdapter.FavouritesViewHolder holder, int position) {
        FavouritesModel favourite = favouritesList.get(position);

        if (isExpanded.containsKey(favourite)) {
            if (isExpanded.get(favourite))
                holder.detailsLayout.setVisibility(View.VISIBLE);
            else if (!isExpanded.get(favourite))
                holder.detailsLayout.setVisibility(View.GONE);
        }

        holder.favouriteName.setText(favourite.getEventName());

        IconCollection icons = new IconCollection();
        holder.favouriteLogo.setImageResource(icons.getIconResource(context, favourite.getCatName()));

        holder.favouriteVenue.setText(favourite.getVenue());
        holder.favouriteTime.setText(favourite.getStartTime() + " to " + favourite.getEndTime());
        holder.favouriteDate.setText(favourite.getDate());
        holder.favouriteMaxParticipants.setText("Team of " + favourite.getParticipants());

        String contact = favourite.getContactNumber() + " (" + favourite.getContactName() + ")";

        SpannableStringBuilder contactStringBuilder = new SpannableStringBuilder(contact);
        contactStringBuilder.setSpan(new UnderlineSpan(), 0, contact.indexOf("(")-1, 0);

        holder.favouriteContact.setText(contactStringBuilder);

    }


    @Override
    public int getItemCount() {
        return favouritesList.size();
    }

    void deleteFavourite (FavouritesModel favourite){
        favouritesDatabase.beginTransaction();
        favouritesDatabase.where(FavouritesModel.class).equalTo("eventName", favourite.getEventName()).equalTo("day",favourite.getDay()).findAll().deleteAllFromRealm();
        favouritesDatabase.commitTransaction();

        favouritesList.clear();
        List<FavouritesModel> newList;

        String[] sortCriteria = {"day", "startTime", "eventName"};
        Sort[] sortOrders = {Sort.ASCENDING, Sort.ASCENDING, Sort.ASCENDING};

        newList = favouritesDatabase.copyFromRealm(favouritesDatabase.where(FavouritesModel.class).findAllSorted(sortCriteria, sortOrders));
        favouritesList.addAll(newList);
        notifyDataSetChanged();

        if (favouritesList.isEmpty()){
            favouritesRecyclerView.setVisibility(View.GONE);
            noFavouritesLayout.setVisibility(View.VISIBLE);
        }
    }

    public class FavouritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView favouriteLogo;
        TextView favouriteName, favouriteVenue, favouriteTime, favouriteDate, favouriteMaxParticipants, favouriteContact;
        ImageButton deleteButton;
        LinearLayout detailsLayout;
        ImageView expandFavourite;


        public FavouritesViewHolder(View itemView) {
            super(itemView);
            favouriteLogo = (ImageView)itemView.findViewById(R.id.favourite_logo_image_view);
            favouriteName = (TextView)itemView.findViewById(R.id.favourite_name_text_view);
            favouriteVenue = (TextView)itemView.findViewById(R.id.favourite_location_text_view);
            favouriteTime = (TextView)itemView.findViewById(R.id.favourite_time_text_view);
            favouriteDate = (TextView)itemView.findViewById(R.id.favourite_date_text_view);
            favouriteMaxParticipants = (TextView)itemView.findViewById(R.id.favourite_participants_text_view);
            favouriteContact = (TextView)itemView.findViewById(R.id.favourite_contact_text_view);
            deleteButton = (ImageButton)itemView.findViewById(R.id.favourite_delete_image_button);
            detailsLayout = (LinearLayout)itemView.findViewById(R.id.favourite_description_linear_layout);
            expandFavourite = (ImageView)itemView.findViewById(R.id.favourite_expand_image_view);

            itemView.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
            favouriteContact.setOnClickListener(this);

        }

        @Override
        public void onClick(final View v) {
            expandFavourite.setRotation(expandFavourite.getRotation()+180);

            if (v.getId() == itemView.getId()){

                if (detailsLayout.getVisibility() == View.VISIBLE){
                    detailsLayout.setVisibility(View.GONE);
                    isExpanded.remove(favouritesList.get(getLayoutPosition()));
                    isExpanded.put(favouritesList.get(getLayoutPosition()), false);
                }
                else if (detailsLayout.getVisibility() == View.GONE){
                    detailsLayout.setVisibility(View.VISIBLE);
                    isExpanded.remove(favouritesList.get(getLayoutPosition()));
                    isExpanded.put(favouritesList.get(getLayoutPosition()), true);
                }
            }


            if (v.getId() == deleteButton.getId()){

                new AlertDialog.Builder(context)
                        .setMessage("Remove " + favouriteName.getText().toString() + " from favourites?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteFavourite(favouritesList.get(getLayoutPosition()));
                                Snackbar.make(v, favouriteName.getText().toString() + " removed from favourites!", Snackbar.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .create().show();
            }

            if (v.getId() == favouriteContact.getId()){
                new AlertDialog.Builder(context)
                        .setMessage("Call " + favouritesList.get(getLayoutPosition()).getContactName() + "?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Potato.potate(context).Intents().callIntent("+91" + favouritesList.get(getLayoutPosition()).getContactNumber());
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .create().show();
            }


        }
    }


}
