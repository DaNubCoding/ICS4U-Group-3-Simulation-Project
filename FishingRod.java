import greenfoot.*;

/**
 * The fishing rod, the thing that is upgraded.
 *
 * @author Andrew Wang
 * @author Martin Baldwin
 * @version April 2024
 */
public class FishingRod extends PixelActor {
    /**
     * Different tiers of fishing rods that the Fishers may own,
     * with certain attributes specific to each tier.
     *
     * @author Andrew Wang
     * @version April 2024
     */
    public enum RodTier {
        WOODEN("rod_wooden_", Hook.HookTier.WORM, 800, 0.4, 88),
        BASIC("rod_basic_", Hook.HookTier.BASIC, 600, 0.8, 112),
        ADVANCED("rod_advanced_", Hook.HookTier.ADVANCED, 400, 1.2, 138);

        public String imagePrefix;
        public Hook.HookTier hook;
        public int castFrequency;
        public double reelInSpeed;
        public int maxDepth;

        /**
         * @param imagePrefix The file name prefix of the fishing rod's image
         * @param hook The tier of the rod's hook represented by an entry in the HookTier enum
         * @param castFrequency The average frequency at which the fishing rod will cast the hook
         * @param reelInSpeed The speed at which the hook is reeled in
         * @param maxDepth The maximum y-coordinate the hook will reach before being reeled in
         */
        private RodTier(String imagePrefix, Hook.HookTier hook, int castFrequency, double reelInSpeed, int maxDepth) {
            this.imagePrefix = imagePrefix;
            this.hook = hook;
            this.castFrequency = castFrequency;
            this.reelInSpeed = reelInSpeed;
            this.maxDepth = maxDepth;
        }
    }

    private Fisher fisher;
    private FishingLine fishingLine;
    private Hook hook;
    private RodTier rodTier;

    private Timer castTimer;

    public FishingRod(Fisher fisher) {
        super(RodTier.WOODEN.imagePrefix + fisher.getSide() + ".png");
        this.fisher = fisher;
        rodTier = RodTier.WOODEN;

        setCenterOfRotation(0, getOriginalImage().getHeight() - 1);
        if (fisher.getSide() == 2) {
            setMirrorX(true);
        }

        castTimer = new Timer((int) (rodTier.castFrequency * Util.randDouble(0.8, 1.2)));
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
     * <p>Effectively removes the hook and fishing line.</p>
     */
    public void reelIn() {
        PixelWorld world = getWorld();
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
    private void setRodTier(RodTier rodTier) {
        this.rodTier = rodTier;
        setImage(rodTier.imagePrefix + fisher.getSide() + ".png");
        setCenterOfRotation(0, getOriginalImage().getHeight() - 1);
    }

    /**
     * Increase the tier of the rod.
     */
    public void increaseRodTier() {
        switch (rodTier) {
            case WOODEN:
                setRodTier(RodTier.BASIC);
                break;
            case BASIC:
                setRodTier(RodTier.ADVANCED);
                break;
        }
    }
}
