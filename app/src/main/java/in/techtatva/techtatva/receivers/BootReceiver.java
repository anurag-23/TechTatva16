package in.techtatva.techtatva.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

import in.techtatva.techtatva.models.FavouritesModel;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by anurag on 27/9/16.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)){

            Log.d("Inside", "Reboot receiver");
            Realm realm = Realm.getDefaultInstance();
            RealmResults<FavouritesModel> favouritesResults = realm.where(FavouritesModel.class).findAll();

            if(!favouritesResults.isEmpty()) {

                List<FavouritesModel> favourites = realm.copyFromRealm(favouritesResults);

                for (FavouritesModel favourite : favourites) {

                    Intent intent1 = new Intent(context, NotificationReceiver.class);
                    intent1.putExtra("eventName", favourite.getEventName());
                    intent1.putExtra("startTime", favourite.getStartTime());
                    intent1.putExtra("eventVenue", favourite.getVenue());
                    intent1.putExtra("eventID", favourite.getId());

                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, Integer.parseInt(favourite.getId()), intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                    PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, Integer.parseInt(favourite.getCatID() + favourite.getId()), intent1, PendingIntent.FLAG_UPDATE_CURRENT);

                    StringBuilder dateStringBuilder = new StringBuilder();
                    StringBuilder hourStringBuilder = new StringBuilder();
                    StringBuilder minuteStringBuilder = new StringBuilder();

                    for (int i = 0; favourite.getDate().charAt(i) != '-'; i++) {
                        dateStringBuilder.append(favourite.getDate().charAt(i));
                    }
                    for (int i = 0; favourite.getStartTime().charAt(i) != ':'; i++) {
                        hourStringBuilder.append(favourite.getStartTime().charAt(i));
                    }
                    int startHour = favourite.getStartTime().indexOf(':') + 1;
                    for (int i = startHour; i < startHour + 2; i++) {
                        minuteStringBuilder.append(favourite.getStartTime().charAt(i));
                    }

                    int eventDate = Integer.parseInt(dateStringBuilder.toString());
                    int eventHour = Integer.parseInt(hourStringBuilder.toString()) - 12;
                    int eventMinute = Integer.parseInt(minuteStringBuilder.toString());

                    Log.d("Date: ", dateStringBuilder.toString());
                    Log.d("Hour: ", hourStringBuilder.toString());
                    Log.d("Minute: ", minuteStringBuilder.toString());

                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(Calendar.SECOND, 0);
                    calendar1.set(Calendar.MINUTE, eventMinute);
                    calendar1.set(Calendar.HOUR, eventHour - 1);
                    calendar1.set(Calendar.AM_PM, Calendar.PM);
                    calendar1.set(Calendar.MONTH, Calendar.OCTOBER);
                    calendar1.set(Calendar.YEAR, 2016);
                    calendar1.set(Calendar.DATE, eventDate);

                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.set(Calendar.SECOND, 0);
                    calendar2.set(Calendar.MINUTE, 0);
                    calendar2.set(Calendar.HOUR, 0);
                    calendar2.set(Calendar.AM_PM, Calendar.AM);
                    calendar2.set(Calendar.MONTH, Calendar.OCTOBER);
                    calendar2.set(Calendar.YEAR, 2016);
                    calendar2.set(Calendar.DATE, eventDate);

                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent1);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pendingIntent2);

                }

            }

        }

    }
}
