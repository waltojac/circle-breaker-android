package edu.gvsu.cis.waltojac.circlebreaker;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Constraints;
import android.util.DisplayMetrics;
import android.util.Log;

public class popUpActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("score", "Lost");

        setContentView(R.layout.popup_window);

        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(Constraints.LayoutParams.WRAP_CONTENT, Constraints.LayoutParams.WRAP_CONTENT);
    }
}
