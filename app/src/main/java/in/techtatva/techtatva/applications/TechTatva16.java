package in.techtatva.techtatva.applications;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.database.FirebaseDatabase;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by anurag on 29/6/16.
 */
public class TechTatva16 extends Application {

    public static final String RATING_DATA = "rating";

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);

    }

}
