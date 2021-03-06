package Components;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Ball {
    private int mX = 100;
    private int mY = 100;
    private double dX = 0;
    private double dY = 0;
    private double ddX = 0;
    private double ddY = 0;
    private int rad = 10;
    private int t = 5;
    private Paint paint;

    public Ball(int x, int y, int rad) {
        mX = x;
        mY = y;
        this.rad = rad;
    }

    public void setSpeed(int dX, int dY){
        this.dX = dX;
        this.dY = dY;
    }

    public void setAcc(double ddX, double ddY) {
        this.ddX = ddX;
        this.ddY = ddY;
    }

    public void setPaint(Paint p) {
        paint = new Paint(p);
    }

    public void update() {
        mX += dX;
        dX += ddX;
        mY += dY;
        dY += ddY;
    }

    public void reflectX() {
        dX -= 2*ddX; // bounce time is being strange with this
        dX = -dX;
    }

    public void reflectY() {
        dY -= 2*ddY;    // bounce time is being strange with this
        dY = -dY;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    public int getRad() {
        return rad;
    }

    public double getAngle() {
        double angle = Math.toDegrees(Math.atan(dY/dX));
        if(dY>=0 && dX>=0){
            Log.d("connor", "dy gr 0: " + dY);
            return angle;
        } else if (dY<0 && dX>=0) {
            Log.d("connor", "dy ls 0 : " + dY);
            return 360-angle;
        } else if (dY>=0 && dX<0) {
            return 180-angle;
        } else {
            return 180 + angle;
        }
    }

    public void draw(Canvas c) {
        if(paint != null) {
            c.drawCircle(mX, mY, rad, paint);
        }
    }
}
