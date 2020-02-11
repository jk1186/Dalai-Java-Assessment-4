package com.mygdx.game.minigame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Class to control the "boss" on each level of minigame
 * @author Luke Taylor
 *
 */
public class Boss extends Enemy {

	public Boss(Vector2 position, Texture texture, int maxHealth, float speed) {
		super(position, 40, 40, texture, maxHealth, speed);	
	}

}
