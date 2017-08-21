package pw.janyo.jysearch;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Handler;
import android.os.Message;

import pw.janyo.jysearch.util.CatchIcon;
import pw.janyo.jysearch.util.EngineManagerUtil;
import pw.janyo.jysearch.util.MyCardViewAdapter;
import pw.janyo.jysearch.util.MyOnItemClickListener;
import pw.janyo.jysearch.util.SearchEngine;
import pw.janyo.jysearch.util.WebIconTool;

/**
 * Created by DickLight on 2017/8/20.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CatchIcon{
    private RecyclerView engineListView;
    private MyCardViewAdapter myAdapter;
    private FloatingActionButton addEngineButton;

    private Handler handler;

    private View cardViewItemView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_main_recycler);

        startClipboardListenerService();

        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1)
                    updateListView();
            }
        };

        engineListView = (RecyclerView)findViewById(R.id.EngineListRecycler);
        engineListView.setLayoutManager(new LinearLayoutManager(this));
        engineListView.setItemAnimator(new DefaultItemAnimator());
        engineListView.setHasFixedSize(true);

        myAdapter = new MyCardViewAdapter(new EngineManagerUtil(this).getAllEngineToEngineArray(), this);
        engineListView.setAdapter(myAdapter);
        //myAdapter.setOnItemLongClickListener(this);
        myAdapter.setOnItemClick(new MyOnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int p) {
                Intent i = new Intent();
                i.setClass(MainActivity.this, SearchActivity.class);
                i.putExtra("EngineName", ((TextView)v.findViewById(R.id.EngineNameCardView)).getText().toString());
                i.putExtra("EngineUrl", ((TextView)v.findViewById(R.id.EngineUrlCardView)).getText().toString());
                startActivity(i);
            }
        });

        addEngineButton = (FloatingActionButton)findViewById(R.id.AddSearchEngine2);
        addEngineButton.setOnClickListener(this);

        myAdapter.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.setHeaderTitle(R.string.Option);
                MenuItem editItem = contextMenu.add(0, view.getId(), 0, R.string.Edit);
                MenuItem removeItem = contextMenu.add(0, view.getId(), 0, R.string.Delete);
                MenuItem setdefault = contextMenu.add(0, view.getId(), 0, R.string.SetDefault);
                cardViewItemView = view;
                editItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        editEngineItem(cardViewItemView);
                        return true;
                    }
                });
                removeItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        delEngineItem(cardViewItemView);
                        return true;
                    }
                });

                setdefault.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        setDefaultEngine(cardViewItemView);
                        return true;
                    }
                });
            }
        });
    }

    private void startClipboardListenerService() {
        Intent i = new Intent();
        i.setClass(this, ClipboardListenerService.class);
        startService(i);
    }

    private void updateListView()
    {
        Log.e("日志", "刷新");
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        showAddNewEngineDialog();
    }



    private void showAddNewEngineDialog() {
        LayoutInflater layout;
        View view;
        layout = LayoutInflater.from(this);
        view = layout.inflate(R.layout.add_new_engine_dialog_view, null);

        final EditText engineNameTextView = view.findViewById(R.id.newEngineName);
        final EditText engineBaseUrlTextView = view.findViewById(R.id.newEngineBaseURL);
        new AlertDialog.Builder(this)
                .setTitle(R.string.AddEngine)
                .setView(view)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addEngine(engineNameTextView.getText().toString(), engineBaseUrlTextView.getText().toString());
                    }
                })
                .create().show();
    }

    private void addEngine(final String name, final String baseUrl)
    {
        SearchEngine senine = new SearchEngine();
        senine.setBaseURL(name, baseUrl);
        new EngineManagerUtil(this, senine).addEngine();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("日志", "ICON保存线程");
                new WebIconTool().saveIconToLocal(baseUrl, MainActivity.this, name, MainActivity.this);
            }
        }).start();
        if (myAdapter.getItemCount() < 0)
        {
            setDefaultEngine(name);
        }
        myAdapter.reloadEngineList(new EngineManagerUtil(this).getAllEngineToEngineArray());
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCatchDone() {
        Message message = new Message();
        message.what = 1;
        handler.sendMessage(message);
        Log.e("日志", "完成保存");
    }


    private void editEngineItem(View cardView) {
        LayoutInflater layout;
        View view;
        layout = LayoutInflater.from(this);
        view = layout.inflate(R.layout.add_new_engine_dialog_view, null);

        final EditText engineNameTextView = view.findViewById(R.id.newEngineName);
        engineNameTextView.setText(((TextView)cardView.findViewById(R.id.EngineNameCardView)).getText().toString());
        final EditText engineBaseUrlTextView = view.findViewById(R.id.newEngineBaseURL);
        engineBaseUrlTextView.setText(((TextView)cardView.findViewById(R.id.EngineUrlCardView)).getText().toString());
        new AlertDialog.Builder(this)
                .setTitle("编辑")
                .setView(view)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addEngine(engineNameTextView.getText().toString(), engineBaseUrlTextView.getText().toString());
                    }
                })
                .create().show();
    }

    private void delEngineItem(View v) {
        TextView name = (TextView)v.findViewById(R.id.EngineNameCardView);
        new EngineManagerUtil(this).removeEngine(name.getText().toString(), this);
        myAdapter.removeItem(name.getText().toString());
        updateListView();
    }

    private void setDefaultEngine(View v)
    {
        TextView name = (TextView)v.findViewById(R.id.EngineNameCardView);
        new EngineManagerUtil(this).setDefaultEngine(name.getText().toString());
    }

    private void setDefaultEngine(String name)
    {
        new EngineManagerUtil(this).setDefaultEngine(name);
    }

}

