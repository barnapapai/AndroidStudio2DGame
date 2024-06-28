package com.example.androidstudio2dgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.androidstudio2dgame.gameobject.Enemy;
import com.example.androidstudio2dgame.gameobject.Entity;
import com.example.androidstudio2dgame.gameobject.Player;
import com.example.androidstudio2dgame.gameobject.Spell;
import com.example.androidstudio2dgame.gamepanel.GameOver;
import com.example.androidstudio2dgame.gamepanel.Joystick;
import com.example.androidstudio2dgame.gamepanel.Performance;

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
    private List<Enemy> enemyList = new ArrayList<>();
    private List<Spell> spellList = new ArrayList<Spell>();
    private int joystickPointerId = 0;
    private int numberOfSpellsToCast = 0;
    private GameOver gameOver;
    private Performance performance;

    public Game(Context context) {
        super(context);

        //Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        //Initialize game panels
        performance = new Performance(context, gameLoop);
        gameOver = new GameOver(context);
        joystick = new Joystick(275, 700, 70, 40);

        //Initialiye game objects
        player = new Player(context, joystick, 2*500, 500);

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //Handle touch actions
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if(joystick.getIsPressed()){
                    //Joystick was pressed before this event
                    numberOfSpellsToCast++;
                }
                else if(joystick.isPressed((double) event.getX(), (double) event.getY())){
                    //Joystick is pressed
                    joystickPointerId = event.getPointerId(event.getActionIndex());
                    joystick.setIsPressed(true);
                }
                else{
                    //Joystick was not previously and in this event pressed
                    numberOfSpellsToCast++;
                }
                return  true;
            case MotionEvent.ACTION_MOVE:
                if(joystick.getIsPressed()){
                    joystick.setActuator((double) event.getX(), (double) event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if(joystickPointerId == event.getPointerId(event.getActionIndex())){
                    joystick.setIsPressed(false);
                    joystick.resetActuator();
                }
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

        //Draw Game Objects
        player.draw(canvas);

        for(Enemy enemy : enemyList){
            enemy.draw(canvas);
        }

        for (Spell spell : spellList) {
            spell.draw(canvas);
        }

        //Draw game panels
        joystick.draw(canvas);
        performance.draw(canvas);

        //Draw Game over if the player is dead
        if(player.getHealthPoints() <= 0){
            gameOver.draw(canvas);
        }
    }

    public void update() {

        //Stop updating the game if the player is dead
        if(player.getHealthPoints() <= 0){
            return;
        }

        //Update game state
        joystick.update();
        player.update();

        //Spawn enemy if time to spawn
        if(Enemy.readyToSpawn()){
            enemyList.add(new Enemy(getContext(), player));
        }

        //Update state of each enemy
        while(numberOfSpellsToCast > 0){
            spellList.add(new Spell(getContext(), player));
            numberOfSpellsToCast--;
        }
        for (Enemy enemy : enemyList) {
            enemy.update();
        }

        //Update state of each spell
        for (Spell spell : spellList) {
            spell.update();
        }

        //Check for collision
        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
        while(iteratorEnemy.hasNext()){
            Entity enemy =  iteratorEnemy.next();
            if (Entity.isColliding(enemy, player)){
                iteratorEnemy.remove();
                player.setHealthPoints(player.getHealthPoints() - 1);
                continue;
            }

            Iterator<Spell> iteratorSpell = spellList.iterator();
            while(iteratorSpell.hasNext()){
                Entity spell = iteratorSpell.next();
                if(Entity.isColliding(spell, enemy)){
                    iteratorSpell.remove();
                    iteratorEnemy.remove();
                    break;
                }
            }
        }
    }
}
