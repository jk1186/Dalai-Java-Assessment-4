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
	protected boolean onFloor, facingRight = false;
	
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
		
		Vector2 newPos = getPosition().add(direction);
		
		setPosition(newPos.x,newPos.y);
		
		boolean COLLISION = getPosition().y <= 211f;
		
		if(COLLISION) {
			setPosition(getPosition().x, 211f);
			onFloor = true;
		}
	}
	
	/**
	 * MiniGameUnits initialise no textures so has nothing to dispose
	 */
	@Override
	public void dispose() {
	}
	
	/**
	 * Getter facingRight
	 * @return boolean returns true if entity should be facing right
	 */
	@Override
	public boolean isFacingRight() {
		return facingRight;
	}
	
}
