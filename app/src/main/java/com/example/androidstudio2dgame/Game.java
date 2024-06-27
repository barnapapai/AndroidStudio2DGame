package com.example.androidstudio2dgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.androidstudio2dgame.object.Enemy;
import com.example.androidstudio2dgame.object.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Game manages all objects in the game and is responsible for updating all states and render
 * all objects on the screen
 */
public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Joystick joystick;
    private final Player player;
    private GameLoop gameLoop;

    private List<Enemy> enemyList = new ArrayList<Enemy>();
    //private final Enemy enemy;

    public Game(Context context) {
        super(context);

        //Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        //Initialiye player
        joystick = new Joystick(275, 700, 70, 40);
        player = new Player(getContext(), joystick, 2*500, 500);
        //enemy = new Enemy(getContext(), player, 500, 200);

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //Handle touch actions
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(joystick.isPressed((double) event.getX(), (double) event.getY())){
                    joystick.setIsPressed(true);
                }
                return  true;
            case MotionEvent.ACTION_MOVE:
                if(joystick.getIsPressed()){
                    joystick.setActuator((double) event.getX(), (double) event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                joystick.setIsPressed(false);
                joystick.resetActuator();
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawUPS(canvas);
        drawFPS(canvas);

        joystick.draw(canvas);
        player.draw(canvas);
        for(Enemy enemy : enemyList){
            enemy.draw(canvas);
        }
    }

    public void drawUPS(Canvas canvas){
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS, 100, 100, paint);
    }

    public void drawFPS(Canvas canvas){
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS, 100, 200, paint);
    }

    public void update() {
        joystick.update();
        player.update();

        //Spawn enemy if time to spawn
        if(Enemy.readyToSpawn()){
            enemyList.add(new Enemy(getContext(), player));
        }

        //Update state pf each enemy
        for (Enemy enemy : enemyList) {
            enemy.update();
        }

        //Check for collision
        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
        while(iteratorEnemy.hasNext()){
            if (Character.isColliding(iteratorEnemy.next(), player)){
                iteratorEnemy.remove();
            }
        }
    }
}
