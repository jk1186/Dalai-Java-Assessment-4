import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.sprites.Entity;
import com.mygdx.game.sprites.Firetruck;
import com.mygdx.game.sprites.Unit;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Graphics.class, Input.class, Timer.class})
public class testFireTruck {

    Texture textureMock = mock(Texture.class);

    //Instance of entity class to test methods on
    Entity testEntity = new Entity(new Vector2(200, 200), 100, 100, textureMock);

    //Instance of the FireTruck class to test on
    Firetruck testFireTruck = new Firetruck(new Vector2(100, 100 ), 101, 102, textureMock,
            103, 10, null, 10, 12, 13, true);

    //ASSESSMENT 4
    @Before
    public void setup() {
        Gdx.graphics = PowerMockito.mock(Graphics.class);
        Gdx.input = PowerMockito.mock(Input.class);
        PowerMockito.mockStatic(Timer.class);
        lenient().when(Gdx.graphics.getDeltaTime()).thenReturn(1f);
        //Need to work out why on earth this works, I just copied + pasted
        Application application = PowerMockito.mock(Application.class);
        Mockito.when(application.getType()).thenReturn(Application.ApplicationType.Desktop);
        Gdx.app = application;
        PowerMockito.when(Timer.post(any(Timer.Task.class))).then(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((Timer.Task) invocation.getArgument(0)).run();
                return null;
            }
        });
    }

    //Testing basic constructor functionality with getters
    @Test
    public void constructorShouldSetCorrectParametersToVariables() {
        assertEquals(13, testFireTruck.getMaxWater());
        assertEquals(13, testFireTruck.getCurrentWater());
        assertTrue(testFireTruck.isSelected());
    }

    //Testing that updateCurrentWater works with standard input
    @Test
    public void updateCurrentWaterShouldChangeCurrentWaterStandard() {
        testFireTruck.updateCurrentWater(10);
        assertEquals(3, testFireTruck.getCurrentWater());
    }

    //Testing that update water works with boundary value to go to 0
    @Test
    public void updateCurrentWaterShouldAllowForWaterToBeZero() {
        testFireTruck.updateCurrentWater(13);
        assertEquals(0, testFireTruck.getCurrentWater());
    }

    //Testing that update water if in the negative water level sets it to 0
    @Test
    public void updateCurrentWaterShouldSetNegativeWaterLevelsToZero() {
        testFireTruck.updateCurrentWater(14);
        assertEquals(0, testFireTruck.getCurrentWater());
    }

    //ASSESSMENT 4 - Dalai Java
    /*@Test
    public void truckShouldMoveWhenCommandedTest() {
        testFireTruck.move(2);
        assertEquals(testFireTruck.getPosition().x, 110);
    }

    //ASSESSMENT 4 - Dalai Java
    @Test
    public void movingEquallyInAllDirectionsShouldResultInOriginalPositionTest() {
        testFireTruck.move(1);
        testFireTruck.move(2);
        testFireTruck.move(3);
        testFireTruck.move(4);
        assertEquals(testFireTruck.getPosition(), new Vector2(100,100));
    }*/

    //ASSESSMENT 4 - Dalai Java
    @Test
    public void speedPowerUpShouldDoubleSpeed() {
        testFireTruck.powerUp("Speed");
        assertEquals(testFireTruck.getSpeed(),20);
    }

    //ASSESSMENT 4 - Dalai Java
    @Test
    public void damagePowerUpShouldDoubleDamage() {
        testFireTruck.powerUp("Damage");
        assertEquals(testFireTruck.getDamage(),24);
    }

    //ASSESSMENT 4 - Dalai Java
    @Test
    public void rangePowerUpShouldDoubleRange() {
        testFireTruck.powerUp("Range");
        assertEquals(testFireTruck.getRange(),20);
    }

    //ASSESSMENT 4 - Dalai Java
    @Test
    public void maxHealthPowerUpShouldIncreaseMaxHealth() {
        testFireTruck.powerUp("Max Health");
        assertEquals(testFireTruck.getCurrentHealth(),113);
        assertEquals(testFireTruck.getMaxHealth(),113);
    }

    //ASSESSMENT 4 - Dalai Java
    @Test
    public void infiniteHealthShouldMakeTruckInvincible() {
        testFireTruck.powerUp("Infinite Health");
        testFireTruck.takeDamage(50);
        assertEquals(testFireTruck.getCurrentHealth(),103);
    }

}
