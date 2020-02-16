package com.mygdx.game.minigame;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;

/**
 * Class used to only have to instantiate textures once to minimise memory usage
 * @author Luke Taylor
 *
 */
public class TextureManager {

	private static Texture fireman1, fireman2, firemanJump;
	private static Texture bombRed1, bombRed2, bombBlue1, bombBlue2;
	private static Texture fullHeart, heart;
	private static ArrayList<Texture> aliens;
	private static Random counter;
	
	public TextureManager() {
		fireman1 = new Texture("firefighter1.png");
		fireman2 = new Texture("firefighter2.png");
		firemanJump = new Texture("firefighterJump.png");
		bombRed1 = new Texture("bombRed1.png");
		bombRed2 = new Texture("bombRed2.png");
		bombBlue1 = new Texture("bombBlue1.png");
		bombBlue2 = new Texture("bombBlue2.png");
		fullHeart = new Texture("fullHeart.png");
		heart = new Texture("emptyHeart.png");
		
		aliens = new ArrayList<Texture>();
		aliens.add(new Texture("alien dark blue.png"));
		aliens.add(new Texture("alien dark yellow.png"));
		aliens.add(new Texture("alien green.png"));
		aliens.add(new Texture("alien grey.png"));
		aliens.add(new Texture("alien idk.png"));
		aliens.add(new Texture("alien light blue.png"));
		aliens.add(new Texture("alien lime.png"));
		aliens.add(new Texture("alien lol.png"));
		aliens.add(new Texture("alien pink.png"));
		aliens.add(new Texture("alien red.png"));
		aliens.add(new Texture("alien salmon.png"));
		aliens.add(new Texture("alien shade.png"));
		aliens.add(new Texture("alien weird.png"));
		aliens.add(new Texture("alien yellow.png"));
		counter = new Random();
	}	
	
	
	/**
	 * @return Texture first blue bomb texture
	 */
	public static Texture getFirstBlueBomb() {
		return bombBlue1;
	}
	
	
	/**
	 * @return Texture second blue bomb texture
	 */
	public static Texture getSecondBlueBomb() {
		return bombBlue2;
	}
	
	
	/**
	 * @return Texture first red bomb texture
	 */
	public static Texture getFirstRedBomb() {
		return bombRed1;
	}
	
	
	/**
	 * @return Texture second red bomb texture
	 */
	public static Texture getSecondRedBomb() {
		return bombRed2;
	}
		
	/**
	 * @return Texture first fireman texture
	 */
	public static Texture getFirstFireman() {
		return fireman1;
	}
	
	
	/**
	 * @return Texture second fireman texture
	 */
	public static Texture getSecondFireman() {
		return fireman2;
	}
	
	
	/**
	 * @return Texture fireman jumping texture
	 */
	public static Texture getFiremanJump() {
		return firemanJump;
	}	
	
	/**
	 * @return Texture Full heart texture
	 */
	public static Texture getFullHeart() {
		return fullHeart;
	}
	
	/**
	 * @return Texture Heart texture
	 */
	public static Texture getEmptyHeart() {
		return heart;
	}
	
	private static int getCounterVal() {
		return Math.abs(counter.nextInt()%aliens.size());
	}
	
	/**
	 * @return Texture random Alien texture
	 */
	public static Texture getNextAlien() {
		return aliens.get(getCounterVal());
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
		for (Texture t: aliens) {
			t.dispose();
		}
	}
	
	
}
