package com.mygdx.game.map;

import java.util.HashMap;

public enum TileType {
	COLLIDABLE("collidable", true, 219),
	NONCOLLIDABLE("non collidable", false, 249);
	
	private final String name;
	private final Boolean collidable;
	private final int id;
	
	private TileType(String name, boolean collidable, int id) {
		this.name = name;
		this.collidable = collidable;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Boolean getCollidable() {
		return collidable;
	}

	public int getId() {
		return id;
	}
	
	private static HashMap<Integer, TileType> tileTypeHashMap = new HashMap<Integer, TileType>();
	
	static {
		for (TileType x : TileType.values()) {
			tileTypeHashMap.put(x.id , x);
		}
	}
	
	public static TileType getTileTypeByID(int id) {
		return tileTypeHashMap.get(id);
	}



}
