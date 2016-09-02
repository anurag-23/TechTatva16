package in.techtatva.techtatva.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import chipset.potato.Potato;
import in.techtatva.techtatva.R;
import in.techtatva.techtatva.adapters.InstaFeedAdapter;
import in.techtatva.techtatva.models.Instagram.InstagramFeed;
import in.techtatva.techtatva.network.InstaFeedAPIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InstaFeedActivity extends AppCompatActivity {

    InstagramFeed feed;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insta_feed);
        context=this;

        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.insta_tt16_hashtag);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Potato.potate(this).Utils().isInternetConnected()){
            loadFeed();
        }
        else{
            View noConnectionLayout = findViewById(R.id.no_connection_layout);
            noConnectionLayout.setVisibility(View.VISIBLE);
        }
    }

    public void loadFeed()
    {
        final View progressDialog=findViewById(R.id.progress_dialog);
        progressDialog.setVisibility(View.VISIBLE);
        Call<InstagramFeed> call = InstaFeedAPIClient.getInterface().getInstagramFeed();
        call.enqueue(new Callback<InstagramFeed>() {
            @Override
            public void onResponse(Call<InstagramFeed> call, Response<InstagramFeed> response) {
                feed = response.body();
                InstaFeedAdapter adapter = new InstaFeedAdapter(context, feed);
                RecyclerView instaFeedRecyclerView=(RecyclerView)findViewById(R.id.insta_feed_recycler_view);
                instaFeedRecyclerView.setAdapter(adapter);
                instaFeedRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                progressDialog.setVisibility(View.GONE);
                instaFeedRecyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<InstagramFeed> call, Throwable t) {
                View noConnectionLayout = findViewById(R.id.no_connection_layout);
                noConnectionLayout.setVisibility(View.VISIBLE);
            }
        });
    }

}
