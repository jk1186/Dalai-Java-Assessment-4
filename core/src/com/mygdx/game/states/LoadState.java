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

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class LoadState  extends State {

    private Texture background = new Texture("Menu.jpg");
    private Texture buttonPressed = new Texture("notPressedLoad.png");
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
        save1 = new Button(buttonPressed, buttonNotPressed, 350, 100,
                new Vector2((Kroy.WIDTH/2) - 175, Kroy.HEIGHT - (Kroy.HEIGHT/3) -50 - 250),false, false);
        save2 = new Button(buttonPressed, buttonNotPressed, 350, 100,
                new Vector2((Kroy.WIDTH/2) - 175, Kroy.HEIGHT - 2* (Kroy.HEIGHT/3) -100),false, false);
        save3 = new Button(buttonPressed, buttonNotPressed, 350, 100,
                new Vector2((Kroy.WIDTH/2) - 175, Kroy.HEIGHT - 3*(Kroy.HEIGHT/3) -50 + 150),false, false);
        File folder = new File("..\\saves\\");
        File[] saveFiles = folder.listFiles();

        if(saveFiles.length > 3) {

            Date d;
            Date currentOldest;
            try {
                currentOldest = new SimpleDateFormat("yyyyMMddHHmmss").parse(saveFiles[0].getName());
            } catch (Exception e) {
                currentOldest = new Date(01 / 02 / 03);
                e.printStackTrace();
            }
            int marker = 0;
            for (int i = 1; i < saveFiles.length; i++) {
                try {
                    d = new SimpleDateFormat("yyyyMMddHHmmss").parse(saveFiles[i].getName());
                } catch (Exception e) {
                    System.out.println("Invalid Filename");
                    d = new Date(01 / 02 / 03);
                    e.printStackTrace();
                }
                if (d.before(currentOldest)) {
                    currentOldest = d;
                    marker = i;
                }
            }
            saveFiles[marker].delete();




            File[] saveFiles2 = folder.listFiles();
            saveFiles = new File[saveFiles2.length];

            File oldest = saveFiles2[0];
            Date oldestDate;
            try {
                oldestDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(saveFiles2[0].getName());
            } catch (Exception e) {
                System.out.println("Invalid Filename");
                oldestDate = new Date(01 / 02 / 03);
                e.printStackTrace();
            }

            File youngest = saveFiles2[0];
            Date youngestDate;
            try {
                youngestDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(saveFiles2[0].getName());
            } catch (Exception e) {
                System.out.println("Invalid Filename");
                youngestDate = new Date(01 / 02 / 03);
                e.printStackTrace();
            }

            File mid = saveFiles2[0];
            Date midDate;
            try {
                midDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(saveFiles2[0].getName());
            } catch (Exception e) {
                System.out.println("Invalid Filename");
                midDate = new Date(01 / 02 / 03);
                e.printStackTrace();
            }


            for (int i = 0; i < saveFiles2.length; i++) {
                try {
                    d = new SimpleDateFormat("yyyyMMddHHmmss").parse(saveFiles2[i].getName());
                } catch (Exception e) {
                    System.out.println("Invalid Filename");
                    d = new Date(01 / 02 / 03);
                    e.printStackTrace();
                }
                if (d.after(oldestDate)) {
                    oldest = saveFiles2[i];
                    oldestDate = d;
                } else if (d.before(youngestDate)) {
                    youngest = saveFiles2[i];
                    youngestDate = d;
                } else {
                    mid = saveFiles2[i];
                    midDate = d;
                }

            }

            saveFiles[0] = youngest;
            saveFiles[1] = mid;
            saveFiles[2] = oldest;

        }

        try{
            saveFile1 = saveFiles[0].getCanonicalPath();
        }catch(Exception e){
            saveFile1 = "empty";
        }
        try{
            saveFile2 = saveFiles[1].getCanonicalPath();
        }catch(Exception e){
            saveFile2 = "empty";
        }
        try{
            saveFile3 = saveFiles[2].getCanonicalPath();
        }catch(Exception e){
            saveFile3 = "empty";
        }

    }


    @Override
    public void update(float deltaTime){
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
            if (Gdx.input.isTouched() && saveFile1 != "empty") {
                if (saveData.getBoolean("effects")) {
                    click.play();
                }
                gameStateManager.push(new PlayState(gameStateManager, saveFile1));
            }
        } else {
            save1.setActive(false);
        }
        if (save2.mouseInRegion()) {
            save2.setActive(true);
            if (Gdx.input.isTouched() && saveFile2 != "empty") {
                if (saveData.getBoolean("effects")) {
                    click.play();
                }
                gameStateManager.push(new PlayState(gameStateManager, saveFile2));
            }
        } else {
            save2.setActive(false);
        }
        if (save3.mouseInRegion()) {
            save3.setActive(true);
            if (Gdx.input.isTouched() && saveFile3 != "empty") {
                if (saveData.getBoolean("effects")) {
                    click.play();
                }
                gameStateManager.push(new PlayState(gameStateManager, saveFile3));
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
