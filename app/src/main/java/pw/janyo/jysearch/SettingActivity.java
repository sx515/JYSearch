package pw.janyo.jysearch;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.widget.CheckBox;


/**
 * Created by DickLight on 2017/8/19.
 */

public class SettingActivity extends PreferenceActivity{

    CheckBoxPreference floatWindowEnableCheckBox;
    SharedPreferences sp;
    SharedPreferences.Editor edit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        InitView();
    }

    private void InitView() {
        floatWindowEnableCheckBox = (CheckBoxPreference) findPreference("float_window_enable");
    }

}
