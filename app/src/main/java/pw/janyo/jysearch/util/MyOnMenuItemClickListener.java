package pw.janyo.jysearch.util;

import android.view.MenuItem;
import android.view.View;

/**
 * Created by DickLight on 2017/8/21.
 */

public interface MyOnMenuItemClickListener extends MenuItem.OnMenuItemClickListener {
    boolean onMenuItemClick(MenuItem menuItem, View view);
}
