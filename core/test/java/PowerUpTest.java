import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.sprites.PowerUps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;


public class PowerUpTest {

    Texture textureMock = mock(Texture.class);

    @Test
    public void testGetterWorksAsExpected() {
        PowerUps p = new PowerUps(new Vector2(1,1), 1,1,textureMock, "Speed");
        Assertions.assertEquals(p.getType(),"Speed");
    }
}
