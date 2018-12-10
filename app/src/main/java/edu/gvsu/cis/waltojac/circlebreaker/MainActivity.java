package edu.gvsu.cis.waltojac.circlebreaker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.gvsu.cis.waltojac.circlebreaker.dummy.LevelContent;
import edu.gvsu.cis.waltojac.circlebreaker.dummy.ScoreContent;

public class MainActivity extends AppCompatActivity {

    public static final int RC_SIGN_IN = 1;
    public static final int PLAY_RV = 1;
    public static final int LEVELS_RV = 1;
    public static final int SCORE_RV = 1;
    private boolean online = true;
    private Menu menu;
    FirebaseUser user;
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    private ArrayList<ScoreReport> list = new ArrayList<ScoreReport>();
    protected int topLevel = 1;


    @Override
    protected void onResume() {
        super.onResume();

        Intent in = getIntent();

        if (in.hasExtra("online")) {
            this.online = in.getBooleanExtra("online", false);
            Log.d("JAKEEEEEE", "Has Extra: " + this.online);

        }

        if (online) {
            loadScore();
            loadLevels();
            user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Toast.makeText(this, "On Main",
                        Toast.LENGTH_LONG).show();
            } else {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(i, RC_SIGN_IN);
            }
        }


    }

    private void loadLevels() {
        Log.d("JAKEEEEEEE", "Loading level data...");
        topLevel = 1;
        dbRef.child("scores").child("waltojac10").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("JAKEEEEEEE", "Recieved level...");
                ScoreReport s = dataSnapshot.getValue(ScoreReport.class);
                topLevel = Integer.parseInt(s.level);
                fillLevels();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    protected void fillLevels() {
        LevelContent.clear();
        for (int i = 1; i <= this.topLevel; i++) {
            LevelContent.addItem(new LevelContent.LevelItem(Integer.toString(i)));
        }
    }

    protected void loadScore() {
        list.clear();
        Log.d("JAKEEEEEEE", "Loading firebase data...");

        dbRef.child("scores").orderByChild("level").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot data, @Nullable String str) {
                ScoreReport s = data.getValue(ScoreReport.class);
                list.add(s);
                //ScoreContent.addItem(new ScoreContent.ScoreItem(Integer.toString(i[0]), s.username, s.level));
                Log.d("JAKEEEEEEE", "Added to list: " + s.username + s.level);
                fillScore();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("JAKEEEEEEE", "onCancelled: " + databaseError);
            }
        });



    }

    protected void fillScore () {
        final int[] i = {1};
        ScoreContent.clear();

        Collections.sort(list, new sortByLevel());

        for (ScoreReport s : list) {
            Log.d("JAKEEEEEEE", "Added an Item: " + s + i[0] + s.username + s.level);
            ScoreContent.addItem(new ScoreContent.ScoreItem(Integer.toString(i[0]), s.username, s.level));
            i[0]++;

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        /*for (int i = 1; i<20; i++){
            ScoreReport item = new ScoreReport( "waltojac" + Integer.toString(i), Integer.toString(i));
            dbRef.child("scores").child("waltojac" + Integer.toString(i)).setValue(item);
        }*/



        Button playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Send to play
                Intent i = new Intent(MainActivity.this, PlayActivity.class);
                startActivityForResult(i, PLAY_RV);
            }
        });

        Button levelsButton = (Button) findViewById(R.id.levelsButton);
        levelsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send to levels
                Intent i = new Intent(MainActivity.this, LevelsActivity.class);
                startActivityForResult(i, LEVELS_RV);
            }
        });

        Button scoreButton = (Button) findViewById(R.id.scoreButton);
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send to levels
                Intent i = new Intent(MainActivity.this, ScoreBoardActivity.class);
                startActivityForResult(i, SCORE_RV);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        this.menu = menu;
        if (!this.online) {
            menu.findItem(R.id.logout_item).setEnabled(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_item:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(i, RC_SIGN_IN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

class sortByLevel implements Comparator<ScoreReport>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(ScoreReport a, ScoreReport b)
    {
        return Integer.parseInt(b.level) - Integer.parseInt(a.level);
    }
}
