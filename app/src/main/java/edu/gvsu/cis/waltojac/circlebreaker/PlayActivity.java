package edu.gvsu.cis.waltojac.circlebreaker;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        super.onCreate(savedInstanceState);
        intent = this.getIntent();

        setContentView(new GameView(this, Integer.parseInt(intent.getStringExtra("level"))));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toast.makeText(this, "Play hit, Level " + intent.getStringExtra("level"),
                Toast.LENGTH_LONG).show();
    }

    public void won(Game game) {
        Log.d("connorrrrrr", "Won");
        game.remake(game.seedLvl + 1);
        Log.d("connorrrrrr", "Won1");

        //user = FirebaseAuth.getInstance().getCurrentUser();
        item = new ScoreReport( user.getDisplayName(), Integer.toString(game.seedLvl + 1));
        Log.d("connorrrrrr", "Won2");

        dbRef = FirebaseDatabase.getInstance().getReference();
        Log.d("connorrrrrr", user.getIdToken(true).toString());

        Log.d("connorrrrrr", "Won4");
    }

    public void updateData() {
        dbRef.child("scores").push().setValue(item);
    }

    public void lost(Game game) {
        Log.d("score", "Lost");
        game.remake(game.seedLvl);
    }
}
