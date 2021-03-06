package com.mygdx.game.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Kroy;

/**
 * The Timer class is used to keep track of how long the level has been running and is used to check if the
 * levels time limit has been reached.
 *
 * @author Lucy Ivatt
 */

// Assessment 4: we have renamed this class as LibGdx already uses a class called Timer which led to confusion
public class Stopwatch {
    private float time;
    private float timeLimit;
    private String timeString;


    public Stopwatch(float timeLimit) {
        time = 0;
        this.timeLimit = timeLimit;
        timeString = "0";
    }

    /**
     * A method which updates the time every game tick using Libgdx's in-built method Gdx.graphics.deltaTime()
     */
    public void update() {
        time += (Gdx.graphics.getDeltaTime());
        if ((timeLimit - time) > 0) {
            timeString = String.format("%.0f", timeLimit - time);
        } else {
            timeString = String.format("%.0f", 0.0);
        }
    }

    /**
     * A method which draws the current time onto the game screen.
     * @param spriteBatch the SpriteBatch used within the PlayState to render elements to the screen
     * @param font the BitmapFont which will be used for the text
     */
    public void drawTime(SpriteBatch spriteBatch, BitmapFont font) {
        font.draw(spriteBatch, "Time Remaining: " + timeString, 780, Kroy.HEIGHT - 1005);
    }
    public void expiredTime(SpriteBatch spriteBatch, BitmapFont font){
        font.draw(spriteBatch, "Time Remaining: " + 0 , 780, Kroy.HEIGHT - 1005);
    }
    public float getTime() {
        if (time < 0){
            return 0;
        }else {
            return time;
        }
    }
}
