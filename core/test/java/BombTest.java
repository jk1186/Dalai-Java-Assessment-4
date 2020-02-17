import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.minigame.Bomb;
import com.mygdx.game.minigame.Enemy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

//66% line coverage

public class BombTest {

    Texture textureMock = mock(Texture.class);

    Bomb testBomb = new Bomb(Vector2.Zero, textureMock);
    Enemy testEnemy = new Enemy(Vector2.Zero, 1, 1, 11, 1, textureMock);

    //test bomb damages enemies
    @Test
    public void bombDoesDamage() {
        ArrayList<Enemy> eList = new ArrayList<Enemy>();
        eList.add(testEnemy);
        testBomb.explode(eList);
        assertEquals(1, testEnemy.getCurrentHealth());
    }

}
