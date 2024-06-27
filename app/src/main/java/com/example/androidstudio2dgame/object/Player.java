package com.example.androidstudio2dgame.object;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.example.androidstudio2dgame.GameLoop;
import com.example.androidstudio2dgame.Joystick;
import com.example.androidstudio2dgame.R;

/**
 * Player is the main character of the game
 */
public class Player extends Character{
    public static final double SPEED_PIXELS_PER_SECOND = 400.0;
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private final Joystick joystick;

    public Player(Context context,  Joystick joystick, double positionX, double positionY){
        super(context, positionX, positionY);

        this.joystick = joystick;
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
    }

    public void update() {
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;
        
        positionX += velocityX;
        positionY += velocityY;
    }
}
