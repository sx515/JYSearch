package pw.janyo.jysearch.util;

import android.view.MenuItem;
import android.view.View;

/**
 * Created by DickLight on 2017/8/21.
 */

public interface MyMenuItem extends MenuItem{
    MyMenuItem setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener, View v);
}
