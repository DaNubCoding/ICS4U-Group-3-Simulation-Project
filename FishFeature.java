import greenfoot.*;

/**
 * All features that may exist on fish. Each feature has attributes for:
 * <ul>
 * <li>a probability of a fish gaining the feature
 * <li>the amount of XP the feature adds to a fish when caught
 * </ul>
 * <p>
 * Note that the image used for each feature is found in the features image
 * subdirectory using the feature's name. For example, a feature named {@code HORN}
 * would attempt to load an image with the path {@code features/horn.png}.
 * <p>
 * Features in this enum should be defined in the desired order of rendering,
 * with later-defined features being drawn on top of earlier-defined features.
 * (The rendering order is a result of the Fish class using an {@link java.util.EnumSet} internally).
 *
 * @author Martin Baldwin
 * @author Sandra Huang
 * @version April 2024
 */
public enum FishFeature {
    BIG_EYE(0.1, 10),
    ANGLER_BOMB(0.1, 15),
    ANGLER_SOCK(0.2, 15),
    HAT_BROWN(0.1, 5),
    HAT_PARTY(0.1, 5);

    private final GreenfootImage image;
    private final double chance;
    private final int value;

    private FishFeature(double chance, int value) {
        image = new GreenfootImage("features/" + name().toLowerCase() + ".png");
        this.chance = chance;
        this.value = value;
    }

    /**
     * Returns the image of this FishFeature.
     *
     * @return the GreenfootImage for the appropriate type of feature
     */
    public GreenfootImage getImage() {
        return image;
    }

    /**
     * Returns the probability of a Fish gaining this FishFeature.
     *
     * @return the probability of gaining this feature, as a double, 0.0 indicating 0% and 1.0 indicating 100%
     */
    public double getChance() {
        return chance;
    }

    /**
     * Returns the XP value of this FishFeature.
     *
     * @return the amount of XP this feature is worth
     */
    public int getValue() {
        return value;
    }
}
