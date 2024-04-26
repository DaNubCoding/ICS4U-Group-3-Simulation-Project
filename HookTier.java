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

    /**
     * The image of the hook.
     */
    public final GreenfootImage image;
    /**
     * The center of rotation of the hook.
     */
    public final IntPair centerOfRotation;
    /**
     * The point where the fish bites the hook.
     */
    public final IntPair fishBitePoint;

    private HookTier(IntPair centerOfRotation, IntPair fishBitePoint) {
        image = new GreenfootImage("hooks/" + name().toLowerCase() + ".png");
        this.centerOfRotation = centerOfRotation;
        this.fishBitePoint = fishBitePoint;
    }
}