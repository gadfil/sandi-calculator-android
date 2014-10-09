package ru.sandi.calculator.controller;

import android.app.Activity;
import android.os.Bundle;
import ru.sandi.calculator.R;
import view.fragment.FirstStepFragment;
import view.fragment.SecondStepFragment;

/**
 * Created by gadfil on 30.09.2014.
 */
public class SecondStepActivity extends Activity {

    private static final String TAG_FRAGMENT_SECOND_STEP = "tag_fragment_second_step";
    private static final String ARG_BOX_ID = "arg_box_id";

    private long mBoxId;
    private long mImageBoxId;
    private long mBoxColorId;
    private String mImageBox;
    private String mImageBoxColor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_activity);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, SecondStepFragment.newInstance(), TAG_FRAGMENT_SECOND_STEP)
                .commit();

    }
}