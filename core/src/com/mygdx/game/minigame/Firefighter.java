package com.mygdx.game.minigame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Luke Taylor
 *
 */
public class Firefighter extends MiniGameUnit {

	/**
	 * 
	 * Used to keep track of the jumping of the Fireman. Possible states are UPWARDS, DOWNWARDS, NOT_JUMPING, START_JUMPINH
	 * @author Luke Taylor
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
	
	private enum FirefighterTexture{
		TEXTURE1,TEXTURE2,JUMPING;
	}
	
	private Jumping jumpState;
	private FirefighterTexture firemanTexture = FirefighterTexture.TEXTURE1;;
	private final static float JUMP_TIME = 0.2f; // Amount of time the entity moves upwards in a jump
	private float nextGraphicUpdate = 0f, currentTime = 0f;
	
	public Firefighter(Vector2 pos) {
		super(pos, 60, 60, TextureManager.getFirstFireman(), 4, 10f);
		jumpState = Jumping.NOT_JUMPING;
	}	
	
	/**
	 * Method to calculate and update the position of the fireman. Calculates key presses and collisions then sets final position
	 * @param deltaTime Amount of time passed since last update call
	 *
	 */
	public void updatePos(float deltaTime) {
		
		boolean moving = false;
		
		boolean[] keys = checkInputKeys(); // Stores boolean values for each key is needs the state of. Formatted like {UP (W), LEFT (A), RIGHT (D)}
		Vector2 movementNeeded = new Vector2();
		
		movementNeeded.add(jumpSequence(keys[0], deltaTime));
		
		// Calculate Horizontal Coordinates
		if (keys[1]) {
			movementNeeded.add(new Vector2(-1,0));// Adds left pointing directional vector
			moving = true;
			setFacingRight(true);
		}
		if(keys[2]) {
			movementNeeded.add(new Vector2(1,0));// Adds right pointing directional vector
			moving = true;
			setFacingRight(false);
		}		
		
		move(movementNeeded);
		if (moving) {
			textureSequence(deltaTime);
		}
	}
	
	private void setFacingRight(boolean val) {
		facingRight = val;
	}
	
	/**
	 * Controls the texture of the Firefighter. Jump texture occurs when jumping, running textures otherwise
	 * @param deltaTime amount of time since last update loop call
	 */
	public void textureSequence(float deltaTime) {
		currentTime += deltaTime;
		switch(firemanTexture) {
		case JUMPING:
			setTexture(TextureManager.getFiremanJump()); // Jump Texture
			if (currentTime >= nextGraphicUpdate) { // Keep nextGraphicUpdate updated as to not have a back log of updates when the jump is finished
				nextGraphicUpdate += 0.1f;
			}
			break;
			
		case TEXTURE1:
			setTexture(TextureManager.getFirstFireman()); // Texture 1
			if (currentTime >= nextGraphicUpdate) {
				firemanTexture = FirefighterTexture.TEXTURE2; // Swap if enough time has elapsed
				nextGraphicUpdate += 0.1f;
			}
			break;
		
		case TEXTURE2:
			setTexture(TextureManager.getSecondFireman());// Texture 2
			if (currentTime >= nextGraphicUpdate) {
				firemanTexture = FirefighterTexture.TEXTURE1; // Swap if enough time has elapsed
				nextGraphicUpdate += 0.1f;
			}
			break;
		}
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
			if (jumpState.hasTimeElapsed()) { // Checks amount of time moving upwards
				jumpState = Jumping.FALLING; // Begins to fall once jump time has elapsed
			}else {
				jumpState.addTime(deltaTime); // Otherwise continues to move upwards
				verticalMov = new Vector2(0,1);
				onFloor = false;
			}
			break;
			
		case NOT_JUMPING: // Occurs when the fireman is on the floor/ place where he can jump
			verticalMov = new Vector2(0,-1); // Apply gravity so player will fall when walking off a ledge
			if (keyPressed) { // Starts jump when key is pressed
				jumpState = Jumping.JUMPING;
				firemanTexture = FirefighterTexture.JUMPING;
				jumpState.startTimer();
				textureSequence(deltaTime); // Update graphics to load jumping texture
			}
			break;
	
		case FALLING: // Occurs when the fireman is falling from a jump he has just made
			verticalMov = new Vector2(0,-1);
			if(onFloor) // Stops falling (ie has the ability to jump again) once on the floor
				jumpState = Jumping.NOT_JUMPING;
				firemanTexture = FirefighterTexture.TEXTURE1;
				textureSequence(deltaTime);
			break;
		}
		return verticalMov;
	}
	

}
