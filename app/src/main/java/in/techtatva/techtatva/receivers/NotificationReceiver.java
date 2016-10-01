package in.techtatva.techtatva.receivers;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.activities.SplashActivity;
import in.techtatva.techtatva.models.FavouritesModel;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by anurag on 26/9/16.
 */
public class NotificationReceiver extends BroadcastReceiver{

    private final String NOTIFICATION_TITLE="Upcoming Event";
    private String notificationText="";
    private final String LAUNCH_APPLICATION="Launch TechTatva'16";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("onReceive()", "called");

        if (intent!=null){
            //Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

            Intent appIntent = new Intent(context, SplashActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, appIntent, 0);

            notificationText = intent.getStringExtra("eventName")+" at "+intent.getStringExtra("startTime")+", "+intent.getStringExtra("eventVenue");

            Notification notify = new NotificationCompat.Builder(context)
                    .setContentTitle(NOTIFICATION_TITLE)
                    .setContentText(notificationText)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent)
                    .setDefaults(NotificationCompat.DEFAULT_SOUND)
                    .addAction(new android.support.v4.app.NotificationCompat.Action(0, LAUNCH_APPLICATION, pendingIntent))
                    .build();

            notificationManager.notify(Integer.parseInt(intent.getStringExtra("eventID")), notify);
        }

    }
}
