package pw.janyo.jysearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;


/**
 * Created by DickLight on 2017/8/19.
 */

public class JumpActivity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String keyWord = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString();
        Intent i = new Intent(this, SearchActivity.class);
        i.putExtra("KeyWord", keyWord);
        startActivity(i);
        this.finish();

    }

}
