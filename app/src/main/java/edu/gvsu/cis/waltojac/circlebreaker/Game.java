package edu.gvsu.cis.waltojac.circlebreaker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import Components.Ball;
import Components.Ring;

public class Game {
    private double width;
    private double height;
    private Ball myB;
    private Ring myRing;
    private boolean playing = true;
    private boolean won = false;

    public Game(Canvas c, Context context) {
        Paint mPaint = new Paint();
        int centerX = c.getWidth()/2;
        int centerY = c.getHeight()/2;

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xFFE82C64);

        myB = new Ball(centerX, centerY, 25);
        myB.setSpeed(0, 10);
        myB.setAcc(0, .5);
        myB.setPaint(mPaint);

        int radius = (int)(c.getWidth() * .45);
        myRing = new Ring(centerX, centerY, radius, 15, context);
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
            myRing.draw(canvas);
        }
    }

    public boolean checkCollision() {
        int ballRad = myB.getRad();
        int ballY = myB.getY();

        int ringRad = myRing.getRad();
        int ringY = myRing.getY();

        if(ballY + ballRad > ringY + ringRad) {
            return true;
        } else {
            return false;
        }
    }

    public void checkWin() {
        if(!playing && won) {
            //won
        } else if (!playing && !won) {
            //lost
        }
    }

    public void registerSensors() {
        myRing.registerSensors();
    }

    public void unregisterSensors() {
        myRing.unRegisterSensors();
    }
}
