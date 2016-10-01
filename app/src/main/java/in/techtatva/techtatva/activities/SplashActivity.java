package in.techtatva.techtatva.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import in.techtatva.techtatva.R;

/**
 * Created by anurag on 24/9/16.
 */
public class SplashActivity extends Activity {

    private final int SPLASH_RUN_TIME=1000;
    private Activity activity;
    private ImageView ttLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ttLogo = (ImageView)findViewById(R.id.splash_tt_logo);

        activity = this;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        }, SPLASH_RUN_TIME);
    }
}
