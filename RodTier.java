import greenfoot.*;

/**
 * Different tiers of fishing rods that the Fishers may own,
 * with certain attributes specific to each tier.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public enum RodTier {
    WOODEN(HookTier.WORM, 800, 0.4, 88),
    BASIC(HookTier.BASIC, 600, 0.8, 112),
    ADVANCED(HookTier.ADVANCED, 400, 1.2, 138);

    public String imagePrefix;
    public HookTier hookTier;
    public int castFrequency;
    public double reelInSpeed;
    public int maxDepth;

    /**
     * @param hookTier The tier of the rod's hook represented by an entry in the HookTier enum
     * @param castFrequency The average frequency at which the fishing rod will cast the hook
     * @param reelInSpeed The speed at which the hook is reeled in
     * @param maxDepth The maximum y-coordinate the hook will reach before being reeled in
     */
    private RodTier(HookTier hookTier, int castFrequency, double reelInSpeed, int maxDepth) {
        imagePrefix = "rods/" + name().toLowerCase() + "_";
        this.hookTier = hookTier;
        this.castFrequency = castFrequency;
        this.reelInSpeed = reelInSpeed;
        this.maxDepth = maxDepth;
    }

    /**
     * Get the RodTier one level above the current one.
     *
     * @return The next RodTier
     */
    public RodTier nextTier() {
        try {
            return RodTier.values()[ordinal() + 1];
        } catch (IndexOutOfBoundsException err) {
            return this;
        }
    }
}