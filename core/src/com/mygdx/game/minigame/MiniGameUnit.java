package com.mygdx.game.minigame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.map.MiniGameTileType;
import com.mygdx.game.map.TiledGameMap;
import com.mygdx.game.sprites.Unit;

/**
 * Abstract class to define basic properties of MiniGameEntities
 * @author Luke Taylor
 *
 */
public abstract class MiniGameUnit extends Unit {

	protected float speed;
	protected boolean facingRight = false;
	
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
     * A method which controllers Firetruck movement depending on the direction input
     * @param direction 1 = Left, 2 = Right, 3 = Up, 4 = Down
     */
    public void move(int direction) { // 1, 2, 3, 4 --> Left, Right, Up, Down
        float deltaTime = Gdx.graphics.getDeltaTime();
        Vector2 newPosition = new Vector2(getPosition());
        if (direction == 2) {
            newPosition.set(getPosition().x + speed * deltaTime, getPosition().y);
        } else if (direction == 1) {
            newPosition.set(getPosition().x - speed * deltaTime, getPosition().y);
        } else if (direction == 3) {
            newPosition.set(getPosition().x, getPosition().y + speed * deltaTime);
        } else if (direction == 4) {
        	if (getPosition().y - speed * deltaTime < 212 || isBelowCollidable(new Vector2(getPosition().x, getPosition().y - speed * deltaTime))  ) {
        		newPosition.set(getPosition().x, getLowestOnLevel(getPosition().y));
        	}else {
        		newPosition.set(getPosition().x, getPosition().y - speed * deltaTime);
        	}
        }
        
        if ((isUncollidableTile(newPosition.x,newPosition.y)									// Check bottom left
        		&& isUncollidableTile(newPosition.x + getWidth(),newPosition.y + getHeight())	// Check top left
        		&& isUncollidableTile(newPosition.x, newPosition.y + getHeight())				// Check bottom right
        		&& isUncollidableTile(newPosition.x + getWidth(),newPosition.y))) {				// Check top right
        	setPosition(newPosition.x,newPosition.y);
        }

        
    }
		
	/**
	 * Checks if position passed is collidable or not
	 * @param pos Vector2 of new position to be checked for collision
	 * @return boolean if new position is on collidable tile or not
	 */
	public boolean isUncollidableTile(float posX, float posY) {
		return !(MiniGameUnitManager.getGameMap().getMiniGameTileTypeByScreenCoordinate(posX,posY).getCollidable());
	}
	
	public boolean isBelowCollidable(Vector2 newPos) {
		return !(isUncollidableTile(newPos.x,newPos.y)||isUncollidableTile(newPos.x+getWidth(),newPos.y));
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
	
	/**
	 * Method used to round down to the nearest layers when entities are landing. Called when entity cannot complete a full downwards movement 
	 * @param gameY float any valid coordinate on the game screen
	 * @return the lowest value of that tile layer
	 */
	public static float getLowestOnLevel(float gameY) {
		int x = (int) (gameY-212)/64;
		x = x*64;
		x += 212;
		return x;
		
	}
	
}
