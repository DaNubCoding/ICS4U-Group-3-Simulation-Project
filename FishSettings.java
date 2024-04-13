import greenfoot.*;
import java.util.Map;
import java.util.EnumMap;
import java.util.Set;
import java.util.EnumSet;
import java.util.Collections;

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
    /**
     * Thrown from {@link #validate} when a FishSettings object contains an
     * invalid setting.
     */
    public static class InvalidSettingException extends RuntimeException {
        public InvalidSettingException() {}

        public InvalidSettingException(String message) {
            super(message);
        }

        public InvalidSettingException(Throwable cause) {
            super(cause);
        }

        public InvalidSettingException(String message, Throwable cause) {
            super(message, cause);
        }
    }

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
    // The average number of evo-points gained per generation
    private Integer evoPointGain = null;
    // The percentage chance of evolving after the threshold is reached
    private Double evolutionChance = null;

    // All features that can be added to the Fish
    private Set<FishFeature> allowedFeatures = null;

    // Image offsets for all types of features
    // Each IntPair defines the x and y offsets of the top left corner of each
    // feature's image from the top left corner of the body image
    private Map<FishFeature, IntPair> featurePoints = new EnumMap<>(FishFeature.class);

    private static void assertNonNull(Object obj, String name) {
        if (obj == null) {
            throw new InvalidSettingException("FishSettings " + name + " was never initialized");
        }
    }

    /**
     * Throw a {@link InvalidSettingException} if any of this FishSettings object's
     * settings are invalid.
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
        assertNonNull(evoPointGain, "evo-point gain");
        assertNonNull(evolutionChance, "evolution chance");
        assertNonNull(allowedFeatures, "allowed features");
        // Ensure feature point map contains every applicable FishFeature
        for (FishFeature feature : allowedFeatures) {
            if (!featurePoints.containsKey(feature)) {
                throw new InvalidSettingException("FishSettings does not have a feature point defined for the allowed FishFeature " + feature);
            }
        }
        featurePoints = Collections.unmodifiableMap(featurePoints);
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
     * Set the number of evo-points gained by an egg is spawned.
     *
     * @param gain The number of evo-points gained
     */
    public void setEvoPointGain(int gain) {
        evoPointGain = gain;
    }

    /**
     * Set the percentage chance of evolving after a the evolution threshold.
     *
     * @param chance The percentage chance of evolving
     */
    public void setEvolutionChance(double chance) {
        evolutionChance = chance;
    }

    /**
     * Sets the allowed FishFeatures.
     * <p>
     * To disallow all features, either pass no arguments or {@code null}.
     *
     * @param features all FishFeatures that apply to this Fish type, or {@code null} for none
     */
    public void setAllowedFeatures(FishFeature... features) {
        allowedFeatures = EnumSet.noneOf(FishFeature.class);
        if (features != null) {
            Collections.addAll(allowedFeatures, features);
        }
    }

    /**
     * Sets the image offset point for the specified FishFeature.
     *
     * @param feature the FishFeature for which the offset point is being defined
     * @param point an IntPair defining the x and y offset of the feature's image from the body image
     */
    public void putFeaturePoint(FishFeature feature, IntPair point) {
        featurePoints.put(feature, point);
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
     * Get the number of evo-points gained by an egg is spawned.
     *
     * @return The number of evo-points gained
     */
    public int getEvoPointGain() {
        return evoPointGain;
    }

    /**
     * Get the percentage chance of evolving after a the evolution threshold.
     *
     * @return The percentage chance of evolving
     */
    public double getEvolutionChance() {
        return evolutionChance;
    }

    /**
     * Returns a set of all allowed FishFeatures.
     *
     * @return a new set containing all FishFeatures that can be added to this Fish type
     */
    public Set<FishFeature> getAllowedFeatures() {
        return EnumSet.copyOf(allowedFeatures);
    }

    /**
     * Tests if the given FishFeature is allowed.
     *
     * @param feature the FishFeature to test
     * @return true if the feature is allowed, false otherwise
     */
    public boolean isFeatureAllowed(FishFeature feature) {
        return allowedFeatures.contains(feature);
    }

    /**
     * Gets the image offset point associated with the given FishFeature.
     *
     * @param feature the FishFeature whose point is to be returned
     * @return an IntPair defining the x and y offset of the feature's image from the body image
     */
    public IntPair getFeaturePoint(FishFeature feature) {
        return featurePoints.get(feature);
    }
}
