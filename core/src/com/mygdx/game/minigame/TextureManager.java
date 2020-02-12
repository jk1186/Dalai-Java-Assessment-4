package com.mygdx.game.minigame;

import com.badlogic.gdx.graphics.Texture;

/**
 * Class used to only have to instantiate textures once to minimise memory usage
 * @author lnt20
 *
 */
public class TextureManager {

	private static Texture fireman1, fireman2, firemanJump;
	private static Texture enemy, boss;
	private static Texture bombRed1, bombRed2, bombBlue1, bombBlue2;
	
	public TextureManager() {
		fireman1 = new Texture("firefighter1.png");
		fireman2 = new Texture("firefighter2.png");
		firemanJump = new Texture("firefighterJump.png");
		enemy = new Texture("black.jpg");
		boss = new Texture("green.jpg");
		bombRed1 = new Texture("bombRed1.png");
		bombRed2 = new Texture("bombRed2.png");
		bombBlue1 = new Texture("bombBlue1.png");
		bombBlue2 = new Texture("bombBlue2.png");
	}	
	
	
	/**
	 * @return first blue bomb texture
	 */
	public static Texture getFirstBlueBomb() {
		return bombBlue1;
	}
	
	
	/**
	 * @return second blue bomb texture
	 */
	public static Texture getSecondBlueBomb() {
		return bombBlue2;
	}
	
	
	/**
	 * @return first red bomb texture
	 */
	public static Texture getFirstRedBomb() {
		return bombRed1;
	}
	
	
	/**
	 * @return second red bomb texture
	 */
	public static Texture getSecondRedBomb() {
		return bombRed2;
	}
	
	
	/**
	 * @return boss texture
	 */
	public static Texture getBoss() {
		return boss;
	}
	
	
	/**
	 * @return enemy texture 
	 */
	public static Texture getEnemy() {
		return enemy;
	}
	
	/**
	 * @return first fireman texture
	 */
	public static Texture getFirstFireman() {
		return fireman1;
	}
	
	
	/**
	 * @return second fireman texture
	 */
	public static Texture getSecondFireman() {
		return fireman2;
	}
	
	
	/**
	 * @return fireman jumping texture
	 */
	public static Texture getFiremanJump() {
		return firemanJump;
	}	
	
	
	/**
	 * Disposes all textures used for the minigame entities
	 */
	public void dispose() {
		fireman1.dispose();
		fireman2.dispose();
		firemanJump.dispose();
		bombRed1.dispose();
		bombRed2.dispose();
		bombBlue1.dispose();
		bombBlue2.dispose();
		enemy.dispose();
		boss.dispose();
	}
	
	
}
