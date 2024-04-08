import greenfoot.*;

/**
 * The fishing rod, the thing that is upgraded.
 *
 * @author Andrew Wang
 * @author Martin Baldwin
 * @version April 2024
 */
public class FishingRod extends PixelActor {
    private Fisher fisher;
    private FishingLine fishingLine;
    private Hook hook;

    private Timer castTimer;
    private int maxDepth;
    private double reelInSpeed;

    public FishingRod(Fisher fisher) {
        super("fishing_rod.png");
        this.fisher = fisher;

        setCenterOfRotation(0, getOriginalImage().getHeight() - 1);
        if (fisher.getSide() == 2) {
            setMirrorX(true);
        }

        maxDepth = SimulationWorld.SEA_FLOOR_Y - SimulationWorld.SEA_SURFACE_Y - 50;
        reelInSpeed = 1.0;
        castTimer = new Timer(720);
    }

    /**
     * Act when added to world, so that it shows up in the correct location.
     */
    @Override
    public void addedToWorld(World world) {
        act();
    }

    public void act() {
        DoublePair newPos = fisher.getRodPosition();
        setLocation(newPos.x, newPos.y);
        setRotation(fisher.getRotation());

        if (castTimer.ended()) {
            cast();
            castTimer.restart();
        }
    }

    /**
     * Cast the fishing rod.
     */
    public void cast() {
        hook = new Hook(this);
        fishingLine = new FishingLine(this, hook);

        // Spawn the hook at the tip of the fishing rod
        DoublePair hookPos = getTipPosition();

        PixelWorld world = getWorld();
        world.addObject(hook, (int) hookPos.x, (int) hookPos.y);
        world.addObject(fishingLine, 0, 0);
    }

    /**
     * Reel in the fishing rod when the hook returns to the surface.
     * <p>Effectively removes the hook and fishing line.</p>
     */
    public void reelIn() {
        PixelWorld world = getWorld();
        world.removeObject(fishingLine);
        world.removeObject(hook);
    }

    /**
     * Get the coordinates of the tip of the rod relative to the world.
     *
     * @return The global x and y coordinates of the tip as a pair of doubles
     */
    public DoublePair getTipPosition() {
        return getImageOffsetGlobalPosition(getOriginalImage().getWidth() - 1, 0);
    }

    /**
     * Get the maximum depth beneath the surface of the water this
     * fishing rod can reach.
     *
     * @return The maximum depth
     */
    public int getMaxDepth() {
        return maxDepth;
    }

    /**
     * Get the speed at which the fishing rod reels in fish.
     *
     * @return The speed at which the fish is reeled in
     */
    public double getReelInSpeed() {
        return reelInSpeed;
    }
}
