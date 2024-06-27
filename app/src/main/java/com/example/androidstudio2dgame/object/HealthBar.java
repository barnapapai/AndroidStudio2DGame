package com.example.androidstudio2dgame.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.androidstudio2dgame.R;

/**
 * Healthbar displays the players health to the screen
 * */
public class HealthBar {

    private Player player;
    private int height, margin;
    private Paint borderPaint, healthPaint;

    public HealthBar(Context context, Player player){
        this.player = player;
        this.height = 20;
        this.margin = 2;

        this.borderPaint = new Paint();
        int borderColor = ContextCompat.getColor(context, R.color.healthBarBorder);
        borderPaint.setColor(borderColor);

        this.healthPaint = new Paint();
        int healthColor = ContextCompat.getColor(context, R.color.healthBarHealth);
        healthPaint.setColor(healthColor);
    }

    public  void draw(Canvas canvas){
        float x = (float) player.getPositionX();
        float y = (float) player.getPositionY();
        float distanceToPlayer = 30;
        float healthPointPercentage = (float) player.getHealthPoints()/player.MAX_HEALTH_POINTS;

        // Get the player's sprite width
        float spriteWidth = player.getSpriteWidth();
        
        //Draw border
        float borderLeft, borderTop, borderRight, borderBottom;
        borderLeft = x;
        borderRight =  x + spriteWidth;
        borderBottom = y - distanceToPlayer;
        borderTop = borderBottom - height;
        canvas.drawRect(borderLeft, borderTop, borderRight, borderBottom, borderPaint);

        //Draw health
        float healthLeft, healthTop, healthRight, healthBottom, healthWidth, healthHeight;
        healthWidth = spriteWidth -2*margin;
        healthHeight = height -2*margin;
        healthLeft = x + margin;
        healthRight = healthLeft + healthWidth*healthPointPercentage;
        healthBottom = borderBottom -margin;
        healthTop = healthBottom - healthHeight;
        canvas.drawRect(healthLeft, healthTop, healthRight, healthBottom, healthPaint);
    }
}
