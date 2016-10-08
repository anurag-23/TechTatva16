package in.techtatva.techtatva.activities;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.adapters.FavouritesAdapter;
import in.techtatva.techtatva.models.FavouritesModel;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class FavouritesActivity extends AppCompatActivity {

    private RealmResults<FavouritesModel> favouritesResults;
    private Realm favouritesDatabase;
    private RecyclerView favouritesRecyclerView;
    private LinearLayout noFavouritesLayout;
    private List<FavouritesModel> favouritesList = new ArrayList<>();
    private FavouritesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_favourites);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        favouritesDatabase = Realm.getDefaultInstance();
        favouritesRecyclerView = (RecyclerView)findViewById(R.id.favourites_recycler_view);
        noFavouritesLayout = (LinearLayout)findViewById(R.id.no_favourites_layout);

        String[] sortCriteria = {"day", "startTime", "eventName"};
        Sort[] sortOrders = {Sort.ASCENDING, Sort.ASCENDING, Sort.ASCENDING};

        favouritesResults = favouritesDatabase.where(FavouritesModel.class).findAllSorted(sortCriteria, sortOrders);
        if (favouritesResults.isEmpty()){
            favouritesRecyclerView.setVisibility(View.GONE);
            noFavouritesLayout.setVisibility(View.VISIBLE);
        }

        else{
            displayData();
        }


    }

    void displayData(){

        favouritesList = favouritesDatabase.copyFromRealm(favouritesResults);
        adapter = new FavouritesAdapter(favouritesList, this, favouritesDatabase, favouritesRecyclerView, noFavouritesLayout);
        favouritesRecyclerView.setAdapter(adapter);
        favouritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        favouritesDatabase.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_favourites, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case android.R.id.home:{
                finish();
                overridePendingTransition(R.anim.hold, R.anim.animation_fade_out);
                break;
            }

            case R.id.delete_all:{

                if (!favouritesDatabase.where(FavouritesModel.class).findAll().isEmpty()) {

                    new AlertDialog.Builder(this)
                            .setMessage("Clear all favourites?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    favouritesDatabase.beginTransaction();
                                    favouritesDatabase.where(FavouritesModel.class).findAll().deleteAllFromRealm();
                                    favouritesDatabase.commitTransaction();

                                    favouritesList.clear();
                                    adapter.notifyDataSetChanged();

                                    favouritesRecyclerView.setVisibility(View.GONE);
                                    noFavouritesLayout.setVisibility(View.VISIBLE);
                                    Snackbar.make(getWindow().getDecorView(), "Favourites cleared!", Snackbar.LENGTH_SHORT).show();

                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .create().show();

                }

                else{
                    Snackbar.make(getWindow().getDecorView(), "Favourites already empty!", Snackbar.LENGTH_SHORT).show();
                }

            }

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.hold, R.anim.animation_fade_out);
    }
}

