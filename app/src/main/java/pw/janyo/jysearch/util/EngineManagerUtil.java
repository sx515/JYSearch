package pw.janyo.jysearch.util;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pw.janyo.jysearch.MyApp;

/**
 * Created by DickLight on 2017/8/19.
 */

public class EngineManagerUtil {
    private MyPreferences EngineNodeList;
    private SearchEngine engine;
    private Context context;
    public EngineManagerUtil(Context c, SearchEngine engine)
    {
        this.context = c;
        EngineNodeList = new MyPreferences(c);
        this.engine = engine;
    }


    public EngineManagerUtil(Context c)
    {
        this.context = c;
        EngineNodeList = new MyPreferences(c);
    }

    public void addEngine()
    {
        EngineNodeList.addNode(engine.getName(), engine.getBaseURL());
    }

    public void removeEngine(String Name, Context context)
    {
        EngineNodeList.removeNode(Name);
        File engineIconDir = new File(MyFileHelper.getCachePrivatePath(context) + "/" + Name);
        if (engineIconDir.exists() && engineIconDir.isDirectory())
            engineIconDir.delete();
    }

    public void setDefaultEngine(String engineName)
    {
        EngineNodeList.setDefaultEngineConfig(engineName);
    }
    public SearchEngine getDefaultEngine()
    {
        String defaultEngineName;
        SearchEngine searchEngine = new SearchEngine();
        defaultEngineName = EngineNodeList.getDefaultEngineConfig();
        Log.e("日志", "默认引擎 " + defaultEngineName);
        searchEngine.setBaseURL(defaultEngineName, getEngineUrlByName(defaultEngineName).getBaseURL());
        return searchEngine;
    }

    public EngineI getEngineUrlByName(String name)
    {
        List<EngineI> list = getAllEngineToEngineArray();
        for (EngineI engineI : list)
        {
            if (engineI.getName().equals(name))
            {
                Log.e("日志", "根据引擎名称找到");
                return engineI;
            }
        }
        return null;
    }

    public List<Map<String, String>> getAllEngine()
    {
        return EngineNodeList.getNodeList();
    }

    public List<EngineI> getAllEngineToEngineArray()
    {
        List<EngineI> engineList = new ArrayList<EngineI>();

        for (Map<String, String> map : EngineNodeList.getNodeList())
        {
            EngineI e = new EngineI(map.get("EngineName"), map.get("BaseURL"), WebIconTool.getIconFromLocal(map.get("EngineName"), context));
            engineList.add(e);
        }
        return engineList;
    }
}

