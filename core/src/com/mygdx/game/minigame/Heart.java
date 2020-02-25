/**
 * 
 */
package com.mygdx.game.minigame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.sprites.Entity;

/**
 * Used to display the health of the user
 * @author Luke Taylor
 * @author Dicycat
 */
public class Heart extends Entity {

	/**
	 * HealthIndicator constructor
	 * @param position position of heart
	 * @param width visible width of entity
	 * @param height visible height of entity
	 * @param texture texture displayed
	 */
	public Heart(Vector2 position) {
		super(position, 50, 50, TextureManager.getFullHeart());
	}
	
	
	/**
	 * Method to change the texture from the active heart to the empty heart
	 */
	public void dieHeart() {
		setTexture(TextureManager.getEmptyHeart());
	}

}
