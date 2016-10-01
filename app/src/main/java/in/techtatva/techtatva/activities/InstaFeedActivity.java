package in.techtatva.techtatva.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import chipset.potato.Potato;
import in.techtatva.techtatva.R;
import in.techtatva.techtatva.adapters.InstaFeedAdapter;
import in.techtatva.techtatva.models.Instagram.InstagramFeed;
import in.techtatva.techtatva.network.InstaFeedAPIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InstaFeedActivity extends AppCompatActivity {

    private float x1, x2, y1, y2;
    private InstagramFeed feed;
    private Context context;
    private View noConnectionLayout;
    private RecyclerView instaFeedRecyclerView;
    private View progressDialog;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insta_feed);
        context=this;

        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.insta_tt16_hashtag);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        instaFeedRecyclerView = (RecyclerView)findViewById(R.id.insta_feed_recycler_view);
        noConnectionLayout = findViewById(R.id.no_connection_layout);
        progressDialog = findViewById(R.id.progress_dialog);

        noConnectionLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ViewConfiguration vc = ViewConfiguration.get(context);
                int mSlop = vc.getScaledTouchSlop();
                final int MAX_HORIZONTAL_SWIPE = 150;

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN: {
                        y1 = event.getY();
                        x1 = event.getX();
                        break;
                    }

                    case MotionEvent.ACTION_UP: {
                        y2 = event.getY();
                        x2 = event.getX();

                        float deltaY = y2 - y1;
                        float deltaX = x2 - x1;

                        if (Math.abs(deltaY) > mSlop && deltaY > 0 && Math.abs(deltaX) < MAX_HORIZONTAL_SWIPE)
                            if (Potato.potate(context).Utils().isInternetConnected()) {
                                noConnectionLayout.setVisibility(View.GONE);
                                toolbar.setVisibility(View.VISIBLE);
                                loadFeed();
                            }
                        break;
                    }
                }

                return true;
            }
        });


        if (Potato.potate(this).Utils().isInternetConnected()){
            loadFeed();
        }
        else{
            noConnectionLayout.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.GONE);
        }
    }

    public void loadFeed()
    {
        progressDialog.setVisibility(View.VISIBLE);
        Call<InstagramFeed> call = InstaFeedAPIClient.getInterface().getInstagramFeed();
        call.enqueue(new Callback<InstagramFeed>() {
            @Override
            public void onResponse(Call<InstagramFeed> call, Response<InstagramFeed> response) {
                feed = response.body();
                InstaFeedAdapter adapter = new InstaFeedAdapter(context, feed);
                instaFeedRecyclerView.setAdapter(adapter);
                instaFeedRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                progressDialog.setVisibility(View.GONE);
                instaFeedRecyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<InstagramFeed> call, Throwable t) {
                progressDialog.setVisibility(View.GONE);
                instaFeedRecyclerView.setVisibility(View.GONE);
                noConnectionLayout.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.GONE);
            }
        });
    }

}
