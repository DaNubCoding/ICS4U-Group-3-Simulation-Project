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
    private Set<FishRecord>[] discoveredFishesByTier;
    // For each type of fish feature, a list of Fish currently in this world with that feature
    private Map<FishFeature, List<Fish>> fishesByFeature;

    private Fisher fisher1;
    private Fisher fisher2;

    // Test text object: draw the current act count in the top right corner of the world
    private Text actText;

    // GifImage for waves
    private GifPixelActor backgroundWaves = new GifPixelActor(new GifImage("wavesanim.gif"));
    private GifPixelActor foregroundWaves = new GifPixelActor(new GifImage("wavesanim.gif"));

    public SimulationWorld() {
        super(250, 160);

        setRenderOrder(Egg.class, Bubble.class, FishingRod.class, Fisher.class, Fish.class, FishingLine.class, Hook.class, Text.class);

        // Initialize fish record keeping structures
        discoveredFishesByTier = new Set[FishSettings.MAX_TIER];
        for (int i = 0; i < discoveredFishesByTier.length; i++) {
            discoveredFishesByTier[i] = new TreeSet<FishRecord>();
        }
        // Initialize fish retrieval structures
        fishesByFeature = new EnumMap<FishFeature, List<Fish>>(FishFeature.class);
        for (FishFeature feature : FishFeature.values()) {
            fishesByFeature.put(feature, new ArrayList<Fish>());
        }

        fisher1 = new Fisher(1);
        fisher2 = new Fisher(2);
        addObject(fisher1, 50, 31);
        addObject(fisher2, 200, 31);

        addObject(new Bass(0), Util.randInt(0, getWidth()), Util.randInt(SEA_SURFACE_Y, SEA_FLOOR_Y));
        addObject(new Salmon(0), Util.randInt(0, getWidth()), Util.randInt(SEA_SURFACE_Y, SEA_FLOOR_Y));
        addObject(new Tuna(0), Util.randInt(0, getWidth()), Util.randInt(SEA_SURFACE_Y, SEA_FLOOR_Y));

        backgroundWaves.setLocation(125, SEA_SURFACE_Y - 5);
        foregroundWaves.setLocation(125, SEA_SURFACE_Y - 5);

        // TODO: remove this
        actText = new Text(Timer.getCurrentAct(), Text.AnchorX.RIGHT, Text.AnchorY.TOP) {
            @Override
            public void act() {
                setContent(Timer.getCurrentAct());
            }
        };
        addObject(actText, getWidth() - 4, 4);

        render();
    }

    @Override
    public void act() {
        render();
        Timer.incrementAct();

        // Temporary test
        if (Greenfoot.isKeyDown("e")) {
            Greenfoot.setWorld(new SummaryWorld(this));
        }
    }

    /**
     * Updates the display image of this world.
     */
    private void render() {
        GreenfootImage canvas = getCanvas();
        // Draw the background
        canvas.drawImage(background, 0, 0);

        backgroundWaves.updateImage();
        backgroundWaves.render(getCanvas());
        // Draw actors
        renderPixelActors();
        foregroundWaves.updateImage();
        foregroundWaves.setTransparency(100);
        foregroundWaves.render(getCanvas());

        // Draw water gradient on top of underwater actors
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
            discoveredFishesByTier[fish.getSettings().getTier() - 1].add(new FishRecord(fish));
        }
    }

    /**
     * Removes an Actor from this world, and if it is a Fish, updates this
     * world's lists of existing fish.
     *
     * @param object the object to remove
     * @see World#removeObject
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
        return new TreeSet<FishRecord>(discoveredFishesByTier[tier - 1]);
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
            return fisher1;
        case 2:
            return fisher2;
        default:
            throw new IllegalArgumentException("Player side value must be 1 or 2");
        }
    }
}
