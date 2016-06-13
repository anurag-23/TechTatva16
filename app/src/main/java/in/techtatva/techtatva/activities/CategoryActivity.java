package in.techtatva.techtatva.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.adapters.CategoryAdapter;
import in.techtatva.techtatva.models.CategoryModel;
/**
 * Created by AYUSH on 13-06-2016.
 */
public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.category_recycler_view);
        CategoryAdapter adapter = new CategoryAdapter(getCategoriesList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public List<CategoryModel> getCategoriesList() {
        int[] text = {R.string.event_01,R.string.event_11, R.string.event_02,R.string.event_12, R.string.event_03,R.string.event_13, R.string.event_04,R.string.event_14, R.string.event_05, R.string.event_06, R.string.event_07, R.string.event_08};

        List<CategoryModel> list = new ArrayList<>();
        for (int i = 0; i < text.length; i++) {
            CategoryModel category = new CategoryModel();
            category.setCategoryName(text[i]);

            list.add(category);
        }

        return list;
    }
}
