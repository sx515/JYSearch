package pw.janyo.jysearch.util;


import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pw.janyo.jysearch.R;

/**
 * Created by DickLight on 2017/8/20.
 */

public class MyCardViewAdapter extends android.support.v7.widget.RecyclerView.Adapter<ViewHolder>{

    private List<EngineI>  list;
    private Context context;
    private MyOnItemClickListener listener;
    private MyOnItemLongClickListener longClickListener;
    private View.OnCreateContextMenuListener onCreateContextMenuListener;


    public MyCardViewAdapter(List<EngineI> list, Context c)
    {
        this.list = list;
        this.context =c;
    }

    public void addItem(EngineI engineI)
    {
        list.add(engineI);
    }

    public void removeItem(String engineName)
    {
        for(EngineI e : list)
        {
            if (e.getName() == engineName)
                list.remove(e);
        }
    }

    public void reloadEngineList(List<EngineI> list)
    {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_content, parent, false);

        return new ViewHolder(v, listener, longClickListener, onCreateContextMenuListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EngineI engine = list.get(position);
        holder.engineName.setText(engine.getName());
        holder.engineUrl.setText(engine.getBaseURL());
        holder.imageView.setImageDrawable(engine.getWebIcon());

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClick(MyOnItemClickListener myOnItemClickListener)
    {
        this.listener = myOnItemClickListener;
    }

    public void setOnItemLongClickListener(MyOnItemLongClickListener myOnItemLongClickListener)
    {
        this.longClickListener = myOnItemLongClickListener;
    }

    public void setOnCreateContextMenuListener(View.OnCreateContextMenuListener onCreateContextMenuListener)
    {
        this.onCreateContextMenuListener = onCreateContextMenuListener;
    }

}

class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements View.OnClickListener,
        View.OnLongClickListener, View.OnCreateContextMenuListener
{
    public ImageView imageView;
    public TextView engineName, engineUrl;

    private MyOnItemClickListener myOnItemClickListener;
    private MyOnItemLongClickListener myOnItemLongClickListener;
    private View.OnCreateContextMenuListener onCreateContextMenuListener;

    private View v;


    public ViewHolder(View v, MyOnItemClickListener myOnItemClickListener,
                      MyOnItemLongClickListener myOnItemLongClickListener,
                      View.OnCreateContextMenuListener onCreateContextMenuListener
                      )
    {
        super(v);
        this.v = v;
        imageView = (ImageView)v.findViewById(R.id.EngineIcon);
        engineName = (TextView)v.findViewById(R.id.EngineNameCardView);
        engineUrl = (TextView)v.findViewById(R.id.EngineUrlCardView);
        this.myOnItemClickListener = myOnItemClickListener;
        this.myOnItemLongClickListener = myOnItemLongClickListener;
        this.onCreateContextMenuListener = onCreateContextMenuListener;

        if (myOnItemClickListener != null) v.setOnClickListener(this);
        if (myOnItemLongClickListener != null)v.setOnLongClickListener(this);
        if (onCreateContextMenuListener != null && myOnItemLongClickListener == null){
            v.setOnCreateContextMenuListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        if (myOnItemClickListener != null)
        {
            myOnItemClickListener.onItemClickListener(view, getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (myOnItemLongClickListener != null)
        {
            myOnItemLongClickListener.onItemLongClickListener(view, getAdapterPosition());
        }
        return false;
    }


    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        if (onCreateContextMenuListener != null)
            this.onCreateContextMenuListener.onCreateContextMenu(contextMenu, view, contextMenuInfo);
    }

}

