package edu.gvsu.cis.waltojac.circlebreaker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import edu.gvsu.cis.waltojac.circlebreaker.dummy.ScoreContent;

public class ScoreBoardActivity extends AppCompatActivity implements ScoreFragment.OnListFragmentInteractionListener {


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        Toast.makeText(this, "Score hit",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onListFragmentInteraction(ScoreContent.ScoreItem item) {
        System.out.println("Interact");
    }
}
