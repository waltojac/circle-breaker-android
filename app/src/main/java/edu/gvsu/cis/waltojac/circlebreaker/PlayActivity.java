package edu.gvsu.cis.waltojac.circlebreaker;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PlayActivity extends AppCompatActivity {
    Intent intent;
    FirebaseUser user;
    String idToken;
    DatabaseReference dbRef;
    ScoreReport item;
    GameView gv;
    int topLevel = 1;
    int highScore = 1;
    String scoreKey;


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference();
        intent = this.getIntent();

        if (intent.hasExtra("topLevel")) {
            this.topLevel = Integer.parseInt(intent.getStringExtra("topLevel"));
            gv = new GameView(this, Integer.parseInt(intent.getStringExtra("topLevel")));
        } else {
            gv = new GameView(this, Integer.parseInt(intent.getStringExtra("level")));
        }

        setContentView(gv);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    public void won(Game game) {
        Log.d("score", "won: ");

        if (isNetworkAvailable()) {
            if (scoreKey != null) {
                if ((game.seedLvl + 1) > highScore) {
                    updateScore(game.seedLvl + 1);
                }
            } else {
                getScoreRef(game, game.seedLvl + 1);
            }
        }

        topLevel = game.seedLvl + 1;

        game.remake(game.seedLvl + 1);

    }

    private void updateScore(int score) {
        item = new ScoreReport(user.getDisplayName(), user.getEmail(), Integer.toString(score));
        dbRef.child("scores").child(scoreKey).setValue(item);
    }

    public void getScoreRef(final Game game, final int i) {
        dbRef.child("scores").orderByChild("email").equalTo(this.user.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    item = new ScoreReport(user.getDisplayName(), user.getEmail(), Integer.toString(2));
                    scoreKey = dbRef.child("scores").push().getKey();
                    dbRef.child("scores").child(scoreKey).setValue(item);
                    highScore = 2;
                } else {
                    for (DataSnapshot score : dataSnapshot.getChildren()) {
                        scoreKey = score.getKey();
                        highScore = Integer.parseInt(score.getValue(ScoreReport.class).level);
                    }
                }
                if (i > highScore) {
                    updateScore(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void lost(Game game) {
        Log.d("score", "Lost");
        game.remake(game.seedLvl);

        topLevel = game.seedLvl;

        //createPopup();
        //startActivity(new Intent(PlayActivity.this, popUpActivity.class));  //Only version of popup I can make thus far
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("topLevel", topLevel);
        setResult(RESULT_OK, intent);
        finish();
    }
}
