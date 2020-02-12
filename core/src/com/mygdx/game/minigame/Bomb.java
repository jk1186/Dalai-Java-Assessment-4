package com.mygdx.game.minigame;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.sprites.Entity;

/**
 * Bomb class to represent the bomb dropped by fireman in the MiniGame
 * @author Luke Taylor
 *
 */
public class Bomb extends Entity{

	private int damage;
	private float range, fuse, currentTime;
	private boolean exploding = false;
	
	
	/**
	 * @author Luke Taylor
	 * Enum to control which texture of the bomb is being shown at any point in time
	 */
	private enum BombTexture{
		FIRST,SECOND;
	}
	
	private BombTexture bombTexture = BombTexture.FIRST;
	private ArrayList<Float> flashPattern = new ArrayList<Float>();
	
	public Bomb(Vector2 pos, Texture texture) {
		super(pos, 35, 35, texture);
		this.damage = 10 	;
		this.range = 200;
		fuse = 2f;
		currentTime = 0;
		for (float f :new float[] {0.4f,0.8f,1.2f, 1.6f, 1.7f,1.8f,1.9f,2f}) {
			flashPattern.add(f);
		}
		
	}
	
	/**
	 * Exploding sequence of bomb
	 * @param enemiesInRange List of enemies to apply damage to
	 */
	public void explode(List<Enemy> enemiesInRange) {
		for (Enemy enemy: enemiesInRange) {
			enemy.takeDamage(damage);
		}
	}
	
	/**
	 * Updates timer based on deltaTime specified
	 * @param deltaTime
	 */
	public void updateTimer(float deltaTime) {
		currentTime += deltaTime;
		if (currentTime >= flashPattern.get(0)) {
			flashPattern.remove(0);
			updateGraphics();
		}
		if (currentTime >= fuse) {
			exploding = true;
		}
	}
	
	/**
	 * Getter for damage attribute
	 * @return damage applied when bomb explodes
	 */
	public int getDamage() {
		return damage;
	}
	
	/**
	 * Getter for range attribute
	 * @return range which enemies get damaged upon explosion
	 */
	public float getRange() {
		return range;
	}
	
	/**
	 * Getter for exploding attribute
	 * @return current state of exploding
	 */
	public boolean isExploding() {
		return exploding;
	}

	/**
	 * Swap bomb graphics between two different images
	 */
	public void updateGraphics() {
		switch(bombTexture) {
		case FIRST:
			setTexture(TextureManager.getFirstRedBomb());
			bombTexture = BombTexture.SECOND;
			break;
		case SECOND:
			setTexture(TextureManager.getSecondRedBomb());
			bombTexture = BombTexture.FIRST;
			break;
		}
	}
	
	/**
	 * Removes functionality of dispose as bomb doesn't need to dispose anything as it doesn't initialise any textures
	 */
	@Override
	public void dispose() {
	}
	
}
