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
import com.badlogic.gdx.utils.Json;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mygdx.game.Kroy;
import com.mygdx.game.map.TiledGameMap;
import com.mygdx.game.misc.Button;
import com.mygdx.game.misc.Stopwatch;
import com.mygdx.game.sprites.*;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Text;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

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

    private Stopwatch stopwatch;
    private float alienSpawnCountdown;
    private float timeSinceAlienKilled;
    private float timeSinceLastFortressRegen;
    private float timeLimit;
    private float timeTaken;

    //private Entity fireStation;
    private FireStation fireStation;

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

    //Assessment 4 - Create defensive weapons for fortresses
    private ArrayList<Projectile> bombs = new ArrayList<Projectile>();

    //Assessment 4 - Array list of fire stations positions
    private ArrayList<Vector2> fireStationPositions = new ArrayList<Vector2>();

    private BitmapFont ui;
    private BitmapFont healthBars;
    private String level;
    private int levelNum;
    public static TiledGameMap gameMap;

    private Sound waterShoot = Gdx.audio.newSound(Gdx.files.internal("honk.wav"));

    //Assessment 4
    private boolean spawn;
    private   ArrayList<Texture> powerTexture = new ArrayList<Texture>();
    Texture maxHealthIncrease = new Texture("powerMaxHealth1.png");
    Texture damageIncrease = new Texture("powerDamage.png");
    Texture infiniteHealth = new Texture("powerInfiniteHealth1.png");
    Texture rangeIncrease = new Texture("powerRange.png");
    Texture speedIncrease = new Texture("powerSpeed1.png");

    private ArrayList<String> powerUpTypes = new ArrayList<String>();

    private ArrayList<PowerUps> powerList = new ArrayList<PowerUps>();
    private ArrayList<PowerUps> clearList = new ArrayList<PowerUps>();

    Gson gson = new Gson();


    public PlayState(GameStateManager gsm,  int levelNumber){
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

        // Assessment 4
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

        // Assesment 4 - Added difficulty to fortreses
        if (levelNumber == 1) { // Bottom left coordinate of map --> (33, 212) Each grid square = 32px

        	gameMap = new TiledGameMap("level1map.tmx");

            firetruck1pos = new Vector2(33 + 10 * 32, 212 + 6 * 32);
            firetruck2pos = new Vector2(33 + 11 * 32, 212 + 6 * 32);
            firetruck3pos = new Vector2(33 + 10 * 32, 212 + 5 * 32);
            firetruck4pos = new Vector2(33 + 11 * 32, 212 + 5 * 32);
            timeLimit = 90;

            // Level 1 Firestation
            fireStation = new FireStation(new Vector2(33 + 8 * 32, 212 + 4 * 32), 128, 128,
                    new Texture("teal.jpg"), 1000);

            // Level 1 Fortress
            fortress = new Fortress(new Vector2(33 + 24 * 32, 212 + 22 * 32), 6 * 32, 4 * 32,
                   new Texture("grey.png"), (int)(Kroy.difficultyMultiplier * 10000), 1.5f, levelNumber, 2, 120);

        }

        else if (levelNumber == 2) {

        	gameMap = new TiledGameMap("level2map.tmx");

            firetruck1pos = new Vector2(33 + 2 * 32, 212 + 4 * 32);
            firetruck2pos = new Vector2(33 + 2 * 32, 212 + 5 * 32);
            firetruck3pos = new Vector2(33 + 2 * 32, 212 + 6 * 32);
            firetruck4pos = new Vector2(33 + 1 * 32, 212 + 4 * 32);

            timeLimit = 120;


            // Level 2 Fire Station
            fireStation = new FireStation(new Vector2(33 + 1 * 32, 212 + 4 * 32), 64, 128,
                    new Texture("teal.jpg"), 1500);

            // Level 2 Fortress
            fortress = new Fortress(new Vector2(33 + 36 * 32, 212 + 19 * 32), 4 * 32, 4 * 32, new Texture("grey.png"),
                    (int)(Kroy.difficultyMultiplier * 12500), 4, levelNumber, 4, 155);
        }

        else if (levelNumber == 3) {

        	gameMap = new TiledGameMap("level3map.tmx");

            firetruck1pos = new Vector2(33 + 29 * 32, 212 + 3 * 32);
            firetruck2pos = new Vector2(33 + 29 * 32, 212 + 2 * 32);
            firetruck3pos = new Vector2(33 + 30 * 32, 212 + 3 * 32);
            firetruck4pos = new Vector2(33 + 30 * 32, 212 + 2 * 32);

            timeLimit = 60;

            // Level 3 Fire Station
            fireStation = new FireStation(new Vector2(33 + 27*32, 212), 6 * 32, 4 * 32, new Texture("teal.jpg"), 2000);

            // Level 3 Fortress
            fortress = new Fortress(new Vector2(33 + 24*32, 212 + 32*21), 224, 96, new Texture("grey.png"),
                    (int)(Kroy.difficultyMultiplier * 15000), 2, levelNumber, 6, 160);
        }


        else if (levelNumber == 4) {

        	gameMap = new TiledGameMap("level4map.tmx");

            firetruck1pos = new Vector2(33 + 7 * 32, 212 + 4 * 32);
            firetruck2pos = new Vector2(33 + 7 * 32, 212 + 5 * 32);
            firetruck3pos = new Vector2(33 + 7 * 32, 212 + 6 * 32);
            firetruck4pos = new Vector2(33 + 8 * 32, 212 + 5 * 32);

            timeLimit = 90;

            // Level 4 Fire Station
            fireStation = new FireStation(new Vector2(33 + 5 * 32, 212 + 4 * 32), 4 * 32, 3 * 32, new Texture("teal.jpg"), 2500);

            // Level 4 Fortress
            fortress = new Fortress(new Vector2(33 + 24*32, 212 + 32*21), 224, 96, new Texture("grey.png"),
                    (int)(Kroy.difficultyMultiplier * 15000), 2, levelNumber, 8, 165);
        }


        else if (levelNumber == 5) {

        	gameMap = new TiledGameMap("level5map.tmx");

            firetruck1pos = new Vector2(33 + 27 * 32, 212 + 14 * 32);
            firetruck2pos = new Vector2(33 + 26 * 32, 212 + 14 * 32);
            firetruck3pos = new Vector2(33 + 27 * 32, 212 + 13 * 32);
            firetruck4pos = new Vector2(33 + 26 * 32, 212 + 13 * 32);

            timeLimit = 90;

            // Level 5 Fire Station
            fireStation = new FireStation(new Vector2(33 + 24 * 32, 212 + 12 * 32), 4 * 32, 3 * 32, new Texture("teal.jpg"), 3000);

            // Level 5 Fortress
            fortress = new Fortress(new Vector2(33 + 4 * 32, 212 + 14 * 32), 4*32, 3*32, new Texture("grey.png"),
                    (int)(Kroy.difficultyMultiplier * 15000), 2, levelNumber, 10, 170);
        }


        else if (levelNumber == 6) {

        	gameMap = new TiledGameMap("level6map.tmx");

            firetruck1pos = new Vector2(33 + 9 * 32, 212 + 4 * 32);
            firetruck2pos = new Vector2(33 + 8 * 32, 212 + 4 * 32);
            firetruck3pos = new Vector2(33 + 9 * 32, 212 + 5 * 32);
            firetruck4pos = new Vector2(33 + 8 * 32, 212 + 5 * 32);

            timeLimit = 60;

            // Level 6 Fire Station
            fireStation = new FireStation(new Vector2(33 + 6 * 32, 212 + 3 * 32), 4 * 32, 3 * 32, new Texture("teal.jpg"), 3000);

            // Level 3 Fortress
            fortress = new Fortress(new Vector2(33 + 24 * 32, 212 + 32 * 21), 224, 96, new Texture("grey.png"),
                    (int)(Kroy.difficultyMultiplier * 15000), 2, levelNumber, 12, 175);
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
        stopwatch = new Stopwatch(timeLimit);


    }

   public PlayState(GameStateManager gsm, String filePath){
        super(gsm);

        background = new Texture("LevelProportions.png");

        quitLevel = new Button(new Texture("PressedQuitLevel.png"),
                new Texture("NotPressedQuitLevel.png"),350 / 2, 100 / 2,
                new Vector2(30, 30), false, false);

        quitGame = new Button(new Texture("PressedQuitGame.png"),
                new Texture("NotPressedQuitGame.png"), 350 / 2, 100 / 2,
                new Vector2(1920 - 30 - 350 / 2, 30), false, false);



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

        // Get Data From json and convert to the required objects and attributes

       Json json = new Json();

        if(filePath != null) {
            try {
                JSONParser parser = new JSONParser();
                Object stuff = parser.parse(new FileReader(filePath));
                JSONArray data = new JSONArray();
                data.add(stuff);
                JSONObject data1 = (JSONObject) data.get(0);

                timeLimit = (float) Integer.parseInt(data1.get("time-left").toString());
                //double timeLimit2 = (double)((double) timeLimit1);
                System.out.println("boi "+timeLimit);

                levelNumber = (int) Integer.parseInt(data1.get("level").toString());
                levelNum = levelNumber - 1; // Need to get value from json file
                level = Integer.toString(levelNumber); // Used as a key when saving level progress
                //fortress = gson.fromJson(data1.get("fortress").toString(), Fortress.class);

                JSONObject fort = (JSONObject) data1.get("fortress");



                Vector2 fortPos = new Vector2( Float.parseFloat(((JSONObject)fort.get("sprite")).get("x").toString()),
                        Float.parseFloat(((JSONObject)fort.get("sprite")).get("y").toString()));

                int fortWidth = (int)Float.parseFloat(((JSONObject)fort.get("sprite")).get("width").toString());

                int fortHeight = (int)Float.parseFloat(((JSONObject)fort.get("sprite")).get("height").toString());
                int fortMaxHP = 15000;
                if(levelNumber == 1){
                    fortMaxHP = 10000;
                }else if(levelNumber == 2){
                    fortMaxHP = 12500;
                }else if(levelNumber == 3){
                    fortMaxHP = 15000;
                }else{
                    fortMaxHP = 15000;
                }

                float fortSPR = Float.parseFloat(fort.get("spawnRate").toString());
                System.out.println(fortSPR);
                float fAR = Float.parseFloat(fort.get("attackRange").toString());
                int fDmg = Integer.parseInt(fort.get("damage").toString());


                float diffM = Float.parseFloat(data1.get("difficulty").toString());

                fortress = new Fortress(fortPos, fortWidth, fortHeight, new Texture("grey.png"), (int)(fortMaxHP * diffM), fortSPR, levelNumber, fDmg, fAR);

                fortress.setHealth(Integer.parseInt(fort.get("currentHealth").toString()));


                ArrayList<Vector2> alienPos = new ArrayList<>();
                JSONArray aPoss = (JSONArray) fort.get("alienPositions");
                System.out.println(aPoss);
                for(int i = 0 ; i < aPoss.size() ; i++){
                    JSONObject pos = (JSONObject) aPoss.get(i);
                    alienPos.add( new Vector2( Float.parseFloat(pos.get("x").toString()),
                                                Float.parseFloat(pos.get("y").toString())));
                }
                fortress.setAlienPositions(alienPos);




                if (levelNumber == 1) { // Bottom left coordinate of map --> (33, 212) Each grid square = 32px
                    gameMap = new TiledGameMap("level1map.tmx");
                    fireStation = new FireStation(new Vector2(33 + 8 * 32, 212 + 4 * 32), 128, 128,
                            new Texture("teal.jpg"), 500);
                } else if (levelNumber == 2) {
                    gameMap = new TiledGameMap("level2map.tmx");
                    fireStation = new FireStation(new Vector2(33 + 1 * 32, 212 + 4 * 32), 64, 128,
                            new Texture("teal.jpg"), 1000);
                } else if (levelNumber == 3) {
                    gameMap = new TiledGameMap("level3map.tmx");
                    fireStation = new FireStation(new Vector2(33 + 27 * 32, 212), 6 * 32, 4 * 32, new Texture("teal.jpg"), 1500);
                } else if (levelNumber == 4) {
                    gameMap = new TiledGameMap("level4map.tmx");
                    fireStation = new FireStation(new Vector2(33 + 5 * 32, 212 + 4 * 32), 4 * 32, 3 * 32, new Texture("teal.jpg"),2000);
                } else if (levelNumber == 5) {
                    gameMap = new TiledGameMap("level5map.tmx");
                    fireStation = new FireStation(new Vector2(33 + 24 * 32, 212 + 12 * 32), 4 * 32, 3 * 32, new Texture("teal.jpg"),2500);
                } else if (levelNumber == 6) {
                    gameMap = new TiledGameMap("level6map.tmx");
                    fireStation = new FireStation(new Vector2(33 + 6 * 32, 212 + 3 * 32), 4 * 32, 3 * 32, new Texture("teal.jpg"),3000);
                }


                JSONArray importedFiretrucks = (JSONArray) data1.get("Firetrucks");

                for(int i = 0 ; i < importedFiretrucks.size() ; i++){
                    JSONObject importedTruck = (JSONObject) importedFiretrucks.get(i);


                    Vector2 pos = new Vector2( Float.parseFloat(((JSONObject)importedTruck.get("sprite")).get("x").toString()),
                            Float.parseFloat(((JSONObject)importedTruck.get("sprite")).get("y").toString()) );

                    int currentWater = Integer.parseInt(importedTruck.get("currentWater").toString());
                    int currentHealth = Integer.parseInt(importedTruck.get("currentHealth").toString());
                    int maxHealth = Integer.parseInt(importedTruck.get("maxHealth").toString());
                    int range = Integer.parseInt(importedTruck.get("range").toString());
                    int speed = Integer.parseInt(importedTruck.get("speed").toString());
                    int damage = Integer.parseInt(importedTruck.get("damage").toString());
                    int maxWater = Integer.parseInt(importedTruck.get("maxWater").toString());

                    Firetruck truck = new Firetruck(pos, 25,25,
                            new Texture("truck.png"), maxHealth, range,
                            null, speed, damage,  maxWater, true);
                    truck.setCurrentHealth(currentHealth);
                    truck.setCurrentWater(currentWater);
                    truck.setSelected( Boolean.parseBoolean(importedTruck.get("selected").toString()));
                    firetrucks.add(truck);
                    /**
                    positions.add( new Vector2( Float.parseFloat((((JSONObject) ( (JSONObject) importedFiretrucks.get(i)).get("sprite")).get("x")).toString()) ,
                            Float.parseFloat((((JSONObject) ( (JSONObject) importedFiretrucks.get(i)).get("sprite")).get("y")).toString()) ) );

                    currentWaters.add( Integer.parseInt( ((JSONObject) importedFiretrucks.get(i)).get("currentWater").toString()));
                    currentHealths.add( Integer.parseInt( ((JSONObject)importedFiretrucks.get(i)).get("currentHealth").toString() ) );
                    **/

                }


                /**
                Vector2 firetruck1pos = positions.get(0);
                Vector2 firetruck2pos = positions.get(1);
                Vector2 firetruck3pos = positions.get(2);
                Vector2 firetruck4pos = positions.get(3);



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

                firetruck1.setCurrentWater(currentWaters.get(0));
                firetruck2.setCurrentWater(currentWaters.get(1));
                firetruck3.setCurrentWater(currentWaters.get(2));
                firetruck4.setCurrentWater(currentWaters.get(3));

                firetruck1.setCurrentHealth(currentHealths.get(0));
                firetruck2.setCurrentHealth(currentHealths.get(1));
                firetruck3.setCurrentHealth(currentHealths.get(2));
                firetruck4.setCurrentHealth(currentHealths.get(3));


                firetrucks.add(firetruck1);
                firetrucks.add(firetruck2);
                firetrucks.add(firetruck3);
                firetrucks.add(firetruck4);

                 **/

                JSONArray importedAliens = (JSONArray) data1.get("Aliens");


                ArrayList<Alien> loadAliens = new ArrayList<>();

                for(int i = 0 ; i < importedAliens.size() ; i++){
                    int aHealth, aMHealth, aSpeed, aDamage, aRange, aWidth, aHeight, aCurrentIndex;
                    float aPx, aPy, aAttackCooldown, aTSinceAttack;

                    JSONObject Alien = ((JSONObject) (importedAliens.get(i)));
                    aHealth = Integer.parseInt(Alien.get("currentHealth").toString());
                    aMHealth = Integer.parseInt(Alien.get("maxHealth").toString());
                    aSpeed = Integer.parseInt(Alien.get("speed").toString());
                    aDamage = Integer.parseInt(Alien.get("damage").toString());
                    aRange = Integer.parseInt(Alien.get("range").toString());
                    aWidth = (int)Float.parseFloat((((JSONObject) Alien.get("sprite")).get("width")).toString());
                    aHeight = (int)Float.parseFloat((((JSONObject) Alien.get("sprite")).get("height")).toString());
                    aPx = Float.parseFloat((((JSONObject) Alien.get("sprite")).get("x")).toString());
                    aPy = Float.parseFloat((((JSONObject) Alien.get("sprite")).get("y")).toString());
                    Vector2 aPos = new Vector2(aPx, aPy);
                    aAttackCooldown = Float.parseFloat(Alien.get("attackCooldown").toString());
                    aTSinceAttack = Float.parseFloat(Alien.get("timeSinceAttack").toString());
                    aCurrentIndex = Integer.parseInt(Alien.get("currentIndex").toString());

                    JSONArray importedWaypoints = (JSONArray) Alien.get("waypoints");
                    ArrayList<Vector2> wayP = new ArrayList<>();
                    for(int j = 0 ; j < importedWaypoints.size() ; j++){
                        JSONObject wp = (JSONObject) importedWaypoints.get(j);
                        wayP.add(new Vector2( Float.parseFloat(wp.get("x").toString()),
                                Float.parseFloat(wp.get("y").toString()) ));

                    }
                    Vector2[] waypoints = new Vector2[wayP.size()];
                    for(int j = 0 ; j < wayP.size() ; j++){
                        waypoints[j] = wayP.get(j);
                    }
                    Alien al = new Alien(aPos, aWidth, aHeight, new Texture("alien.gif"), aMHealth,
                            aRange, null, aSpeed, aDamage, waypoints, aAttackCooldown);
                    al.setHealth(aHealth);
                    al.setCurrentIndex(aCurrentIndex);
                    al.setTimeSinceAttack(aTSinceAttack);
                    aliens.add(al);



                    }


                JSONArray importedPowerUps = (JSONArray)data1.get("PowerUps");
                for(int i = 0 ; i < importedPowerUps.size() ; i++){
                    JSONObject powerUp = (JSONObject) importedPowerUps.get(i);
                    float xCoord = Float.parseFloat(((JSONObject)powerUp.get("sprite")).get("x").toString());
                    float yCoord = Float.parseFloat(((JSONObject)powerUp.get("sprite")).get("y").toString());
                    Vector2 pos = new Vector2( xCoord , yCoord );
                    String type = powerUp.get("type").toString().trim();
                    Texture powerUpTexture;
                    String texturePath = ((JSONObject)((JSONObject)((JSONObject)((JSONObject)((JSONObject)powerUp.get("sprite")).get("texture")).get("data")).get("file")).get("file")).get("path").toString();
                    powerList.add(new PowerUps(pos, 50, 50, new Texture(texturePath), type) );
                }


                stopwatch = new Stopwatch(timeLimit);
            }catch (Exception e){

                e.printStackTrace();
            }
       }
    }


    /**
     * The game logic which is executed due to specific user inputs. Is called in the update method.
     */
    public void handleInput() {
        // Checks for hover and clicks on the 2 buttons located on the play screen.
        if (quitGame.mouseInRegion()){
            quitGame.setActive(true);
            if (Gdx.input.isTouched()) {
                serializeState();
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
                // Assessment 4
                //serializeState();
                gameStateManager.pop();
            }
        }

        else {
            quitLevel.setActive(false);
        }

        // If the user presses the space bar, creates Projectile instance if the selected firetruck has water remaining.
        // Then adds this to the water ArrayList and removes 1 water from the firetrucks tank.
        for (Firetruck firetruck : firetrucks) {

            //Assessment 4 - Fortress's weapon
            if (firetruck.isSelected()) {
                Projectile bomb = new Projectile(new Vector2(fortress.getPosition().x + fortress.getWidth() / 2, fortress.getPosition().y + fortress.getWidth() / 2), 5, 5,
                        new Texture("red.png"), (new Vector2(firetruck.getPosition().x, firetruck.getPosition().y)), 10, fortress.getDamage(), fortress.getAttackRange());
                bombs.add(bomb);
            }

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
    // Assessment 4
    private void serializeState(){

        try {

            String locRoot = Gdx.files.getLocalStoragePath();
            String da = new SimpleDateFormat("yyyyMMddHHmmss'.json'").format(new Date());
            String dir = locRoot+"/saves/";
            String d = dir+da;
            System.out.println(d);
            int i = 0;
            //FileWriter writer = new FileWriter(d);
            new File(dir).mkdirs();
            File file = new File(d);
            file.createNewFile();
            FileWriter writer = new FileWriter(d);
            writer.write("{\n\t\"Firetrucks\" : [\n");
            for (Firetruck f : firetrucks) {
                if (!(i == 0)) {
                    writer.write(", ");
                }
                i++;
                writer.write("\t\t");
                gson.toJson(f, writer);
                writer.write("\n");
            }
            i = 0;
            writer.write("\t],\n\t\"Aliens\" : [\n");
            for (Alien a : aliens) {
                if (!(i == 0)) {
                    writer.write(", ");
                }
                i++;
                writer.write("\t\t");
                gson.toJson(a, writer);
                writer.write("\n");
            }
            writer.write("\t],\n\t\"PowerUps\" : [\n");
            i = 0;
            for (PowerUps p : powerList) {
                if (!(i == 0)) {
                    writer.write(", ");
                }
                i++;
                writer.write("\t\t");
                gson.toJson(p, writer);
                writer.write("\n");

            }
            writer.write("\t],\n\t\"fortress\" : ");
            gson.toJson(fortress, writer);
            writer.write(",\n\t\"level\" : ");
            gson.toJson(level, writer);
            writer.write(",\n\t\"difficulty\" : ");
            gson.toJson(Kroy.difficultyMultiplier, writer);
            writer.write(",\n\t\"time-left\" : ");
            gson.toJson((int) (timeLimit - stopwatch.getTime()), writer);
            writer.write("\n}");
            writer.flush();
            writer.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Updates the game logic before the next render() is called
     * @param deltaTime the amount of time which has passed since the last render() call
     */
    @Override
    public void update(float deltaTime)  {
        // Calls input handler and updates timer each tick of the game.
        handleInput();
        stopwatch.update();
        System.out.println((Kroy.difficultyMultiplier));
        // Updates aliens and attacks firetruck if there is a firetruck in range and the Aliens attack cooldown is over.
        // Adds the created bullet projectile to the ArrayList bullets
        for (Alien alien : aliens) {
            //Assessment 4
            if (timeLimit - stopwatch.getTime() > timeLimit / 2){
            alien.update();
            alien.truckInRange(firetrucks, fireStation);
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
            if (timeLimit - stopwatch.getTime() <= timeLimit / 2) {
                alien.updateToFireStation(fireStation.getPosition());
                alien.truckInRange(firetrucks, fireStation);
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

        // Assessment 4 - Fortress's weapon
        for (Projectile bomb : new ArrayList<Projectile>(bombs)) {
            bomb.update();
            if (bomb.getLength() > bomb.getMaxLength()) {
                bomb.dispose();
                bombs.remove(bomb);
            }
            for (Firetruck truck: new ArrayList<Firetruck>(firetrucks)) {
                if (bomb.hitUnit(truck)) {
                    truck.takeDamage(bomb.getDamage());
                    bombs.remove(bomb);
                    if (truck.getCurrentHealth() <= 0) {
                        truck.setSelected(false);
                        firetrucks.remove(truck);
                        if(firetrucks.size() == 0) {
                            levelLost = true;
                            timeTaken = stopwatch.getTime();
                        }
                        destroyedFiretrucks.add(truck);
                    }
                }
            }
        }

        // Updates all bullets each tick, checks if bullet collides with firetruck and then removes health from the
        // firetruck. If a firetruck is destroyed, checks if all have been destroyed and then activates game over screen.
        for (Projectile bullet : new ArrayList<Projectile>(bullets)) {
            bullet.update();
            if (bullet.getLength() > bullet.getMaxLength()) {
                bullet.dispose();
                bullets.remove(bullet);
            }

            //Assessment 4 - Do damage to fire station
            if (bullet.hitUnit(fireStation)){
                fireStation.takeDamage(bullet.getDamage());
                bullets.remove(bullet);
                if (fireStation.getCurrentHealth() <= 0)  {
                    levelLost = true;
                    timeTaken = stopwatch.getTime();
                }
            }

            for (Firetruck truck : new ArrayList<Firetruck>(firetrucks)) {
                if (bullet.hitUnit(truck)) {
                    truck.takeDamage(bullet.getDamage());
                    bullets.remove(bullet);
                    if (truck.getCurrentHealth() <= 0) {
                        truck.setSelected(false);
                        firetrucks.remove(truck);
                        if(firetrucks.size() == 0) {
                            levelLost = true;
                            timeTaken = stopwatch.getTime();
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
                    timeTaken = stopwatch.getTime();
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
        if (stopwatch.getTime() > timeLimit) {
            levelLost = true;
        }

        // Forces user back to level select screen, even without needing to press ENTER after 4 seconds.
        if (stopwatch.getTime() > timeLimit + 4) {
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
        if ((14 < timeLimit - stopwatch.getTime()) && (timeLimit - stopwatch.getTime() < 16)){
            Kroy.INTRO.setPitch(Kroy.ID, 2f);
        }
        // Assessment 4 - Power Ups
        if ((int) stopwatch.getTime() % 10 == 0 && this.spawn == false && stopwatch.getTime() > 1){
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
            PowerUps powerUp = new PowerUps(position, 50, 50, powerTexture.get(r), powerUpTypes.get(r));
            powerList.add(powerUp);
            this.spawn = true;
        }
        // Stops from spawning on every frame in the 10th second
        if((int) stopwatch.getTime() % 11 == 0 && this.spawn == true && stopwatch.getTime() > 1){
            System.out.println("Reset: " +  stopwatch.getTime());
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

        //Assessment 4

        healthBars.draw(spriteBatch, "HP: " + fireStation.getCurrentHealth(), fireStation.getPosition().x +70,
                fireStation.getPosition().y + fortress.getHeight() + 20);

        // Draws updated alien locations
        for (Alien alien : aliens) {
            spriteBatch.draw(alien.getTexture(), alien.getPosition().x, alien.getPosition().y, alien.getWidth(),
                    alien.getHeight());
            healthBars.draw(spriteBatch, "HP: " + alien.getCurrentHealth(), alien.getPosition().x,
                    alien.getPosition().y + alien.getHeight() + 10);

        }

        // Draws updated projectile locations

        //Assessment 4 - Fortress's weapon
        for (Projectile bomb : bombs) {
            spriteBatch.draw(bomb.getTexture(), bomb.getPosition().x, bomb.getPosition().y, bomb.getWidth(),
                    bomb.getHeight());
        }

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

        stopwatch.drawTime(spriteBatch, ui);
        System.out.println(timeLimit);
        if (timeLimit > stopwatch.getTime()) {
            stopwatch.drawTime(spriteBatch, ui);
        }else{
            stopwatch.expiredTime(spriteBatch, ui);
        }
        ui.setColor(Color.WHITE);

        // Gives user 15 second warning as time limit approaches.
        if ((timeLimit - 15) < stopwatch.getTime() && stopwatch.getTime() < (timeLimit - 10)) {
            ui.draw(spriteBatch, "The firestation is being attacked \n You have 15 seconds before it's destroyed!",
                    50, 1020);
        }

        // Draws UI Text onto the screen
        int t1HP, t2HP, t3HP, t4HP;
        t1HP = 0;
        t2HP = 0;
        t3HP = 0;
        t4HP = 0;

        for(int i = 0 ; i < firetrucks.size() ; i++){
            if(i == 0){
                t1HP = firetrucks.get(0).getCurrentHealth();
            }else if(i == 1){
                t2HP = firetrucks.get(1).getCurrentHealth();
            }else if(i == 2){
                t3HP = firetrucks.get(2).getCurrentHealth();
            }else if(i == 3){
                t4HP = firetrucks.get(3).getCurrentHealth();
            }
        }


        ui.setColor(Color.DARK_GRAY);
        ui.draw(spriteBatch, "Truck 1 Health: " + (t1HP),
        		70, Kroy.HEIGHT - 920);
        ui.draw(spriteBatch, "Truck 2 Health: " + (t2HP),
        		546, Kroy.HEIGHT - 920);
        ui.draw(spriteBatch, "Truck 3 Health: " + (t3HP),
        		1023, Kroy.HEIGHT - 920);
        ui.draw(spriteBatch, "Truck 4 Health: " + (t4HP),
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

        //Assessment 4 - Fortress's weapon
        for (Projectile bomb : bombs) {
            bomb.dispose();
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
    			{new Vector2(33 + 2 * 32, 212 + 5 * 32), new Vector2(33 + 15 * 32, 212 + 25 * 32)},	//Level 5
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
