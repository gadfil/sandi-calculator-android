package ru.sandi.calculator.controller;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import ru.sandi.calculator.R;
import ru.sandi.calculator.model.adapter.Box3dArrayAdapter;
import ru.sandi.calculator.model.database.Provider;
import ru.sandi.calculator.model.object.Box3dObject;
import ru.sandi.calculator.model.object.ImageTextObject;
import ru.sandi.calculator.model.operation.network.Api;
import ru.sandi.calculator.model.table.BoxTable;
import ru.sandi.calculator.model.table.ImageBoxTable;
import view.fragment.FirstStepFragment;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by gadfil on 27.09.2014.
 */
public class FirstStepActivity extends Activity implements FirstStep, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG_FRAGMENT_FIRST_STEP = "tag_first_step";
    private static final int LOADER_ID_BOX_AND_IMAGE = -1;
    private GridView mGridBox3d;
    private boolean isBox = false;

    //    private int m
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_activity);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, FirstStepFragment.newInstance(), TAG_FRAGMENT_FIRST_STEP)
                .commit();


    }

    @Override
    public void init() {
        mGridBox3d = (GridView) getFragmentManager()
                .findFragmentByTag(TAG_FRAGMENT_FIRST_STEP)
                .getView()
                .findViewById(R.id.grid_view_first_step_container_for_box);

    }

    @Override
    public void selectBox() {

    }

    @Override
    public void setBoxColor(String imageColor, long colorId, int position) {
        Log.d("color","dialog " +  imageColor );
        Log.d("color","dialog " + colorId);
        ((Box3dArrayAdapter)mGridBox3d.getAdapter()).setBoxColor(imageColor, position);
        
    }

    @Override
    public void setArg(int width, int height, int depth, String typeOfLaminate, String typeOfSystemDoor) {
        Bundle arg = new Bundle();
        arg.putString(BoxTable.WIDTH, String.valueOf(width));
        arg.putString(BoxTable.HEIGHT, String.valueOf(height));
        arg.putString(BoxTable.DEPTH, String.valueOf(depth));
        arg.putString(BoxTable.COLLECTION, "standard");

        if (isBox) {
            getLoaderManager().restartLoader(LOADER_ID_BOX_AND_IMAGE, arg, this);
        } else {
            isBox = true;
            getLoaderManager().initLoader(LOADER_ID_BOX_AND_IMAGE, arg, this);
        }
    }

    @Override
    public void selectBox(long _id, long id) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID_BOX_AND_IMAGE:
                Log.d("aaa", "*********");
                Log.d("aaa", args.getString(BoxTable.WIDTH));
                Log.d("aaa", args.getString(BoxTable.HEIGHT));
                Log.d("aaa", args.getString(BoxTable.DEPTH));
                Log.d("aaa", args.getString(BoxTable.COLLECTION));
                return new CursorLoader(this, Provider.UNION_BOX_IMAGE_BOX_URI,
                        new String[]{
                                BoxTable.TABLE_NAME + "." + BoxTable._ID,
                                BoxTable.TABLE_NAME + "." + BoxTable.ID,
                                BoxTable.TABLE_NAME + "." + BoxTable.NUMBER,
                                ImageBoxTable.TABLE_NAME + "." + ImageBoxTable.ID + " AS " +ImageBoxTable.TABLE_NAME + ImageBoxTable.ID,
                                ImageBoxTable.TABLE_NAME + "." + ImageBoxTable.IMAGE
                        },
                        BoxTable.ID_IMAGE_3D + "=" + ImageBoxTable.TABLE_NAME +  ImageBoxTable.ID
                                + " AND " + BoxTable.WIDTH + " =  "+ args.getString(BoxTable.WIDTH)
                                + " AND " + BoxTable.HEIGHT + " =  "+ args.getString(BoxTable.HEIGHT)
                                + " AND " + BoxTable.DEPTH + " =  "+ args.getString(BoxTable.DEPTH)
                                + " AND " + BoxTable.COLLECTION + " = \""+ args.getString(BoxTable.COLLECTION ) + "\"",
                        null,
                        null
                );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
//        Log.d("aaa", "" + cursor);
//        Log.d("aaa", "" + Arrays.toString(cursor.getColumnNames()));
//        Log.d("aaa", "" + cursor.getCount());

        if (cursor != null && cursor.moveToFirst()) {

            ArrayList<Box3dObject> list = new ArrayList<Box3dObject>();
            int number = cursor.getColumnIndexOrThrow(BoxTable.NUMBER);
            int id = cursor.getColumnIndexOrThrow(BoxTable.ID);
            int _id = cursor.getColumnIndexOrThrow(BoxTable._ID);
            int image = cursor.getColumnIndexOrThrow(ImageBoxTable.IMAGE);
            do {
                Box3dObject box3d = new Box3dObject();
                box3d.set_id(cursor.getLong(_id));
                box3d.setId(cursor.getLong(id));
                box3d.setNumber("№ " + cursor.getString(number));
                box3d.setImage(Api.BASE_IMAGE_URL + cursor.getString(image));

                list.add(box3d);
//                list.add(new ImageTextObject("№ "+ cursor.getString(number),"http://sandy.upper.sitenn.ru"+cursor.getString(image), cursor.getLong(id)));
                Log.d("log", "http://sandy.upper.sitenn.ru" + cursor.getString(image));
            } while (cursor.moveToNext());
            Box3dArrayAdapter mBox3dAdapter = new Box3dArrayAdapter(this, R.layout.item_box_image_and_number, list);
            mGridBox3d.setAdapter(mBox3dAdapter);
            Log.d("aaa", "" + Arrays.toString(cursor.getColumnNames()));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}