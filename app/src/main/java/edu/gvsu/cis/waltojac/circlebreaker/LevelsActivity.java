package edu.gvsu.cis.waltojac.circlebreaker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class LevelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        Toast.makeText(this, "Levels hit",
                Toast.LENGTH_LONG).show();
    }
}
