import greenfoot.*;
import java.util.List;

/**
 * The hook at the end of the fishing line, latches onto fish.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public class Hook extends PixelActor {
    /**
     * Different tiers of hooks corresponding to tiers of fishing rods,
     * with certain attributes specific to each tier.
     *
     * @author Andrew Wang
     * @version April 2024
     */
    public static enum HookTier {
        WORM("hook_worm", new IntPair(1, 0)),
        BASIC("hook_basic", new IntPair(1, 1)),
        ADVANCED("hook_advanced", new IntPair(1, 1));
        
        public final String fileName;
        public final IntPair centerOfRotation;
        
        /**
         * @param fileName The name of the image file of the hook
         * @param centerOfRotation The center of rotation of the image of the hook
         */
        private HookTier(String fileName, IntPair centerOfRotation) {
            this.fileName = fileName;
            this.centerOfRotation = centerOfRotation;
        }
    }
    
    private FishingRod fishingRod;
    private HookTier hookTier;
    // Change this to true to start the reel-in process
    private boolean reelingIn;
    // TODO: Will be dependent on the hook tier
    public final IntPair fishBitePoint = new IntPair(1, 8);

    public Hook(FishingRod fishingRod) {
        super(fishingRod.getRodTier().hook.fileName + ".png");
        this.fishingRod = fishingRod;
        
        hookTier = fishingRod.getRodTier().hook;
        IntPair center = hookTier.centerOfRotation;
        setCenterOfRotation(center.x, center.y);
        
        reelingIn = false;
    }

    public void act() {
        if (reelingIn) {
            // Move back towards tip of rod if reeling in
            DoublePair rodTip = fishingRod.getTipPosition();
            setHeading(rodTip.x, rodTip.y);
            move(fishingRod.getReelInSpeed());
            // Remove hook and fishing line when it gets pulled above the surface
            if (getY() < SimulationWorld.SEA_SURFACE_Y - 10) {
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
     * Get the point at which fish will get hooked.
     * <p>When distance to fish is calculated, it uses this point and the fish's catch point.</p>
     *
     * @return A DoublePair representing the global position of the bite point
     */
    public DoublePair getBitePoint() {
        return getImageOffsetGlobalPosition(fishBitePoint.x, fishBitePoint.y);
    }
}
