package edu.gvsu.cis.waltojac.circlebreaker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Toast.makeText(this, "Play hit",
                Toast.LENGTH_LONG).show();
    }
}
