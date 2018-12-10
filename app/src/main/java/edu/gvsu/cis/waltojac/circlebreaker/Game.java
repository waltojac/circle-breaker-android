package edu.gvsu.cis.waltojac.circlebreaker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import java.util.Random;

import Components.Ball;
import Components.Ring;

public class Game {
    private double width;
    private double height;
    private Ball myB;
    private Ring myRing;
    private boolean playing = true;
    private boolean won = false;
    private boolean reported = false;
    public int seedLvl;
    private PlayActivity playActivity;
    private Canvas c;
    private Paint ballP;

    public Game(Canvas c, Context context, int level) {
        this.c = c;
        playActivity = (PlayActivity) context;
        Log.d("score", "Context: " + playActivity);
        seedLvl = level;
        Random rand = new Random(seedLvl);
        Paint mPaint = new Paint();
        int centerX = c.getWidth()/2;
        int centerY = c.getHeight()/2;

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xFFE82C64);
        ballP = mPaint;

        int radius = (int)(c.getWidth() * .45);
        int numSectors = rand.nextInt(seedLvl/2) + rand.nextInt(seedLvl/2) + 5;
        myRing = new Ring(centerX, centerY, radius, numSectors, context, seedLvl);

        myB = new Ball(centerX, centerY, 20);
        myB.setSpeed(0, 10);
        myB.setAcc(0, .5);
        myB.setPaint(ballP);
    }

    public void remake(int level) {
        reported = false;
        playing = true;
        won = false;

        int centerX = c.getWidth()/2;
        int centerY = c.getHeight()/2;

        seedLvl = level;
        Random rand = new Random(seedLvl);

        int radius = (int)(c.getWidth() * .45);
        int numSectors = rand.nextInt(seedLvl/2) + rand.nextInt(seedLvl/2) + 5;
        myRing = new Ring(centerX, centerY, radius, numSectors, playActivity, seedLvl);

        myB = new Ball(centerX, centerY, 20);
        myB.setSpeed(0, 10);
        myB.setAcc(0, .5);
        myB.setPaint(ballP);
    }

    public void moveFrame(Canvas canvas) {


        if (canvas != null) {
            checkWin();
            canvas.drawColor(0xFFA8A8A8);
            width = canvas.getWidth();
            height = canvas.getHeight();
            int bRad = myB.getRad();
            int bX = myB.getX();
            int bY = myB.getY();

            if(checkCollision()) {
                if(playing && myRing.checkHit(0))
                    myB.reflectY();
                else
                    playing = false;
            }

            myB.update();
            myRing.update();

            myB.draw(canvas);
            won = myRing.draw(canvas);
        }
    }

    public boolean checkCollision() {
        int ballRad = myB.getRad();
        int ballY = myB.getY();
        int ballX = myB.getX();

        int ringRad = myRing.getRad();
        int ringY = myRing.getY();
        int ringX = myRing.getX();

        int distX = ballX - ringX;
        int distY = ballY - ringY;
        double dist = Math.sqrt(distX * distX + distY * distY) + ballRad;

        if (dist > ringRad - ballRad) {
            return true;
        } else {
            return false;
        }
    }

    public void checkWin() {
        if(!playing && won) {
            //won
            if(playActivity != null && !reported) {
                reported = true;
                playActivity.won(this);
            }
        } else if (!playing && !won) {
            // lost
            if(playActivity != null && !reported) {
                reported = true;
                playActivity.lost(this);
            }
        }
    }

    public void registerSensors() {
        myRing.registerSensors();
    }

    public void unregisterSensors() {
        myRing.unRegisterSensors();
    }
}
