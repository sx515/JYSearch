package pw.janyo.jysearch.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by DickLight on 2017/8/19.
 */

public class SearchEngine {
    private String BaseURL;
    private String Name;



    public void setBaseURL(String engineName, String url)
    {
        this.BaseURL = url;
        this.Name = engineName;
    }

    public void setBaseURL(String url)
    {
        this.BaseURL = url;
    }

    public String getBaseURL()
    {
        return this.BaseURL;
    }

    public String getName()
    {
        return this.Name;
    }
    public void JumpToSearch(Context c, String KeyValue)
    {
        Intent i = new Intent();
        i.setAction("android.intent.action.VIEW");
        i.setData(Uri.parse(BaseURL + KeyValue));
        c.startActivity(i);

    }
}
