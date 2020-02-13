/**
 * 
 */
package com.mygdx.game.minigame;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

/**
 * Used to control the different Health Entities of the
 * @author Luke Taylor
 *
 */
public class HealthBar {

	private ArrayList<Heart> hearts = new ArrayList<Heart>();
	private int nextLiveHeart = 0;
	private boolean noLivesLeft = false;
	
	/**
	 * Constructor for HealthBar
	 */
	public HealthBar(Vector2 pos) {
		hearts.add(new Heart(new Vector2(pos.x,pos.y)));
		hearts.add(new Heart(new Vector2(pos.x+60,pos.y)));
		hearts.add(new Heart(new Vector2(pos.x+120,pos.y)));
		hearts.add(new Heart(new Vector2(pos.x+180,pos.y)));
	}
	
	/**
	 * Function transforms health from one to another
	 */
	public void looseHealth() {
		hearts.get(nextLiveHeart).dieHeart();// Update current life counter
		nextLiveHeart += 1;
		
		if(nextLiveHeart>3) { // Checks if last life has been used
			noLivesLeft = true;
		}
	}
	
	/**
	 * Getter for noLivesLeft
	 * @return boolean returns true if player has no more lives left
	 */
	public boolean isDead() {
		return noLivesLeft;
	}

	
	/**
	 * Getter for hearts ArrayList
	 * @return ArrayList<Heart> returns list of heart objects to render
	 */
	public ArrayList<Heart> getHearts() {
		return hearts;
	}
}
