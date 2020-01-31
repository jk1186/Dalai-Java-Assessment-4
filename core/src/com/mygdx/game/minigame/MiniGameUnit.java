package com.mygdx.game.minigame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.sprites.Unit;

/**
 * Abstract class to define basic properties of MiniGameEntities
 * @author lnt20
 *
 */
public abstract class MiniGameUnit extends Unit {

	protected float speed;
	protected boolean onFloor;
	
	public MiniGameUnit(Vector2 position, int width, int height, Texture texture, int maxHealth, float speed) {
		super(position, width, height, texture, maxHealth);
		this.speed = speed;
	}
	
	/**
	 * Abstract method will be used to update the position of the unit. Abstract as different classes will move in different ways
	 * @param deltaTime
	 */
	public abstract void updatePos(float deltaTime);

	/**
	 * Moves the unit in the direction of the parameter with the speed of the global variable speed
	 * @param direction
	 */
	public void move(Vector2 direction) {
		direction.nor();
		direction.mul(new Matrix3().setToScaling(speed, speed));
		
		Vector2 newPos = position.add(direction);
		
		setPosition(newPos.x,newPos.y);
		
		boolean COLLISION = position.y <= 211f;
		
		if(COLLISION) {
			setPosition(position.x, 211f);
			onFloor = true;
		}
	}
	
}
