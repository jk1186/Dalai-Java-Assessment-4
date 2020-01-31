package com.mygdx.game.minigame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * Class used as framework for Fireman class as well as for the objects needed for MiniGameState
 * @author lnt20
 *
 */
public class Enemy extends MiniGameUnit {
		
	private final static float MOVEMENT_TIME = 1f, WAIT_TIME = 1f;
	
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
	
	public Enemy() {
		super(new Vector2(800,211), 20, 20, new Texture("black.jpg"), 10, 5f);
		direction = MoveDirection.STAND;
	}

	/**
	 * Method updates the position of the enemy
	 */
	public void updatePos(float deltaTime) {
		
		Vector2 movementNeeded = new Vector2(0,-1);
		
		switch(direction) {
		case LEFT:
			movementNeeded.add(new Vector2(-1,0));
			if (direction.hasWalkTimeElapsed()) {
				direction = MoveDirection.STAND;
				direction.setRightNext();
				direction.startTimer();
			}else {
				direction.addTime(deltaTime);
			}
			break;
			
			
		case RIGHT:		
			movementNeeded.add(new Vector2(1,0));
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
		
		move(movementNeeded);
	}
	
}
