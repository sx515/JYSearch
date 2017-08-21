package pw.janyo.jysearch.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pw.janyo.jysearch.R;


/**
 * Created by DickLight on 2017/8/20.
 */

public class WebIconTool {
    private final static String ICON_NAME = "favicon.ico";
    public void saveIconToLocal(String url, Context context, String engineName, CatchIcon catchIcon)
    {
        Log.e("日志", "保存ICON");
        String re = "http(s)?://([\\w-]+\\.)+[\\w-]+/?";
        Pattern pattern = Pattern.compile(re);
        Matcher matcher = pattern.matcher(url);
        matcher.find();
        String iconUrl = matcher.group() + ICON_NAME;

        Log.e("日志", iconUrl);

        try {
            URL url1 = new URL(iconUrl);
            InputStream inputStream = url1.openStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            File engineIconDir = new File(MyFileHelper.getCachePrivatePath(context) + "/" + engineName);
            if (!(engineIconDir.exists()&&engineIconDir.isAbsolute()))
                engineIconDir.mkdir();

            File engineIconFile = new File(MyFileHelper.getCachePrivatePath(context) + "/" + engineName + "/icon.png");
            if (!engineIconFile.exists())
            {
                FileOutputStream fileOutputStream = new FileOutputStream(engineIconFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, fileOutputStream);

            }

            catchIcon.onCatchDone();
            Log.e("日志", "完成抓取");

        }catch (MalformedURLException e)
        {
            e.printStackTrace();
            Log.e("异常", e.toString());
        }catch (IOException e)
        {
            Log.e("异常", e.toString());
        }

    }

    public static Drawable getIconFromLocal(String engineName, Context context)
    {
        Drawable drawable = Drawable.createFromPath(MyFileHelper.getCachePrivatePath(context) + "/" + engineName + "/" + "icon.png");
        if (drawable != null)
            return drawable;
        else
            return context.getResources().getDrawable(R.mipmap.ic_extension_white_48dp);
    }
}
