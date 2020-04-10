package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * The class which creates the Fortress within the level
 *
 * @author Jordan Spooner
 */
public class Fortress extends Unit {

    //Assessment 4 - A unique spec in terms of the range of its defensive weapons and the amount of damage these weapons can deal to Fire Engines.
    private int damage;
    private float attackRange;

    private float spawnRate;
    private ArrayList<Vector2> alienPositions = new ArrayList<>();

    public Fortress(Vector2 position, int width, int height, Texture texture, int maxHealth, float spawnRate, int level, int damage, float attackRange) {
        super(position, width, height, texture, maxHealth);
        this.spawnRate = spawnRate;
        //Assessment 4 - Fortress's weapon
        this.damage = damage;
        this.attackRange = attackRange;

        if (level == 1) {
            alienPositions.add(new Vector2(22 * 32, 1044 - 3 * 32));
            alienPositions.add(new Vector2(33 * 32, 1044 - 3 * 32));
            alienPositions.add(new Vector2(24 * 32, 1044 - 6 * 32));
            alienPositions.add(new Vector2(31 * 32, 1044 - 6 * 32));
            alienPositions.add(new Vector2(28 * 32 - 16, 1044 - 8 * 32));
        }

        else if (level == 2) {
            alienPositions.add(new Vector2(33 + 37 * 32, 212 + 15 * 32));
            alienPositions.add(new Vector2(33 + 41 * 32, 212 + 15 * 32));
            alienPositions.add(new Vector2(33 + 33 * 32, 212 + 17 * 32));
            alienPositions.add(new Vector2(33 + 30 * 32, 212 + 20 * 32));
            alienPositions.add(new Vector2(33 + 30 * 32, 212 + 24 * 32));
            alienPositions.add(new Vector2(33 + 44 * 32, 212 + 20 * 32));
            alienPositions.add(new Vector2(33 + 44 * 32, 212 + 24 * 32));
        }

        else if (level == 3) {
            alienPositions.add(new Vector2(33 + 25 * 32, 212 + 17 * 32));
            alienPositions.add(new Vector2(33 + 29 * 32, 212 + 17 * 32));
            alienPositions.add(new Vector2(33 + 32 * 32, 212 + 19 * 32));
            alienPositions.add(new Vector2(33 + 22 * 32, 212 + 19 * 32));
            alienPositions.add(new Vector2(33 + 20 * 32, 212 + 21 * 32));
            alienPositions.add(new Vector2(33 + 34 * 32, 212 + 21 * 32));
            alienPositions.add(new Vector2(33 + 20 * 32, 212 + 24 * 32));
            alienPositions.add(new Vector2(33 + 34 * 32, 212 + 24 * 32));
        }

        else if (level == 4) {
            alienPositions.add(new Vector2(33 + 25 * 32, 212 + 17 * 32));
            alienPositions.add(new Vector2(33 + 29 * 32, 212 + 17 * 32));
            alienPositions.add(new Vector2(33 + 32 * 32, 212 + 19 * 32));
            alienPositions.add(new Vector2(33 + 22 * 32, 212 + 19 * 32));
            alienPositions.add(new Vector2(33 + 20 * 32, 212 + 21 * 32));
            alienPositions.add(new Vector2(33 + 34 * 32, 212 + 21 * 32));
            alienPositions.add(new Vector2(33 + 20 * 32, 212 + 24 * 32));
            alienPositions.add(new Vector2(33 + 34 * 32, 212 + 24 * 32));
        }

        else if (level == 5) {
            alienPositions.add(new Vector2(33 + 5 * 32, 212 + 17 * 32));
            alienPositions.add(new Vector2(33 + 9 * 32, 212 + 17 * 32));
            alienPositions.add(new Vector2(33 + 12 * 32, 212 + 19 * 32));
            alienPositions.add(new Vector2(33 + 2 * 32, 212 + 19 * 32));
            alienPositions.add(new Vector2(33 + 10 * 32, 212 + 11 * 32));
            alienPositions.add(new Vector2(33 + 4 * 32, 212 + 11 * 32));
            alienPositions.add(new Vector2(33 + 10 * 32, 212 + 14 * 32));
            alienPositions.add(new Vector2(33 + 4 * 32, 212 + 14 * 32));
        }

        else if (level == 6) {
            alienPositions.add(new Vector2(33 + 25 * 32, 212 + 17 * 32));
            alienPositions.add(new Vector2(33 + 29 * 32, 212 + 17 * 32));
            alienPositions.add(new Vector2(33 + 32 * 32, 212 + 19 * 32));
            alienPositions.add(new Vector2(33 + 22 * 32, 212 + 19 * 32));
            alienPositions.add(new Vector2(33 + 20 * 32, 212 + 21 * 32));
            alienPositions.add(new Vector2(33 + 34 * 32, 212 + 21 * 32));
            alienPositions.add(new Vector2(33 + 20 * 32, 212 + 24 * 32));
            alienPositions.add(new Vector2(33 + 34 * 32, 212 + 24 * 32));
        }
    }

    public float getSpawnRate() {
        return this.spawnRate;
    }

    public void setHealth(int health){
        this.currentHealth = health;
    }

    public ArrayList<Vector2> getAlienPositions() {
        return alienPositions;
    }
    //Assessment 4 - Fortress's weapon
    public int getDamage() { return this.damage; }
    public float getAttackRange() {return this.attackRange; }

    public void setAlienPositions(ArrayList<Vector2>alienPositions){
        this.alienPositions = alienPositions;
    }
}