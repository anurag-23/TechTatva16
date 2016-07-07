package in.techtatva.techtatva.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ProgressBar;

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
    private ProgressBar spinner;
    private Realm categoriesDatabase;
    private RealmResults<CategoryModel> categoriesResults = null;
    private float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadCategories();
    }

    public void loadCategories(){

        setContentView(R.layout.activity_category);

        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        spinner = (ProgressBar)findViewById(R.id.category_progress_bar);

        categoriesDatabase = Realm.getDefaultInstance();

        Call<CategoriesModel> call = CategoriesAPIClient.getInterface().getCategories();

        call.enqueue(new Callback<CategoriesModel>() {
            @Override
            public void onResponse(Call<CategoriesModel> call, Response<CategoriesModel> response) {

                spinner.setVisibility(View.GONE);

                categoriesDatabase.beginTransaction();
                categoriesDatabase.delete(CategoryModel.class);
                categoriesDatabase.copyToRealm(response.body().getCategoriesList());
                categoriesDatabase.commitTransaction();

                categoriesResults = categoriesDatabase.where(CategoryModel.class).findAll();

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.category_recycler_view);
                CategoryAdapter adapter = new CategoryAdapter(getSupportFragmentManager(), categoriesResults);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));

            }

            @Override
            public void onFailure(Call<CategoriesModel> call, Throwable t) {
                categoriesResults = categoriesDatabase.where(CategoryModel.class).findAll();

                if (categoriesResults.isEmpty())
                    setContentView(R.layout.no_connection_layout);

                else{
                    spinner.setVisibility(View.GONE);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.category_recycler_view);
                    CategoryAdapter adapter = new CategoryAdapter(getSupportFragmentManager(), categoriesResults);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
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
                    loadCategories();

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
