import greenfoot.*;

/**
 * The hook at the end of the fishing line, latches onto fish.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public class Hook extends PixelActor {
    private FishingRod fishingRod;
    private FishingLine fishingLine;
    private HookTier hookTier;
    // Change this to true to start the reel-in process
    private boolean reelingIn;
    private Fish attachedFish;
    private double horizontalSpeed;
    private double localSpeedMultiplier;

    /**
     * Initialize the hook given fishing rod and fishing line.
     *
     * @param fishingRod The fishing rod the hook belongs to
     * @param fishingLine The fishing line the hook is attached to
     * @param dispersion The amount to disperse the hook's horizontal speed, used for casting multiple hooks
     */
    public Hook(FishingRod fishingRod, FishingLine fishingLine, double dispersion) {
        super(fishingRod.getRodTier().hookTier.image, Layer.HOOK);
        this.fishingRod = fishingRod;
        this.fishingLine = fishingLine;

        hookTier = fishingRod.getRodTier().hookTier;
        IntPair center = hookTier.centerOfRotation;
        setCenterOfRotation(center.x, center.y);
        setMirrorX(!fishingRod.getMirrorX());
        horizontalSpeed = (Util.randInt(0, 1) * 2 - 1) * Util.randDouble(0.0, 0.3) * dispersion;
        localSpeedMultiplier = Util.randDouble(0.8, 1.2);

        reelingIn = false;
        attachedFish = null;
    }

    public void act() {
        UserSettings userSettings = ((SimulationWorld) getWorld()).getUserSettings();
        double speedMultiplier = userSettings.getHookSpeedMultiplier(fishingRod.getFisher().getSide());

        // A bit of horizontal velocity
        setLocation(getDoubleX() + horizontalSpeed, getDoubleY());
        horizontalSpeed *= 0.98;

        if (reelingIn) {
            // Move back towards tip of rod if reeling in
            DoublePair rodTip = fishingRod.getTipPosition();
            setHeading(rodTip.x, rodTip.y);
            move(fishingRod.getReelInSpeed() * speedMultiplier);
            // Remove hook and fishing line when it gets pulled above the surface
            if (getDoubleY() <= rodTip.y + 5) {
                fishingRod.reelIn(this);
                return;
            }
        } else {
            setLocation(getDoubleX(), getDoubleY() + 0.4 * speedMultiplier * localSpeedMultiplier);
        }

        // Reel in if reached max depth
        if (getY() > fishingRod.getMaxDepth()) {
            reelIn();
        }
    }

    /**
     * Begin the reel-in process.
     */
    public void reelIn() {
        reelingIn = true;
    }

    /**
     * Occupy this hook with the given fish and begin the reel-in process.
     */
    public void reelIn(Fish fish) {
        attachedFish = fish;
        reelIn();
    }

    /**
     * Get the point at which fish will get hooked.
     * <p>When distance to fish is calculated, it uses this point and the fish's catch point.</p>
     *
     * @return A DoublePair representing the global position of the bite point
     */
    public DoublePair getBitePoint() {
        IntPair fishBitePoint = hookTier.fishBitePoint;
        return getImageOffsetGlobalPosition(fishBitePoint.x, fishBitePoint.y);
    }

    /**
     * Get whether the hook is already occupied by a fish and cannot hook
     * another one.
     *
     * @return True if already hooking a fish, false otherwise
     */
    public boolean isOccupied() {
        return attachedFish != null;
    }

    /**
     * Get the fish currently on this hook.
     *
     * @return the fish object that is hooked by this hook, or null if there is none
     */
    public Fish getAttachedFish() {
        return attachedFish;
    }

    /**
     * Get the tier of the hook.
     *
     * @return The {@link HookTier} of the hook
     */
    public HookTier getTier() {
        return hookTier;
    }

    /**
     * Get the fishing line attached to this hook.
     *
     * @return The fishing line attached to this hook
     */
    public FishingLine getFishingLine() {
        return fishingLine;
    }
}
