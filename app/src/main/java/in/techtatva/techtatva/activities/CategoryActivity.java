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
        int[] text = {R.string.category_01,R.string.category_02, R.string.category_03,R.string.category_04, R.string.category_05,R.string.category_06,R.string.category_07, R.string.category_08,R.string.category_09, R.string.category_10, R.string.category_11};

        List<CategoryModel> list = new ArrayList<>();
        for (int i = 0; i < text.length; i++) {
            CategoryModel category = new CategoryModel();
            category.setCategoryName(text[i]);

            list.add(category);
        }

        return list;
    }
}
