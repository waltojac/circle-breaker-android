package edu.gvsu.cis.waltojac.circlebreaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class PlayActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));
        intent = this.getIntent();
        Toast.makeText(this, "Play hit, Level " + intent.getStringExtra("level"),
                Toast.LENGTH_LONG).show();
    }
}
