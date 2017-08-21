package pw.janyo.jysearch;

import android.app.Service;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by DickLight on 2017/8/19.
 */

public class ClipboardListenerService  extends Service{
    private static ClipboardManager clipboardManager;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        clipboardManager = (ClipboardManager)getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                Log.e("日志", "剪切板内容触发");
                Intent searchActivity =new Intent();
                searchActivity.setClass(getApplicationContext(), SearchActivity.class);
                searchActivity.putExtra("KeyWord", clipboardManager.getText().toString());
                startActivity(searchActivity);
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }
}
