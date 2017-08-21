package pw.janyo.jysearch;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by DickLight on 2017/8/19.
 */

public class MyApp extends Application{

    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
