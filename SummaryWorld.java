import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * SummaryWorld displays the summary screens at the end of the simulation
 *
 * @author Sandra Huang
 * @version April 2024
 */
public class SummaryWorld extends PixelWorld
{
    //simulation world from which SummaryWorld pulls information from
    private SimulationWorld simWorld;
    //variable to keep track of which page you are on
    private int pageNumber;
    private boolean keyPressed;
    //backgrounds for each type of summary screen
    private static final GreenfootImage PLAYER_SUMMARY_BACKGROUND = new GreenfootImage("summary/bg_player.png");
    private static final GreenfootImage FISH_SUMMARY_BACKGROUND = new GreenfootImage("summary/bg_fish.png");
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
    private static ArrayList<FishRecord> fishesToDisplay;
    private static int fishesIndex;
    private static int currentTier;
    private static int actNumber;
    private static final int FISH_SPAWN_FREQUENCY = 100;
    private static double fishFrequencyMultiplier;
    private static Slider speedMultiplierSlider = new Slider<Double>(1.0, 4.0, 1.0, 60, new Color(54, 119, 122));

    /**
     * Create a SummaryWorld with the information from the completed simulation
     *
     * @param simWorld the game world from which SummaryWorld needs to pull information from
     *
     */
    public SummaryWorld(SimulationWorld simWorld){
        super(250, 160);

        this.simWorld = simWorld;

        pageNumber = 1;

        displayPlayerSummary(1);
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
    public void act(){
        if(Greenfoot.isKeyDown("enter") && !keyPressed){
            pageNumber++;
            if(pageNumber == 2){
                displayPlayerSummary(2);
            }else if(pageNumber == 3){
                displayFishSummary();
            }else if(pageNumber == 4){
                Greenfoot.setWorld(new TitleWorld());
            }
        }
        keyPressed = Greenfoot.isKeyDown("enter");

        if(pageNumber == 3){ //if we are currently on the fish summary page
            actNumber++;
            if(speedMultiplierSlider.getValue().doubleValue() != FishRecord.getSpeedMultiplier()){
                double newSpeedMultiplier = speedMultiplierSlider.getValue().doubleValue();
                FishRecord.setSpeedMultiplier(newSpeedMultiplier);
                fishFrequencyMultiplier = 1/newSpeedMultiplier;
            }
            if(actNumber >= Math.round(FISH_SPAWN_FREQUENCY * fishFrequencyMultiplier)){
                addNextFishToWorld();
                actNumber = 0;
            }
            render();
        }
    }

    /**
     * Displays the player summary pages
     * @param playerNum the player identifier (player 1 or 2)
     */
    private void displayPlayerSummary(int playerNum){
        GreenfootImage canvas = getCanvas();
        //draw the background
        canvas.drawImage(PLAYER_SUMMARY_BACKGROUND, 0, 0);

        int playerIndexNum = playerNum-1;
        //draw player on the screen
        canvas.drawImage(PLAYER_IMAGES[playerIndexNum], 0, 0);

        Fisher fisher = simWorld.getFisher(playerNum);
        int expEarned = 12497; //simWorld.get____(playerNum);
        Text expText = new Text(expEarned, Text.AnchorX.LEFT, Text.AnchorY.TOP);
        addObject(expText, 77, 56);
        expText.render(canvas);

        //spacing for drawing boxes
        int ySpacing = 2;
        int xSpacing = 5;

        //draw unlocked boats
        int boatUnlocked = fisher.getBoatTier().ordinal();
        int boatXDrawPos = 9;
        int boatYDrawPos = 75;

        for(int i=0; i<= boatUnlocked; i++){ //**IMPORTANT !!!** change to i<=boatUnlocked if boatUnlocked is 0-indexed
            canvas.drawImage(BOAT_BACKGROUND, boatXDrawPos, boatYDrawPos);
            canvas.drawImage(PLAYER_BOATS[playerIndexNum][i], boatXDrawPos, boatYDrawPos);
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
        int rodYDrawPos = 75;
        for(int i=0; i<=rodUnlocked; i++){ //**IMPORTANT !!!** change to i<=rodUnlocked if rodUnlocked is 0-indexed
            canvas.drawImage(ROD_BACKGROUND, rodXDrawPos, rodYDrawPos);
            canvas.drawImage(PLAYER_RODS[playerIndexNum][i], rodXDrawPos, rodYDrawPos);
            if(i%2==0){
                rodXDrawPos += ROD_BACKGROUND.getWidth() + xSpacing;
            }else{
                rodXDrawPos -= (ROD_BACKGROUND.getWidth() + xSpacing)/2;
                rodYDrawPos += ROD_BACKGROUND.getHeight() + ySpacing;
            }
        }
        // Display new canvas image
        updateImage();

        //remove expText to not interfere with rest of summary screen
        removeObject(expText);
    }

    /**
     * Displays the fish summary page, organising the fish by rarity
     */
    private void displayFishSummary(){
        GreenfootImage canvas = getCanvas();
        canvas.drawImage(FISH_SUMMARY_BACKGROUND, 0, 0); //draw the background
        addObject(speedMultiplierSlider, 170, 46);
        renderPixelActors();
        updateImage();

        //sets variable default values to run the fish summary screen
        currentTier = 1;
        fishesToDisplay = new ArrayList(simWorld.getDiscoveredFishesOfTier(currentTier));
        fishesIndex = 0;
        actNumber = 0;
        fishFrequencyMultiplier = 1.0;
        FishRecord.setSpeedMultiplier(1.0);
    }

    private void addNextFishToWorld(){
        if(currentTier>4){
            return;
        }
        if(fishesIndex >= fishesToDisplay.size()){
            currentTier++;
            fishesToDisplay = new ArrayList(simWorld.getDiscoveredFishesOfTier(currentTier));
            fishesIndex = 0;
        }else{
            addObject(fishesToDisplay.get(fishesIndex), 0, getHeight()/2);
            fishesIndex++;
        }
    }

    private void render(){
        GreenfootImage canvas = getCanvas();
        canvas.drawImage(FISH_SUMMARY_BACKGROUND, 0, 0);

        renderPixelActors();

        updateImage();
    }
}
