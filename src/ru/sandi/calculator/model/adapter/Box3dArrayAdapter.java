package ru.sandi.calculator.model.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.squareup.picasso.Picasso;
import ru.sandi.calculator.R;
import ru.sandi.calculator.model.object.Box3dObject;

import java.util.ArrayList;

/**
 * Created by gadfil on 30.09.2014.
 */
public class Box3dArrayAdapter extends ArrayAdapter<Box3dObject> {
    private Context mContext;
    private ArrayList<Box3dObject>mList;
    private int mSelect = -1;


    public Box3dArrayAdapter(Context context, int textViewResourceId, ArrayList<Box3dObject> list) {
        super(context, textViewResourceId, list);
        mList = list;
        mContext = context;
    }



    public void selectBox(int position){
        mSelect = position;
        notifyDataSetChanged();
    }


    public void setBoxColor(String color, int position){

        mList.get(position).setColor(color);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
            view = inflater.inflate(R.layout.item_box_image_and_number, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.text1);
            viewHolder.image = (ImageView) view.findViewById(R.id.image);
            viewHolder.color = (ImageView) view.findViewById(R.id.image_background);
            viewHolder.layout = (RelativeLayout) view.findViewById(R.id.root);


            view.setTag(viewHolder);

        }else {
            view = convertView;
        }
        ViewHolder holder = (ViewHolder) view.getTag();
//        holder.id = mList.get(position).getId();
        holder.text.setText(mList.get(position).getNumber());
        Picasso.with(mContext).load(mList.get(position).getImage()).into(holder.image);
        if(mList.get(position).getColor() !=null){
            Picasso.with(mContext).load(mList.get(position).getColor()).into(holder.color);
        }
        if(position== mSelect){
            holder.layout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.selected_box));

        }else {
            holder.layout.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent) );
        }

//        Picasso.with(mContext).load(mList.get(position).getImage()).into(holder.image);
        Log.d("log", "# " + mList.get(position).getImage());
        holder.image.setTag( mList.get(position));
        return view;
    }


    static class ViewHolder {
        protected TextView text;
        protected ImageView image;
        protected ImageView color;
        protected RelativeLayout layout;
    }
}
