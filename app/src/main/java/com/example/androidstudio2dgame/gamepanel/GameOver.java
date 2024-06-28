package com.example.androidstudio2dgame.gamepanel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.androidstudio2dgame.R;

/**
 * Panel that draws the text Game over to the screen
 * */
public class GameOver {
    private Context context;
    private Bitmap bitmap;

    public GameOver(Context context) {
        this.context = context;
    }

    public void draw(Canvas canvas){
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gameovertext);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = width * 2; // double the size
        int newHeight = height * 2; // double the size

        // create a new bitmap with the new size
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);

        float x = 800;
        float y = 200;

        canvas.drawBitmap(scaledBitmap, x, y, null);
    }

}
