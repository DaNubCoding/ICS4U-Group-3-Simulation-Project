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

        castTimer = new Timer(100);
        rodBar = new UIBar(30, 8, 800, "ui_bar_gold.jpg");
    }

    @Override
    public void addedToWorld(World world) {
        world.addObject(rodBar, fisher.getBarX(), 12);
        if (fisher.getMirrorX()) {
            setMirrorX(true);
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
            castTimer.restart(getNextCastDelay());
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
     * Get the number of acts before the next time the rod is casted.
     *
     * @return The number of acts before next cast
     */
    private int getNextCastDelay() {
        UserSettings userSettings = ((SimulationWorld) getWorld()).getUserSettings();
        double multiplier = userSettings.getRodDelayMultiplier(fisher.getSide());
        return (int) (rodTier.castFrequency * Util.randDouble(0.8, 1.2) * multiplier);
    }

    /**
     * Reel in the fishing rod when the hook returns to the surface.
     * <p>Effectively gains the fish's XP value and removes the fish, hook, and fishing line.</p>
     */
    public void reelIn() {
        PixelWorld world = getWorld();
        Fish caughtFish = hook.getAttachedFish();
        if (caughtFish != null) {
            fisher.gainExp(caughtFish.getValue());
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

    /**
     * Get the Fisher that this FishingRod belongs to.
     *
     * @return The Fisher this FishingRod belongs to
     */
    public Fisher getFisher() {
        return fisher;
    }
}
