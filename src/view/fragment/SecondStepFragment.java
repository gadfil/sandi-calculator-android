package view.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ClipData;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.squareup.picasso.Picasso;
import ru.sandi.calculator.R;
import ru.sandi.calculator.model.adapter.DoorFrameAdapter;
import ru.sandi.calculator.model.database.LoaderIdUtil;
import ru.sandi.calculator.model.database.Provider;
import ru.sandi.calculator.model.object.DoorFrameObject;
import ru.sandi.calculator.model.table.BoxTable;
import ru.sandi.calculator.model.table.DoorContentTable;
import ru.sandi.calculator.model.table.DoorFrameTable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by gadfil on 06.10.2014.
 */
public class SecondStepFragment extends Fragment implements View.OnTouchListener, View.OnDragListener,
        LoaderManager.LoaderCallbacks<Cursor> {
    private long mBoxId;
    private long mColorBoxId;

    private Spinner mSpinnerDoorColor;
    private ListView mDoorFrameListView;
    private ListView mContentListView;
    private ImageView mDoor1;
    private ImageView mDoor2;
    private ImageView mCurrentDoor;

    private String[] testArr = new String[]{"ЗОЛОТО", "ХРОМ", "БРОНЗА", "ШАМПАНЬ"};
    private SimpleCursorAdapter mColorAdapter;
    private SimpleCursorAdapter mDoorContentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.second_step_fragment, container, false);

        mSpinnerDoorColor = (Spinner) rootView.findViewById(R.id.second_step_spinner);
        mDoorFrameListView = (ListView) rootView.findViewById(R.id.second_step_door_listView);
        mContentListView = (ListView) rootView.findViewById(R.id.second_step_listView_content);
        mDoor1 = (ImageView) rootView.findViewById(R.id.door_1);
        mDoor2 = (ImageView) rootView.findViewById(R.id.door_2);
        mCurrentDoor = (ImageView) rootView.findViewById(R.id.second_step_current_door);

        rootView.findViewById(R.id.second_step_root).setOnDragListener(this);
        mDoor1.setOnDragListener(this);
        mDoor2.setOnDragListener(this);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, testArr);
//        mSpinnerDoorColor.setAdapter(adapter);


        mColorAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_item, null,
                new String[]{DoorFrameTable.COLOR}, new int[]{android.R.id.text1}, 0);

        mDoorContentAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, null,
                new String[]{DoorContentTable.TITLE}, new int[]{android.R.id.text1}, 0);

        mSpinnerDoorColor.setAdapter(mColorAdapter);
        mContentListView.setAdapter(mDoorContentAdapter);
        ArrayList<DoorFrameObject> list = new ArrayList<DoorFrameObject>();
        list.add(new DoorFrameObject("file:///android_asset/Ramka_1_zoloto.png", "Рамка 1 золото"));
        list.add(new DoorFrameObject("file:///android_asset/Ramka_2_zoloto.png", "Рамка 2 золото"));
        list.add(new DoorFrameObject("file:///android_asset/Ramka_3_zoloto.png", "Рамка 3 золото"));
        list.add(new DoorFrameObject("file:///android_asset/Ramka_4_zoloto.png", "Рамка 4 золото"));
        list.add(new DoorFrameObject("file:///android_asset/Ramka_5_zoloto.png", "Рамка 5 золото"));
        list.add(new DoorFrameObject("file:///android_asset/Ramka_6_zoloto.png", "Рамка 6 золото"));
        list.add(new DoorFrameObject("file:///android_asset/Ramka_7_zoloto.png", "Рамка 7 золото"));

        DoorFrameAdapter doorFrameAdapter = new DoorFrameAdapter(getActivity(), R.layout.item_door_frame, list, this);
        mDoorFrameListView.setAdapter(doorFrameAdapter);

//        SELECT * FROM DOOR_CONTENT  first, DOOR_CONTENT second
//                WHERE
//        first.parent = 1 AND first.id = second.parent


        Cursor cursor = getActivity().getContentResolver().query(Provider.UNION_DOOR_CONTENT_URI,
                new String[]{
                        "first._id", "first.id","first.parent","first.type", "first.title",
                        "second._id as second__id", "second.id as second_id","second.parent as second_parent","second.type as second_type", "second.title as second_title",
                },
                " first.parent = 1088 AND first.id = second.parent  ",
                null, null
                );
        Log.d("aaa", Arrays.toString(cursor.getColumnNames()));


        Log.d("vvv", " LOADER_ID_DOOR_FRAME_COLOR ");
        getActivity().getLoaderManager().initLoader(LoaderIdUtil.LOADER_ID_DOOR_FRAME_COLOR, null, this);
        getActivity().getLoaderManager().initLoader(LoaderIdUtil.LOADER_ID_DOOR_CONTENT_PARENT_0, null, this);
        return rootView;
    }


    public static Fragment newInstance() {
        return new SecondStepFragment();
    }

    DoorFrameObject mFrameObject;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (view.getId() == R.id.image) {
//                mDoor.setX(event.getX());
//                mDoor.setY(event.getY());
//                Picasso.with(getActivity()).load(((DoorFrameObject)view.getTag()).getSrcImage()).into(mDoor);
//                mDoor.setVisibility(View.VISIBLE);
                mFrameObject = (DoorFrameObject) view.getTag();
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
//                view.setVisibility(View.INVISIBLE);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                // do nothing
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
//                v.setBackgroundDrawable(enterShape);
                break;
            case DragEvent.ACTION_DRAG_EXITED:
//                v.setBackgroundDrawable(normalShape);
                break;
            case DragEvent.ACTION_DROP:
                // Dropped, reassign View to ViewGroup
//                View view = (View) event.getLocalState();
//                ViewGroup owner = (ViewGroup) view.getParent();
//                owner.removeView(view);
//                LinearLayout container = (LinearLayout) v;
//                container.addView(view);
//                view.setVisibility(View.VISIBLE);

                switch (v.getId()) {
                    case R.id.door_1:

                        Picasso.with(getActivity()).load(mFrameObject.getSrcImage()).into(mCurrentDoor);
                        Picasso.with(getActivity()).load(mFrameObject.getSrcImage()).into(mDoor1);
                        break;
                    case R.id.door_2:

                        Picasso.with(getActivity()).load(mFrameObject.getSrcImage()).into(mCurrentDoor);
                        Picasso.with(getActivity()).load(mFrameObject.getSrcImage()).into(mDoor2);
                        break;
                }
                break;
            case DragEvent.ACTION_DRAG_ENDED:
//                v.setBackgroundDrawable(normalShape);

            default:
                break;
        }
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {

            case LoaderIdUtil.LOADER_ID_DOOR_FRAME_COLOR:
                return new CursorLoader(getActivity(),
                        Uri.parse(Provider.DOOR_FRAME_URI.toString() + "?" + Provider.DISTINCT + "=true&" + Provider.GROUP_BY + "=" + DoorFrameTable.COLOR),
                        new String[]{DoorFrameTable.COLOR, DoorFrameTable._ID},
                        null,
                        null,
                        DoorFrameTable._ID);

            case LoaderIdUtil.LOADER_ID_DOOR_CONTENT_PARENT_0:
                return new CursorLoader(getActivity(),
                        Provider.DOOR_CONTENT_URI,
                        new String[]{DoorContentTable.TITLE, DoorContentTable.ID, DoorContentTable._ID},
                        DoorContentTable.PARENT +" =  0 ",
                        null,
                        DoorFrameTable.ID);
            default:
                return null;
        }
//        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {

            case LoaderIdUtil.LOADER_ID_DOOR_FRAME_COLOR:
                mColorAdapter.swapCursor(data);
                break;
            case LoaderIdUtil.LOADER_ID_DOOR_CONTENT_PARENT_0:
                mDoorContentAdapter.swapCursor(data);
                break;
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {

            case LoaderIdUtil.LOADER_ID_DOOR_FRAME_COLOR:
                mColorAdapter.swapCursor(null);
                break;
            case LoaderIdUtil.LOADER_ID_DOOR_CONTENT_PARENT_0:
                mDoorContentAdapter.swapCursor(null);
                break;
        }

    }
}