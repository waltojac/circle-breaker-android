package edu.gvsu.cis.waltojac.circlebreaker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ScoreBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        Toast.makeText(this, "Score hit",
                Toast.LENGTH_LONG).show();
    }
}
