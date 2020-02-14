package com.mygdx.game.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;

/**
 * @author Martha Cartwright
 * @author Luke Taylor
 *
 */
public class TiledGameMap {
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer tiledMapRenderer;
	private final float L = -33, R = 1887, T = -212, B = 868; 
	private final Matrix4 PROJECTMATRIX = new Matrix4(new float[]
			{2/(R-L),0,0,0,
			0,-2/(T-B),0,0,
			0,0,1,0,
			-(R+L)/(R-L),(T+B)/(T-B),0,1});
	
	public TiledGameMap (String map){
		tiledMap = new TmxMapLoader().load(map);
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
	}
	
	public void render (){ //Uses projection matrices to get the map to render in the right place
		tiledMapRenderer.setView(PROJECTMATRIX, 0, 0, 1856, 832);
		tiledMapRenderer.render();
		
	}
	
	public void update (float delta) {}
	
	public void dispose() {
		tiledMap.dispose();
	}
	
	public TileType getTileTypeByScreenCoordinate(float posX, float posY) {;
		return TileType.getTileTypeByID(((TiledMapTileLayer)tiledMap.getLayers().get(0)).getCell((int) (posX-33)/32,(int) (posY-212)/32).getTile().getId());
		
	}
	
	public MiniGameTileType getMiniGameTileTypeByScreenCoordinate(float posX, float posY) {
//		System.out.println("==========================");
//		int x = ((TiledMapTileLayer)tiledMap.getLayers().get("Collisions")).getCell((int) (posX-33)/64,(int) (posY-212)/64).getTile().getId();
//		System.out.println(x);
//		System.out.println("==========================");
//		System.out.println(MiniGameTileType.getTileTypeById(x));
//		System.out.println("==========================");
		return MiniGameTileType.getTileTypeById(((TiledMapTileLayer)tiledMap.getLayers().get("Collisions")).getCell((int) (posX-33)/64,(int) (posY-212)/64).getTile().getId());
	}
	
}
