package in.techtatva.techtatva.activities;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import chipset.potato.Potato;
import in.techtatva.techtatva.R;
import in.techtatva.techtatva.adapters.ResultAdapter;
import in.techtatva.techtatva.models.results.ResultModel;
import in.techtatva.techtatva.models.results.ResultsListModel;
import in.techtatva.techtatva.network.APIClient;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends AppCompatActivity {

    private float x1, x2, y1, y2;
    private Realm resultsDatabase;
    private RecyclerView resultsRecyclerView;
    private View noConnectionLayout;
    private View progressDialog;
    private ResultAdapter resultAdapter;
    private Toolbar toolbar;
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<EventRound> eventRounds;
    private final String LOAD_RESULTS = "load";
    private final String UPDATE_RESULTS = "update";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        resultsRecyclerView = (RecyclerView)findViewById(R.id.result_recycler_view);
        noConnectionLayout = findViewById(R.id.result_no_connection_layout);
        progressDialog = findViewById(R.id.result_progress_dialog);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.result_swipe_refresh);

        if (swipeRefreshLayout!=null)
            swipeRefreshLayout.setColorSchemeResources(R.color.color_primary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Potato.potate(context).Utils().isInternetConnected()) {
                    noConnectionLayout.setVisibility(View.GONE);
                    resultsRecyclerView.setVisibility(View.VISIBLE);
                    loadResults(UPDATE_RESULTS);
                }
            }
        });

        context = this;
        resultsDatabase = Realm.getDefaultInstance();
        RealmResults<ResultModel> results = resultsDatabase.where(ResultModel.class).findAll();

        eventRounds = new ArrayList<>();
        resultAdapter = new ResultAdapter(getSupportFragmentManager(),eventRounds, this);
        resultsRecyclerView.setAdapter(resultAdapter);
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                                resultsRecyclerView.setVisibility(View.VISIBLE);
                                loadResults(LOAD_RESULTS);
                            }
                        break;
                    }
                }

                return true;
            }
        });

        if (!results.isEmpty()){
            displayResults();
            loadResults(UPDATE_RESULTS);
        }
        else if (Potato.potate(this).Utils().isInternetConnected()){
            loadResults(LOAD_RESULTS);
        }
        else{
            resultsRecyclerView.setVisibility(View.GONE);
            noConnectionLayout.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.GONE);
        }

    }

    public void displayResults(){
        RealmResults<ResultModel> results = resultsDatabase.where(ResultModel.class).findAllSorted("eventName");

        List<ResultModel> resultsList = resultsDatabase.copyFromRealm(results);
        eventRounds.clear();

        for (ResultModel result :resultsList){
            arrange:
            {
                if (!eventRounds.isEmpty()){
                    for (EventRound round : eventRounds) {
                        if (round.eventName.equals(result.getEventName() + " (Round " + result.getRound().toUpperCase() + ")")) {
                            round.result.add(result);
                            break arrange;
                        }
                    }
                }
                EventRound round = new EventRound();
                round.eventName = result.getEventName()+" (Round "+result.getRound()+")";
                round.catName = result.getCatName();
                round.result.add(result);
                eventRounds.add(round);
            }

        }

        resultAdapter.notifyDataSetChanged();
    }

    public void loadResults(final String operation){

        if(operation.equals(LOAD_RESULTS)){
            resultsRecyclerView.setVisibility(View.GONE);
            progressDialog.setVisibility(View.VISIBLE);
        }

        Call<ResultsListModel> call = APIClient.getInterface().getResults();

        call.enqueue(new Callback<ResultsListModel>() {
            @Override
            public void onResponse(Call<ResultsListModel> call, Response<ResultsListModel> response) {
                ResultsListModel resultsModel = response.body();

                resultsDatabase.beginTransaction();
                resultsDatabase.delete(ResultModel.class);
                resultsDatabase.copyToRealm(resultsModel.getData());
                resultsDatabase.commitTransaction();

                if(operation.equals(LOAD_RESULTS)){
                    progressDialog.setVisibility(View.GONE);
                    resultsRecyclerView.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                }

                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);

                displayResults();
            }

            @Override
            public void onFailure(Call<ResultsListModel> call, Throwable t) {
                if (operation.equals(LOAD_RESULTS)){
                    progressDialog.setVisibility(View.GONE);
                    resultsRecyclerView.setVisibility(View.GONE);
                    noConnectionLayout.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.GONE);
                    Log.d("Reached", "here");
                }


                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public class EventRound{
        public String eventName;
        public String catName;
        public List<ResultModel> result;

        EventRound(){
            eventName = null;
            result = new ArrayList<>();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menu_refresh:{
                if (Potato.potate(context).Utils().isInternetConnected()) {
                    noConnectionLayout.setVisibility(View.GONE);
                    resultsRecyclerView.setVisibility(View.VISIBLE);
                    Snackbar.make(resultsRecyclerView, "Refreshing data...", Snackbar.LENGTH_LONG).show();
                    loadResults(UPDATE_RESULTS);
                }
                break;
            }
            case android.R.id.home:{
                finish();
                overridePendingTransition(R.anim.hold, R.anim.animation_fade_out);
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        resultsDatabase.close();
    }
}
