package com.mygdx.game.minigame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * @author lnt20
 *
 */
public class Fireman extends MiniGameUnit {

	/**
	 * 
	 * Used to keep track of the jumping of the Fireman. Possible states are UPWARDS, DOWNWARDS, NOT_JUMPING, START_JUMPINH
	 * @author lnt20
	 *
	 */
	private enum Jumping{
		JUMPING, NOT_JUMPING, FALLING;
		
		private float timer = 0;
		
		/**
		 * Method to start the timer for jumping upwards
		 * @param time
		 */
		public void startTimer() {
			timer = 0f;
		}
		
		/**
		 * @return true if enough time has elapsed since jump start
		 */
		public boolean hasTimeElapsed() {
			return timer>=JUMP_TIME;
		}
		
		/**
		 * Increments timer by value passed
		 * @param time time added to timer
		 */
		public void addTime(float time) {
			timer += time;
		}
		
	}
	
	private Jumping jumpState;
	private final static float JUMP_TIME = 0.2f; // Amount of time the entity moves upwards in a jump
	
	public Fireman(Vector2 pos, Texture texture) {
		super(pos, 20, 20, texture, 100, 10f);
		jumpState = Jumping.NOT_JUMPING;
	}	
	
	/**
	 * Method to calculate and update the position of the fireman. Calculates key presses and collisions then sets final position
	 * @param deltaTime Amount of time passed since last update call
	 *
	 */
	public void updatePos(float deltaTime) {
		
		boolean[] keys = checkInputKeys(); // Stores boolean values for each key is needs the state of. Formatted like {UP (W), LEFT (A), RIGHT (D)}
		Vector2 movementNeeded = new Vector2();
		
		movementNeeded.add(jumpSequence(keys[0], deltaTime));
		
		// Calculate Horizontal Coordinates
		if (keys[1]) {
			movementNeeded.add(new Vector2(-1,0));// Adds left pointing directional vector
		}
		if(keys[2]) {
			movementNeeded.add(new Vector2(1,0));// Adds right pointing directional vector
		}
		
		move(movementNeeded);
	}
	
	
	/**
	 * Used to get key press state of movement keys as a boolean list
	 * @return List of booleans representing state of different keys, {W, A, D}
	 */
	private boolean[] checkInputKeys() {
		return new boolean[] {Gdx.input.isKeyPressed(Keys.W),Gdx.input.isKeyPressed(Keys.A),Gdx.input.isKeyPressed(Keys.D)};
	}

	/**
	 * Private method used to control and calculate the jumping sequence of the fireman
	 * @param keyPressed boolean representing if jump key has been pressed in the last iteration
	 * @param deltaTime float to say how long since last sequence has been run
	 * @return Vector2 the vertical movement vector the fireman needs to experience
	 */
	private Vector2 jumpSequence(boolean keyPressed, float deltaTime) {
		Vector2 verticalMov = new Vector2();
		
		switch(jumpState){
		case JUMPING: // Occurs when the fireman is currently moving upwards in the air
			if (jumpState.hasTimeElapsed()) {
				jumpState = Jumping.FALLING;
			}else {
				jumpState.addTime(deltaTime);
				verticalMov = new Vector2(0,1);
				onFloor = false;
			}
			break;
			
		case NOT_JUMPING: // Occurs when the fireman is on the floor/ place where he can jump
			verticalMov = new Vector2(0,-1);
			if (keyPressed) {
				jumpState = Jumping.JUMPING;
				jumpState.startTimer();
			}
			break;
	
		case FALLING: // Occurs when the fireman is falling from a jump he has just made
			verticalMov = new Vector2(0,-1);
			if(onFloor) {
				jumpState = Jumping.NOT_JUMPING;
			}
			break;
			
		default:
			break;
			
		}
		
		return verticalMov;
	}
	
	/**
	 * Method returns Vector2 of centre point of the fireman
	 * @return Vector2 with centre position of the fireman
	 */
	public Vector2 getCentre() {
		return new Vector2((position.x + topRight.x)/2,(position.y + topRight.y)/2);
	}
	
	

}
