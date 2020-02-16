/**
 * 
 */
package com.mygdx.game.map;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;

/**
 * Used to control minigame tile types
 * @author Luke Taylor
 */
public enum MiniGameTileType {
	PLATFORM(1, true, false), SPIKE(2, false, true), OTHER(11, false, false);
	
	private int id;
	private boolean collidable, damaging;
	private static HashMap<Integer, MiniGameTileType> tileTypeHashMap = new HashMap<Integer, MiniGameTileType>();
	
	static {// Creates the HashMap needed to store all Tiles to their respective id's
		tileTypeHashMap.put(MiniGameTileType.PLATFORM.getId(), MiniGameTileType.PLATFORM);
		tileTypeHashMap.put(MiniGameTileType.OTHER.getId(), MiniGameTileType.OTHER);
		tileTypeHashMap.put(MiniGameTileType.SPIKE.getId(), MiniGameTileType.SPIKE);
	}
	
	MiniGameTileType(int id, boolean collidable, boolean damaging){
		this.id = id;
		this.collidable = collidable;
		this.damaging = damaging;
	}
	
	/**
	 * Getter for id
	 * @return int id of tile
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Returns MiniGameTileType of whatever type is linked to the id passed
	 * @param id of tile wanted
	 * @return MiniGameTileType type of tile the id correlates to
	 */
	public static MiniGameTileType getTileTypeById(int id) {
		return tileTypeHashMap.get(id);
	}
	
	/**
	 * Getter for collidable
	 * @return boolean is tiled collidable
	 */
	public boolean getCollidable() {
		return collidable;
	}
	
	
	/**
	 * Getter for damaging
	 * @return boolean is tile damaging
	 */
	public boolean getDamaging() {
		return damaging;
	}
	
}
