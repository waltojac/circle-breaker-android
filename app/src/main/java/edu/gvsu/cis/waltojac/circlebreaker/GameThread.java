package edu.gvsu.cis.waltojac.circlebreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;

class GameThread extends Thread{
    private GameView view;
    private boolean running = false;
    private SurfaceHolder mSurfaceHolder;
    private Game g;
    private long delay = 5;

    public GameThread (GameView view) {
        this.view = view;
        mSurfaceHolder = view.getHolder();
    }

    public void setRunning(boolean run, Game g) {
        running = run;
        this.g = g;
    }

    @Override
    public void run() {
        while(running) {
            Canvas c = view.getHolder().lockCanvas(null);
            //try{
            if( c!= null) {
                synchronized(mSurfaceHolder){
                    g.moveFrame(c);
                }
                mSurfaceHolder.unlockCanvasAndPost(c);
            }

            try{
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                //nothing
            }
        }
    }
}
