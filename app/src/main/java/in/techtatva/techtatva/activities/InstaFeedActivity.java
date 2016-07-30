package in.techtatva.techtatva.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import java.util.ArrayList;
import java.util.List;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.adapters.InstaFeedAdapter;
import in.techtatva.techtatva.models.InstaFeedModel;

public class InstaFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insta_feed);

        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.insta_tt16_hashtag);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        RecyclerView instaFeedRecyclerView = (RecyclerView)findViewById(R.id.insta_feed_recycler_view);
        InstaFeedAdapter adapter = new InstaFeedAdapter(this, getInstaFeedList());
        instaFeedRecyclerView.setAdapter(adapter);
        instaFeedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<InstaFeedModel> getInstaFeedList() {

        InstaFeedModel model = new InstaFeedModel();
        model.setDisplay(R.drawable.tt_logo);
        model.setName("mittechtatva");
        model.setImage(R.drawable.tt_insta_img);

        model.setDescription("mittechtatva Fuel RC 4.0 #FuelRC #FeaturedEvent #TechTatva16");
        model.setLikes(34);
        model.setComments(0);

        List<InstaFeedModel> list = new ArrayList<>();
        list.add(model);
        return list;
    }


}
