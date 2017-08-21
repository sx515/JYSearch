package pw.janyo.jysearch.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DickLight on 2017/8/19.
 */

public class MyPreferences implements EnginePerferences {

    protected SharedPreferences sp, spForDefault;
    protected SharedPreferences.Editor editor, editorForDefault;
    protected final String Preferences_Name = "SearchEngineList";
    protected final String DefaultEngineConfig = "DefaultEngine";
    private Context context;

    public MyPreferences(Context c)
    {
        this.context = c;
        sp = c.getSharedPreferences(Preferences_Name, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    @Override
    public void addNode(String key, String Value) {
        editor.putString(key, Value);
        editor.commit();
    }

    @Override
    public String getNode(String key) {
        return sp.getString(key, "");
    }

    @Override
    public void removeNode(String key) {
        editor.remove(key);
        editor.commit();
    }

    @Override
    public List<Map<String, String>> getNodeList() {
        List<Map<String, String>> nodelist = new ArrayList<Map<String, String>>();
        for (Map.Entry<String, String> list : ((Map<String, String>) sp.getAll()).entrySet())
        {
            Map<String, String> map = new HashMap<String, String>();
            map.put("EngineName", list.getKey());
            map.put("BaseURL", list.getValue());
            nodelist.add(map);
        }

        return nodelist;
    }

    public void setDefaultEngineConfig(String name)
    {
        spForDefault = context.getSharedPreferences(DefaultEngineConfig, Context.MODE_PRIVATE);
        editorForDefault = spForDefault.edit();
        editorForDefault.putString("DEFAULT_ENGINE", name);
        editorForDefault.commit();
    }

    public String getDefaultEngineConfig()
    {
        spForDefault = context.getSharedPreferences(DefaultEngineConfig, Context.MODE_PRIVATE);
        return spForDefault.getString("DEFAULT_ENGINE", "NULL");
    }
}
interface EnginePerferences
{
    public void addNode(String key, String Value);
    public String getNode(String key);
    public void removeNode(String key);
    public List<Map<String, String>> getNodeList();
}
