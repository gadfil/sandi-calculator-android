package ru.sandi.calculator.model.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import ru.sandi.calculator.R;
import ru.sandi.calculator.model.object.DoorFrameObject;

import java.util.ArrayList;

/**
 * Created by gadfil on 25.09.2014.
 */
public class DoorFrameAdapter extends ArrayAdapter<DoorFrameObject> {
    private ArrayList<DoorFrameObject> mList;
    private Context mContext;
    private View.OnTouchListener mOnTouchListener;
    public DoorFrameAdapter(Context context, int textViewResourceId, ArrayList<DoorFrameObject> list, View.OnTouchListener listener) {
        super(context, textViewResourceId, list);
        mList = list;
        mContext = context;
        this.mOnTouchListener = listener;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
            view = inflater.inflate(R.layout.item_door_frame, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.text1);
            viewHolder.image = (ImageView) view.findViewById(R.id.image);


            viewHolder.image.setOnTouchListener(mOnTouchListener);
            view.setTag(viewHolder);

        }else {
            view = convertView;
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(mList.get(position).getTitle());
        Picasso.with(mContext).load(mList.get(position).getSrcImage()).into(holder.image);
        holder.image.setTag( mList.get(position));
        return view;
    }

    static class ViewHolder {
        protected TextView text;
        protected ImageView image;
    }
}
