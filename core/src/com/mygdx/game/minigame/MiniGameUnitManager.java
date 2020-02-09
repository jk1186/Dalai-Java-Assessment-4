package com.mygdx.game.minigame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.sprites.Entity;

/**
 * Class used to store all MiniGameUnits in a more convenient way. Iterable as to allow it to loop through all MiniGameUnits
 * @author lnt20
 *
 */
public class MiniGameUnitManager implements Iterable<Entity>{

	private Fireman fireman;
	private Boss boss;
	private List<Enemy> enemies;
	private TextureManager textureManager = new TextureManager();
	private Bomb bomb;
	
	public MiniGameUnitManager() {
		fireman = new Fireman(new Vector2(800,211), TextureManager.getFireman());
        boss = new Boss(new Vector2(400,211), TextureManager.getBoss(), 20, 7.5f);
		enemies = new ArrayList<Enemy>();
		addEnemy(new Vector2(800,211));
	}

	/**
	 * Method adds an enemy into the game at positon of vector spawnPos
	 * @param spawnPos
	 */
	public void addEnemy(Vector2 spawnPos) {
		enemies.add(new Enemy(spawnPos,20,20, TextureManager.getEnemy(),10,5f));
	}

	/**
	 * Method runs updateAll method of each MiniGameUnit stored.
	 * @param deltaTime amount of time passed since last function call
	 */
	public void updateAll(float deltaTime) {
		if (fireman != null) {
			fireman.updatePos(deltaTime);
			if (bomb == null && Gdx.input.isKeyPressed(Keys.SPACE)) {
				spawnBomb(fireman.getCentre());
			}
		}
		if (bomb != null) {
			bomb.updateTimer(deltaTime);
			if (bomb.isExploding()) {
				bomb.explode(getEnemiesInRange(bomb.getPosition(), bomb.getRange()));
				bomb = null;
			}
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
	public Iterator<Entity> iterator() {
		ArrayList<Entity> output = new ArrayList<Entity>();
		if (fireman != null) {
			output.add(fireman);
		}
		if (boss != null) {
			output.add(boss);
		}
		if (bomb != null) {
			output.add(bomb);
		}
		if (enemies.isEmpty() == false) {
			output.addAll(enemies);
		}
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
	 * Spawns bomb at position of fireman
	 * @param pos of place to spawn bomb
	 */
	public void spawnBomb(Vector2 pos) {
		bomb = new Bomb(pos, TextureManager.getFirstBomb());
	}
	
	/**
	 * Getter for list of enemies
	 * @return ArrayList of enemies stored in manager
	 */
	public List<Enemy> getEnemies() {
		return enemies;
	}
	
	/**
	 * Method used to calculate all enemies in range of a certain point
	 * @param point Centre point to calculate distance from
	 * @param range Distance between centre point and enemy for which method calculates
	 * @return ArrayList<Enemy> containing all enemies which are within range specified of point specified
	 */
	public List<Enemy> getEnemiesInRange(Vector2 point, float range){
		ArrayList<Enemy> output = new ArrayList<Enemy>();
		
		if (boss == null) {
			for (Enemy enemy: enemies) {
				if (enemy.getPosition().dst(point) > range) {
					output.add(enemy);
				}
			}
		}else {
			if (boss.getPosition().dst(point) > range) {
				output.add(boss);
			}
		}
		
		
		return output;		
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
		bomb.dispose();
	}
}
