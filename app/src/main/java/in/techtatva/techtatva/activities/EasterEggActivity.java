package in.techtatva.techtatva.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import chipset.potato.Potato;
import in.techtatva.techtatva.R;

/**
 * Created by anurag on 5/10/16.
 */
public class EasterEggActivity extends AppCompatActivity {

    private float x1,x2,y1,y2;
    private final String LUG_FB = "https://www.facebook.com/LUGManipal";
    private final String LUG_GITHUB = "https://www.github.com/LUGM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easter_egg);
        setTheme(R.style.EasterEggTheme);

        Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(3000);

        LinearLayout fbLayout = (LinearLayout)findViewById(R.id.lug_fb_layout);
        LinearLayout githubLayout = (LinearLayout)findViewById(R.id.lug_github_layout);

        fbLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Potato.potate(EasterEggActivity.this).Intents().browserIntent(LUG_FB);
            }
        });

        githubLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Potato.potate(EasterEggActivity.this).Intents().browserIntent(LUG_GITHUB);
            }
        });

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        ViewConfiguration vc = ViewConfiguration.get(this);
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
                    finish();
                    overridePendingTransition(R.anim.hold, R.anim.animation_down);
                break;
            }
        }

        return super.onTouchEvent(event);
    }
}
