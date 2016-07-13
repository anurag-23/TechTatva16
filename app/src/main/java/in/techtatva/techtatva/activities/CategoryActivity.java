package in.techtatva.techtatva.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import java.util.List;

import chipset.potato.Potato;
import in.techtatva.techtatva.R;
import in.techtatva.techtatva.adapters.CategoryAdapter;
import in.techtatva.techtatva.models.categories.CategoriesModel;
import in.techtatva.techtatva.models.categories.CategoryModel;
import in.techtatva.techtatva.network.CategoriesAPIClient;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AYUSH on 13-06-2016.
 */
public class CategoryActivity extends AppCompatActivity {

    private Context context;
    private View progressDialog;
    private Realm categoriesDatabase;
    private List<CategoryModel> categoriesList;
    private RecyclerView categoriesRecyclerView;
    private CategoryAdapter adapter;
    private float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        progressDialog = findViewById(R.id.category_progress_dialog);
        categoriesRecyclerView = (RecyclerView) findViewById(R.id.category_recycler_view);

        categoriesDatabase = Realm.getDefaultInstance();

        RealmResults<CategoryModel> categoriesResults = categoriesDatabase.where(CategoryModel.class).findAll();

        if (!categoriesResults.isEmpty()){
            displayData();
            loadCategories("update");
        }
        else if (Potato.potate(this).Utils().isInternetConnected()){
            loadCategories("load");
        }
        else{
            View noConnectionLayout = findViewById(R.id.category_no_connection_layout);
            noConnectionLayout.setVisibility(View.VISIBLE);
            categoriesRecyclerView.setVisibility(View.GONE);
        }
    }

    public void displayData(){

        RealmResults<CategoryModel> categoriesResults = categoriesDatabase.where(CategoryModel.class).findAll();
        categoriesList = categoriesDatabase.copyFromRealm(categoriesResults);

        adapter = new CategoryAdapter(getSupportFragmentManager(), categoriesList);
        categoriesRecyclerView.setAdapter(adapter);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    public void loadCategories(final String operation){

        if (operation.equals("load")) {
            progressDialog.setVisibility(View.VISIBLE);
            categoriesRecyclerView.setVisibility(View.GONE);
        }

        Call<CategoriesModel> call = CategoriesAPIClient.getInterface().getCategories();

        call.enqueue(new Callback<CategoriesModel>() {
            @Override
            public void onResponse(Call<CategoriesModel> call, Response<CategoriesModel> response) {

                if (operation.equals("load")){
                    progressDialog.setVisibility(View.GONE);
                    categoriesRecyclerView.setVisibility(View.VISIBLE);
                }

                categoriesDatabase.beginTransaction();
                categoriesDatabase.delete(CategoryModel.class);
                categoriesDatabase.copyToRealm(response.body().getCategoriesList());
                categoriesDatabase.commitTransaction();

                if (operation.equals("load"))
                    displayData();

                else if (operation.equals("update")){
                    RealmResults<CategoryModel> categoriesResults = categoriesDatabase.where(CategoryModel.class).findAll();
                    List<CategoryModel> updatedCategories = categoriesDatabase.copyFromRealm(categoriesResults);
                    categoriesList.clear();
                    categoriesList.addAll(updatedCategories);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CategoriesModel> call, Throwable t) {

                 if(operation.equals("load")){
                     progressDialog.setVisibility(View.GONE);
                     categoriesRecyclerView.setVisibility(View.VISIBLE);
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
                    loadCategories("load");

                break;
            }
        }

        return super.onTouchEvent(event);
    }

    /* public List<CategoryModel> getCategoriesList() {
        int[] text = {R.string.category_01,R.string.category_02, R.string.category_03,R.string.category_04, R.string.category_05,R.string.category_06,R.string.category_07, R.string.category_08,R.string.category_09, R.string.category_10, R.string.category_11};

        List<CategoryModel> list = new ArrayList<>();
        for (int i = 0; i < text.length; i++) {
            CategoryModel category = new CategoryModel();
            category.setCategoryName(text[i]);

            list.add(category);
        }

        return list;
    }*/
}
