package in.techtatva.techtatva.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import chipset.potato.Potato;
import in.techtatva.techtatva.R;

public class AboutUsActivity extends AppCompatActivity {

    final String FB_URL = "https://www.facebook.com/MITtechtatva";
    final String TWITTER_URL = "https://www.twitter.com/MITtechtatva";
    final String INSTA_URL = "https://www.instagram.com/MITtechtatva";
    final String YOUTUBE_URL = "https://www.youtube.com/TechTatva";
    final String GPLUS_URL = "https://plus.google.com/+TechTatva";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        final Context context = this;

        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_about_us);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.twitter_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Potato.potate(context).Intents().browserIntent(TWITTER_URL);
            }
        });
        findViewById(R.id.fb_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Potato.potate(context).Intents().browserIntent(FB_URL);
            }
        });
        findViewById(R.id.youtube_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Potato.potate(context).Intents().browserIntent(YOUTUBE_URL);
            }
        });
        findViewById(R.id.insta_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Potato.potate(context).Intents().browserIntent(INSTA_URL);
            }
        });
        findViewById(R.id.gplus_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Potato.potate(context).Intents().browserIntent(GPLUS_URL);
            }
        });

    }

}
