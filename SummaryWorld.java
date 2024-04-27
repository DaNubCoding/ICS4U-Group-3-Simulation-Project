import greenfoot.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SummaryWorld displays the summary screens at the end of the simulation
 *
 * @author Sandra Huang
 * @version April 2024
 */
public class SummaryWorld extends PixelWorld
{
    //background images for the summary screen
    private static final GreenfootImage BACKGROUND = new GreenfootImage("background.png");
    private static final GreenfootImage FOREGROUND = new GreenfootImage("foreground.png");
    private static final GreenfootImage TITLE_TEXT = new GreenfootImage("summary/title_text.png");

    //player images
    private static final GreenfootImage[] PLAYER_IMAGES = {new GreenfootImage("summary/player_1.png"), new GreenfootImage("summary/player_2.png")};

    //all possible rods to unlock
    private static final GreenfootImage[][] PLAYER_RODS = {{new GreenfootImage("summary/rod_1.png"), new GreenfootImage("summary/rod_2_blue.png"), new GreenfootImage("summary/rod_3_blue.png")},
                                                           {new GreenfootImage("summary/rod_1.png"), new GreenfootImage("summary/rod_2_pink.png"), new GreenfootImage("summary/rod_3_pink.png")}};
    private static final GreenfootImage ROD_BACKGROUND = new GreenfootImage("summary/rod_bg.png");

    //all possible boats to unlock
    private static final GreenfootImage[][] PLAYER_BOATS = {{new GreenfootImage("summary/boat_1_blue.png"), new GreenfootImage("summary/boat_2_blue.png"), new GreenfootImage("summary/boat_3_blue.png")},
                                                            {new GreenfootImage("summary/boat_1_pink.png"), new GreenfootImage("summary/boat_2_pink.png"), new GreenfootImage("summary/boat_3_pink.png")}};
    private static final GreenfootImage BOAT_BACKGROUND = new GreenfootImage("summary/boat_bg.png");

    //variables for fish summary screen
    private static final int FISH_SPAWN_INTERVAL = 40;

    //simulation world from which SummaryWorld pulls information from
    private SimulationWorld simWorld;
    //current background image of the summary screen
    private GreenfootImage canvasBackground;
    //variable to keep track of which page you are on
    private int pageNumber;
    private boolean keyPressed;

    //variables for fish summary screen
    private ArrayList<FishRecord> fishesToDisplay;
    private int fishesIndex;
    private int currentTier;
    private int counter;
    private Slider speedMultiplierSlider = new Slider<Double>(0.5, 3.0, 1.0, 60, new Color(54, 119, 122));
    private FishRecord curFish;

    /**
     * Create a SummaryWorld with the information from the completed simulation
     *
     * @param simWorld the game world from which SummaryWorld needs to pull information from
     *
     */
    public SummaryWorld(SimulationWorld simWorld){
        super(250, 160);

        this.simWorld = simWorld;
        //save the background image (modified based on player number/fish) to redraw while fading
        canvasBackground = new GreenfootImage(getWidth(), getHeight());

        pageNumber = 1;
        displayPlayerSummary(1);

        triggerFadeIn(0.02);
        render();

        Music.set("credits.wav");
    }

    /**
     * Checks for a mouse click and changes pages when clicked
     * <p>
     * Page 1 displays player 1's player information
     * <p>
     * Page 2 displays player 2's player information
     * <p>
     * Page 3 displays all of the unlocked fish evolutions
     */
    @Override
    public void act(){
        if(Greenfoot.isKeyDown("enter") && !keyPressed){
            displayNextPage();
        }
        keyPressed = Greenfoot.isKeyDown("enter");

        if(pageNumber == 3){ //if we are currently on the fish summary page
            counter++;
            double speedMultiplier = speedMultiplierSlider.getValue().doubleValue();
            if(speedMultiplier != FishRecord.getSpeedMultiplier()){
                FishRecord.setSpeedMultiplier(speedMultiplier);
            }
            if(curFish!=null){
                int curFishDistance = curFish.getX() - curFish.getOriginalImage().getWidth()/2;
                if(FISH_SPAWN_INTERVAL <= curFishDistance){
                    addNextFishToWorld();
                    counter = 0;
                }
            }
        }
        render();

        if (isFadeOutComplete()) {
            Greenfoot.setWorld(new TitleWorld(true));
        }
    }
    /**
     * Displays the next page
     */
    public void displayNextPage(){
        pageNumber++;
        if(pageNumber ==2){
            displayPlayerSummary(2);
        }else if(pageNumber == 3){
            displayFishSummary();
        }else if(pageNumber == 4){
            triggerFadeOut(0.01);
        }
        return;
    }
    /**
     * Displays the player summary pages
     * @param playerNum the player identifier (player 1 or 2)
     */
    private void displayPlayerSummary(int playerNum){
        removeAllActors();

        int playerIndexNum = playerNum-1;

        Button nextPageButton = new Button("Continue", this::displayNextPage);
        addObject(nextPageButton, 125, 145);

        Fisher fisher = simWorld.getFisher(playerNum);
        int expEarned = fisher.getTotalExp();
        Text expText = new Text("Total EXP: " + expEarned, Text.AnchorX.LEFT, Text.AnchorY.TOP);
        addObject(expText, 9, 59);

        Text boatsUnlocked = new Text("Boats Unlocked", Text.AnchorX.LEFT, Text.AnchorY.TOP);
        addObject(boatsUnlocked, 9, 70);

        Text rodsUnlocked = new Text("Rods Unlocked", Text.AnchorX.LEFT, Text.AnchorY.TOP);
        addObject(rodsUnlocked, 164, 70);

        Text playerName;
        if(playerNum==1){
            playerName = new Text("Marie B. O'Logist", Text.AnchorX.CENTER, Text.AnchorY.TOP, new Color(196, 224, 224, 200));
        }else{
            playerName = new Text("Su C. Sheph", Text.AnchorX.CENTER, Text.AnchorY.TOP, new Color(224, 196, 196, 200));
        }
        addObject(playerName, 125, 43);
        //render the background and title
        canvasBackground.drawImage(BACKGROUND, 0, 0);
        canvasBackground.drawImage(TITLE_TEXT, 52, 6);

        //draw player on the screen
        canvasBackground.drawImage(PLAYER_IMAGES[playerIndexNum], 0, 0);

        //spacing for drawing boxes
        int ySpacing = 2;
        int xSpacing = 5;

        //draw unlocked boats
        int boatUnlocked = fisher.getBoatTier().ordinal();
        int boatXDrawPos = 9;
        int boatYDrawPos = 80;

        for(int i=0; i<=boatUnlocked; i++){
            canvasBackground.drawImage(BOAT_BACKGROUND, boatXDrawPos, boatYDrawPos);
            canvasBackground.drawImage(PLAYER_BOATS[playerIndexNum][i], boatXDrawPos, boatYDrawPos);
            if(i%2==0){
                boatXDrawPos += BOAT_BACKGROUND.getWidth() + xSpacing;
            }else{
                boatXDrawPos -= (BOAT_BACKGROUND.getWidth() + xSpacing)/2;
                boatYDrawPos += BOAT_BACKGROUND.getHeight() + ySpacing;
            }
        }

        //draw unlocked rods
        int rodUnlocked = fisher.getFishingRod().getRodTier().ordinal();
        int rodXDrawPos = 165;
        int rodYDrawPos = 80;
        for(int i=0; i<=rodUnlocked; i++){
            canvasBackground.drawImage(ROD_BACKGROUND, rodXDrawPos, rodYDrawPos);
            canvasBackground.drawImage(PLAYER_RODS[playerIndexNum][i], rodXDrawPos, rodYDrawPos);
            if(i%2==0){
                rodXDrawPos += ROD_BACKGROUND.getWidth() + xSpacing;
            }else{
                rodXDrawPos -= (ROD_BACKGROUND.getWidth() + xSpacing)/2;
                rodYDrawPos += ROD_BACKGROUND.getHeight() + ySpacing;
            }
        }
    }

    /**
     * Displays the fish summary page, organising the fish by rarity
     */
    private void displayFishSummary(){
        removeAllActors();

        Button nextPageButton = new Button("Back to Title", this::displayNextPage);
        addObject(nextPageButton, 125, 140);

        //render the background and title
        canvasBackground.drawImage(BACKGROUND, 0, 0);
        canvasBackground.drawImage(TITLE_TEXT, 52, 6);

        addObject(speedMultiplierSlider, 178, 46);

        Text fishDiscoveredText = new Text("Fish Discovered: ", Text.AnchorX.LEFT, Text.AnchorY.CENTER);
        addObject(fishDiscoveredText, 9, 46);

        Text speedText = new Text("Speed: ", Text.AnchorX.LEFT, Text.AnchorY.CENTER);
        addObject(speedText, 148, 46);

        //sets variable default values to run the fish summary screen
        currentTier = 1;
        fishesToDisplay = new ArrayList(simWorld.getDiscoveredFishesOfTier(currentTier));
        fishesIndex = 0;
        counter = 0;
        FishRecord.setSpeedMultiplier(1.0);
        addNextFishToWorld();
    }

    /**
     * Adds the next discovered fish to the world, organised by tier
     */
    private void addNextFishToWorld(){
        while(fishesIndex>=fishesToDisplay.size()){
            currentTier++;
            if(currentTier>FishSettings.MAX_TIER){
                curFish = null;
                return;
            }
            fishesToDisplay = new ArrayList(simWorld.getDiscoveredFishesOfTier(currentTier));
            fishesIndex = 0;
        }
        curFish = fishesToDisplay.get(fishesIndex);
        addObject(curFish, -curFish.getOriginalImage().getWidth()/2, getHeight()/2);
        fishesIndex++;
    }

    /**
     * Renders the background, all of the text, and the fish onto the canvas
     */
    private void render(){
        GreenfootImage canvas = getCanvas();
        canvas.drawImage(canvasBackground, 0, 0);
        renderPixelActors();
        canvas.drawImage(FOREGROUND, 0, 0);
        updateImage();
    }

    /**
     * Removes all of the actors from the world, to clean up the scene before the summary screen is reset to the next page
     */
    private void removeAllActors(){
        List<PixelActor> allActors = getObjects(PixelActor.class);
        for(PixelActor actor : allActors){
            removeObject(actor);
        }
    }
}
