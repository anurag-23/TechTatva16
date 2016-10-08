package in.techtatva.techtatva.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

import java.util.List;

import chipset.potato.Potato;
import in.techtatva.techtatva.R;
import in.techtatva.techtatva.adapters.CategoryAdapter;
import in.techtatva.techtatva.models.categories.CategoriesListModel;
import in.techtatva.techtatva.models.categories.CategoryModel;
import in.techtatva.techtatva.network.APIClient;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AYUSH on 13-06-2016.
 */
public class CategoriesActivity extends AppCompatActivity {

    private Context context;
    private View progressDialog;
    private Realm categoriesDatabase;
    private List<CategoryModel> categoriesList;
    private RecyclerView categoriesRecyclerView;
    private Toolbar toolbar;
    private CategoryAdapter adapter;
    private View noConnectionLayout;
    private final String LOAD_CATEGORIES = "load";
    private final String UPDATE_CATEGORIES = "update";
    private float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        progressDialog = findViewById(R.id.categories_progress_dialog);
        categoriesRecyclerView = (RecyclerView) findViewById(R.id.categories_recycler_view);
        noConnectionLayout = findViewById(R.id.categories_no_connection_layout);

        categoriesDatabase = Realm.getDefaultInstance();

        RealmResults<CategoryModel> categoriesResults = categoriesDatabase.where(CategoryModel.class).findAll();

        if (!categoriesResults.isEmpty()){
            displayData();
            loadCategories(UPDATE_CATEGORIES);
        }
        else if (Potato.potate(this).Utils().isInternetConnected()){
            loadCategories(LOAD_CATEGORIES);
        }
        else{
            noConnectionLayout.setVisibility(View.VISIBLE);
            categoriesRecyclerView.setVisibility(View.GONE);
            toolbar.setVisibility(View.GONE);
        }
    }

    public void displayData(){

        RealmResults<CategoryModel> categoriesResults = categoriesDatabase.where(CategoryModel.class).findAllSorted("categoryName");
        categoriesList = categoriesDatabase.copyFromRealm(categoriesResults);

        adapter = new CategoryAdapter(getSupportFragmentManager(), categoriesList, this);
        categoriesRecyclerView.setAdapter(adapter);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    public void loadCategories(final String operation){

        if (operation.equals(LOAD_CATEGORIES)) {
            progressDialog.setVisibility(View.VISIBLE);
            categoriesRecyclerView.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
        }

        Call<CategoriesListModel> call = APIClient.getInterface().getCategories();

        call.enqueue(new Callback<CategoriesListModel>() {
            @Override
            public void onResponse(Call<CategoriesListModel> call, Response<CategoriesListModel> response) {

                if (operation.equals(LOAD_CATEGORIES)) {
                    progressDialog.setVisibility(View.GONE);
                    categoriesRecyclerView.setVisibility(View.VISIBLE);
                }

                categoriesDatabase.beginTransaction();
                categoriesDatabase.delete(CategoryModel.class);
                categoriesDatabase.copyToRealm(response.body().getCategoriesList());
                categoriesDatabase.commitTransaction();

                if (operation.equals(LOAD_CATEGORIES))
                    displayData();

                else if (operation.equals(UPDATE_CATEGORIES)) {
                    RealmResults<CategoryModel> categoriesResults = categoriesDatabase.where(CategoryModel.class).findAllSorted("categoryName");
                    List<CategoryModel> updatedCategories = categoriesDatabase.copyFromRealm(categoriesResults);
                    categoriesList.clear();
                    categoriesList.addAll(updatedCategories);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CategoriesListModel> call, Throwable t) {

                if (operation.equals(LOAD_CATEGORIES)) {
                    progressDialog.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);
                    noConnectionLayout.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        categoriesDatabase.close();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        ViewConfiguration vc = ViewConfiguration.get(context);
        int mSlop = vc.getScaledTouchSlop();
        final int MAX_HORIZONTAL_SWIPE = 150;

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:{
                y1 = event.getY();
                x1 = event.getX();
                break;
            }

            case MotionEvent.ACTION_UP:{
                y2 = event.getY();
                x2 = event.getX();

                float deltaY = y2-y1;
                float deltaX = x2-x1;

                if (Math.abs(deltaY) > mSlop && deltaY>0 && Math.abs(deltaX) < MAX_HORIZONTAL_SWIPE)
                    if (Potato.potate(this).Utils().isInternetConnected()){
                        noConnectionLayout.setVisibility(View.GONE);
                        loadCategories(LOAD_CATEGORIES);
                    }
                    else{
                        Toast.makeText(CategoriesActivity.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
                    }

                break;
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:{
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
