import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.sprites.Projectile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

//62% line coverage

//Class to test the Projectiles Class
public class ProjectileTest {

    Texture textureMock = mock(Texture.class);

    //Ensure default constructor works as intended
    Projectile testProjectile = new Projectile(new Vector2(100, 100), 10, 10, textureMock,
            new Vector2(200, 200), 10.0f, 10, 1);

    //Using the other constructor to ensure it complies correctly
    Projectile otherProjectileConstructor =  new Projectile(new Vector2(100, 100), 10, 10, textureMock,
            new Vector2(200, 200), 10.0f, 10);

    //Test if setLength() correctly sets length with standard values
    @Test
    public void setLengthShouldReturnDistanceBetweenVectors() {
        testProjectile.update();
        testProjectile.setLength();
        assertEquals(10.0, testProjectile.getLength());
    }

}


