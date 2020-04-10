package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;


public class PowerUps extends Entity{
    String type;

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
    }

    public String getType(){return this.type;}

}
