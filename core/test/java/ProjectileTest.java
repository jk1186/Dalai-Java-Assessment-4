import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.sprites.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

//62% line coverage

//Class to test the Projectiles Class
public class ProjectileTest {

    Texture textureMock = mock(Texture.class);

    //Ensure default constructor works as intended
    Projectile testProjectile = new Projectile(new Vector2(100, 100), 10, 10, textureMock,
            new Vector2(200, 200), 10.0f, 10, 1);

    //Using the other constructor to ensure it complies correctly
    Projectile otherProjectile =  new Projectile(new Vector2(100, 100), 10, 10, textureMock,
            new Vector2(200, 200), 10.0f, 10);


    //ASSESSMENT 4 - Dalai Java
    @Test
    public void testEightParameterConstructorWorksAsExpected() {
        assertEquals(testProjectile.getMaxLength(), 1);
        assertEquals(testProjectile.getDamage(), 10);
    }

    //ASSESSMENT 4 - Dalai Java
    @Test
    public void testSevenParameterConstructorWorksAsExpected() {
        assertEquals(otherProjectile.getDamage(), 10);
    }

    //Test if setLength() correctly sets length with standard values
    @Test
    public void setLengthShouldReturnDistanceBetweenVectors() {
        testProjectile.update();
        testProjectile.setLength();
        assertEquals(10.0, testProjectile.getLength());
    }

    //ASSESSMENT 4 - Dalai Java
    @Test
    public void hitUnitShouldReturnTrueIfHitUnit() {
        Fortress u = new Fortress(new Vector2(100,100), 5,5,textureMock,1, 1f,1);
        Assertions.assertTrue(testProjectile.hitUnit((Unit) u));
    }

    //ASSESSMENT 4 - Dalai Java
    @Test
    public void hitUnitShouldReturnFalseIfNotHitUnit() {
        Fortress u = new Fortress(new Vector2(0,0), 5,5,textureMock,1, 1f,1);
        Assertions.assertFalse(testProjectile.hitUnit((Unit) u));
    }

}


