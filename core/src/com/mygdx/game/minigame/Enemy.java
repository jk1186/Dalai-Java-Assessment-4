package com.mygdx.game.minigame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * Class used as framework for Fireman class as well as for the objects needed for MiniGameState
 * @author Luke Taylor
 * @author Dicycat
 *
 */
public class Enemy extends MiniGameUnit {
		
	private final static float MOVEMENT_TIME = 0.8f, WAIT_TIME = 1f;
	
	/**
	 * Enum used to control the movement of the enemys
	 * @author lnt20
	 *
	 */
	private enum MoveDirection{
		LEFT, RIGHT, STAND;
		
		private boolean leftNext = false;
		private float timer = 0f;
		
		/**
		 *  Sets state to know that left is the next direction
		 */
		public void setLeftNext() {
			leftNext = true;
		}
		
		/**
		 * Sets state to know that right is the next direction
		 */
		public void setRightNext() {
			leftNext = false;
		}
		
		/**
		 * Starts timer for current state
		 */
		public void startTimer() {
			timer = 0f;
		}
		
		/**
		 * @return true if enough time has elapsed since movement started
		 */
		public boolean hasWalkTimeElapsed() {
			return timer>=MOVEMENT_TIME;
		}
		
		/**
		 * @return true if enough time has elapsed since waiting started
		 */
		public boolean hasWaitTimeElapsed() {
			return timer>=WAIT_TIME;
		}
		
		/**
		 * Increments timer by value passed
		 * @param time time added to timer
		 */
		public void addTime(float time) {
			timer += time;
		}
		
	}
	
	private MoveDirection direction;
	
	public Enemy(Vector2 startPos, int height, int width, int maxHealthPoints, float speed, Texture texture) {
		super(startPos, height, width, texture, maxHealthPoints, speed);
		direction = MoveDirection.STAND;
	}

	/**
	 * Method updates the position of the enemy
	 */
	public void updatePos(float deltaTime) {
		move(4);
		switch(direction) {
		case LEFT:
			move(1);
			if (direction.hasWalkTimeElapsed()) {
				direction = MoveDirection.STAND;
				direction.setRightNext();
				direction.startTimer();
			}else {
				direction.addTime(deltaTime);
			}
			break;
			
			
		case RIGHT:		
			move(2);
			if(direction.hasWalkTimeElapsed()) {
				direction = MoveDirection.STAND;
				direction.setLeftNext();
				direction.startTimer();
			}else {
				direction.addTime(deltaTime);
			}
			break;
	
			
		case STAND:
			if(direction.hasWaitTimeElapsed()) {
				if (direction.leftNext) {
					direction = MoveDirection.LEFT;
					direction.startTimer();
				}else {
					direction = MoveDirection.RIGHT;
					direction.startTimer();
				}
			}else {
				direction.addTime(deltaTime);
			}
			break;
		}
	}
	
	/**
	 * @param unit Firefighter entity to test if enemy collides with
	 * @return true if Firefighter does collide with enemy
	 */
	public boolean collisionWithEntity(Firefighter unit) {		
		if (unit != null) {
			return (
					// Checks if bottom left of unit is within the area of the enemy
					isBetween(unit.getPosition().x,getPosition().x,getTopRight().x) &&  
					isBetween(unit.getPosition().y,getPosition().y,getTopRight().y)) ||
					(
					//Checks if top right of unit is within the area of the enemy
					isBetween(unit.getTopRight().x,getPosition().x,getTopRight().x) && 
					isBetween(unit.getTopRight().y,getPosition().y,getTopRight().y))||
					(
					//Checks if bottom right of unit is within the area of the enemy
					isBetween(unit.getTopRight().x,getPosition().x,getTopRight().x) && 
					isBetween(unit.getPosition().y,getPosition().y,getTopRight().y))||
					(
					//Checks if top left of unit is within the area of the enemy
					isBetween(unit.getPosition().x,getPosition().x,getTopRight().x) && 
					isBetween(unit.getTopRight().y,getPosition().y,getTopRight().y));					
		}else {
			return false;
		}
	}
	
	/**
	 * Tests whether target is between point1 and point2
	 * @param target target point
	 * @param point1 one end of the scope
	 * @param point2 other end of the scope
	 * @return boolean returns true if target is between point1 and point2
	 */
	private boolean isBetween(float target, float point1, float point2) {
		return ((target >= point1 && target <= point2 ) || (target <= point1 && target >= point2));
	}
	
}
