import greenfoot.*;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map;
import java.util.EnumMap;
import java.util.List;
import java.util.ArrayList;

/**
 * The world were the magic happens...
 *
 * @author Martin Baldwin
 * @author Brandon Law
 * @author Matthew Li
 * @version April 2024
 */
public class SimulationWorld extends PixelWorld {
    private static final GreenfootImage background = new GreenfootImage("background.png");
    private static final GreenfootImage foreground = new GreenfootImage("foreground.png");

    /** The y coordinate of the surface of the water, in canvas pixels, relative to the top of this world. */
    public static final int SEA_SURFACE_Y = 37;
    /** The y coordinate of the sea floor, in canvas pixels, relative to the top of this world. */
    public static final int SEA_FLOOR_Y = 140;

    // For each fish tier, a set of FishRecords describing all fish at that tier that appeared in this world
    private List<Set<FishRecord>> discoveredFishesByTier;
    // For each type of fish feature, a list of Fish currently in this world with that feature
    private Map<FishFeature, List<Fish>> fishesByFeature;

    private Fisher leftFisher;
    private Fisher rightFisher;

    private EndState endState;

    private UserSettings userSettings;

    // Test text object: draw the current act count in the top right corner of the world
    private Text actText;

    // GifImage for waves
    private GifPixelActor waves = new GifPixelActor(new GifImage("wavesanim.gif"), Layer.FOREGROUND);

    public SimulationWorld(UserSettings userSettings) {
        super(250, 160);
        this.userSettings = userSettings;

        // Initialize fish record keeping structures
        discoveredFishesByTier = new ArrayList<Set<FishRecord>>();
        for (int i = 0; i < FishSettings.MAX_TIER; i++) {
            discoveredFishesByTier.add(new TreeSet<FishRecord>());
        }
        // Initialize fish retrieval structures
        fishesByFeature = new EnumMap<FishFeature, List<Fish>>(FishFeature.class);
        for (FishFeature feature : FishFeature.values()) {
            fishesByFeature.put(feature, new ArrayList<Fish>());
        }

        leftFisher = new LeftFisher();
        rightFisher = new RightFisher();
        addObject(leftFisher, 50, 31);
        addObject(rightFisher, 200, 31);

        for (int i = 0; i < userSettings.getBassCount(); i++) {
            addObject(new Bass(0, null), Util.randInt(0, getWidth()), Util.randInt(SEA_SURFACE_Y, SEA_FLOOR_Y));
        }
        for (int i = 0; i < userSettings.getSalmonCount(); i++) {
            addObject(new Salmon(0, null), Util.randInt(0, getWidth()), Util.randInt(SEA_SURFACE_Y, SEA_FLOOR_Y));
        }
        for (int i = 0; i < userSettings.getTunaCount(); i++) {
            addObject(new Tuna(0, null), Util.randInt(0, getWidth()), Util.randInt(SEA_SURFACE_Y, SEA_FLOOR_Y));
        }

        waves.setLocation(125, SEA_SURFACE_Y - 5);

        actText = new Text(Timer.getCurrentAct(), Text.AnchorX.CENTER, Text.AnchorY.TOP) {
            @Override
            public void act() {
                setContent(Timer.getCurrentAct());
            }
        };
        addObject(actText, getWidth() / 2, 4);

        triggerFadeIn(0.004);

        render();
    }

    @Override
    public void act() {
        render();
        Timer.incrementAct();

        // If there are no more fish or eggs in the world, end the simulation
        if (getObjects(Fish.class).size() + getObjects(Egg.class).size() == 0) {
            triggerFadeOut(0.004);
            endState = EndState.EXTINCTION;
        }

        // Temporary test
        if (Greenfoot.isKeyDown("e")) {
            triggerFadeOut(0.02);
            endState = EndState.TEST;
        }

        if (isFadeOutComplete()) {
            Greenfoot.setWorld(new EndWorld(this, endState));
        }
    }

    /**
     * Updates the display image of this world.
     */
    private void render() {
        GreenfootImage canvas = getCanvas();
        // Draw the background
        canvas.drawImage(background, 0, 0);

        waves.updateImage();
        waves.setTransparency(255);
        waves.render(canvas);

        renderPixelActors();

        waves.setTransparency(100);
        waves.render(canvas);

        canvas.drawImage(foreground, 0, 0);

        // Display new canvas image
        updateImage();
    }

    /**
     * Adds an Actor to this world, and if it is a Fish, records that it has
     * been discovered.
     *
     * @param object the object to add
     * @param x the x coordinate of the location where the object is added
     * @param y the y coordinate of the location where the object is added
     * @see World#addObject
     * @see PixelWorld#addObject
     */
    @Override
    public void addObject(Actor object, int x, int y) {
        super.addObject(object, x, y);

        if (object instanceof Fish) {
            Fish fish = (Fish) object;
            // Store this fish by its features for retrieval by other fish
            for (FishFeature feature : fish.getFeatureSet()) {
                fishesByFeature.get(feature).add(fish);
            }
            // Discover this type of fish
            discoveredFishesByTier.get(fish.getSettings().getTier() - 1).add(new FishRecord(fish));
        }
    }

    /**
     * Removes an Actor from this world, and if it is a Fish, updates this
     * world's lists of existing fish.
     *
     * @param object the object to remove
     * @see World#removeObject
     * @see PixelWorld#removeObject
     */
    @Override
    public void removeObject(Actor object) {
        super.removeObject(object);

        if (object instanceof Fish) {
            // Remove this fish from all of this world's lists
            Fish fish = (Fish) object;
            for (FishFeature feature : fish.getFeatureSet()) {
                fishesByFeature.get(feature).remove(fish);
            }
        }
    }

    /**
     * Returns a set of FishRecords describing all types of discovered fish at
     * the specified tier.
     *
     * @param tier the tier value to retrieve discovered fishes from, from 1 to {@link FishSettings#MAX_TIER}
     * @return a new set of FishRecord objects describing discovered fishes of the given tier
     */
    public Set<FishRecord> getDiscoveredFishesOfTier(int tier) {
        return new TreeSet<FishRecord>(discoveredFishesByTier.get(tier - 1));
    }

    /**
     * Returns a list of all fishes currently in this world with the given
     * feature.
     *
     * @param feature the FishFeature on fish to retrieve
     * @return a new list of all Fish in this world with the given feature
     */
    public List<Fish> getFishesByFeature(FishFeature feature) {
        return new ArrayList<Fish>(fishesByFeature.get(feature));
    }

    /**
     * Gets the fisher actor on the given side.
     *
     * @param side either 1 or 2, corresponding to the left and right fishers, respectively
     * @return the fisher actor in this world with the matching side value
     */
    public Fisher getFisher(int side) {
        switch (side) {
        case 1:
            return leftFisher;
        case 2:
            return rightFisher;
        default:
            throw new IllegalArgumentException("Player side value must be 1 or 2");
        }
    }

    /**
     * Get the UserSettings that contains all the user settings used in the
     * SimulationWorld.
     *
     * @return The UserSettings object of this SimulationWorld
     */
    public UserSettings getUserSettings() {
        return userSettings;
    }
}
