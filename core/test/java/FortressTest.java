import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.sprites.Fortress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

//66% line coverage

public class FortressTest {

    Texture textureMock = mock(Texture.class);

    //Instance of Fortress to ensure the constructor works as intended with level 1
    Fortress testFortressLevel1 = new Fortress(new Vector2(100, 100), 100, 100, textureMock,
            100, 5.0f, 1, 0, 0f);

    //testing to ensure the constructor worked through testing of getters
    @Test
    public void constructorShouldGiveCorrectSpawnLevel1() {
        assertEquals(5.0f, testFortressLevel1.getSpawnRate());
    }

    //Instance of Fortress to ensure the constructor works as intended with level 2
    Fortress testFortressLevel2 = new Fortress(new Vector2(100, 100), 100, 100, textureMock,
            100, 10.0f, 2, 0, 0f);

    //Fortress testFortressLevel2
    @Test
    public void constructorShouldGiveCorrectSpawnLevel2() {
        assertEquals(10.0f, testFortressLevel2.getSpawnRate());
    }

    //Instance of Fortress to ensure the constructor works as intended with level 3
    Fortress testFortressLevel3 = new Fortress(new Vector2(100, 100), 100, 100, textureMock,
            100, 15.0f, 3, 0, 0f);

    //Fortress testFortressLevel3
    @Test
    public void constructorShouldGiveCorrectSpawnLevel3() {
        assertEquals(15.0f, testFortressLevel3.getSpawnRate());
    }

    //Instance of Fortress to ensure the constructor works as intended with level 4
    Fortress testFortressLevel4 = new Fortress(new Vector2(100, 100), 100, 100, textureMock,
            100, 20.0f, 4, 0, 0f);

    //Fortress testFortressLevel3
    @Test
    public void constructorShouldGiveCorrectSpawnLevel4() {
        assertEquals(20.0f, testFortressLevel4.getSpawnRate());
    }

    //Instance of Fortress to ensure the constructor works as intended with level 5
    Fortress testFortressLevel5 = new Fortress(new Vector2(100, 100), 100, 100, textureMock,
            100, 25.0f, 5, 0, 0f);

    //Fortress testFortressLevel3
    @Test
    public void constructorShouldGiveCorrectSpawnLevel5() {
        assertEquals(25.0f, testFortressLevel5.getSpawnRate());
    }

    //Instance of Fortress to ensure the constructor works as intended with level 6
    Fortress testFortressLevel6 = new Fortress(new Vector2(100, 100), 100, 100, textureMock,
            100, 30.0f, 6, 0, 0f);

    //Fortress testFortressLevel3
    @Test
    public void constructorShouldGiveCorrectSpawnLevel6() {
        assertEquals(30.0f, testFortressLevel6.getSpawnRate());
    }

    //ASSESSMENT 4 - Dalai Java
    @Test
    public void correctAlienPositionsShouldBeAddedForGivenLevel() {
        //Instance of Fortress to ensure the constructor works as intended
        Fortress f = new Fortress(new Vector2(100, 100), 100, 100, textureMock,
                100, 5.0f, 3, 0, 0f);
        //Need to change this into parameterized tests
        Assertions.assertTrue(f.getAlienPositions().contains(new Vector2(833, 756)));
        Assertions.assertTrue(f.getAlienPositions().contains(new Vector2(961, 756)));
    }

}
