package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Kroy;
import com.mygdx.game.minigame.Enemy;
import com.mygdx.game.minigame.Fireman;
import com.mygdx.game.minigame.MiniGameUnit;
import com.mygdx.game.misc.Button;

/**
 * A state used to control the minigame phase of the game
 * @author lnt20
 * 
 */
public class MiniGameState extends State {
	
	private Texture background;
	private Button quitLevel;
	private Button quitGame;
	private Fireman fireMan;
	private Enemy enemy;
	
	public MiniGameState(GameStateManager gameStateManager) {
		super(gameStateManager);
		background = new Texture("LevelProportions.png");
		
        quitLevel = new Button(new Texture("PressedQuitLevel.png"),
                new Texture("NotPressedQuitLevel.png"),350 / 2, 100 / 2,
                new Vector2(30, 30), false, false);

        quitGame = new Button(new Texture("PressedQuitGame.png"),
                new Texture("NotPressedQuitGame.png"), 350 / 2, 100 / 2,
                new Vector2(1920 - 30 - 350 / 2, 30), false, false);
        
        fireMan = new Fireman(100);
        enemy = new Enemy();
        
	}

	

	
	/**
	 * Performs the logic and graphical updates the state needs to run
	 *@param deltaTime the amount of time which has passed since the last render() call
	 */
	@Override
	public void update(float deltaTime) {
		handleInput();
		fireMan.updatePos(deltaTime);
		enemy.updatePos(deltaTime);


	}
	
	
	/**
     * The game logic which is executed due to specific user inputs. Is called in the update method.
     */
    public void handleInput() {

        // Checks for hover and clicks on the 2 buttons located on the play screen.
        if (quitGame.mouseInRegion()){
            quitGame.setActive(true);
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
                System.exit(0);
                }
            }

        else {
            quitGame.setActive(false);
        }

        if (quitLevel.mouseInRegion()){
            quitLevel.setActive(true);
            if (Gdx.input.isTouched()) {
                gameStateManager.pop();
            }
        }

        else {
            quitLevel.setActive(false);
        }
    
    }


	/**
     * Used to draw elements onto the screen.
     * @param spriteBatch a container for all elements which need rendering to the screen.
     */ 
	@Override
	public void render(SpriteBatch spriteBatch) {
		
		spriteBatch.begin();
		
        // Draws background and map onto play screen
        spriteBatch.draw(background, 0, 0, Kroy.WIDTH, Kroy.HEIGHT);

        // Draws buttons onto play screen
        spriteBatch.draw(quitLevel.getTexture(), quitLevel.getPosition().x, quitLevel.getPosition().y, quitLevel.getWidth(), quitLevel.getHeight());
        spriteBatch.draw(quitGame.getTexture(), quitGame.getPosition().x, quitGame.getPosition().y, quitGame.getWidth(), quitGame.getHeight());

        
        // Draws MiniGameUnits
        for (MiniGameUnit mg: new MiniGameUnit[] {fireMan,enemy}) {
	        spriteBatch.draw(mg.getTexture(), mg.getPosition().x, mg.getPosition().y, mg.getWidth(), mg.getHeight());
        }
        spriteBatch.end();
	}

	/**
	 * Used to dispose all texture, music etc. when no longer required
	 */
	@Override
	public void dispose() {
		background.dispose();
		quitLevel.dispose();
		quitGame.dispose();
		fireMan.dispose();
		enemy.dispose();

	}

}
