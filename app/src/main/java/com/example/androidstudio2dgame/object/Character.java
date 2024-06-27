package com.example.androidstudio2dgame.object;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Character is an abstract class whichimplements a draw method from GameObject
 * */
public abstract class Character extends GameObject {
    protected Bitmap bitmap;

    public Character(Context context, double positionX, double positionY){
        super(positionX, positionY);
    }

    //Getting rectangle of sprites
    public Rect getBounds() {
        return new Rect((int)positionX, (int)positionY, (int)positionX + bitmap.getWidth(), (int)positionY + bitmap.getHeight());
    }

    public static boolean isColliding(Character obj1, Character obj2){
        return Rect.intersects(obj1.getBounds(), obj2.getBounds());
    }

    public void draw(Canvas canvas) {
        // Draw the bitmap
        canvas.drawBitmap(bitmap, (float) positionX, (float) positionY, null);
    }
}
