package pw.janyo.jysearch.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DickLight on 2017/8/21.
 */

public class SearchHistory {
    protected static final String NEW_LINE = "\n";
    public static void saveHistory(String keyWord, Context context)
    {
        try
        {
            RandomAccessFile randomAccessFile = new RandomAccessFile(MyFileHelper.getHistoryFilePath(context), "rw");
            randomAccessFile.seek(0);
            randomAccessFile.write(NEW_LINE.getBytes());
            randomAccessFile.seek(0);
            randomAccessFile.write(keyWord.getBytes());
            randomAccessFile.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static List<String> getHisttoryList(Context context)
    {
        List<String> list = new ArrayList<String>();
        if ( !new File(MyFileHelper.getHistoryFilePath(context)).exists())
        {
            return list;
        }
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(MyFileHelper.getHistoryFilePath(context)));
            String swap = null;
            while((swap = bufferedReader.readLine()) != null)
            {
                list.add(swap);
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        return list;
    }
}
