package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.states.PlayState;

/**
 * The class which creates a firetruck object to be controlled by the user within the PlayState.
 *
 * @author Cassie Lillystone
 */

public class Firetruck extends Character {

    private int maxWater;
    private int currentWater;
    private boolean selected;
    private boolean infiniteHealth;

    public Firetruck(Vector2 position, int width, int height, Texture texture, int maxHealth, int range, Unit target,
                     int speed, int dps, int maxWater, boolean selected) {
        super(position, width, height, texture, maxHealth, range, target, speed, dps);
        this.maxWater = maxWater;
        this.currentWater = maxWater;
        this.selected = selected;
        this.infiniteHealth = false;
    }

    /**
     * A method which controllers Firetruck movement depending on the direction input
     * @param direction 1 = Left, 2 = Right, 3 = Up, 4 = Down
     */
    public void move(int direction) { // 1, 2, 3, 4 --> Left, Right, Up, Down
        float deltaTime = Gdx.graphics.getDeltaTime();
        Vector2 newPosition = new Vector2(getPosition());
        if (direction == 2) {
            newPosition.set(getPosition().x + getSpeed() * deltaTime, getPosition().y);
        } else if (direction == 1) {
            newPosition.set(getPosition().x - getSpeed() * deltaTime, getPosition().y);
        } else if (direction == 3) {
            newPosition.set(getPosition().x, getPosition().y + getSpeed() * deltaTime);
        } else if (direction == 4) {
            newPosition.set(getPosition().x, getPosition().y - getSpeed() * deltaTime);
        }
        
        if (!(isOnCollidableTile(newPosition.x,newPosition.y)
        		|| isOnCollidableTile(newPosition.x + getWidth(),newPosition.y + getHeight())
        		|| isOnCollidableTile(newPosition.x, newPosition.y + getHeight())
        		|| isOnCollidableTile(newPosition.x + getWidth(),newPosition.y))) {
        	setPosition(newPosition.x,newPosition.y);
        }
    }

    //ASSESSMENT 4
    /**
     * Activates a given power up
     * @param type the power-up to be activated
     */
    public void powerUp(String type) {
        if (type == "Max Health") {
            maxHealth = maxHealth + 10;
            currentHealth = currentHealth + 10;
        }

        else if (type == "Damage") {
            this.setDamage(this.getDamage() * 2);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    setDamage(getDamage() / 2);
                }
            }, 10);
        }

        else if (type == "Infinite Health") {
            this.infiniteHealth = true;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    infiniteHealth = false;
                }
            }, 10);
        }

        else if (type == "Range") {
            this.setRange(this.getRange() * 2);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    setRange(getRange() / 2);
                }
            }, 10);
        }

        else if (type == "Speed") {
            this.setSpeed(this.getSpeed() * 2);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    setSpeed(getSpeed() / 2);
                }
            }, 10);
        }
    }

    @Override
    public void takeDamage(int damage) {
        if (!infiniteHealth) {
            super.takeDamage(damage);
        }
    }
    
	public Boolean isOnCollidableTile(float posX, float posY) {
		return PlayState.gameMap.getTileTypeByScreenCoordinate(posX,posY).getCollidable();
	}

    public int getMaxWater() {
        return maxWater;
    }

    public int getCurrentWater() {
        return currentWater;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setCurrentWater(int currentWater) {
        this.currentWater = currentWater;
    }

    public void updateCurrentWater(int waterUsed) {
        if ((this.currentWater - waterUsed) < 0) {
            this.currentWater = 0;
            return;
        }
        this.currentWater -=  waterUsed;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}