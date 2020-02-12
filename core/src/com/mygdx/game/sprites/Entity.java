package com.mygdx.game.sprites;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * The class containing an instance of the Sprite class allowing for relevant attributes. Also contains getters and/or setters which are inherited by
 * multiple classes and is itself implemented to create obstacle object.
 *
 * @author Bethany Gilmore
 * @author Luke Taylor
 */

public class Entity {

    private Sprite sprite;
    
    public Entity(Vector2 position, int width, int height, Texture texture) {
    	sprite = new Sprite(texture,(int) position.x,(int) position.y,width,height);
    	sprite.setPosition(position.x, position.y);
    }

    /**
     * Removes entities texture from loaded memory
     */
    public void dispose() {
    	sprite.getTexture().dispose();
    }

    /**
     * Sets the new positon of the entity
     * @param x new X position of the entity
     * @param y new Y position of the entity
     */
    public void setPosition(float x, float y) {
    	sprite.setPosition(x, y);
    }
    
    /**
     * Gets the position of the entity
     * @return Vector2 position of the entity
     */
    public Vector2 getPosition() {
        return new Vector2(sprite.getX(),sprite.getY());
    }

    /**
     * Gets width of the entity
     * @return float width of the entity
     */
    public float getWidth() {
        return sprite.getWidth();
    }

    /**
     * Gets height of the entity
     * @return float height of the entity
     */
    public float getHeight() {
        return sprite.getHeight();
    }

    /**
     * Gets texture of the entitiy
     * @return Texture graphic of the entity
     */
    public Texture getTexture() {
        return sprite.getTexture();
    }

    /**
     * Sets new texture of entity
     * @param texture new texture of the entity
     */
    public void setTexture(Texture texture) {
        sprite.setTexture(texture);
    }

    /**
     * Gets top right position of the entity
     * @return Vector2 top right of the entity
     */
    public Vector2 getTopRight() {
        return new Vector2(sprite.getX()+getWidth(),sprite.getY()+getHeight());
    }

    /**
     * Gets the X scale of the entity
     * @return float X scale of the entity (see Sprite.getScale())
     */
    public float getScaleX() {
    	return sprite.getScaleX();
    }
    /**
     * Gets the Y scale of the entity
     * @return float Y scale of the entity (see Sprite.getScale())
     */
    public float getScaleY() {
    	return sprite.getScaleY();
    }
    
    /**
     * Gets the angle of rotation of the entity
     * @return float Rotation of the entity
     */
    public float getRotation() {
    	return sprite.getRotation();
    }
    
	/**
	 * True if entity should face right (see MiniGame)
	 * @return boolean if the entity is facing right (relevent for minigame)
	 */
	public boolean isFacingRight() {
		return false;
	}

	
	/**
	 * Gets centre of the entity
	 * @return Vector2 Centre vector of the entity
	 */
	public Vector2 getCentre() {
		return new Vector2(sprite.getX()+sprite.getOriginX(), sprite.getY()+sprite.getOriginX());
	}
}
