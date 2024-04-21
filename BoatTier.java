import greenfoot.*;

/**
 * Different tiers of boats that the Fishers may own,
 * with certain attributes specific to each tier.
 *
 * @author Martin Baldwin
 * @author Andrew Wang
 * @version April 2024
 */
public enum BoatTier {
    WOODEN(new IntPair(30, 16), 1.3, new IntPair(19, 22)),
    STEEL(new IntPair(32, 11), 0.5, new IntPair(20, 17)),
    YACHT(new IntPair(38, 17), 0.1, new IntPair(21, 25));

    public final String imagePrefix;
    public final IntPair rodOffset;
    public final double driftMagnitudeFactor;
    public final IntPair centerOfRotation;

    /**
     * @param rodOffset The offset of the rod relative to the boat's image
     * @param driftMagnitudeFactor The factor to multiply any drift-related movement by
     * @param centerOfRotation The center of rotation of the boat relative to the boat's image
     */
    private BoatTier(IntPair rodOffset, double driftMagnitudeFactor, IntPair centerOfRotation) {
        imagePrefix = "boats/" + name().toLowerCase() + "_";
        this.rodOffset = rodOffset;
        this.driftMagnitudeFactor = driftMagnitudeFactor;
        this.centerOfRotation = centerOfRotation;
    }

    /**
     * Get the BoatTier one level above the current one.
     *
     * @return The next BoatTier
     */
    public BoatTier nextTier() {
        try {
            return BoatTier.values()[ordinal() + 1];
        } catch (IndexOutOfBoundsException err) {
            return this;
        }
    }
}