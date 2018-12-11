package edu.gvsu.cis.waltojac.circlebreaker;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
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


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (this.isNetworkAvailable()) {
            this.online = true;

            user = FirebaseAuth.getInstance().getCurrentUser();

            /*for (int i = 1; i<20; i++){
                ScoreReport item = new ScoreReport( this.user.getDisplayName(), this.user.getEmail(), Integer.toString(i));
                dbRef.child("scores").push().setValue(item);
            }*/

            if (user != null) {
                loadScore();
                loadLevels();
            } else {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(i, RC_SIGN_IN);
            }

        } else {
            this.online = false;
            fillLevels();
        }

        TextView title = findViewById(R.id.level);
        title.setText("Level: " + topLevel);
    }

    private void loadLevels() {
        topLevel = 1;
        dbRef.child("scores").orderByChild("email").equalTo(this.user.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot score : dataSnapshot.getChildren()) {
                    ScoreReport s = score.getValue(ScoreReport.class);
                    topLevel = Integer.parseInt(s.level);
                    TextView title = findViewById(R.id.level);
                    title.setText("Level: " + topLevel);
                    fillLevels();
                }
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
                Log.d("JAKEEEEEEE", "Added to list: " + s.displayName + s.level);
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
            ScoreContent.addItem(new ScoreContent.ScoreItem(Integer.toString(i[0]), s.displayName, s.level));
            i[0]++;

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://www.facebook.com/Circle-Breaker-App-459444724585718/"))
                .setQuote("Checkout this new app Circle Breaker!")
                .build();

        ShareButton shareButton = (ShareButton)findViewById(R.id.shareButton);
        shareButton.setShareContent(content);


        Button playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Send to play
                Intent i = new Intent(MainActivity.this, PlayActivity.class);
                i.putExtra("topLevel", Integer.toString(topLevel));
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
        } else {
            menu.findItem(R.id.logout_item).setEnabled(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_item:
                if (online) {
                    this.topLevel = 1;
                    FirebaseAuth.getInstance().signOut();
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(i, RC_SIGN_IN);
                    return true;
                }
                else {
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLAY_RV) {
            if (resultCode == RESULT_OK){
                this.topLevel = data.getIntExtra("topLevel", 1);
                Log.d("JAKEEEEEEEEE", "onActivityResult: " + this.topLevel);
            }
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
