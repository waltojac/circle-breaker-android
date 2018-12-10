package edu.gvsu.cis.waltojac.circlebreaker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameView extends SurfaceView {

    private Bitmap bmp;
    private SurfaceHolder holder;
    private GameThread gameThread;
    private Game game;
    private int seedLvl;

    public GameView (Context context, int level) {
        super(context);
        seedLvl = level;
        gameThread = new GameThread(this);
        holder = getHolder();

        holder.addCallback( new SurfaceHolder.Callback(){
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder){
                Canvas c = holder.lockCanvas();
                game = new Game(c, getContext(), seedLvl);
                game.registerSensors();
                holder.unlockCanvasAndPost(c);
                gameThread.setRunning(true, game);
                gameThread.start();
                //Canvas c = holder.lockCanvas();
                //draw(c);
                //holder.unlockCanvasAndPost(c);
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHold){
                game.unregisterSensors();
                boolean retry = true;
                gameThread.setRunning(false, game);
                while (retry) {
                    try{
                        gameThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                    }
                }
            }
        });
    }


}
