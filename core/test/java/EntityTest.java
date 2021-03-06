import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.sprites.Entity;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

//70% line coverage (not testing getter and setters)

//Class to test methods in the Entity Class.
public class EntityTest {
    Texture textureMock = mock(Texture.class);

    //Instance of entity class to test methods on
    Entity testEntity = new Entity(new Vector2(100, 100), 100, 100, textureMock);

    //Testing a standard move and checking corners
    @Test
    public void testSetPositionStandard(){
        testEntity.setPosition(100, 100);
        assertEquals(new Vector2(100, 100), testEntity.getPosition());
        assertEquals(new Vector2(200, 200), testEntity.getTopRight());

    }

    //Testing that setPosition() will allow for the boundary input of 0
    @Test
    public void testSetPositionShouldAllowForZeros() {
        testEntity.setPosition(0, 0);
        assertEquals(new Vector2(0, 0), testEntity.getPosition());
        assertEquals(new Vector2(100, 100), testEntity.getTopRight());
    }

    //ASSESSMENT 4 - Dalai Java
    @Test
    public void testTextureGetterAndSetter() {
        Texture textureMock2 = mock(Texture.class);
        testEntity.setTexture(textureMock2);
        assertEquals(testEntity.getTexture(),textureMock2);
    }

    //Testing that negative numbers throw an IllegalArgumentException
    @Test
    public void testSetPositionShouldThrowExceptionForOutOfBoundaryNegative() {
        try{
            testEntity.setPosition(-100, -100);
        } catch (IllegalArgumentException e){
            assertEquals(new Vector2(100, 100), testEntity.getPosition());
        } catch (Exception e){
            Assert.fail();
        }
    }

}





