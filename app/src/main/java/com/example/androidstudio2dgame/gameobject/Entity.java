package com.example.androidstudio2dgame.gameobject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Entity is an abstract class which implements a draw method from GameObject
 * */
public abstract class Entity extends GameObject {
    protected Bitmap bitmap;

    public Entity(Context context, double positionX, double positionY){
        super(positionX, positionY);
    }

    //Getting rectangle of sprites
    public Rect getBounds() {
        return new Rect((int)positionX, (int)positionY, (int)positionX + bitmap.getWidth(), (int)positionY + bitmap.getHeight());
    }

    //Collision check
    public static boolean isColliding(Entity obj1, Entity obj2){
        return Rect.intersects(obj1.getBounds(), obj2.getBounds());
    }

    public void draw(Canvas canvas) {
        // Draw the bitmap
        canvas.drawBitmap(bitmap, (float) positionX, (float) positionY, null);
    }
}
