package ru.sandi.calculator.model.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gadfil on 29.09.2014.
 */
public class ImageTextCursorAdapter extends SimpleCursorAdapter  implements SimpleCursorAdapter.ViewBinder {
    private Context mContext;
    private HashMap<Integer, String> mImageMap;

    public ImageTextCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, String[]fromImage) {
        super(context, layout, c, from, to, 0);
        mContext=context;
        setViewBinder(this);
        mImageMap = new HashMap<Integer, String>();
        for(int i = 0; i< fromImage.length; i ++){
            mImageMap.put(c.getColumnIndex(fromImage[i]), fromImage[i]);
        }

    }

    @Override
    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
        if(mImageMap.containsKey(columnIndex)){
            ImageView imageView = (ImageView)view;
            Picasso.with(mContext).load("http://sandy.upper.sitenn.ru"+cursor.getString(columnIndex)).into(imageView);
        }
        return false;
    }
}
