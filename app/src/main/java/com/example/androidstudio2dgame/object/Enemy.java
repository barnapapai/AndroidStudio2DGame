package com.example.androidstudio2dgame.object;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.example.androidstudio2dgame.GameLoop;
import com.example.androidstudio2dgame.R;

/**
 * Enemy is a character which always moves in the direction of the player.
 * */
public class Enemy extends Character{
    private static final double SPEED_PIXELS_PER_SECOND = Player.SPEED_PIXELS_PER_SECOND*0.6;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    private final Player player;

    public Enemy(Context context, Player player, double positionX, double positionY){
        super(context, positionX, positionY);
        this.player = player;

        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.slugblue);
    }
    @Override
    public void update() {
        //Velocity in direction of player

        //Calculate vector
        double distanceToPlayerX = player.getPositionX() - positionX;
        double distanceToPlayerY = player.getPositionY() - positionY;

        //Absolute distance
        double distanceToPlayer = GameObject.getDistanceBetweenObjects(this, player);

        //Caclulate direction
        double directonX = distanceToPlayerX/distanceToPlayer;
        double directonY = distanceToPlayerY/distanceToPlayer;

        //Set velocity
        if(distanceToPlayer > 0){
            velocityX = directonX*MAX_SPEED;
            velocityY = directonY*MAX_SPEED;
        }
        else{
            velocityX = 0;
            velocityY = 0;
        }

        //Update position
        positionX += velocityX;
        positionY += velocityY;
    }
}
