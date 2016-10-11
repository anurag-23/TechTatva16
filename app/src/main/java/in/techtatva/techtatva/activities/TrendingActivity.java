package in.techtatva.techtatva.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.models.RatingsModel;
import in.techtatva.techtatva.models.categories.CategoryModel;
import in.techtatva.techtatva.models.instagram.Image.Image;
import in.techtatva.techtatva.resources.IconCollection;
import io.realm.Realm;
import io.realm.RealmResults;

public class TrendingActivity extends AppCompatActivity {

    private DatabaseReference reference;
    TextView trending1,trending2,trending3, trendingDesc1, trendingDesc2, trendingDesc3;
    Map<String,Double> categoryRatings=new HashMap<>();
    private Realm categoriesDatabase;
    private CardView card1, card2, card3;
    ImageView logo1, logo2, logo3;
    LinearLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);

        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_trending);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoriesDatabase = Realm.getDefaultInstance();

        reference=FirebaseDatabase.getInstance().getReference();
        reference.keepSynced(true);
        trending1=(TextView)findViewById(R.id.trending_1_text_view);
        trending2=(TextView)findViewById(R.id.trending_2_text_view);
        trending3=(TextView)findViewById(R.id.trending_3_text_view);

        trendingDesc1 = (TextView)findViewById(R.id.trending_1_text_view_2);
        trendingDesc2 = (TextView)findViewById(R.id.trending_2_text_view_2);
        trendingDesc3 = (TextView)findViewById(R.id.trending_3_text_view_2);

        rootLayout = (LinearLayout)findViewById(R.id.trending_root_layout);

        logo1 = (ImageView)findViewById(R.id.trending_1_image_view);
        logo2 = (ImageView)findViewById(R.id.trending_2_image_view);
        logo3 = (ImageView)findViewById(R.id.trending_3_image_view);

        card1 = (CardView)findViewById(R.id.trending_card_1);
        card2 = (CardView)findViewById(R.id.trending_card_2);
        card3 = (CardView)findViewById(R.id.trending_card_3);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    double counter = 0;
                    for (DataSnapshot postPostSnapShot : postSnapShot.getChildren()) {
                        RatingsModel rating = postPostSnapShot.getValue(RatingsModel.class);
                        counter += Double.parseDouble(rating.getRating());
                    }
                    counter = counter / postSnapShot.getChildrenCount();
                    categoryRatings.put(postSnapShot.getKey(), counter);
                }
                setViews();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        card1.setVisibility(View.VISIBLE);
        card2.setVisibility(View.VISIBLE);
        card3.setVisibility(View.VISIBLE);
    }

    private void setViews()
    {
        categoryRatings=sortByComparator(categoryRatings,false);
        int counter=0;
        CategoryModel category = new CategoryModel();
        for (Map.Entry<String, Double> entry :categoryRatings.entrySet())
        {
            if (counter<3) category = categoriesDatabase.where(CategoryModel.class).equalTo("categoryName", entry.getKey()).findFirst();
            if(counter==3)
            {
                break;
            }
            else if(counter==0)
            {
                trending1.setText(entry.getKey());
                if (category!=null){
                    trendingDesc1.setText(category.getCategoryDescription());
                    trendingDesc1.setVisibility(View.VISIBLE);
                }
                else trendingDesc1.setVisibility(View.GONE);
                logo1.setImageResource(new IconCollection().getIconResource(this, entry.getKey()));
            }
            else if(counter==1)
            {
                trending2.setText(entry.getKey());
                if (category!=null){
                    trendingDesc2.setText(category.getCategoryDescription());
                    trendingDesc2.setVisibility(View.VISIBLE);
                }
                else trendingDesc2.setVisibility(View.GONE);
                logo2.setImageResource(new IconCollection().getIconResource(this, entry.getKey()));
            }
            else if(counter==2)
            {
                trending3.setText(entry.getKey());
                if (category!=null){
                    trendingDesc3.setText(category.getCategoryDescription());
                    trendingDesc3.setVisibility(View.VISIBLE);
                }
                else trendingDesc3.setVisibility(View.GONE);
                logo3.setImageResource(new IconCollection().getIconResource(this, entry.getKey()));
            }
            counter++;
        }
        Snackbar.make(rootLayout, "Trending categories updated!", Snackbar.LENGTH_SHORT).show();
    }

    private static Map<String, Double> sortByComparator(Map<String, Double> unsortMap, final boolean order)
    {

        List<Entry<String, Double>> list = new LinkedList<Entry<String, Double>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Double>>()
        {
            public int compare(Entry<String, Double> o1,
                               Entry<String, Double> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Entry<String, Double> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        categoriesDatabase.close();
    }
}
