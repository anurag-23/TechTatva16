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

    final String fb_url = "https://www.facebook.com/MITtechtatva";
    final String twitter_url = "https://www.twitter.com/MITtechtatva";
    final String insta_url = "https://www.instagram.com/MITtechtatva";
    final String youtube_url = "https://www.youtube.com/TechTatva";
    final String gplus_url = "https://plus.google.com/+TechTatva";

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
                Potato.potate(context).Intents().browserIntent(twitter_url);
            }
        });
        findViewById(R.id.fb_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Potato.potate(context).Intents().browserIntent(fb_url);
            }
        });
        findViewById(R.id.youtube_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Potato.potate(context).Intents().browserIntent(youtube_url);
            }
        });
        findViewById(R.id.insta_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Potato.potate(context).Intents().browserIntent(insta_url);
            }
        });
        findViewById(R.id.gplus_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Potato.potate(context).Intents().browserIntent(gplus_url);
            }
        });

    }

}
