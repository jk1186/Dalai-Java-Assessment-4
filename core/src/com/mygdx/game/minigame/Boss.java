package com.mygdx.game.minigame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Class to control the "boss" on each level of minigame
 * @author Luke Taylor
 *
 */
public class Boss extends Enemy {

	public Boss(Vector2 position, int maxHealth, float speed) {
		super(position, 50, 50, maxHealth, speed);	
	}

}
