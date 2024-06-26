package com.example.androidstudio2dgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Player {
    private Bitmap bitmap;
    private double positionX;
    private double positionY;

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

    public void update() {
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
