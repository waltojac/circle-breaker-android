package Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import static android.content.Context.SENSOR_SERVICE;

public class Ring implements SensorEventListener {
    private int mX = 100;
    private int mY = 100;
    private double rotation = 0;
    private double dRot = 0;
    private int rad = 500;
    private int t = 30;
    private double gap = .5;
    private double spin = 1;

    int color = 0xFF142847;
    private Paint mPaint;

    private int winCheck;

    private int numSectors;
    private double sectorSpan;
    private RingSector sectors[]; // if sector still valid

    private final SensorManager mSensorManager;
    private final Sensor mAccelerometer;

    public Ring(int x, int y,int rad, int numSectors, Context context) {
        mX = x;
        mY = y;
        this.rad = rad;
        this.numSectors = numSectors;
        winCheck = numSectors;
        sectorSpan = 360 / numSectors;

        sectors = new RingSector[numSectors];
        for(int i=0; i < numSectors; i++) {
            sectors[i] = new RingSector((i*sectorSpan) + gap, ((i+1)*sectorSpan) - gap, 1); // leaves a small gap
        }

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(t);
        mPaint.setColor(color);

        mSensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Log.d("sensor", "manager: " + mSensorManager);
        Log.d("sensor", "Accel: " + mAccelerometer);

        //mSensorManager.registerListener(this, mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
    }

    public int getRad() {
        return rad;
    }

    public int getY() {
        return mY;
    }

    public void update() {
        double change;
        change = dRot; // access accellerometer
        rotation = (rotation + change) % 360;
    }

    /***
     * Takes the angle of the collision and checks if that sector is still in play
     * False is a loss on the game
     * @param angle
     * @return valid hit
     */
    public boolean checkHit(double angle) {
        angle = (angle + 90 + 360 - rotation) % 360; // Java likes 0 deg on right, the clockwise.
        Log.d("hit", "angle: " + angle);

        int hit = (int) (angle / sectorSpan);

        if (sectors[hit].isValid()) {
            sectors[hit].hit(1);
            Log.d("hit", "Hit num: " + hit);
            return true;
        } else {
            return false; // loss
        }
    }

    public void draw(Canvas c){
        double from;
        double to;
        RectF oval = new RectF(mX - rad, mY - rad, mX + rad, mY + rad);

        for(int i=0; i<numSectors; i++) {
            if (sectors[i].isValid()) {
                from = sectors[i].getFrom();
                to = sectors[i].getTo();
                c.drawArc(oval, (float)(from + rotation), (float)(to - from), false, mPaint);
                //Log.d("sector", "getting drawn");
            }
        }
    }

    public void registerSensors() {
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        Log.d("sensor", "registered");
    }

    public void unRegisterSensors() {
        mSensorManager.unregisterListener(this);
        Log.d("sensor", "unregistered");
    }

    @Override
    public void onSensorChanged(SensorEvent e) {
        if(e.values[0] > 0) {
            dRot = e.values[0]/2;
        } else if ( e.values[0] < 0) {
            dRot = e.values[0]/2;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
