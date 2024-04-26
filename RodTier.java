import greenfoot.*;

/**
 * Different tiers of fishing rods that the Fishers may own,
 * with certain attributes specific to each tier.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public enum RodTier {
    WOODEN(HookTier.WORM, 800, 0.4, 88, 3),
    BASIC(HookTier.BASIC, 600, 0.8, 112, 6),
    ADVANCED(HookTier.ADVANCED, 400, 1.2, 132, 12);

    /**
     * The prefix for the image files of the rod.
     */
    public final String imagePrefix;
    /**
     * The tier of the rod's hook represented by an entry in the {@link HookTier} enum.
     */
    public final HookTier hookTier;
    /**
     * The average frequency at which the fishing rod will cast the hook.
     */
    public final int castFrequency;
    /**
     * The speed at which the hook is reeled in.
     */
    public final double reelInSpeed;
    /**
     * The maximum y-coordinate the hook will reach before being reeled in.
     */
    public final int maxDepth;
    /**
     * The maximum number of hooks that can be cast at once.
     */
    public final int maxMulticast;

    private RodTier(HookTier hookTier, int castFrequency, double reelInSpeed, int maxDepth, int maxMulticast) {
        imagePrefix = "rods/" + name().toLowerCase() + "_";
        this.hookTier = hookTier;
        this.castFrequency = castFrequency;
        this.reelInSpeed = reelInSpeed;
        this.maxDepth = maxDepth;
        this.maxMulticast = maxMulticast;
    }

    /**
     * Get the RodTier one level above the current one.
     *
     * @return The next {@link RodTier}
     */
    public RodTier nextTier() {
        try {
            return RodTier.values()[ordinal() + 1];
        } catch (IndexOutOfBoundsException err) {
            return this;
        }
    }
}