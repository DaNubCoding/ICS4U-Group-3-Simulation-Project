import greenfoot.*;

/**
 * The hook at the end of the fishing line, latches onto fish.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public class Hook extends PixelActor {
    private FishingRod fishingRod;
    private HookTier hookTier;
    // Change this to true to start the reel-in process
    private boolean reelingIn;
    private Fish attachedFish;

    public Hook(FishingRod fishingRod) {
        super(fishingRod.getRodTier().hookTier.image, Layer.HOOK);
        this.fishingRod = fishingRod;

        hookTier = fishingRod.getRodTier().hookTier;
        IntPair center = hookTier.centerOfRotation;
        setCenterOfRotation(center.x, center.y);
        setMirrorX(!fishingRod.getMirrorX());

        reelingIn = false;
        attachedFish = null;
    }

    public void act() {
        if (reelingIn) {
            // Move back towards tip of rod if reeling in
            DoublePair rodTip = fishingRod.getTipPosition();
            setHeading(rodTip.x, rodTip.y);
            move(fishingRod.getReelInSpeed());
            // Remove hook and fishing line when it gets pulled above the surface
            if (getDoubleY() <= rodTip.y + 5) {
                fishingRod.reelIn();
                return;
            }
        } else {
            setLocation(getDoubleX(), getDoubleY() + 0.4);
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
}
