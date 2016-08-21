package in.techtatva.techtatva.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import in.techtatva.techtatva.R;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_about_us);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.twitter_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "This feature hasn't been implemented yet!", Snackbar.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.fb_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"This feature hasn't been implemented yet!", Snackbar.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.youtube_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"This feature hasn't been implemented yet!", Snackbar.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.insta_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"This feature hasn't been implemented yet!", Snackbar.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.gplus_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"This feature hasn't been implemented yet!", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

}
