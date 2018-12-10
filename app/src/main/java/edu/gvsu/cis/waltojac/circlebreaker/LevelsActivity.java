package edu.gvsu.cis.waltojac.circlebreaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import edu.gvsu.cis.waltojac.circlebreaker.dummy.LevelContent;

public class LevelsActivity extends AppCompatActivity implements LevelFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        Toast.makeText(this, "Levels hit",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onListFragmentInteraction(LevelContent.LevelItem item) {
        Log.d("JAKEEEEEEE", "Interact" + item);
        //start intent with level item.id
        Intent i = new Intent(this, PlayActivity.class);
        i.putExtra("level", item.id);
        startActivity(i);
    }
}
