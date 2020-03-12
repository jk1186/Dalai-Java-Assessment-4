import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.sprites.Alien;
import com.mygdx.game.sprites.Firetruck;
import com.mygdx.game.sprites.Unit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AlienTest {
    Texture textureMock = mock(Texture.class);

    //Default constructor to ensure it works as intended
    Alien testAlien = new Alien(new Vector2(100, 100), 100, 100, textureMock, 100,
            10, null, 0, 5, null, 10.0f);

    //Test Alien with wayPoints
    Alien wayPointAlien = new Alien(new Vector2(200, 200), 100, 100, textureMock, 100,
            10, null, 1, 5, new Vector2[]{new Vector2(200, 200), new Vector2(201, 200)}, 10.0f);

    //ASSESSMENT 4 - Test basic constructor functionality
    @Test
    public void constructorsShouldSetCorrectParametersToValues() {
        Assertions.assertEquals(testAlien.getAttackCooldown(),10.0f);
    }


    //Test if truckInRange will set a new target with an in range mocked truck
    @Test
    public void truckInRangeShouldChangeTargetForInRangeTruck() {
        Firetruck truckMock = mock(Firetruck.class);
        when(truckMock.getCurrentHealth()).thenReturn(100);
        when(truckMock.getPosition()).thenReturn(new Vector2(100, 100));
        when(truckMock.getTopRight()).thenReturn(new Vector2(150, 150));

        ArrayList<Firetruck> firetrucks = new ArrayList<>();
        firetrucks.add(truckMock);

        testAlien.truckInRange(firetrucks);
        assertEquals(truckMock, testAlien.getTarget());
    }

    //Test if truckInRange will not change the target for a mocked truck not in range
    @Test
    public void truckInRangeShouldNotChangeTargetForOutOfRangeTruck() {
        Firetruck truckMock = mock(Firetruck.class);
        when(truckMock.getPosition()).thenReturn(new Vector2(500, 500));
        when(truckMock.getTopRight()).thenReturn(new Vector2(600, 600));

        ArrayList<Firetruck> firetrucks = new ArrayList<>();
        firetrucks.add(truckMock);

        testAlien.truckInRange(firetrucks);
        assertEquals(null, testAlien.getTarget());
    }

    //Test if truckInRange will set target to null if current target has no health
    @Test
    public void truckInRangeShouldSetTargetToNullWhenTargetHasNoHealth() {
        Unit unitMock = mock(Unit.class);
        when(unitMock.getCurrentHealth()).thenReturn(0);

        ArrayList<Firetruck> firetrucks = new ArrayList<>();

        testAlien.setTarget(unitMock);

        testAlien.truckInRange(firetrucks);
        assertEquals(null, testAlien.getTarget());
    }


    //Test to see if waypoints loop once the end of the route is reached
    @Test
    public void loopPatrolWhenEndIsReached() {
        wayPointAlien.update();
        wayPointAlien.update();
        assertEquals(new Vector2(200, 200), wayPointAlien.getNextWaypoint());
    }

    //ASSESSMENT 4 - Dalai Java
    @Test
    public void updateTimeSinceAttackShouldIncreaseTimeTest() {
        testAlien.updateTimeSinceAttack(5);
        assertEquals(testAlien.getTimeSinceAttack(),5,0);
    }

    //ASSESSMENT 4 - Dalai Java
    @Test
    public void resetTimeSinceAttackShouldResetTimeToZeroTest() {
        testAlien.updateTimeSinceAttack(5);
        testAlien.resetTimeSinceAttack();
        assertEquals(testAlien.getTimeSinceAttack(),0,0);
    }

    @Test
    public void alienMovesTowardsWaypointTest() {
        wayPointAlien.setPosition(0,100);
        wayPointAlien.update();
        assertEquals(wayPointAlien.getPosition(), new Vector2(1,100));
    }

    @Test
    public void alienShouldMoveToNewWaypointWhenReachedOldOneTest() {
        wayPointAlien.setPosition(200,200);
        wayPointAlien.update();
        assertEquals(wayPointAlien.getCurrentIndex(), 1);
    }

    @Test
    public void alienShouldGoBackToInitialWaypointWhenEndReachedTest() {
        wayPointAlien.setPosition(200,200);
        wayPointAlien.update();
        wayPointAlien.setPosition(201,200);
        wayPointAlien.update();
        assertEquals(wayPointAlien.getCurrentIndex(), 0);
    }
}
