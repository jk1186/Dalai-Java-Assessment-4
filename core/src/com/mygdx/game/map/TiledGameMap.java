package com.mygdx.game.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;

public class TiledGameMap {
	TiledMap tiledMap;
	OrthogonalTiledMapRenderer tiledMapRenderer;
	
	public TiledGameMap (){
		tiledMap = new TmxMapLoader().load("museumlevel.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
	}
	
	public void render (){ //Uses projection matrices to get the map to render in the right place
		float l = -33;
		float r = 1887;
		float t = -211;
		float b = 868;
		
		tiledMapRenderer.setView(new Matrix4(new float[]
				{2/(r-l),0,0,0,
				0,-2/(t-b),0,0,
				0,0,1,0,
				-(r+l)/(r-l),(t+b)/(t-b),0,1}), 0, 0, 1856, 832);
		tiledMapRenderer.render();
		
	}
	
	public void update (float delta) {
		// TODO Auto-generated method stub
	}
	
	public void dispose() {
		tiledMap.dispose();
	}

}
