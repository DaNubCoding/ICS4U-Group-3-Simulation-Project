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
    private RodTier rodTier;
    private UIBar rodBar;

    private Timer castTimer;

    public FishingRod(Fisher fisher) {
        super(Layer.FISHING_ROD);
        this.fisher = fisher;
        setRodTier(RodTier.WOODEN);

        setCenterOfRotation(0, getOriginalImage().getHeight() - 1);
        if (fisher.getSide() == 2) {
            setMirrorX(true);
        }

        castTimer = new Timer((int) (rodTier.castFrequency * Util.randDouble(0.8, 1.2)));
        rodBar = new UIBar(30, 8, 800, "Gold.jpg");
    }

    @Override
    public void addedToWorld(World world) {
        if (fisher.getSide() == 1) {
            world.addObject(rodBar, 2, 12);
        } else {
            world.addObject(rodBar, world.getWidth() - 32, 12);
        }

        act();
    }

    public void act() {
        DoublePair newPos = fisher.getRodPosition();
        setLocation(newPos.x, newPos.y);
        setRotation(fisher.getRotation());

        // Check if the previous hook has been pulled back
        if (castTimer.ended() && hook == null) {
            cast();
            castTimer.restart(rodTier.castFrequency);
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
     * <p>Effectively gains the fish's XP value and removes the fish, hook, and fishing line.</p>
     */
    public void reelIn() {
        PixelWorld world = getWorld();
        Fish caughtFish = hook.getAttachedFish();
        if (caughtFish != null) {
            int fishValue = caughtFish.getValue();
            double percentage = Util.randDouble(0.3, 0.7);
            fisher.gainExp((int) Math.floor(fishValue * percentage));
            rodBar.gainExp((int) Math.ceil(fishValue * (1 - percentage)));
            world.removeObject(caughtFish);
        }
        world.removeObject(fishingLine);
        world.removeObject(hook);
        fishingLine = null;
        hook = null;
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
        return rodTier.maxDepth;
    }

    /**
     * Get the speed at which the fishing rod reels in fish.
     *
     * @return The speed at which the fish is reeled in
     */
    public double getReelInSpeed() {
        return rodTier.reelInSpeed;
    }

    /**
     * Get the tier of the rod.
     *
     * @return The tier of the rod represented by a RodTier enum entry
     */
    public RodTier getRodTier() {
        return rodTier;
    }

    /**
     * Set the rod's tier to a new tier, update image accordingly.
     *
     * @param rodTier The tier of the rod as a RodTier enum element
     */
    public void setRodTier(RodTier rodTier) {
        this.rodTier = rodTier;
        setImage(rodTier.imagePrefix + fisher.getSide() + ".png");
        setCenterOfRotation(0, getOriginalImage().getHeight() - 1);
    }

    /**
     * Get the UIBar object that represents the EXP of this fishing rod.
     *
     * @return The UIBar object
     */
    public UIBar getRodBar() {
        return rodBar;
    }
}
