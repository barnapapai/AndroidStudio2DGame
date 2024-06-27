package com.example.androidstudio2dgame.object;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.example.androidstudio2dgame.GameLoop;
import com.example.androidstudio2dgame.R;

public class Spell extends Entity {
    private static final double SPEED_PIXELS_PER_SECOND = 800.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    public Spell(Context context, Player spellcaster) {
        super(
                context,
                spellcaster.getPositionX(),
                spellcaster.getPositionY()
        );
        velocityX = spellcaster.getDirectionX()*MAX_SPEED;
        velocityY = spellcaster.getDirectionY()*MAX_SPEED;


        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fireball);
    }

    @Override
    public void update() {
        positionX += velocityX;
        positionY += velocityY;
    }
}
