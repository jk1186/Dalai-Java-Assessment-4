package com.mygdx.game.minigame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Class used to store all MiniGameUnits in a more convenient way. Iterable as to allow it to loop through all MiniGameUnits
 * @author lnt20
 *
 */
public class MiniGameUnitManager implements Iterable<MiniGameUnit>{

	private Fireman fireman;
	private Boss boss;
	private List<Enemy> enemies;
	
	public MiniGameUnitManager() {
		fireman = new Fireman();
        boss = new Boss(new Vector2(400,211), new Texture("green.jpg"), 20, 7.5f);
		enemies = new ArrayList<Enemy>();
		addEnemy(new Vector2(800,211));
	}

	/**
	 * Method adds an enemy into the game at positon of vector spawnPos
	 * @param spawnPos
	 */
	public void addEnemy(Vector2 spawnPos) {
		enemies.add(new Enemy(spawnPos,20,20, new Texture("black.jpg"),10,5f));
	}

	/**
	 * Method runs updateAll method of each MiniGameUnit stored.
	 * @param deltaTime amount of time passed since last function call
	 */
	public void updateAll(float deltaTime) {
		if (fireman != null) {
			fireman.updatePos(deltaTime);
		}
		if (boss != null) {
			boss.updatePos(deltaTime);
		}
		for (Enemy enemy: enemies) {
			enemy.updatePos(deltaTime);
		}
		
	}
	
	/**
	 * Method removes Fireman object from manager
	 */
	public void removeFireman() {
		fireman = null;
	}
	
	/**
	 * Method removes Boss object from manager
	 */
	public void removeBoss() {
		boss = null;
	}
	
	/**
	 * Method removes enemy object at index specified from manager
	 * @param index index of enemy to remove
	 */
	public void removeEnemy(int index) {
		enemies.remove(index);
	}
	
	/**
	 * Allows class to be iterated through to provide a convenient way to loop through all MiniGameUnits on screen 
	 */
	public Iterator<MiniGameUnit> iterator() {
		ArrayList<MiniGameUnit> output = new ArrayList<MiniGameUnit>();
		output.add(fireman);
		output.add(boss);
		output.addAll(enemies);
		return output.iterator();
	}
	
	/**
	 * Getter for Boss object
	 * @return Boss object stored in manager
	 */
	public Boss getBoss() {
		return boss;
	}
	
	/**
	 * Getter for list of enemies
	 * @return ArrayList of enemies stored in manager
	 */
	public List<Enemy> getEnemies() {
		return enemies;
	}
	
	/**
	 * Getter for Fireman object
	 * @return Fireman object stored in manager
	 */
	public Fireman getFireman() {
		return fireman;
	}

	/**
	 * Method disposes all objects from the screen
	 */
	public void dispose() {
		fireman.dispose();
		boss.dispose();
		for (Enemy e: enemies) {
			e.dispose();
		}
	}
}
