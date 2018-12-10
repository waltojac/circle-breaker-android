package edu.gvsu.cis.waltojac.circlebreaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class PlayActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = this.getIntent();
        setContentView(new GameView(this, Integer.parseInt(intent.getStringExtra("level"))));
        Toast.makeText(this, "Play hit, Level " + intent.getStringExtra("level"),
                Toast.LENGTH_LONG).show();
    }

    public void won(Game game) {
        Log.d("score", "Won");
        game.remake(game.seedLvl + 1);
    }

    public void lost(Game game) {
        Log.d("score", "Lost");
        game.remake(game.seedLvl);
    }
}
