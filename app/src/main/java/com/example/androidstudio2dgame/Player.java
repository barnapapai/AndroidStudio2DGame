package com.example.androidstudio2dgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Player {
    private static final double SPEED_PIXELS_PER_SECOND = 400.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private Bitmap bitmap;
    private double positionX;
    private double positionY;
    private double velocityX;

    private double velocityY;

    public Player(Context context, float positionX, float positionY){
        this.positionX = positionX;
        this.positionY = positionY;

        // Load the bitmap from the drawable resources
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
    }

    public void draw(Canvas canvas) {
        // Draw the bitmap at the player's position
        canvas.drawBitmap(bitmap, (float) positionX, (float) positionY, null);
    }

    public void update(Joystick joystick) {
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;
        positionX += velocityX;
        positionY += velocityY;
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
