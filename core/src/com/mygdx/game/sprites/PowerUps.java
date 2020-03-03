package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

//TODO: Create powerups for instant health, water or immunuity. Generate random position, when collide apply effect for x seconds
public class PowerUps extends Entity{
    String type;
    int duration;

    /**
     *
     * @param position
     * @param width
     * @param height
     * @param texture
     * @param type string of which power up to be created, Options are: infiite health, double speed, increase max health, increase range and double damage
     */
    public PowerUps(Vector2 position, int width, int height, Texture texture, String type){
        super(position, width, height, texture);
        this.type = type;
        this.duration = 10;
    }


}
