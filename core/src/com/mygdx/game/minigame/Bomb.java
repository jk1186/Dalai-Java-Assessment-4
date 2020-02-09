package com.mygdx.game.minigame;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.sprites.Entity;

/**
 * Bomb class to represent the bomb dropped by fireman in the MiniGame
 * @author lnt20
 *
 */
public class Bomb extends Entity{

	private int damage;
	private float range, fuse, currentTime;
	private boolean exploding = false, firstTextureDisplayed = true;
	
	public Bomb(Vector2 pos, Texture texture) {
		super(pos, 5, 5, texture);
		this.damage = 10 	;
		this.range = 50;
		fuse = 2f;
		currentTime = 0;
	}
	
	/**
	 * Exploding sequence of bomb
	 * @param enemiesInRange List of enemies to apply damage to
	 */
	public void explode(List<Enemy> enemiesInRange) {
		for (Enemy enemy: enemiesInRange) {
			enemy.addHealth(-damage);
		}
	}
	
	/**
	 * Updates timer based on deltaTime specified
	 * @param deltaTime
	 */
	public void updateTimer(float deltaTime) {
		currentTime += deltaTime;
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
		if (firstTextureDisplayed) {
			setTexture(TextureManager.getSecondBomb());
			firstTextureDisplayed = false;
		}else {
			setTexture(TextureManager.getFirstBomb());
			firstTextureDisplayed = true;
		}
	}
	
}
