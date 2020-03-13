package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Kroy;
import com.mygdx.game.map.TiledGameMap;
import com.mygdx.game.misc.Button;
import com.mygdx.game.misc.Timer;
import com.mygdx.game.sprites.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Implementation of the abstract class State which contains the methods and attributes required to control the
 * gameplay logic for each level as well as rendering to the screen.
 *
 * @author Lucy Ivatt and Bethany Gilmore
 * @author Luke Taylor, Martha Cartwright and Riju De
 * @author Dicycat
 */

public class PlayState extends State {


    private Texture background;

    private boolean levelLost;
    private boolean levelWon;
    private Preferences saveData;

    private Button quitLevel;
    private Button quitGame;

    private int levelNumber;

    private Timer timer;
    private float alienSpawnCountdown;
    private float timeSinceAlienKilled;
    private float timeSinceLastFortressRegen;
    private float timeLimit;
    private float timeTaken;

    private Entity fireStation;
    private Fortress fortress;
    private Firetruck firetruck1;
    private Firetruck firetruck2;
    private Firetruck firetruck3;
    private Firetruck firetruck4;
    private ArrayList<Entity> obstacles = new ArrayList<Entity>();
    public ArrayList<Firetruck> firetrucks = new ArrayList<Firetruck>();
    private ArrayList<Firetruck> destroyedFiretrucks = new ArrayList<Firetruck>();
    private ArrayList<Alien> aliens = new ArrayList<Alien>();
    private ArrayList<Projectile> bullets = new ArrayList<Projectile>();
    private ArrayList<Projectile> water = new ArrayList<Projectile>();

    private BitmapFont ui;
    private BitmapFont healthBars;
    private String level;
    private int levelNum;
    public static TiledGameMap gameMap;

    private Sound waterShoot = Gdx.audio.newSound(Gdx.files.internal("honk.wav"));

    //Assesment 4
    private boolean spawn;
    private   ArrayList<Texture> powerTexture = new ArrayList<Texture>();
    Texture maxHealthIncrease = new Texture("powerMaxHealth.png");
    Texture damageIncrease = new Texture("powerDamage.png");
    Texture infiniteHealth = new Texture("powerInfiniteHealth.png");
    Texture rangeIncrease = new Texture("powerRange.png");
    Texture speedIncrease = new Texture("powerSpeed.png");

    private ArrayList<String> powerUpTypes = new ArrayList<String>();

    private ArrayList<PowerUps> powerList = new ArrayList<PowerUps>();
    private ArrayList<PowerUps> clearList = new ArrayList<PowerUps>();


    //TODO: Add way to save level state to continue later
    //TODO: Add PowerUp effects to map
    public PlayState(GameStateManager gsm, int levelNumber) {
        super(gsm);
        this.levelNumber = levelNumber;
        background = new Texture("LevelProportions.png");

        quitLevel = new Button(new Texture("PressedQuitLevel.png"),
                new Texture("NotPressedQuitLevel.png"),350 / 2, 100 / 2,
                new Vector2(30, 30), false, false);

        quitGame = new Button(new Texture("PressedQuitGame.png"),
                new Texture("NotPressedQuitGame.png"), 350 / 2, 100 / 2,
                new Vector2(1920 - 30 - 350 / 2, 30), false, false);

        levelNum = levelNumber-1;
        level = Integer.toString(levelNumber); // Used as a key when saving level progress

        levelLost = false;
        levelWon = false;

        saveData = Gdx.app.getPreferences("Kroy");

        ui = new BitmapFont(Gdx.files.internal("font.fnt"));
        ui.setColor(Color.DARK_GRAY);

        healthBars = new BitmapFont();

        alienSpawnCountdown = 0;
        timeSinceLastFortressRegen = 0;
        timeSinceAlienKilled = -1;

        // Assesment 4
        spawn = false;
        powerTexture.add(maxHealthIncrease);
        powerTexture.add(damageIncrease);
        powerTexture.add(infiniteHealth);
        powerTexture.add(rangeIncrease);
        powerTexture.add(speedIncrease);

        powerUpTypes.add("Max Health");
        powerUpTypes.add("Damage");
        powerUpTypes.add("Infinite Health");
        powerUpTypes.add("Range");
        powerUpTypes.add("Speed");



        Vector2 firetruck1pos = new Vector2(0,0);
        Vector2 firetruck2pos = null;
        Vector2 firetruck3pos = null;
        Vector2 firetruck4pos = null;

        // Assessment 4 - Added Kroy.difficultyMultiplier to fortresses.

        if (levelNumber == 1) { // Bottom left coordinate of map --> (33, 212) Each grid square = 32px

        	gameMap = new TiledGameMap("level1map.tmx");

            firetruck1pos = new Vector2(33 + 10 * 32, 212 + 6 * 32);
            firetruck2pos = new Vector2(33 + 11 * 32, 212 + 6 * 32);
            firetruck3pos = new Vector2(33 + 10 * 32, 212 + 5 * 32);
            firetruck4pos = new Vector2(33 + 11 * 32, 212 + 5 * 32);
            timeLimit = 90;

            // Level 1 Firestation
            fireStation = new Entity(new Vector2(33 + 8 * 32, 212 + 4 * 32), 128, 128,
                    new Texture("teal.jpg"));

            // Level 1 Fortress
            fortress = new Fortress(new Vector2(33 + 24 * 32, 212 + 22 * 32), 6 * 32, 4 * 32,
                    new Texture("grey.png"), (int)(Kroy.difficultyMultiplier * 10000), 1.5f, levelNumber);
        }

        else if (levelNumber == 2) {

        	gameMap = new TiledGameMap("level2map.tmx");

            firetruck1pos = new Vector2(33 + 2 * 32, 212 + 4 * 32);
            firetruck2pos = new Vector2(33 + 2 * 32, 212 + 5 * 32);
            firetruck3pos = new Vector2(33 + 2 * 32, 212 + 6 * 32);
            firetruck4pos = new Vector2(33 + 2 * 32, 212 + 7 * 32);

            timeLimit = 120;


            // Level 2 Fire Station
            fireStation = new Entity(new Vector2(33 + 1 * 32, 212 + 4 * 32), 64, 128,
                    new Texture("teal.jpg"));

            // Level 2 Fortress
            fortress = new Fortress(new Vector2(33 + 36 * 32, 212 + 19 * 32), 4 * 32, 4 * 32, new Texture("grey.png"),
                    (int)(Kroy.difficultyMultiplier * 12500), 4, levelNumber);
        }

        else if (levelNumber == 3) {

        	gameMap = new TiledGameMap("level3map.tmx");

            firetruck1pos = new Vector2(33 + 27 * 32, 212 + 3 * 32);
            firetruck2pos = new Vector2(33 + 28 * 32, 212 + 3 * 32);
            firetruck3pos = new Vector2(33 + 29 * 32, 212 + 3 * 32);
            firetruck4pos = new Vector2(33 + 27 * 32, 212 + 2 * 32);

            timeLimit = 60;

            // Level 3 Fire Station
            fireStation = new Entity(new Vector2(33 + 27*32, 212), 6 * 32, 4 * 32, new Texture("teal.jpg"));

            // Level 3 Fortress
            fortress = new Fortress(new Vector2(33 + 24*32, 212 + 32*21), 224, 96, new Texture("grey.png"),
                    (int)(Kroy.difficultyMultiplier * 15000), 2, levelNumber);
        }


        else if (levelNumber == 4) {

        	gameMap = new TiledGameMap("level4map.tmx");

            firetruck1pos = new Vector2(33 + 7 * 32, 212 + 4 * 32);
            firetruck2pos = new Vector2(33 + 7 * 32, 212 + 5 * 32);
            firetruck3pos = new Vector2(33 + 7 * 32, 212 + 6 * 32);
            firetruck4pos = new Vector2(33 + 8 * 32, 212 + 5 * 32);

            timeLimit = 90;

            // Level 4 Fire Station
            fireStation = new Entity(new Vector2(33 + 5 * 32, 212 + 4 * 32), 4 * 32, 3 * 32, new Texture("teal.jpg"));

            // Level 4 Fortress
            fortress = new Fortress(new Vector2(33 + 24*32, 212 + 32*21), 224, 96, new Texture("grey.png"),
                    (int)(Kroy.difficultyMultiplier * 15000), 2, levelNumber);
        }


        else if (levelNumber == 5) {

        	gameMap = new TiledGameMap("level5map.tmx");

            firetruck1pos = new Vector2(33 + 27 * 32, 212 + 14 * 32);
            firetruck2pos = new Vector2(33 + 26 * 32, 212 + 14 * 32);
            firetruck3pos = new Vector2(33 + 27 * 32, 212 + 13 * 32);
            firetruck4pos = new Vector2(33 + 26 * 32, 212 + 13 * 32);

            timeLimit = 90;

            // Level 5 Fire Station
            fireStation = new Entity(new Vector2(33 + 24 * 32, 212 + 12 * 32), 4 * 32, 3 * 32, new Texture("teal.jpg"));

            // Level 5 Fortress
            fortress = new Fortress(new Vector2(33 + 4 * 32, 212 + 14 * 32), 4*32, 3*32, new Texture("grey.png"),
                    (int)(Kroy.difficultyMultiplier * 15000), 2, levelNumber);
        }


        else if (levelNumber == 6) {

        	gameMap = new TiledGameMap("level6map.tmx");

            firetruck1pos = new Vector2(33 + 9 * 32, 212 + 4 * 32);
            firetruck2pos = new Vector2(33 + 8 * 32, 212 + 4 * 32);
            firetruck3pos = new Vector2(33 + 9 * 32, 212 + 5 * 32);
            firetruck4pos = new Vector2(33 + 8 * 32, 212 + 5 * 32);

            timeLimit = 60;

            // Level 6 Fire Station
            fireStation = new Entity(new Vector2(33 + 6 * 32, 212 + 3 * 32), 4 * 32, 3 * 32, new Texture("teal.jpg"));

            // Level 3 Fortress
            fortress = new Fortress(new Vector2(33 + 24 * 32, 212 + 32 * 21), 224, 96, new Texture("grey.png"),
                    (int)(Kroy.difficultyMultiplier * 15000), 2, levelNumber);
        }



        //Firetrucks created here | Health, range, speed, dps, capacity
        firetruck1 = new Firetruck(firetruck1pos, 25,25,
                new Texture("truck.png"), 200, 100,
                null, 100, 2,  175, true);
        firetruck2 = new Firetruck(firetruck2pos, 25,25,
                new Texture("truck.png"), 100, 200,
                null, 100,  2,  175, false);
        firetruck3 = new Firetruck(firetruck3pos, 25,25,
                new Texture("truck.png"), 100, 100,
                null, 200, 2,  175, false);
        firetruck4 = new Firetruck(firetruck4pos, 25,25,
                new Texture("truck.png"), 100, 100,
                null, 100,  4,  175, false);

        firetrucks.add(firetruck1);
        firetrucks.add(firetruck2);
        firetrucks.add(firetruck3);
        firetrucks.add(firetruck4);
        timer = new Timer(timeLimit);


    }

    /**
     * The game logic which is executed due to specific user inputs. Is called in the update method.
     */
    public void handleInput() {
        // Checks for hover and clicks on the 2 buttons located on the play screen.
        if (quitGame.mouseInRegion()){
            quitGame.setActive(true);
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
                System.exit(0);
                }
            }

        else {
            quitGame.setActive(false);
        }

        if (quitLevel.mouseInRegion()){
            quitLevel.setActive(true);
            if (Gdx.input.isTouched()) {
                gameStateManager.pop();
            }
        }

        else {
            quitLevel.setActive(false);
        }

        // If the user presses the space bar, creates Projectile instance if the selected firetruck has water remaining.
        // Then adds this to the water ArrayList and removes 1 water from the firetrucks tank.
        for (Firetruck firetruck : firetrucks) {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && firetruck.isSelected() && firetruck.getCurrentWater() > 0) {
                Projectile drop = new Projectile(new Vector2(firetruck.getPosition().x + firetruck.getWidth() / 2, firetruck.getPosition().y + firetruck.getHeight() / 2), 5, 5,
                        new Texture("lightblue.jpg"), (new Vector2(Gdx.input.getX(), Kroy.HEIGHT - Gdx.input.getY())), 5, firetruck.getDamage(), firetruck.getRange());
                water.add(drop);
                if (saveData.getBoolean("effects")) {
                    waterShoot.play();
                }

                firetruck.updateCurrentWater(1);
            }
        }

        // Opens pause menu if user hits escape
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            gameStateManager.push(new OptionState(gameStateManager));

        }

        // Switches active firetruck if the mouse clicks within another selectable firetruck.
        Vector2 mousePos = new Vector2(Gdx.input.getX(), Kroy.HEIGHT - Gdx.input.getY());
        if (Gdx.input.isTouched()) {
            for (Firetruck truck : firetrucks) {
                if (mousePos.x >= (truck.getPosition().x) && mousePos.x <= (truck.getPosition().x + truck.getWidth())
                        && mousePos.y >= (truck.getPosition().y) && mousePos.y <= (truck.getPosition().y
                        + truck.getHeight())) {
                    for (Firetruck clearTruck : firetrucks) {
                        clearTruck.setSelected(false);
                    }
                    truck.setSelected(true);
                }
            }
        }

        // Changes which truck is moving and calls the truckMovement() method with the selected truck as input.


        for(Firetruck firetruck: firetrucks){
            if(firetruck.isSelected()) {
                firetruck.truckMovement();
            }
        }
    }

    /**
     * Updates the game logic before the next render() is called
     * @param deltaTime the amount of time which has passed since the last render() call
     */
    @Override
    public void update(float deltaTime) {
        // Calls input handler and updates timer each tick of the game.
        handleInput();
        timer.update();

        // Updates aliens and attacks firetruck if there is a firetruck in range and the Aliens attack cooldown is over.
        // Adds the created bullet projectile to the ArrayList bullets
        for (Alien alien : aliens) {
            alien.update();
            alien.truckInRange(firetrucks);
            if (alien.getTimeSinceAttack() >= alien.getAttackCooldown()) {
                if (alien.hasTarget()) {
                    Projectile bullet = new Projectile(new Vector2(alien.getPosition().x + alien.getWidth() / 2, alien.getPosition().y + alien.getHeight() / 2), 5, 5,
                            new Texture("red.png"), (new Vector2(alien.getTarget().getPosition().x, alien.getTarget().getPosition().y)), 5, alien.getDamage(), (alien.getRange()+50));
                    bullets.add(bullet);
                    alien.resetTimeSinceAttack();
                }
            }
            alien.updateTimeSinceAttack(deltaTime);
        }
        alienSpawnCountdown -= deltaTime;
        timeSinceAlienKilled -= deltaTime;


        // Respawns an alien as long as the spawn cooldown is over and enough time has passed since the
        // user killed an alien.
        if (alienSpawnCountdown <= 0 && timeSinceAlienKilled <= 0) {
            spawnAlien();
            alienSpawnCountdown = fortress.getSpawnRate();
            timeSinceAlienKilled = 0;
        }

        // Updates all bullets each tick, checks if bullet collides with firetruck and then removes health from the
       // firetruck. If a firetruck is destroyed, checks if all have been destroyed and then activates game over screen.
        for (Projectile bullet : new ArrayList<Projectile>(bullets)) {
            bullet.update();
            if (bullet.getLength() > bullet.getMaxLength()) {
                bullet.dispose();
                bullets.remove(bullet);
            }
            for (Firetruck truck : new ArrayList<Firetruck>(firetrucks)) {
                if (bullet.hitUnit(truck)) {
                    truck.takeDamage(bullet.getDamage());
                    bullets.remove(bullet);
                    if (truck.getCurrentHealth() == 0) {
                        truck.setSelected(false);
                        firetrucks.remove(truck);
                        if(firetrucks.size() == 0) {
                            levelLost = true;
                            timeTaken = timer.getTime();
                        }
                        destroyedFiretrucks.add(truck);
                    }
                }
            }
        }

        // Refills firetrucks tank if truck reaches the fire station.
        for (Firetruck truck : firetrucks) {
            if (!(truck.getTopRight().y < fireStation.getPosition().y || truck.getPosition().y > fireStation.getTopRight().y ||
                    truck.getTopRight().x < fireStation.getPosition().x || truck.getPosition().x > fireStation.getTopRight().x)) {
            	truck.addHealth(1);
                truck.setCurrentWater(truck.getMaxWater());
            }
            for (PowerUps pow : powerList){
                if(!(truck.getTopRight().y < pow.getPosition().y || truck.getPosition().y > pow.getTopRight().y ||
                        truck.getTopRight().x < pow.getPosition().x || truck.getPosition().x > pow.getTopRight().x)){
                    truck.powerUp(pow.getType());
                    clearList.add(pow);
                }
            }
            for (PowerUps pow : clearList) {
                powerList.remove(pow);
            }
        }

        // Updates all water drops each tick, if the drop reaches a certain distance then it is deleted. Otherwise,
        // Checks if drop collides with alien/fortress and then removes health from it if so. If alien dies, removes it
        // and adds its coordinates back to the fortresses potential spawn locations. If fortress reaches 0 then
        // game win screen is called and level progress saved.

        for (Projectile drop : new ArrayList<Projectile>(water)) {
            drop.update();
            if (drop.getLength() > drop.getMaxLength()) {
                drop.dispose();
                water.remove(drop);
            }
            for (Alien alien : new ArrayList<Alien>(aliens)) {
                if (drop.hitUnit(alien)) {
                    alien.takeDamage(drop.getDamage());
                    water.remove(drop);
                    if (alien.getCurrentHealth() == 0) {
                        fortress.getAlienPositions().add(alien.getPosition());
                        alien.dispose();
                        aliens.remove(alien);
                        timeSinceAlienKilled = fortress.getSpawnRate();
                    }
                }
            }
            if (drop.hitUnit(fortress)) {
                fortress.takeDamage(drop.getDamage());
                if (fortress.getCurrentHealth() == 0) {
                    levelWon = true;
                    timeTaken = timer.getTime();
                    saveData.putBoolean(level, true);
                    saveData.flush();
                }
            }
        }

        // Regenerates fortress health each second
        if(timeSinceLastFortressRegen <= 0) {
            fortress.addHealth(10);
            timeSinceLastFortressRegen = 1;
        }
        timeSinceLastFortressRegen -= deltaTime;

        // If the time is greater than the time limit, calls end game state.
        if (timer.getTime() > timeLimit) {
            levelLost = true;
        }

        // Forces user back to level select screen, even without needing to press ENTER after 4 seconds.
        if (timer.getTime() > timeLimit + 4) {
            gameStateManager.set(new LevelSelectState(gameStateManager));
        }

        // Forces user back to level select screen, even without needing to press ENTER after 4 seconds.
        if (levelWon) {
            gameStateManager.set(new MiniGameState(gameStateManager, levelNum));
        }

        if (levelLost && Gdx.input.isKeyPressed(Keys.ENTER)) {
        	gameStateManager.set(new LevelSelectState(gameStateManager));
        }

        // Speeds up the background music when the player begins to run out of time.
        if ((14 < timeLimit - timer.getTime()) && (timeLimit - timer.getTime() < 16)){
            Kroy.INTRO.setPitch(Kroy.ID, 2f);
        }
        // Assesment 4 - Power Ups
        if ((int)timer.getTime() % 10 == 0 && this.spawn == false && timer.getTime() > 1){
            Random rand = new Random() ;
            int r = rand.nextInt(5);
            Vector2 position = null;
            Boolean blocked = true;
            //Checks whether the power up is created on a reachable square and generates a new position if not.
            while (blocked) {
                int x = rand.nextInt(56);
                int y = rand.nextInt(26);
                position = new Vector2(33 + x * 32, 212 + y * 32);
                blocked = (PlayState.gameMap.getTileTypeByScreenCoordinate(position.x,position.y).getCollidable());
            }
            PowerUps powerUp = new PowerUps(position, 32, 32, powerTexture.get(r), powerUpTypes.get(r));
            powerList.add(powerUp);
            this.spawn = true;
        }
        // Stops from spawning on every frame in the 10th second
        if((int)timer.getTime() % 11 == 0 && this.spawn == true && timer.getTime() > 1){
            System.out.println("Reset: " +  timer.getTime());
            this.spawn = false;
        }
    }

    /**
     * Used to draw elements onto the screen.
     * @param spriteBatch a container for all elements which need rendering to the screen.
     */
    @Override
    public void render(SpriteBatch spriteBatch) {

    	//Creates separate spriteBatch as to load background behind the map which needs to be loaded outside a spritebatch
    	spriteBatch.begin();
    	spriteBatch.draw(background, 0, 0, Kroy.WIDTH, Kroy.HEIGHT);
    	spriteBatch.end();


    	// Renders the tiled map
    	gameMap.render();


    	// Rest of the game objects are loaded and rendered above the tilemap
        spriteBatch.begin();
        // Draws buttons onto play screen
        spriteBatch.draw(quitLevel.getTexture(), quitLevel.getPosition().x, quitLevel.getPosition().y,
                quitLevel.getWidth(), quitLevel.getHeight());

        spriteBatch.draw(quitGame.getTexture(), quitGame.getPosition().x, quitGame.getPosition().y,
                quitGame.getWidth(), quitGame.getHeight());

        // Draws updated firetrucks and overhead water tank statistics.
        for (Firetruck truck : firetrucks) {
            Sprite truckSprite = new Sprite(truck.getTexture());
            truckSprite.setPosition(truck.getPosition().x,truck.getPosition().y);
            truckSprite.setRegionHeight((int)truck.getHeight());
            truckSprite.setRegionWidth((int)truck.getWidth());
            truckSprite.setRotation(truck.getTruckRotation());
            truckSprite.draw(spriteBatch);
            /**
            spriteBatch.draw(truckSprite.setRotation(truck.getRotation()), truck.getPosition().x, truck.getPosition().y, truck.getWidth(),
                    truck.getHeight());
             **/
            healthBars.draw(spriteBatch, "Water: " + truck.getCurrentWater(), truck.getPosition().x,
                    truck.getPosition().y + truck.getHeight() + 10);
        }

        // Draws updated fortress HP
        healthBars.draw(spriteBatch, "HP: " + fortress.getCurrentHealth(), fortress.getPosition().x + 70,
                fortress.getPosition().y + fortress.getHeight() + 20);

        // Draws updated alien locations
        for (Alien alien : aliens) {
            spriteBatch.draw(alien.getTexture(), alien.getPosition().x, alien.getPosition().y, alien.getWidth(),
                    alien.getHeight());
            healthBars.draw(spriteBatch, "HP: " + alien.getCurrentHealth(), alien.getPosition().x,
                    alien.getPosition().y + alien.getHeight() + 10);

        }

        // Draws updated projectile locations
        for (Projectile bullet : bullets) {
            spriteBatch.draw(bullet.getTexture(), bullet.getPosition().x, bullet.getPosition().y, bullet.getWidth(),
                    bullet.getHeight());
        }

        for (Projectile drop : water) {
            spriteBatch.draw(drop.getTexture(), drop.getPosition().x, drop.getPosition().y, drop.getWidth(),
                    drop.getHeight());
        }

        for(PowerUps power: powerList){
            spriteBatch.draw(power.getTexture(), power.getPosition().x, power.getPosition().y, power.getWidth(),
                    power.getHeight());
        }

        timer.drawTime(spriteBatch, ui);
        ui.setColor(Color.WHITE);

        // Gives user 15 second warning as time limit approaches.
        if ((timeLimit - 15) < timer.getTime() && timer.getTime() < (timeLimit - 10)) {
            ui.draw(spriteBatch, "The firestation is being attacked \n You have 15 seconds before it's destroyed!",
                    50, 1020);
        }

        // Draws UI Text onto the screen
        ui.setColor(Color.DARK_GRAY);
        ui.draw(spriteBatch, "Truck 1 Health: " + Integer.toString(firetruck1.getCurrentHealth()),
        		70, Kroy.HEIGHT - 920);
        ui.draw(spriteBatch, "Truck 2 Health: " + Integer.toString(firetruck2.getCurrentHealth()),
        		546, Kroy.HEIGHT - 920);
        ui.draw(spriteBatch, "Truck 3 Health: " + Integer.toString(firetruck3.getCurrentHealth()),
        		1023, Kroy.HEIGHT - 920);
        ui.draw(spriteBatch, "Truck 4 Health: " + Integer.toString(firetruck4.getCurrentHealth()),
        		1499, Kroy.HEIGHT - 920);

        for (Firetruck truck: firetrucks) {
            if (truck.getCurrentWater() < 20) {
                spriteBatch.draw(new Texture("refillWarning.png"), truck.getPosition().x - 25,
                        truck.getPosition().y + 40);
            }
        }

        // If end game reached, draws level fail or level won images to the screen
        if (levelLost) {
            spriteBatch.draw(new Texture("levelFail.png"), 0, 0);
            Kroy.INTRO.setPitch(Kroy.ID, 1f);
        }
//
//        if (levelWon & !levelLost) {
//            spriteBatch.draw(new Texture("LevelWon.png"), 0, 0);
//            Kroy.INTRO.setPitch(Kroy.ID, 1f);
//        }
        spriteBatch.end();
    }

    /**
     * Used to dispose of all textures, music etc. when no longer required to avoid memory leaks
     */
    @Override
    public void dispose() {
        background.dispose();
        quitLevel.dispose();
        quitGame.dispose();
        waterShoot.dispose();
        gameMap.dispose();

        for (Firetruck firetruck : firetrucks) {
            firetruck.dispose();
        }

        for (Alien alien : aliens) {
            alien.dispose();
        }

        for (Firetruck firetruck : destroyedFiretrucks) {
            firetruck.dispose();
        }

        for (Projectile bullet : bullets) {
            bullet.dispose();
        }

        for (Projectile drop : water) {
            drop.dispose();
        }

        for (Entity obstacles: obstacles) {
            obstacles.dispose();
        }
        for(Entity pow : powerList){
            pow.dispose();
        }

        fireStation.dispose();
        fortress.dispose();
    }


    /**
     * Used to spawn the Aliens around the fortress by accessing the spawnRate and alienPositions stored within
     * the fortress object.
     */
    public void spawnAlien() {
        Random rand = new Random();
        if (fortress.getAlienPositions().size() > 0) {
            Vector2 coordinate = fortress.getAlienPositions().get(rand.nextInt(fortress.getAlienPositions().size()));
            Alien alien = new Alien(coordinate, 32, 32, new Texture("alien.gif"), 30 + rand.nextInt(60),
                    300, null, 1, (int) (Kroy.difficultyMultiplier * (5 + rand.nextInt(15))), randomPatrolRoute(coordinate),
                    (float) (2.5 - Kroy.difficultyMultiplier) * (1 + rand.nextInt(3)));
            aliens.add(alien);
            fortress.getAlienPositions().remove(coordinate);
        }
    }

    /**
     * Added by DicyCat
     * Generates a random patrol route within a fixed bound.
     * @param spawnPos Initial position of the alien
     * @return An array of Vector2 waypoints that form a route
     */
    public Vector2[] randomPatrolRoute(Vector2 spawnPos) {
    	Random rand = new Random();
    	Vector2[][] patrolSpace = {		//Area to patrol | Bottom Left, Top Right
    			{new Vector2(33 + 15 * 32, 212 + 15 * 32), new Vector2(33 + 35 * 32, 212 + 25 * 32)},	//Level 1
    			{new Vector2(33 + 20 * 32, 212 + 5 * 32), new Vector2(33 + 45 * 32, 212 + 19 * 32)},	//Level 2
    			{new Vector2(33 + 15 * 32, 212 + 15 * 32), new Vector2(33 + 40 * 32, 212 + 19 * 32)},	//Level 3
    			{new Vector2(33 + 15 * 32, 212 + 10 * 32), new Vector2(33 + 35 * 32, 212 + 25 * 32)},	//Level 4
    			{new Vector2(33 + 2 * 32, 212 + 5 * 32), new Vector2(33 + 15 * 32, 212 + 25 * 32)},	//Level 5	TODO
    			{new Vector2(33 + 15 * 32, 212 + 10 * 32), new Vector2(33 + 40 * 32, 212 + 25 * 32)},	//Level 6
    	};
    	Vector2[] patrolRoute = new Vector2[(2 + rand.nextInt(4))];	//Create patrol of random length
    	patrolRoute[0] = spawnPos; //Set first patrol point as spawn position
    	for (int i = 1; i < patrolRoute.length; i++) {
			patrolRoute[i] = new Vector2(1 + rand.nextInt((int)(patrolSpace[levelNumber-1][1].x - patrolSpace[levelNumber-1][0].x)) + patrolSpace[levelNumber-1][0].x,
					1 + rand.nextInt((int)(patrolSpace[levelNumber-1][1].y - patrolSpace[levelNumber-1][0].y)) + patrolSpace[levelNumber-1][0].y);
		}

    	return patrolRoute;
    }
}
