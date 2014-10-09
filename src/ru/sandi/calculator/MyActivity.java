package ru.sandi.calculator;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.squareup.picasso.Picasso;
import ru.sandi.calculator.model.adapter.DoorFrameAdapter;
import ru.sandi.calculator.model.object.DoorFrameObject;

import java.util.ArrayList;

import android.view.ViewGroup.LayoutParams;

public class MyActivity extends Activity implements View.OnTouchListener {

    private ListView mListView;
    private ImageView door1;
//    private RelativeLayout root;
    LayoutParams imageParams;
    int eX, eY;
    int topY, leftX, rightX, bottomY;
    private int offset_x = 0;
    private int offset_y = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);

        setContentView(R.layout.test_door_drag_and_drop);

        ArrayList<DoorFrameObject> list = new ArrayList<DoorFrameObject>();
        list.add(new DoorFrameObject("file:///android_asset/Ramka_1_zoloto.png", "Рамка 1 золото"));
        list.add(new DoorFrameObject("file:///android_asset/Ramka_2_zoloto.png", "Рамка 2 золото"));
        list.add(new DoorFrameObject("file:///android_asset/Ramka_3_zoloto.png", "Рамка 3 золото"));
        list.add(new DoorFrameObject("file:///android_asset/Ramka_4_zoloto.png", "Рамка 4 золото"));
        list.add(new DoorFrameObject("file:///android_asset/Ramka_5_zoloto.png", "Рамка 5 золото"));
        list.add(new DoorFrameObject("file:///android_asset/Ramka_6_zoloto.png", "Рамка 6 золото"));
        list.add(new DoorFrameObject("file:///android_asset/Ramka_7_zoloto.png", "Рамка 7 золото"));

//        root = (RelativeLayout) findViewById(R.id.content);
        View root = findViewById(android.R.id.content).getRootView();
        door1 = (ImageView) findViewById(R.id.door1);
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setOnTouchListener(this);
        DoorFrameAdapter adapter = new DoorFrameAdapter(this, R.layout.item_door_frame, list, this);
//        root.setOnTouchListener(this);
        mListView.setAdapter(adapter);
        root.setOnTouchListener(this);

        door1.setOnDragListener(new MyDragListener());

    }

//    private boolean i
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("log", "# ACTION_DOWN x = " + event.getX() + ", y = " + event.getY());
                if(v.getId() == R.id.image){
                    Picasso.with(this).load(((DoorFrameObject)v.getTag()).getSrcImage()).into(door1);
                    door1.setVisibility(View.VISIBLE);
                   ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDrag(data, shadowBuilder, v, 0);
                    v.setVisibility(View.INVISIBLE);
                    return true;
                }
                break;

            case MotionEvent.ACTION_UP:
                Log.d("log", "# ACTION_UP  x = " + event.getX() + ", y = " + event.getY());

                // not box door
//                if(true){
//                    door1.setVisibility(View.GONE);
//                }

                break;
            case MotionEvent.ACTION_MOVE:
//                Log.d("log", "# ACTION_MOVE  x = " + event.getX() + ", y = " + event.getY());
//                eX = (int) event.getX();
//                eY = (int) event.getY();
//                int x = (int) event.getX() - offset_x;
//                int y = (int) event.getY() - offset_y;
//                int w = getWindowManager().getDefaultDisplay().getWidth() - 50;
//                int h = getWindowManager().getDefaultDisplay().getHeight() - 10;
//                if (x > w) x = w;
//                if (y > h) y = h;
//                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
//                lp.setMargins((int)event.getX(), (int)event.getY(),
//                        (int)event.getX() + getResources().getDimensionPixelOffset(R.dimen.image_door_width),
//                        (int)event.getY() + getResources().getDimensionPixelOffset(R.dimen.image_door_height));
//
////                door1.se  tX(v.getX());
////                door1.setY(v.getY());
//                door1.setLayoutParams(lp);
//                door1.bringToFront();
                break;
            default:
                break;
        }

        return false;
    }

    class MyDragListener implements View.OnDragListener {
//        Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
//        Drawable normalShape = getResources().getDrawable(R.drawable.shape);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
//                    v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
//                    v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
//                    v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }
    }



}
