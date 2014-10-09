package view.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import ru.sandi.calculator.R;
import ru.sandi.calculator.controller.FirstStep;
import ru.sandi.calculator.model.adapter.Box3dArrayAdapter;
import ru.sandi.calculator.model.database.DatabaseHelper;
import ru.sandi.calculator.model.database.LoaderIdUtil;
import ru.sandi.calculator.model.database.Provider;
import ru.sandi.calculator.model.object.Box3dObject;
import ru.sandi.calculator.model.operation.network.Api;
import ru.sandi.calculator.model.table.BoxTable;
import ru.sandi.calculator.model.table.DoorTable;
import ru.sandi.calculator.model.table.ImageBoxTable;
import view.fragment.dialog.SelectBoxColorDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by gadfil on 27.09.2014.
 */
public class FirstStepFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener,
        AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {


    private RadioGroup mRadioGroupDepth;
    private GridView mGridViewContainerForBox;
    private Spinner mSpinnerWidth;
    private Spinner mSpinnerHeight;
    private Spinner mSpinnerTypeOfLaminate;
    private Spinner mSpinnerTypeOfSystemDoor;
    private SimpleCursorAdapter mAdapterHeight;
    private SimpleCursorAdapter mAdapterWidth;
    private SimpleCursorAdapter mAdapterTypeOfSystemsDoor;
    private Box3dArrayAdapter mBox3dAdapter;
    private FirstStep mFirstStep;
    private int mArgWidth = -1;
    private int mArgHeight = -1;
    private int mArgDepth = -1;
    private String mArgSpinnerTypeOfLaminate = null;
    private String mArgTypeOfSystemDoor = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.first_step_fragment, container, false);
        findViews(rootView);
        getActivity().getContentResolver().query(Provider.IMAGE_BOX_URI, null, null, null, null);
        mAdapterWidth = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_spinner_item,
                null,
                new String[]{BoxTable.WIDTH, BoxTable._ID},
                new int[]{android.R.id.text1},
                0);
        mSpinnerWidth.setAdapter(mAdapterWidth);


        mAdapterHeight = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_spinner_item,
                null,
                new String[]{BoxTable.HEIGHT, BoxTable._ID},
                new int[]{android.R.id.text1},
                0);
        mSpinnerHeight.setAdapter(mAdapterHeight);


        mAdapterTypeOfSystemsDoor = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_spinner_item,
                null,
                new String[]{DoorTable.TITLE, DoorTable._ID},
                new int[]{android.R.id.text1},
                0);
        mSpinnerTypeOfSystemDoor.setAdapter(mAdapterTypeOfSystemsDoor);

        getActivity().getLoaderManager().initLoader(LoaderIdUtil.LOADER_ID_WIDTH, null, this);
        getActivity().getLoaderManager().initLoader(LoaderIdUtil.LOADER_ID_HEIGHT, null, this);
        getActivity().getLoaderManager().initLoader(LoaderIdUtil.LOADER_ID_DEPTH, null, this);
        getActivity().getLoaderManager().initLoader(LoaderIdUtil.LOADER_ID_TYPE_OF_SYSTEM_DOOR, null, this);

//        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
//        SQLiteDatabase database = dbHelper.getWritableDatabase();
//
//        Cursor cursor = database.rawQuery("SELECT BOX._id,BOX.id, BOX.NUMBER, IMAGE_BOX.IMAGE FROM BOX, IMAGE_BOX WHERE BOX.ID_IMAGE_3D = IMAGE_BOX.ID",
//                null);
//        if (cursor != null && cursor.moveToFirst()) {
//            int number = cursor.getColumnIndexOrThrow(BoxTable.NUMBER);
//            int id = cursor.getColumnIndexOrThrow(BoxTable.ID);
//            int _id = cursor.getColumnIndexOrThrow(BoxTable._ID);
//            int image = cursor.getColumnIndexOrThrow(ImageBoxTable.IMAGE);
//            ArrayList<Box3dObject> list = new ArrayList<Box3dObject>();
//            do {
//                Box3dObject box3d = new Box3dObject();
//                box3d.set_id(cursor.getLong(_id));
//                box3d.setId(cursor.getLong(id));
//                box3d.setNumber("№ " + cursor.getString(number));
//                box3d.setImage(Api.BASE_IMAGE_URL + cursor.getString(image));
//
//                list.add(box3d);
////                list.add(new ImageTextObject("№ "+ cursor.getString(number),"http://sandy.upper.sitenn.ru"+cursor.getString(image), cursor.getLong(id)));
//                Log.d("log", "http://sandy.upper.sitenn.ru" + cursor.getString(image));
//            } while (cursor.moveToNext());
//            mBox3dAdapter = new Box3dArrayAdapter(getActivity(), R.layout.item_box_image_and_number, list);
//            mGridViewContainerForBox.setAdapter(mBox3dAdapter);
//        }
//        Log.d("log", "" + Arrays.toString(cursor.getColumnNames()));
        mGridViewContainerForBox.setOnItemClickListener(this);
//        new SelectBoxColorDialog().show(getFragmentManager(),"fd");

        mSpinnerWidth.setOnItemSelectedListener(this);
        mSpinnerHeight.setOnItemSelectedListener(this);
        mSpinnerTypeOfLaminate.setOnItemSelectedListener(this);
        mSpinnerTypeOfSystemDoor.setOnItemSelectedListener(this);
        mRadioGroupDepth.setOnCheckedChangeListener(this);

        return rootView;
    }

    private void findViews(View rootView) {
        mRadioGroupDepth = (RadioGroup) rootView.findViewById(R.id.radio_group_first_step_container_for_depth);
        mGridViewContainerForBox = (GridView) rootView.findViewById(R.id.grid_view_first_step_container_for_box);
        mSpinnerWidth = (Spinner) rootView.findViewById(R.id.spinner_first_step_width);
        mSpinnerHeight = (Spinner) rootView.findViewById(R.id.spinner_first_step_height);
        mSpinnerTypeOfLaminate = (Spinner) rootView.findViewById(R.id.spinner_first_step_type_of_laminate);
        mSpinnerTypeOfSystemDoor = (Spinner) rootView.findViewById(R.id.spinner_first_step_type_of_system_door);
    }

    public static Fragment newInstance() {
        return new FirstStepFragment();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LoaderIdUtil.LOADER_ID_WIDTH:
                return new CursorLoader(getActivity(),
                        Uri.parse(Provider.BOX_URI.toString() + "?" + Provider.DISTINCT + "=true&" + Provider.GROUP_BY + "=" + BoxTable.WIDTH),
                        new String[]{BoxTable.WIDTH, BoxTable._ID},
                        null,
                        null,
                        BoxTable.WIDTH);

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mFirstStep = (FirstStep) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement FirstStep.");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirstStep.init();
        sendArg();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        try {
            switch (parent.getId()){
                case R.id.spinner_first_step_width:
                    mArgWidth = Integer.parseInt(((TextView)view).getText().toString());                    
                    break;
                case R.id.spinner_first_step_height:
                    mArgHeight = Integer.parseInt(((TextView)view).getText().toString());                    
                    break;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        sendArg();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }

    private void sendArg(){
        if(mArgHeight>0&&mArgWidth>0&&mArgDepth>0){
            mFirstStep.setArg(mArgWidth, mArgHeight, mArgDepth,"", "");
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton radioButton =(RadioButton) getView().findViewById(checkedId);
        mArgDepth = Integer.parseInt( radioButton.getText().toString());
        sendArg();

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}