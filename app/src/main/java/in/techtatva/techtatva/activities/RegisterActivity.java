package in.techtatva.techtatva.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import chipset.potato.Potato;
import in.techtatva.techtatva.R;

public class RegisterActivity extends AppCompatActivity {

    private float x1, x2, y1, y2;
    private WebView registerWebView;
    private Toolbar toolbar;
    private Context context;
    private View noConnectionLayout;
    private WebViewClient mWebViewClient;
    private final String REGISTER_URL = "http://register.mitportals.in/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_activity_register);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        context = this;
        registerWebView = (WebView) findViewById(R.id.online_register);
        WebSettings mWebSettings = registerWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        registerWebView.setWebChromeClient(new WebChromeClient());
        mWebViewClient = new WebViewClient();
        noConnectionLayout = findViewById(R.id.no_connection_layout);

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
                                loadWebView();
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
                            }
                        break;
                    }
                }

                return true;
            }
        });

        if (Potato.potate(this).Utils().isInternetConnected()) {
            loadWebView();
        } else {
            noConnectionLayout.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.GONE);
        }
    }

    public void loadWebView(){
        registerWebView.setWebViewClient(mWebViewClient);
        registerWebView.loadUrl(REGISTER_URL);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:{
                finish();
                overridePendingTransition(R.anim.hold, R.anim.animation_fade_out);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.hold, R.anim.animation_fade_out);
    }

}