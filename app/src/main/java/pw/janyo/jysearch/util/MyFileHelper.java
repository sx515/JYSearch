package pw.janyo.jysearch.util;

import android.content.Context;

/**
 * Created by DickLight on 2017/8/20.
 */

public class MyFileHelper {

    public static String getCachePrivatePath(Context c)
    {
        return c.getCacheDir().getPath();
    }

    public static String getHistoryFilePath(Context c)
    {
        return c.getFilesDir().getPath() + "/SearchHistory.txt";
    }

}
