package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Kroy;
import com.mygdx.game.misc.Button;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * An implementation of the abstract class State which controls the Level Select Screen.
 *
 * @author Lucy Ivatt
 * @since 11/1/2020
 */

public class LevelSelectState extends State{

    private Texture background;
    private Button level1;
    private Button level2;
    private Button level3;
    private Button level4;
    private Button level5;
    private Button level6;
    private Button back;
    private ArrayList<Button> buttons;

    private Preferences saveData;
    private Sound click = Gdx.audio.newSound(Gdx.files.internal("click.wav"));
    private Sound honk = Gdx.audio.newSound(Gdx.files.internal("honk.wav"));

    //DJ - Added all textures here to fix the memory leak that NPStudios had (new textures were being created each
    // update() call

    private Texture PressedGreenTexture1 = new Texture("PressedGreen1.png");
    private Texture PressedGreenTexture2 = new Texture("PressedGreen2.png");
    private Texture PressedGreenTexture3 = new Texture("PressedGreen3.png");
    private Texture PressedGreenTexture4 = new Texture("PressedGreen4.png");
    private Texture PressedGreenTexture5 = new Texture("PressedGreen5.png");


    private Texture NotPressedGreenTexture1 = new Texture("NotPressedGreen1.png");
    private Texture NotPressedGreenTexture2 = new Texture("NotPressedGreen2.png");
    private Texture NotPressedGreenTexture3 = new Texture("NotPressedGreen3.png");
    private Texture NotPressedGreenTexture4 = new Texture("NotPressedGreen4.png");
    private Texture NotPressedGreenTexture5 = new Texture("NotPressedGreen5.png");



    private Texture PressedBlueTexture2 = new Texture("PressedBlue2.png");
    private Texture PressedBlueTexture3 = new Texture("PressedBlue3.png");
    private Texture PressedBlueTexture4 = new Texture("PressedBlue4.png");
    private Texture PressedBlueTexture5 = new Texture("PressedBlue5.png");
    private Texture PressedBlueTexture6 = new Texture("PressedBlue6.png");

    private Texture NotPressedBlueTexture2 = new Texture("NotPressedBlue2.png");
    private Texture NotPressedBlueTexture3 = new Texture("NotPressedBlue3.png");
    private Texture NotPressedBlueTexture4 = new Texture("NotPressedBlue4.png");
    private Texture NotPressedBlueTexture5 = new Texture("NotPressedBlue5.png");
    private Texture NotPressedBlueTexture6 = new Texture("NotPressedBlue6.png");

    // Assesment 4
    private Texture tick;
    private Texture cross;
    private Button easy;
    private Button normal;
    private Button hard;
    final double EASY = 0.5;
    final double NORMAL = 1;
    final double HARD = 2;




    protected LevelSelectState(GameStateManager gameStateManager) {
        super(gameStateManager);
        background = new Texture("LevelSelectBackground.png");
        saveData = Gdx.app.getPreferences("Kroy");

        saveData.putBoolean("1", true);
        saveData.putBoolean("2", true);
        saveData.putBoolean("3", true);
        saveData.putBoolean("4", true);
        saveData.putBoolean("5", true);

        back = new Button(new Texture("backbutton2.png"), new Texture("backbutton1.png"),
                100, 100, new Vector2(30, 960), false, false);

        level1 = new Button(new Texture("PressedBlue1.png"), new Texture("NotPressedBlue1.png"),
                350, 100, new Vector2(Kroy.WIDTH / 2 - 350 / 2 - 100 - 350, 400), false,
                false);

        level2 = new Button(new Texture("PressedGrey2.png"), new Texture("PressedGrey2.png"),
                350, 100, new Vector2(Kroy.WIDTH / 2 - 350 / 2, 400), false, true);

        level3 = new Button(new Texture("PressedGrey3.png"), new Texture("PressedGrey3.png"),
                350, 100, new Vector2(Kroy.WIDTH / 2 + 350 / 2 + 100, 400), false,
                true);

        level4 = new Button(new Texture("PressedGrey4.png"), new Texture("PressedGrey4.png"),
                350, 100, new Vector2(Kroy.WIDTH / 2 - 350 / 2 - 100 - 350, 200), false,
                true);

        level5 = new Button(new Texture("PressedGrey5.png"), new Texture("PressedGrey5.png"),
                350, 100, new Vector2(Kroy.WIDTH / 2 - 350 / 2, 200), false, true);

        level6 = new Button(new Texture("PressedGrey6.png"), new Texture("PressedGrey6.png"),
                350, 100, new Vector2(Kroy.WIDTH / 2 + 350 / 2 + 100, 200), false,
                true);



        // Assessment 4
        tick = new Texture("tick.png");
        cross = new Texture("cross.png");

        easy = new Button(tick, cross, 100, 100, new Vector2(Kroy.WIDTH / 2  - 500, 50), saveData.getBoolean("easy"),(!(saveData.getBoolean("easy"))));
        normal = new Button(tick, cross, 100, 100, new Vector2(Kroy.WIDTH / 2 - 350 / 2 + 125, 50), saveData.getBoolean("normal"), (!(saveData.getBoolean("normal"))));
        hard = new Button(tick, cross, 100, 100, new Vector2(Kroy.WIDTH / 2 + 350 / 2 + 225  , 50), saveData.getBoolean("hard"), (!(saveData.getBoolean("hard"))));


        buttons = new ArrayList<>(Arrays.asList(level1, level2, level3, level4, level5, level6, back, easy , normal, hard));


        if(saveData.getBoolean("easy")){
            Kroy.setDifficultyMultiplier(EASY);
        }
        else if(saveData.getBoolean("normal")){
            Kroy.setDifficultyMultiplier(NORMAL);
        }
        else if (saveData.getBoolean("hard")){
            Kroy.setDifficultyMultiplier(HARD);
        }
        System.out.println(Kroy.difficultyMultiplier);
    }

    /**
     * The game logic which is executed due to specific user inputs. Is called in the update method.
     *
     * Checks if mouse is hovering over a button and plays the animation accordingly as well as checking for
     * mouse clicks which will activate the function of the button.
     */
    public void handleInput(){
        if (back.mouseInRegion()) {
            back.setActive(true);
            if (Gdx.input.isTouched()) {
                if (saveData.getBoolean("effects")) {
                    click.play();
                }
                gameStateManager.pop();
            }
        } else {
            back.setActive(false);
        }

        if (level1.mouseInRegion() && level1.isLocked() == false){
            level1.setActive(true);
            if (Gdx.input.isTouched()) {
                if (saveData.getBoolean("effects")) {
                    honk.play();
                }
                gameStateManager.push(new PlayState(gameStateManager, 1));
            }
        }
        else {
            level1.setActive(false);
        }

        if (level2.mouseInRegion() && level2.isLocked() == false){
            level2.setActive(true);
            if (Gdx.input.isTouched()) {
                if (saveData.getBoolean("effects")) {
                    honk.play();
                }
                gameStateManager.push(new PlayState(gameStateManager, 2));
            }
        }
        else {
            level2.setActive(false);
        }

        if (level3.mouseInRegion() && level3.isLocked() == false){
            level3.setActive(true);
            if (Gdx.input.isTouched()) {
                if (saveData.getBoolean("effects")) {
                    honk.play();
                }
                gameStateManager.push(new PlayState(gameStateManager, 3));
            }
        }
        else {
            level3.setActive(false);
        }

        if (level4.mouseInRegion() && level4.isLocked() == false){
            level4.setActive(true);
            if (Gdx.input.isTouched()) {
                if (saveData.getBoolean("effects")) {
                    honk.play();
                }
                gameStateManager.push(new PlayState(gameStateManager, 4));
            }
        }
        else {
            level4.setActive(false);
        }

        if (level5.mouseInRegion() && level5.isLocked() == false){
            level5.setActive(true);
            if (Gdx.input.isTouched()) {
                if (saveData.getBoolean("effects")) {
                    honk.play();
                }
                gameStateManager.push(new PlayState(gameStateManager, 5));
            }
        }
        else {
            level5.setActive(false);
        }

        if (level6.mouseInRegion() && level6.isLocked() == false){
            level6.setActive(true);
            if (Gdx.input.isTouched()) {
                if (saveData.getBoolean("effects")) {
                    honk.play();
                }

                gameStateManager.push(new PlayState(gameStateManager, 6));
            }
        }
        else {
            level6.setActive(false);
        }

        // Assessment 4
        if(easy.mouseInRegion()){
            if(Gdx.input.isTouched()) {
                saveData.putFloat("Diff", (float)EASY);
                easy.setActive(true);
                normal.setActive(false);
                hard.setActive(false);

                saveData.putBoolean("easy", true);
                saveData.putBoolean("normal", false);
                saveData.putBoolean("hard", false);

                Kroy.setDifficultyMultiplier(EASY);
            }
        }
        else if(normal.mouseInRegion()){
            if(Gdx.input.isTouched()) {
                saveData.putFloat("Diff", (float)NORMAL);
                normal.setActive(true);
                easy.setActive(false);
                hard.setActive(false);
                saveData.putBoolean("easy", false);
                saveData.putBoolean("normal", true);
                saveData.putBoolean("hard", false);
                Kroy.setDifficultyMultiplier(NORMAL);
            }
        }
        else if(hard.mouseInRegion()){
            if(Gdx.input.isTouched()) {
                saveData.putFloat("Diff", (float)HARD);
                hard.setActive(true);
                normal.setActive(false);
                easy.setActive(false);
                saveData.putBoolean("easy", false);
                saveData.putBoolean("normal", false);
                saveData.putBoolean("hard", true);
                Kroy.setDifficultyMultiplier(HARD);
            }
        }
        saveData.flush();
    }

    /**
     * Updates the game logic before the next render() is called. In this instance the update method checks the game
     * save and unlocks level buttons accordingly.
     * @param deltaTime the amount of time which has passed since the last render() call
     */
    @Override
    public void update(float deltaTime) {
        handleInput();

        if(saveData.getBoolean("1") == true) {
            level1.setOnTexture(PressedGreenTexture1);
            level1.setOffTexture(NotPressedGreenTexture1);
            level2.setLocked(false);

            level2.setOnTexture(PressedBlueTexture2);
            level2.setOffTexture(NotPressedBlueTexture2);
        }

        if(saveData.getBoolean("2") == true) {
            level2.setOnTexture(PressedGreenTexture2);
            level2.setOffTexture(NotPressedGreenTexture2);
            level3.setLocked(false);

            level3.setOnTexture(PressedBlueTexture3);
            level3.setOffTexture(NotPressedBlueTexture3);
        }

        if(saveData.getBoolean("3") == true) {
            level3.setOnTexture(PressedGreenTexture3);
            level3.setOffTexture(NotPressedGreenTexture3);
            level4.setLocked(false);

            level4.setOnTexture(PressedBlueTexture4);
            level4.setOffTexture(NotPressedBlueTexture4);
        }

        if(saveData.getBoolean("4") == true) {
            level4.setOnTexture(PressedGreenTexture4);
            level4.setOffTexture(NotPressedGreenTexture4);
            level5.setLocked(false);

            level5.setOnTexture(PressedBlueTexture5);
            level5.setOffTexture(NotPressedBlueTexture5);
        }

        if(saveData.getBoolean("5") == true) {
            level5.setOnTexture(PressedGreenTexture5);
            level5.setOffTexture(NotPressedGreenTexture5);
            level6.setLocked(false);

            level6.setOnTexture(PressedBlueTexture6);
            level6.setOffTexture(NotPressedBlueTexture6);
        }

        if(saveData.getBoolean("6") == true) {
            level6.setOnTexture(PressedGreenTexture5);
            level6.setOffTexture(NotPressedGreenTexture5);
        }

    }


    /**
     * Used to draw elements onto the screen.
     * @param spriteBatch a container for all elements which need rendering to the screen
     */
    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, Kroy.WIDTH, Kroy.HEIGHT);
        spriteBatch.draw(back.getTexture(), back.getPosition().x, back.getPosition().y, back.getWidth(), back.getHeight());

        for (Button level : buttons) {
            spriteBatch.draw(level.getTexture(), level.getPosition().x, level.getPosition().y, level.getWidth(), level.getHeight());
        }
        spriteBatch.end();
        //dispose();
    }

    /**
     * Used to dispose of all textures, music etc. when no longer required to avoid memory leaks
     */
    @Override
    public void dispose() {
        background.dispose();
        for (Button button : buttons) {
            button.dispose();
        }

        click.dispose();
        honk.dispose();

        //DJ - disposing of all textures once screen closed to fix memory leak
        PressedGreenTexture1.dispose();
        PressedGreenTexture2.dispose();
        PressedGreenTexture3.dispose();
        PressedGreenTexture4.dispose();
        PressedGreenTexture5.dispose();

        NotPressedGreenTexture1.dispose();
        NotPressedGreenTexture2.dispose();
        NotPressedGreenTexture3.dispose();
        NotPressedGreenTexture4.dispose();
        NotPressedGreenTexture5.dispose();

        PressedBlueTexture2.dispose();
        PressedBlueTexture3.dispose();
        PressedBlueTexture4.dispose();
        PressedBlueTexture5.dispose();
        PressedBlueTexture6.dispose();

        NotPressedBlueTexture2.dispose();
        NotPressedBlueTexture3.dispose();
        NotPressedBlueTexture4.dispose();
        NotPressedBlueTexture5.dispose();
        NotPressedBlueTexture6.dispose();

        // Assessment 4
        tick.dispose();
        cross.dispose();
    }
}
