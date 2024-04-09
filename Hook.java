import greenfoot.*;
import java.util.List;

/**
 * The hook at the end of the fishing line, latches onto fish.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public class Hook extends PixelActor {
    private FishingRod fishingRod;
    // Change this to true to start the reel-in process
    private boolean reelingIn;
    // TODO: Will be dependent on the hook tier
    public final IntPair fishBitePoint = new IntPair(1, 8);

    public Hook(FishingRod fishingRod) {
        super("hook_basic.png");
        this.fishingRod = fishingRod;
        setCenterOfRotation(7, 0);
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
        if (getY() > SimulationWorld.SEA_SURFACE_Y + fishingRod.getMaxDepth()) {
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
