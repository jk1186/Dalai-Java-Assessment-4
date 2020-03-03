package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

//TODO: Create powerups for instant health, water or immunuity. Generate random position, when collide apply effect for x seconds
public class PowerUps extends Entity{
    String type;
    public PowerUps(Vector2 position, int width, int height, Texture texture, String type){
        super(position, width, height, texture);
        this.type = type;
    }


}
