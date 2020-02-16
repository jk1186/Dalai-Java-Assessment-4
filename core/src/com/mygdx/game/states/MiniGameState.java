package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Kroy;
import com.mygdx.game.map.TiledGameMap;
import com.mygdx.game.minigame.Boss;
import com.mygdx.game.minigame.Enemy;
import com.mygdx.game.minigame.Firefighter;
import com.mygdx.game.minigame.MiniGameUnit;
import com.mygdx.game.minigame.MiniGameUnitManager;
import com.mygdx.game.minigame.TextureManager;
import com.mygdx.game.misc.Button;
import com.mygdx.game.sprites.Entity;

/**
 * A state used to control the minigame phase of the game
 * @author Luke Taylor
 * 
 */
public class MiniGameState extends State {
	
	private Texture background;
	private Button quitLevel;
	private Button quitGame;
	private MiniGameUnitManager unitManager;
	private TiledGameMap gameMap;
	private String[] mapFiles = {"MiniGameLevel1.tmx","MiniGameLevel2.tmx"};
	
	public MiniGameState(GameStateManager gameStateManager, int level) {
		super(gameStateManager);
		gameMap = new TiledGameMap(mapFiles[level]);
		background = new Texture("miniGameLevelProportions.png");
		
        quitLevel = new Button(new Texture("PressedQuitLevel.png"),
                new Texture("NotPressedQuitLevel.png"),350 / 2, 100 / 2,
                new Vector2(30, 30), false, false);

        quitGame = new Button(new Texture("PressedQuitGame.png"),
                new Texture("NotPressedQuitGame.png"), 350 / 2, 100 / 2,
                new Vector2(1920 - 30 - 350 / 2, 30), false, false);
        
        unitManager = new MiniGameUnitManager(gameMap,level);
        
        
	}	

	
	/**
	 * Performs the logic and graphical updates the state needs to run
	 *@param deltaTime the amount of time which has passed since the last render() call
	 */
	@Override
	public void update(float deltaTime) {		
		handleInput();
		unitManager.updateAll(deltaTime);     
		// Checks if user presses ENTER when game is over and takes them back to level select.
        if ((unitManager.isLevelLost()|| unitManager.isLevelWon()) && Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            gameStateManager.set(new LevelSelectState(gameStateManager));
        }
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

		spriteBatch.end();

		gameMap.render();
		
		spriteBatch.begin();
        
        // Draws buttons onto play screen
        spriteBatch.draw(quitLevel.getTexture(), quitLevel.getPosition().x, quitLevel.getPosition().y, quitLevel.getWidth(), quitLevel.getHeight());
        spriteBatch.draw(quitGame.getTexture(), quitGame.getPosition().x, quitGame.getPosition().y, quitGame.getWidth(), quitGame.getHeight());
      
        //Loop through all entities in unitManager and renders them
		for (Entity mg: unitManager) {
        	spriteBatch.draw(
        			mg.getTexture(), 											//Gives texture that needs to be rendered 
        			mg.getPosition().x, mg.getPosition().y, 					//Gives position of the entity
        			mg.getCentre().y, mg.getCentre().y, 						//Gives the centre of the entity
        			mg.getWidth(), mg.getHeight(), 								//Gives the height and width of the texture
        			mg.getScaleX(), mg.getScaleY(), 							//Gives the relative scale of the texture
        			mg.getRotation(), 1, 1, 									//Gives the angle of rotation
        			mg.getTexture().getWidth(), mg.getTexture().getHeight(), 	//Gives the height and width of the texture
        			mg.isFacingRight(), false									//Gives the direction it's meant to face
        			);
        }
        
        // If fireman dead, display lossing splash screen
        if (unitManager.isLevelLost()) {
            spriteBatch.draw(new Texture("levelFail.png"), 0, 0);
            Kroy.INTRO.setPitch(Kroy.ID, 1f);
        }

        // If boss defeated, display winning splash screen
        if (unitManager.isLevelWon()) {
            spriteBatch.draw(new Texture("LevelWon.png"), 0, 0);
            Kroy.INTRO.setPitch(Kroy.ID, 1f);
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
		unitManager.dispose(); //Calls disposal of all textures of units stored here

	}

}
