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
import ru.sandi.calculator.model.object.ImageTextObject;

import java.util.ArrayList;

/**
 * Created by gadfil on 29.09.2014.
 */
public class ImageTextArrayAdapter extends ArrayAdapter<ImageTextObject> {

    private View.OnClickListener mListener;
    private  ArrayList<ImageTextObject> mList;
    private Context mContext;
    private int mLayoutId;

    public ImageTextArrayAdapter(Context context, int textViewResourceId, ArrayList<ImageTextObject> list, View.OnClickListener listener) {
        super(context, textViewResourceId,list);
        mListener = listener;
        mList = list;
        mContext = context;
        mLayoutId = textViewResourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
            view = inflater.inflate(mLayoutId, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.text1);
            viewHolder.image = (ImageView) view.findViewById(R.id.image);


            viewHolder.image.setOnClickListener(mListener);
            view.setTag(viewHolder);

        }else {
            view = convertView;
        }
        ViewHolder holder = (ViewHolder) view.getTag();
//        holder.id = mList.get(position).getId();
        holder.text.setText(mList.get(position).getText());
        Picasso.with(mContext).load(mList.get(position).getImage()).into(holder.image);
//        Picasso.with(mContext).load(mList.get(position).getImage()).into(holder.image);
        Log.d("log", "# " +mList.get(position).getImage() );
        holder.image.setTag( mList.get(position));
        return view;
    }

    static class ViewHolder {
        protected TextView text;
        protected ImageView image;
        protected long id;
    }

}
