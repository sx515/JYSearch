package pw.janyo.jysearch;

import android.app.AlertDialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import java.util.List;

import pw.janyo.jysearch.util.EngineManagerUtil;
import pw.janyo.jysearch.util.SearchEngine;
import pw.janyo.jysearch.util.SearchHistory;

/**
 * Created by DickLight on 2017/8/21.
 */

public class SearchActivity extends AppCompatActivity{

    private String engineUrl, engineName;
    private EditText keyWordEditView;
    private TextView usingEngineName;
    private ListView historyList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        engineName = intent.getStringExtra("EngineName");
        engineUrl = intent.getStringExtra("EngineUrl");

        keyWordEditView = (EditText)findViewById(R.id.SearchActivityKeyWord);
        usingEngineName = (TextView)findViewById(R.id.UsingEngineName);
        historyList = (ListView)findViewById(R.id.SearchHistoryListView);

        if (engineName == null || engineUrl == null)
        {
            SearchEngine s = new EngineManagerUtil(this).getDefaultEngine();
            engineName = s.getName();
            engineUrl = s.getBaseURL();
            if (intent.getStringExtra("KeyWord") != null)
            {
                keyWordEditView.setText(intent.getStringExtra("KeyWord"));
            }
        }

        usingEngineName.setText(getResources().getString(R.string.Using) + engineName);
        keyWordEditView.setFocusable(true);
        keyWordEditView.setFocusableInTouchMode(true);
        keyWordEditView.requestFocus();

        buildHistoryList();

        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                keyWordEditView.setText(((TextView)view.findViewById(android.R.id.text1)).getText().toString());
            }
        });

        usingEngineName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEngineListDialog();
            }
        });

        keyWordEditView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    SearchHistory.saveHistory(keyWordEditView.getText().toString(), SearchActivity.this);
                    SearchEngine se = new SearchEngine();
                    se.setBaseURL(engineUrl);
                    se.JumpToSearch(getApplicationContext(), textView.getText().toString());

                    finish();
                    return true;
                }
                return false;
            }
        });
    }

    private void buildHistoryList() {
        List<String> list = SearchHistory.getHisttoryList(this);
        String stringArray[];
        if (list.size() != 0)
        {
            if (list.size() > 5)
            {
                stringArray = new String[5];
                for (int i=0; i<5; i++)
                    stringArray[i] = list.get(i);
            }
            else
            {
                stringArray = new String[list.size()];
                list.toArray(stringArray);
            }
            historyList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringArray));
        }

    }

    private void showEngineListDialog() {
        View v = LayoutInflater.from(this).inflate(R.layout.search_activity_engine_list_view, null);
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.ChooseEngine)
                .setView(v)
                .create();
        dialog.show();

        ListView lv = (ListView)v.findViewById(R.id.DialogEngineListView);
        ListAdapter adapter = new SimpleAdapter(this, new EngineManagerUtil(this).getAllEngine(), R.layout.engine_list_view, new String[]{"EngineName", "BaseURL"}, new int[]{R.id.lvv_enginename, R.id.lvv_enginebaseurl});
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                engineName = ((TextView)view.findViewById(R.id.lvv_enginename)).getText().toString();
                usingEngineName.setText(R.string.Using + engineName);
                engineUrl = ((TextView)view.findViewById(R.id.lvv_enginebaseurl)).getText().toString();
                dialog.cancel();
            }
        });

    }
}
