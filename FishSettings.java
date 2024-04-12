import greenfoot.*;
import java.util.Map;

/**
 * A class to contain all Fish subclass-specific settings.
 * <p>
 * This was created to vastly improve the cleanliness of the Fish superclass and
 * its subclasses.
 * <p>
 * For maintainers: when adding a new field, a new line in {@link @validate}
 * must be added in addition to its corresponding getter and setter.
 *
 * @author Martin Baldwin
 * @version April 2024
 */
public class FishSettings {
    // Amount of XP this Fish type is worth without any features
    private Integer baseValue = null;
    // Base image of this Fish type without any features
    private GreenfootImage bodyImage = null;
    // Point relative to body image where this Fish type may be caught from
    private IntPair catchOffset = null;
    // The average swimming speed of the Fish
    private Double swimSpeed = null;
    // The minimum and maximum depth the fish can go to relative to the background image
    private Integer minDepth = null;
    private Integer maxDepth = null;
    // The average number of frames before the fish rotates to a new direction
    private Integer averageTurnInterval = null;
    // The maximum degrees the fish can tilt
    private Integer maxTurnDegrees = null;
    // The size of the egg that the fish will spawn
    private Egg.EggSize eggSize = null;
    // The color of the egg that the fish will spawn
    private Egg.EggColor eggColor = null;
    // The frequency at which the Fish spawns eggs
    private Integer eggSpawnFrequency = null;

    // Image offsets for all types of features
    // Each IntPair defines the x and y offsets of the top left corner of each
    // feature's image from the top left corner of the body image
    private Map<FishFeature, IntPair> featurePoints = null;

    private static void assertNonNull(Object obj, String name) {
        if (obj == null) {
            throw new NullPointerException("FishSettings " + name + " was never initialized");
        }
    }

    /**
     * Throw a {@link NullPointerException} if any of this FishSettings object's
     * settings are {@code null}.
     */
    public void validate() {
        assertNonNull(baseValue, "base value");
        assertNonNull(bodyImage, "body image");
        assertNonNull(catchOffset, "catch offset");
        assertNonNull(swimSpeed, "swim speed");
        assertNonNull(minDepth, "min depth");
        assertNonNull(maxDepth, "max depth");
        assertNonNull(averageTurnInterval, "average turn interval");
        assertNonNull(maxTurnDegrees, "max turn degrees");
        assertNonNull(eggSize, "egg size");
        assertNonNull(eggColor, "egg color");
        assertNonNull(eggSpawnFrequency, "egg spawn frequency");
        assertNonNull(featurePoints, "feature point map");
    }

    /**
     * Sets the base XP value.
     *
     * @param value the amount of XP this Fish type is worth without any features
     */
    public void setBaseValue(int value) {
        baseValue = value;
    }

    /**
     * Sets the base body image.
     *
     * @param image the base image of this Fish type without any features
     */
    public void setBodyImage(GreenfootImage image) {
        bodyImage = image;
    }

    /**
     * Sets the catch offset.
     *
     * @param offset the point relative to body image where this Fish type may be caught from
     */
    public void setCatchOffset(IntPair offset) {
        catchOffset = offset;
    }

    /**
     * Sets the swimming speed.
     *
     * @param speed the average swimming speed of the Fish
     */
    public void setSwimSpeed(double speed) {
        swimSpeed = speed;
    }

    /**
     * Sets the minimum depth.
     *
     * @param depth the minimum depth the fish can go to relative to the background image
     */
    public void setMinDepth(int depth) {
        minDepth = depth;
    }

    /**
     * Sets the maximum depth.
     *
     * @param depth the maximum depth the fish can go to relative to the background image
     */
    public void setMaxDepth(int depth) {
        maxDepth = depth;
    }

    /**
     * Sets the average turn interval.
     *
     * @param interval the average number of frames before the fish rotates to a new direction
     */
    public void setAverageTurnInterval(int interval) {
        averageTurnInterval = interval;
    }

    /**
     * Sets the maximum turn angle.
     *
     * @param degrees the maximum number of degrees up or down the fish can tilt
     */
    public void setMaxTurnDegrees(int degrees) {
        maxTurnDegrees = degrees;
    }

    /**
     * Sets the size of the egg that is spawned by the fish.
     * 
     * @param size The size of the egg spawned by the fish
     */
    public void setEggSize(Egg.EggSize size) {
        eggSize = size;
    }

    /**
     * Sets the color of the egg that is spawned by the fish.
     * 
     * @param size The color of the egg spawned by the fish
     */
    public void setEggColor(Egg.EggColor color) {
        eggColor = color;
    }

    /**
     * Set the spawn frequency of eggs.
     * 
     * @param spawnFrequency The frequency at which the Fish spawns eggs
     */
    public void setEggSpawnFrequency(int spawnFrequency) {
        eggSpawnFrequency = spawnFrequency;
    }

    /**
     * Sets the feature point map.
     *
     * @param featurePoints a map of FishFeature to IntPair defining x and y offsets of each feature's image from the body image
     * @throws IllegalArgumentException if the given map does not contain mappings for all values of FishFeature
     */
    public void setFeaturePoints(Map<FishFeature, IntPair> featurePoints) {
        // Ensure feature point map contains every existing FishFeature
        if (featurePoints.size() != FishFeature.class.getEnumConstants().length) {
            throw new IllegalArgumentException("Number of points defined in FishSettings feature point map does not match the number of existing FishFeature types");
        }
        this.featurePoints = featurePoints;
    }

    /**
     * Gets the base XP value.
     *
     * @return the amount of XP this Fish type is worth without any features
     */
    public int getBaseValue() {
        return baseValue;
    }

    /**
     * Gets the base body image.
     *
     * @return the base image of this Fish type without any features
     */
    public GreenfootImage getBodyImage() {
        return bodyImage;
    }

    /**
     * Gets the catch offset.
     *
     * @return the point relative to body image where this Fish type may be caught from
     */
    public IntPair getCatchOffset() {
        return catchOffset;
    }

    /**
     * Gets the swimming speed.
     *
     * @return the average swimming speed of the Fish
     */
    public double getSwimSpeed() {
        return swimSpeed;
    }

    /**
     * Gets the minimum depth.
     *
     * @return the minimum depth the fish can go to relative to the background image
     */
    public int getMinDepth() {
        return minDepth;
    }

    /**
     * Gets the maximum depth.
     *
     * @return the maximum depth the fish can go to relative to the background image
     */
    public int getMaxDepth() {
        return maxDepth;
    }

    /**
     * Gets the average turn interval.
     *
     * @return the average number of frames before the fish rotates to a new direction
     */
    public int getAverageTurnInterval() {
        return averageTurnInterval;
    }

    /**
     * Gets the maximum turn angle.
     *
     * @return the maximum number of degrees up or down the fish can tilt
     */
    public int getMaxTurnDegrees() {
        return maxTurnDegrees;
    }

    /**
     * Gets the size of the egg that is spawned by the fish.
     * 
     * @return The size of the egg spawned by the fish
     */
    public Egg.EggSize getEggSize() {
        return eggSize;
    }

    /**
     * Gets the color of the egg that is spawned by the fish.
     * 
     * @return The color of the egg spawned by the fish
     */
    public Egg.EggColor getEggColor() {
        return eggColor;
    }

    /**
     * Get the spawn frequency of eggs.
     * 
     * @return The frequency at which the Fish spawns eggs
     */
    public int getEggSpawnFrequency() {
        return eggSpawnFrequency;
    }

    /**
     * Gets the feature point map.
     *
     * @return a map of FishFeature to IntPair defining x and y offsets of each feature's image from the body image
     */
    public Map<FishFeature, IntPair> getFeaturePoints() {
        return featurePoints;
    }
}
