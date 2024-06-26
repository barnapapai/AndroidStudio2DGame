package com.example.androidstudio2dgame.object;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Character is an abstract class whichimplements a draw method from GameObject
 * */
public abstract class Character extends GameObject {
    protected Bitmap bitmap;

    public Character(Context context, double positionX, double positionY){
        super(positionX, positionY);
    }

    public void draw(Canvas canvas) {
        // Draw the bitmap
        canvas.drawBitmap(bitmap, (float) positionX, (float) positionY, null);
    }
}
