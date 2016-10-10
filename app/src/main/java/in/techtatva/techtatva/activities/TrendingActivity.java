package in.techtatva.techtatva.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

public class TrendingActivity extends AppCompatActivity {

    private DatabaseReference reference;
    TextView trending1,trending2,trending3;
    Map<String,Double> categoryRatings=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);

        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_trending);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        reference=FirebaseDatabase.getInstance().getReference();
        reference.keepSynced(true);
        trending1=(TextView)findViewById(R.id.trending_1__text_view);
        trending2=(TextView)findViewById(R.id.trending_2__text_view);
        trending3=(TextView)findViewById(R.id.trending_3__text_view);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    double counter=0;
                    for(DataSnapshot postPostSnapShot:postSnapShot.getChildren())
                    {
                        RatingsModel rating=postPostSnapShot.getValue(RatingsModel.class);
                        counter+=Double.parseDouble(rating.getRating());
                    }
                    counter=counter/postSnapShot.getChildrenCount();
                    categoryRatings.put(postSnapShot.getKey(),counter);
                }
                setViews();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void setViews()
    {
        categoryRatings=sortByComparator(categoryRatings,false);
        int counter=0;
        for (Map.Entry<String, Double> entry :categoryRatings.entrySet())
        {
            if(counter==3)
            {
                break;
            }
            else if(counter==0)
            {
                trending1.setText(entry.getKey()+" = "+entry.getValue());
            }
            else if(counter==1)
            {
                trending2.setText(entry.getKey()+" = "+entry.getValue());
            }
            else if(counter==2)
            {
                trending3.setText(entry.getKey()+" = "+entry.getValue());
            }
            counter++;
        }
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


}
