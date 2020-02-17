package com.mygdx.game.minigame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.map.MiniGameTileType;
import com.mygdx.game.map.TiledGameMap;
import com.mygdx.game.sprites.Entity;

/**
 * Class used to store all MiniGameUnits in a more convenient way. Iterable as to allow it to loop through all MiniGameUnits
 * @author Luke Taylor
 * @author Dicycat
 *
 */
public class MiniGameUnitManager implements Iterable<Entity>{

	
	private final Vector2[][] ENEMY_SPAWN_POSITIONS = {
			{new Vector2(800,276)},
			{new Vector2(920, 404)},
			{new Vector2(800, 276), new Vector2(200, 532)},
			{new Vector2(940, 404), new Vector2(1340, 853)},
			{new Vector2(800, 276), new Vector2(200, 532)},
			{new Vector2(940, 404), new Vector2(1340, 853)}
			};
	
	
	private Firefighter fireman;
	private static TiledGameMap gameMap;
	private Boss boss;
	private List<Enemy> enemies;
	private TextureManager textureManager = new TextureManager();
	private Bomb bomb;
	private boolean levelWon = false, levelLost = false;
	private HealthBar healthBar;
	private Vector2 spawnPoint = new Vector2(1600,276), bossSpawn = new Vector2(400,276);
	private Vector2[] enemySpawns;
	
	public MiniGameUnitManager(TiledGameMap map, int level) {
		
		enemySpawns = ENEMY_SPAWN_POSITIONS[level];
		textureManager = new TextureManager();
		
		gameMap = map;
		fireman = new Firefighter(spawnPoint);
		enemies = new ArrayList<Enemy>();
		healthBar = new HealthBar(new Vector2(829,100));
		for (Vector2 pos: enemySpawns) {
			addEnemy(pos);
		}
		
	}

	/**
	 * Method adds an enemy into the game at position of vector spawnPos
	 * @param spawnPos
	 */
	public void addEnemy(Vector2 spawnPos) {
		enemies.add(new Enemy(spawnPos,35,35,10,300f));
	}

	/**
	 * Method runs update methods of each Entities stored. Also checks for death of Entities and controls spawning of other Entities
	 * @param deltaTime amount of time passed since last function call
	 */
	public void updateAll(float deltaTime) {			
		// Fireman updates
		if (fireman != null) {
			fireman.updatePos(deltaTime);
			if (fireman.onDamagingTile()) {// Should fireman take environmental damage
				firefighterDeath();
			}
			if (bomb == null && Gdx.input.isKeyPressed(Keys.SPACE)) { // Bomb spawning
				spawnBomb(fireman.getPosition());
			}
			if (fireman.isDead()) { // Fireman death
				fireman = null;
				levelLost = true;
			}
		}
		
		// Bomb updates
		if (bomb != null) {
			bomb.updateTimer(deltaTime);
			if (bomb.isExploding()) { // Bomb Exploding
				bomb.explode(getEnemiesInRange(bomb.getPosition(), bomb.getRange()));
				bomb = null;
			}
		}

		// Boss updates
		if (boss != null) {
			boss.updatePos(deltaTime);
			if (boss.collisionWithEntity(fireman)) { // Player collisions with boss
				firefighterDeath();
			}
			if (boss.isDead()) { // Boss death
				boss = null;
				levelWon = true;
			}
		}
		
		// Enemy updates
		ArrayList<Enemy> deadEnemies = new ArrayList<Enemy>();
		
		for (Enemy enemy: enemies) {
			enemy.updatePos(deltaTime);
			
			if (enemy.collisionWithEntity(fireman)) { // Player Collisions
				firefighterDeath();
			}
			
			if (enemy.isDead()) { // Marking any enemy which dies
				deadEnemies.add(enemy);
			}
		}

		for (Enemy enemy: deadEnemies) {// Removing enemies from the code
			enemies.remove(enemy);
		}
		if (enemies.isEmpty() && boss == null) { // Boss spawning once all enemies dead
			spawnBoss();
		}
	}
	
	/**
	 * Allows class to be iterated through to provide a convenient way to loop through all MiniGameUnits on screen 
	 */
	public Iterator<Entity> iterator() {
		ArrayList<Entity> output = new ArrayList<Entity>();
		if (fireman != null) {// Checks fireman exists
			output.add(fireman);
		}
		if (boss != null) {// Checks boss exists
			output.add(boss);
		}
		if (bomb != null) {// Checks bomb exists
			output.add(bomb);
		}
		if (enemies.isEmpty() == false) {// Checks enemies exist
			output.addAll(enemies);
		}
		output.addAll(healthBar.getHearts());
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
	 * Getter for levelWon
	 * @return boolean true if level has been won
	 */
	public boolean isLevelWon() {
		return levelWon;
	}
	
	/**
	 * Summons and instantiates boss object
	 */
	public void spawnBoss() {
        boss = new Boss(bossSpawn,20, 450f);
	}
	
	/**
	 * Spawns bomb at position of fireman
	 * @param pos of place to spawn bomb
	 */
	public void spawnBomb(Vector2 pos) {
		bomb = new Bomb(pos, TextureManager.getFirstBlueBomb());
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
		if (boss != null) {
			if (boss.getPosition().dst(point) <= range) {
				output.add(boss);
				return output;
			}
		}
		
		for (Enemy enemy: enemies) {
			if (enemy.getPosition().dst(point) <= range) {
				output.add(enemy);
			}
		}
		return output;		
	}
	
	/**
	 * Getter for Fireman object
	 * @return Fireman object stored in manager
	 */
	public Firefighter getFireman() {
		return fireman;
	}

	/**
	 * Method disposes all objects from the screen
	 */
	public void dispose() {
		textureManager.dispose();
	}
	
	/**
	 * Methods removes life from firefighter and updates graphics
	 */
	public void firefighterDeath() {
		fireman.setPosition(spawnPoint.x, spawnPoint.y); // Reset fireman spawn point
		if(!(healthBar.isDead())) { // Makes sure lives are left
			healthBar.looseHealth();
			fireman.takeDamage(1);
		}else {
			levelLost = true;
		}
	}

	/**
	 * Getter for levelLost
	 * @return boolean returns true if level is lost
	 */
	public boolean isLevelLost() {
		return levelLost ;
	}

	public static TiledGameMap getGameMap() {
		return gameMap;
	}
}
