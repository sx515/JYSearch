package pw.janyo.jysearch.util;

import android.graphics.drawable.Drawable;

/**
 * Created by DickLight on 2017/8/20.
 * 适用于CardView的Engine封装
 */

public class EngineI extends SearchEngine {
    private Drawable webIcon;

    public EngineI(String name, String url, Drawable icon) {
        setBaseURL(name, url);
        this.webIcon = icon;
    }

    public Drawable getWebIcon() {
        return webIcon;
    }

    public void setWebIcon(Drawable i) {
        this.webIcon = i;
    }
}
