import greenfoot.*;
import java.util.Set;
import java.util.HashSet;

/**
 * The world were the magic happens...
 *
 * @author Martin Baldwin
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
    private Set<FishRecord>[] discoveredFishByTier;

    private Fisher fisher1;
    private Fisher fisher2;

    // Test text object: draw the current act count in the top right corner of the world
    private Text actText;

    public SimulationWorld() {
        super(250, 160);

        setRenderOrder(Egg.class, Bubble.class, FishingRod.class, Fisher.class, Fish.class, FishingLine.class, Hook.class, Text.class);

        discoveredFishByTier = new Set[FishSettings.MAX_TIER];
        for (int i = 0; i < discoveredFishByTier.length; i++) {
            discoveredFishByTier[i] = new HashSet<FishRecord>();
        }

        fisher1 = new Fisher(1);
        fisher2 = new Fisher(2);
        addObject(fisher1, 50, 36);
        addObject(fisher2, 200, 36);

        addObject(new Bass(0), Util.randInt(0, getWidth()), Util.randInt(SEA_SURFACE_Y, SEA_FLOOR_Y));
        addObject(new Salmon(0), Util.randInt(0, getWidth()), Util.randInt(SEA_SURFACE_Y, SEA_FLOOR_Y));
        addObject(new Tuna(0), Util.randInt(0, getWidth()), Util.randInt(SEA_SURFACE_Y, SEA_FLOOR_Y));

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
        // Draw actors
        renderPixelActors();
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
            // Discover this type of fish
            Fish fish = (Fish) object;
            discoveredFishByTier[fish.getSettings().getTier() - 1].add(new FishRecord(fish));
        }
    }

    /**
     * Returns a set of FishRecords describing all types of discovered fish at
     * the specified tier.
     *
     * @param tier the tier value to retrieve discovered fish from, from 1 to {@link FishSettings#MAX_TIER}
     * @return a new set of FishRecord objects describing discovered fish of the given tier
     */
    public Set<FishRecord> getDiscoveredFishOfTier(int tier) {
        return new HashSet<FishRecord>(discoveredFishByTier[tier - 1]);
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
