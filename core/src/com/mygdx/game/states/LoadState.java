package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Kroy;
import com.mygdx.game.misc.Button;

import java.io.IOException;

public class LoadState  extends State {

    private Texture background = new Texture("Menu.jpg");
    private Texture buttonPressed = new Texture("PressedLoad.png");
    private Texture buttonNotPressed = new Texture("notPressedLoad.png");
    private Button save1;
    private Button save2;
    private Button save3;
    private Button back;
    private Preferences saveData;
    private Sound click = Gdx.audio.newSound(Gdx.files.internal("click.wav"));
    private BitmapFont font;
    private String saveFile1;
    private String saveFile2;
    private String saveFile3;

    public LoadState(GameStateManager gameStateManager){
        super(gameStateManager);
        saveData = Gdx.app.getPreferences("kroy");
        font = new BitmapFont();
        back = new Button(new Texture("backbutton2.png"), new Texture("backbutton1.png"),
                100, 100, new Vector2(30, 960), false, false);
        save1 = new Button(buttonNotPressed, buttonPressed, 350, 100,
                new Vector2((Kroy.WIDTH/2) - 175, Kroy.HEIGHT - (Kroy.HEIGHT/3) -50 - 250),false, false);
        save2 = new Button(buttonNotPressed, buttonPressed, 350, 100,
                new Vector2((Kroy.WIDTH/2) - 175, Kroy.HEIGHT - 2* (Kroy.HEIGHT/3) -100),false, false);
        save3 = new Button(buttonNotPressed, buttonPressed, 350, 100,
                new Vector2((Kroy.WIDTH/2) - 175, Kroy.HEIGHT - 3*(Kroy.HEIGHT/3) -50 + 150),false, false);
        saveFile1 = "..\\saves\\save1.json";
        saveFile2 = "..\\saves\\save2.json";
        saveFile3 = "..\\saves\\save3.json";
    }

    @Override
    public void update(float deltaTime) throws IOException {
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
        if (save1.mouseInRegion()) {
            save1.setActive(true);
            if (Gdx.input.isTouched()) {
                if (saveData.getBoolean("effects")) {
                    click.play();
                }
                //gameStateManager.push(new GameState(gameStateManager, saveFile1));
            }
        } else {
            save1.setActive(false);
        }
        if (save2.mouseInRegion()) {
            save2.setActive(true);
            if (Gdx.input.isTouched()) {
                if (saveData.getBoolean("effects")) {
                    click.play();
                }
                // gameStateManager.push(new GameState(gameStateManager, saveFile2));
            }
        } else {
            save2.setActive(false);
        }
        if (save3.mouseInRegion()) {
            save3.setActive(true);
            if (Gdx.input.isTouched()) {
                if (saveData.getBoolean("effects")) {
                    click.play();
                }
                // gameStateManager.push(new GameState(gameStateManager, saveFile3));
            }
        } else {
            save3.setActive(false);
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, Kroy.WIDTH, Kroy.HEIGHT);
        spriteBatch.draw(save1.getTexture(), save1.getPosition().x, save1.getPosition().y, save1.getWidth(), save1.getHeight());
        spriteBatch.draw(save2.getTexture(), save2.getPosition().x, save2.getPosition().y, save2.getWidth(), save2.getHeight());
        spriteBatch.draw(save3.getTexture(), save3.getPosition().x, save3.getPosition().y, save3.getWidth(), save3.getHeight());
        spriteBatch.draw(back.getTexture(), back.getPosition().x, back.getPosition().y, back.getWidth(), back.getHeight());

        font.draw(spriteBatch, saveFile1, save1.getPosition().x + 50, save1.getPosition().y + 50);
        font.draw(spriteBatch, saveFile2, save2.getPosition().x + 50, save2.getPosition().y + 50);
        font.draw(spriteBatch, saveFile3, save3.getPosition().x + 50, save3.getPosition().y + 50);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        save1.dispose();
        save2.dispose();
        save3.dispose();
        background.dispose();
        buttonNotPressed.dispose();
        buttonPressed.dispose();
    }
}
