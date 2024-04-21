import greenfoot.*;

/**
 * Different tiers of hooks corresponding to tiers of fishing rods,
 * with certain attributes specific to each tier.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public enum HookTier {
    WORM(new IntPair(1, 0), new IntPair(0, 3)),
    BASIC(new IntPair(1, 1), new IntPair(2, 4)),
    ADVANCED(new IntPair(1, 1), new IntPair(5, 11));

    public final GreenfootImage image;
    public final IntPair centerOfRotation;
    public final IntPair fishBitePoint;

    /**
     * @param fileName The name of the image file of the hook
     * @param centerOfRotation The center of rotation of the image of the hook
     */
    private HookTier(IntPair centerOfRotation, IntPair fishBitePoint) {
        image = new GreenfootImage("hooks/" + name().toLowerCase() + ".png");
        this.centerOfRotation = centerOfRotation;
        this.fishBitePoint = fishBitePoint;
    }
}