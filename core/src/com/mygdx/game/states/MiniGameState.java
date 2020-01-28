/**
 * 
 */
package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * A state used to control the minigame phase of the game
 * @author lnt20
 * 
 *
 */
public class MiniGameState extends State {

	public MiniGameState(GameStateManager gameStateManager) {
		super(gameStateManager);
		// TODO Auto-generated constructor stub
	}

	
	/**
	 *@param deltaTime the amount of time which has passed since the last render() call
	 */
	@Override
	public void update(float deltaTime) {
		
	}

	/**
     * Used to draw elements onto the screen.
     * @param spriteBatch a container for all elements which need rendering to the screen.
     */ 
	@Override
	public void render(SpriteBatch spriteBatch) {
		// TODO Auto-generated method stub

	}

	/**
	 * Used to dispose all texture, music etc. when no longer required
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
