package edu.gvsu.cis.waltojac.circlebreaker;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
