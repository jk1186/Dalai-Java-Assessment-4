package com.mygdx.game.minigame;

import com.badlogic.gdx.graphics.Texture;

/**
 * Class used to only have to instantiate textures once to minimise memory usage
 * @author lnt20
 *
 */
public class TextureManager {

	private static Texture fireman, enemy, boss, bomb1, bomb2;
	
	public TextureManager() {
		fireman = new Texture("blue.jpg");
		enemy = new Texture("black.jpg");
		boss = new Texture("green.jpg");
		bomb1 = new Texture("red.png");
		bomb2 = new Texture("yellow.png");
	}
	
	/**
	 * First Bomb texture
	 * @return Bomb texture
	 */
	public static Texture getFirstBomb() {
		return bomb1;
	}
	
	/**
	 * Second Bomb texture
	 * @return Bomb Texture
	 */
	public static Texture getSecondBomb() {
		return bomb2;
	}
	
	/**
	 * Boss Texture
	 * @return Boss texture
	 */
	public static Texture getBoss() {
		return boss;
	}
	
	/**
	 * Enemy Texture
	 * @return Enemy texture
	 */
	public static Texture getEnemy() {
		return enemy;
	}
	
	/**
	 * Fireman Texture
	 * @return Fireman texture
	 */
	public static Texture getFireman() {
		return fireman;
	}
	
	
	/**
	 * Disposes all textures used for the minigame entities
	 */
	public void dispose() {
		fireman.dispose();
		bomb1.dispose();
		bomb2.dispose();
		enemy.dispose();
		boss.dispose();
	}
	
	
}
