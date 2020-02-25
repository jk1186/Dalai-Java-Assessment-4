package com.mygdx.game.minigame;

import com.badlogic.gdx.math.Vector2;

/**
 * Class to control the "boss" on each level of minigame
 * @author Luke Taylor
 * @author Dicycat
 *
 */
public class Boss extends Enemy {

	public Boss(Vector2 position, int maxHealth, float speed) {
		super(position, 50, 50, maxHealth, speed, TextureManager.getNextAlien());
	}

}
