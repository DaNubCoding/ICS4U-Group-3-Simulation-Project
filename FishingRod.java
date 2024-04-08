import greenfoot.*;

/**
 * The fishing rod, the thing that is upgraded.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public class FishingRod extends PixelActor {
    private Fisher fisher;
    private FishingLine fishingLine;
    private Hook hook;
    // Position relative to center of rotation of the boat
    private int relativePosX;
    private int relativePosY;
    // Offset of the tip of the rod from the center of rotation
    private int tipOffsetX;
    private int tipOffsetY;
    private Timer castTimer;
    private int maxDepth;
    private double reelInSpeed;

    public FishingRod(Fisher fisher) {
        super("fishing_rod.png");
        this.fisher = fisher;

        if (fisher.getSide() == 1) {
            // Left fisher
            setCenterOfRotation(0, 9);
            relativePosX = 14;
            relativePosY = -6;
            tipOffsetX = 9;
            tipOffsetY = -9;
        } else {
            // Right fisher
            setCenterOfRotation(9, 9);
            setMirrorX(true);
            relativePosX = -15;
            relativePosY = -5;
            tipOffsetX = 9;
            tipOffsetY = -9;
        }

        maxDepth = SimulationWorld.seafloorY - SimulationWorld.seaSurfaceY - 50;
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
        DoublePair newPos = fisher.getRelativeOffset(relativePosX, relativePosY);
        setLocation(fisher.getDoubleX() + newPos.x, fisher.getDoubleY() + newPos.y);
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

        // Find the location of the tip of the fishing rod
        // and spawn the hook there
        DoublePair hookPos;
        if (fisher.getSide() == 1) {
            hookPos = fishingLine.getRelativeOffset(10, -10);
        } else {
            hookPos = fishingLine.getRelativeOffset(-10, -10);
        }
        hookPos = new DoublePair(hookPos.x + getDoubleX(), hookPos.y + getDoubleY());

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
     * Get the offset of the tip of the rod from the center of rotation.
     *
     * @return The x and y offset of the tip as a two-element array
     */
    public DoublePair getTipOffset() {
        return getRelativeOffset(tipOffsetX, tipOffsetY);
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
