import greenfoot.*;

/**
 * The hook at the end of the fishing line, latches onto fish.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public class Hook extends PixelActor {
    private FishingRod fishingRod;
    private boolean reachedMaxDepth;

    public Hook(FishingRod fishingRod) {
        super("hook_basic.png");
        this.fishingRod = fishingRod;
        setCenterOfRotation(7, 0);
        reachedMaxDepth = false;
    }

    public void act() {
        if (reachedMaxDepth) {
            DoublePair rodTip = fishingRod.getTipPosition();
            setHeading(rodTip.x, rodTip.y);
            move(fishingRod.getReelInSpeed());
            // Remove hook and fishing line when it gets pulled above the surface
            if (getY() < SimulationWorld.SEA_SURFACE_Y - 10) {
                fishingRod.reelIn();
            }
        } else {
            setLocation(getDoubleX(), getDoubleY() + 0.4);
        }

        if (getY() > SimulationWorld.SEA_SURFACE_Y + fishingRod.getMaxDepth()) {
            reachedMaxDepth = true;
        }
    }
}
