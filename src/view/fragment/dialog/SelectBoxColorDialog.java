package view.fragment.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import ru.sandi.calculator.R;
import ru.sandi.calculator.controller.FirstStep;
import ru.sandi.calculator.model.adapter.ImageTextArrayAdapter;
import ru.sandi.calculator.model.adapter.ImageTextCursorAdapter;
import ru.sandi.calculator.model.database.DatabaseHelper;
import ru.sandi.calculator.model.object.ImageTextObject;
import ru.sandi.calculator.model.operation.network.Api;
import ru.sandi.calculator.model.table.BoxColorTable;
import ru.sandi.calculator.model.table.ImageBoxColorTable;
import ru.sandi.calculator.model.table.ImageBoxTable;

import java.util.ArrayList;

/**
 * Created by gadfil on 29.09.2014.
 */
public class SelectBoxColorDialog extends DialogFragment   implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener{

    private static final String ARG_POSITION = "arg_position" ;
    private Context mContext;
    private int mBoxPosition = -1;
    private FirstStep mFirstStep;

    public static DialogFragment newInstance(int position){
        SelectBoxColorDialog dialog = new SelectBoxColorDialog();
        Bundle arg = new Bundle();
        arg.putInt(ARG_POSITION, position);
        dialog.setArguments(arg);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null && getArguments().containsKey(ARG_POSITION)){
            mBoxPosition = getArguments().getInt(ARG_POSITION);
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new   AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_title_select_color_box)
                .setNegativeButton(R.string.cancel, this)
                .setPositiveButton(R.string.ok, this);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_fragment_select_box_color,null);
        ListView  listView = (ListView)v.findViewById(R.id.listView);
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT BOX_COLOR._ID, BOX_COLOR.TITLE, IMAGE_BOX_COLOR.IMAGE FROM  BOX_COLOR, IMAGE_BOX_COLOR WHERE  BOX_COLOR.ID_COLOR = IMAGE_BOX_COLOR.ID ",
                null);
        ArrayList<ImageTextObject> list = new ArrayList<ImageTextObject>();
        if(cursor!=null&&cursor.moveToFirst()){

            int id = cursor.getColumnIndexOrThrow(BoxColorTable._ID);
            int text = cursor.getColumnIndexOrThrow(BoxColorTable.TITLE);
            int image = cursor.getColumnIndexOrThrow(ImageBoxColorTable.IMAGE);
            do{
                ImageTextObject object = new ImageTextObject(cursor.getString(text), Api.BASE_IMAGE_URL + cursor.getString(image), cursor.getLong(id));
                list.add(object);
            }while (cursor.moveToNext());
        }
        ImageTextArrayAdapter adapter = new ImageTextArrayAdapter(getActivity(), R.layout.item_image_text, list, null);
//        ImageTextCursorAdapter adapter = new ImageTextCursorAdapter(getActivity(),R.layout.item_image_text,
//                cursor,new String[]{BoxColorTable.TITLE, ImageBoxTable.IMAGE}, new int[]{R.id.text1, R.id.image},
//                new String[]{ImageBoxTable.IMAGE});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        builder.setView(v);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mFirstStep.setBoxColor(((ImageTextObject)parent.getAdapter().getItem(position)).getImage(),
                ((ImageTextObject)parent.getAdapter().getItem(position)).getId(), mBoxPosition );
        Log.d("color", "dialog " + ((ImageTextObject) parent.getAdapter().getItem(position)).getImage());
        Log.d("color", "dialog " + ((ImageTextObject) parent.getAdapter().getItem(position)).getId());
    }
}
