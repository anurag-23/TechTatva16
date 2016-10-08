package in.techtatva.techtatva.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import in.techtatva.techtatva.R;
import in.techtatva.techtatva.models.events.EventDetailsModel;
import in.techtatva.techtatva.models.events.EventsListModel;
import in.techtatva.techtatva.models.events.ScheduleListModel;
import in.techtatva.techtatva.models.events.ScheduleModel;
import in.techtatva.techtatva.network.APIClient;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anurag on 24/9/16.
 */
public class SplashActivity extends Activity {

    private final int SPLASH_RUN_TIME=1000;
    private Activity activity;
    private ImageView ttLogo;
    private Realm database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        database = Realm.getDefaultInstance();

        ttLogo = (ImageView)findViewById(R.id.splash_tt_logo);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(ttLogo);

        Glide.with(this).load(R.raw.tt_anim).into(imageViewTarget);
        activity = this;

        RealmResults<ScheduleModel> scheduleResults = database.where(ScheduleModel.class).findAll();

        if (scheduleResults.isEmpty()) {
            Call<EventsListModel> call1 = APIClient.getInterface().getEvents();
            Call<ScheduleListModel> call2 = APIClient.getInterface().getSchedule();

            call1.enqueue(new Callback<EventsListModel>() {
                @Override
                public void onResponse(Call<EventsListModel> call, Response<EventsListModel> response) {
                    database.beginTransaction();
                    database.where(EventDetailsModel.class).findAll().deleteAllFromRealm();
                    database.copyToRealm(response.body().getEvents());
                    database.commitTransaction();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadMain();
                        }
                    }, 3000);
                }

                @Override
                public void onFailure(Call<EventsListModel> call, Throwable t) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadMain();
                        }
                    }, 5000);
                }
            });

            call2.enqueue(new Callback<ScheduleListModel>() {
                @Override
                public void onResponse(Call<ScheduleListModel> call, Response<ScheduleListModel> response) {
                    database.beginTransaction();
                    database.where(ScheduleModel.class).findAll().deleteAllFromRealm();
                    database.copyToRealm(response.body().getData());
                    database.commitTransaction();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadMain();
                        }
                    }, 3000);
                }

                @Override
                public void onFailure(Call<ScheduleListModel> call, Throwable t) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadMain();
                        }
                    }, 5000);
                }
            });
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadMain();
                }
            },2000);
        }

    }

    public void loadMain(){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
        overridePendingTransition(R.anim.animation_fade_in, R.anim.hold);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
