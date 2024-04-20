import greenfoot.*;
import java.util.Set;
import java.util.EnumSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.EnumMap;
import java.util.Collections;
import java.util.Arrays;

/**
 * A class to contain all Fish subclass-specific settings.
 * <p>
 * This was created to vastly improve the cleanliness of the Fish superclass and
 * its subclasses.
 * <p>
 * For maintainers: when adding a new field, a new line in {@link validate}
 * must be added in addition to its corresponding getter and setter.
 *
 * @author Martin Baldwin
 * @version April 2024
 */
public class FishSettings {
    /** The greatest tier value a fish can have. */
    public static final int MAX_TIER = 5;

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

    // Tier value of this Fish type
    private Integer tier = null;
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
    // All subclasses of Fish that this Fish type may evolve into
    private List<Class<? extends Fish>> evolutions = null;

    // All features that can be added to the Fish
    private Set<FishFeature> allowedFeatures = null;

    // The Fish must have at least one of the features in each set, chosen randomly according to its integer weight
    private List<Map<FishFeature, Integer>> requiredFeatureSets = new ArrayList<>();

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
        assertNonNull(tier, "tier");
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
        assertNonNull(evolutions, "evolutions");
        assertNonNull(allowedFeatures, "allowed features");
        // Ensure required features are all allowed
        for (Map<FishFeature, Integer> map : requiredFeatureSets) {
            if (!allowedFeatures.containsAll(map.keySet())) {
                throw new InvalidSettingException("FishSettings attempting to require FishFeatures that are not allowed");
            }
        }
        // Ensure feature point map contains every applicable FishFeature
        for (FishFeature feature : allowedFeatures) {
            if (!featurePoints.containsKey(feature)) {
                throw new InvalidSettingException("FishSettings does not have a feature point defined for the allowed FishFeature " + feature);
            }
        }
    }

    /**
     * Sets the tier.
     *
     * @param tier the tier value of this Fish type
     */
    public void setTier(int tier) {
        if (tier < 1 || tier > MAX_TIER) {
            throw new IllegalArgumentException("Tier value must be between 1 and FishSettings.MAX_TIER=" + MAX_TIER + ", inclusive (got " + tier + ")");
        }
        this.tier = tier;
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
     * Sets the base body image from the given path.
     *
     * @param imagePath the path of the image file containing the base image of this Fish type without any features
     */
    public void setBodyImage(String imagePath) {
        setBodyImage(new GreenfootImage(imagePath));
    }

    /**
     * Sets the catch offset, the point where this Fish type may be caught from.
     *
     * @param x the horizontal offset relative to body image
     * @param y the vertical offset relative to body image
     */
    public void setCatchOffset(int x, int y) {
        catchOffset = new IntPair(x, y);
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
     * @param color The color of the egg spawned by the fish
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
     * Sets the possible evolutions.
     * <p>
     * To set no evolutions, either pass no arguments or {@code null}.
     *
     * @param classes all subclasses of Fish that this Fish type may evolve into, or {@code null} for none
     */
    public void setEvolutions(Class<? extends Fish>... classes) {
        evolutions = new ArrayList<Class<? extends Fish>>();
        if (classes != null) {
            Collections.addAll(evolutions, classes);
        }
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
     * Adds a required feature set. This Fish type is required to have one of
     * the features from the given features array upon hatching. The weights
     * array specifies the random weight of the corresponding feature (where
     * array indices match), to give each feature a specific chance of occuring
     * when being chosen from this set of required features.
     *
     * @param features an array of FishFeatures that this Fish type must have one of upon hatching
     * @param weights an array of integer weightings that affect how likely it is for its corresponding feature to occur
     * @throws IllegalArgumentException if the two arrays differ in length
     */
    public void addRequiredFeatureSet(FishFeature[] features, int[] weights) {
        if (features.length != weights.length) {
            throw new IllegalArgumentException("FishSettings required feature set must have same number of features and weights");
        }
        Map<FishFeature, Integer> map = new EnumMap<>(FishFeature.class);
        for (int i = 0; i < features.length; i++) {
            map.put(features[i], weights[i]);
        }
        requiredFeatureSets.add(map);
    }

    /**
     * Sets the image offset point for the specified FishFeature.
     *
     * @param feature the FishFeature for which the offset point is being defined
     * @param x the horizontal offset of the feature's image from the body image
     * @param y the vertical offset of the feature's image from the body image
     */
    public void setFeaturePoint(FishFeature feature, int x, int y) {
        featurePoints.put(feature, new IntPair(x, y));
    }

    /**
     * Gets the tier.
     *
     * @return the tier value of this Fish type
     */
    public int getTier() {
        return tier;
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
     * Gets the possible evolutions.
     *
     * @return an array of all subclasses of Fish that this Fish type may evolve into
     */
    public List<Class<? extends Fish>> getEvolutions() {
        return new ArrayList<Class<? extends Fish>>(evolutions);
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
     * Returns a set of FishFeatures from all required feature sets chosen
     * randomly according to their respective weight values.
     *
     * @return a new set of random FishFeatures that fulfills this Fish type's required features
     */
    public Set<FishFeature> chooseRequiredFeatures() {
        Set<FishFeature> result = EnumSet.noneOf(FishFeature.class);
        for (Map<FishFeature, Integer> map : requiredFeatureSets) {
            // Pick a random feature from each set
            int totalWeight = 0;
            for (int weight : map.values()) {
                totalWeight += weight;
            }
            int chooseWeight = Util.randInt(0, totalWeight - 1);
            // Find the feature that corresponds to the chosen value
            int accWeight = 0;
            for (FishFeature feature : map.keySet()) {
                accWeight += map.get(feature);
                if (accWeight > chooseWeight) {
                    result.add(feature);
                    break;
                }
            }
        }
        return result;
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
