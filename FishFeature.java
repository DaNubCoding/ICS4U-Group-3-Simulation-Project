import greenfoot.*;
import java.util.Set;
import java.util.List;
import java.util.EnumSet;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * All features that may exist on fish. Each feature has attributes for:
 * <ul>
 * <li>a probability of a fish gaining the feature
 * <li>the amount of XP the feature adds to a fish when caught
 * <li>an optional functional operation run every act on a given fish object
 * </ul>
 * <p>
 * Note that the image used for each feature is found in the features image
 * subdirectory using the feature's name. For example, a feature named {@code HORN}
 * would attempt to load an image with the path {@code features/horn.png}.
 * <p>
 * Features in this enum should be defined in the desired order of rendering,
 * with later-defined features being drawn on top of earlier-defined features.
 * (The rendering order is a result of the Fish class using an {@link java.util.EnumSet} internally).
 * <p>
 * Feature functions are the first thing to run in a fish's act, and are
 * expected to operate via side effects.
 *
 * @author Martin Baldwin
 * @author Sandra Huang
 * @version April 2024
 */
public enum FishFeature {
    ANGLER_LIGHT(0.1, 5, null),
    ANGLER_BOMB(0.05, 15, FishFeature::actBomb),
    ANGLER_SOCK(0.005, 15, null), // Behaviour implemented from other Fish
    BIG_EYE(0.1, 10, null),
    HAT_BROWN(0.1, 5, null),
    HAT_PARTY(0.05, 5, FishFeature::actHatParty),
    ;

    /**
     * All sets of FishFeatures that are mutually incompatible. If a fish
     * already has a feature that is contained within one of the following sets,
     * any attempts to add any features from the set that contains it will be
     * ignored.
     */
    private static final Set<FishFeature>[] mutuallyExclusiveSets = new Set[] {
        EnumSet.range(ANGLER_LIGHT, ANGLER_SOCK),
        EnumSet.range(HAT_BROWN, HAT_PARTY),
    };

    private final GreenfootImage image;
    private final double chance;
    private final int value;
    private final Consumer<Fish> act;

    /**
     * @param chance the probability of gaining this feature, as a double, 0.0 indicating 0% and 1.0 indicating 100%
     * @param value the amount of XP this feature is worth
     * @param act a function to run every act on all Fish objects with this feature
     */
    private FishFeature(double chance, int value, Consumer<Fish> act) {
        image = new GreenfootImage("features/" + name().toLowerCase() + ".png");
        this.chance = chance;
        this.value = value;
        this.act = act;
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

    /**
     * Perform the operation assigned to this FishFeature on the given Fish object.
     *
     * @param fish the Fish object to act upon from this feature
     */
    public void actOn(Fish fish) {
        if (act == null) {
            return;
        }
        act.accept(fish);
    }

    /**
     * Tests if this FishFeature is compatible with a collection of FishFeatures.
     *
     * @param the collection of features to test for compatibility with this feature
     * @return true if this feature is compatible with the other features, false otherwise
     */
    public boolean isCompatibleWith(Collection<FishFeature> features) {
        for (Set<FishFeature> set : mutuallyExclusiveSets) {
            if (set.contains(this)) {
                for (FishFeature other : features) {
                    if (set.contains(other)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Kill the given fish and all fish around it at a random point in time.
     */
    private static void actBomb(Fish fish) {
        // Only explode at a random point after the fish has existed for some time
        if (fish.getAge() > 120 && Util.randDouble(0, 1) < 0.001) {
            blowUp(fish);
        }
    }

    /**
     * Make a given fish blow up.
     */
    private static void blowUp(Fish fish) {
        PixelWorld world = fish.getWorld();
        world.removeObject(fish);

        // Find the world position of the bomb itself, offset from the feature, body image, and fish position
        IntPair bombOffset = fish.getSettings().getFeaturePoint(FishFeature.ANGLER_BOMB);
        DoublePair bombPos = fish.getImageOffsetGlobalPosition(fish.getBodyOffsetX() + bombOffset.x + 12, fish.getBodyOffsetY() + bombOffset.y + 9);
        // Get all fishes and eggs around the bomb
        List<Fish> fishInRange = world.getObjectsInRange(32, (int) bombPos.x, (int) bombPos.y, Fish.class);
        List<Egg> eggsInRange = world.getObjectsInRange(32, (int) bombPos.x, (int) bombPos.y, Egg.class);

        for (Fish other : fishInRange) {
            if (other.getWorld() == null) {
                continue;
            }
            if (other.hasFeature(ANGLER_BOMB)) {
                blowUp(other);
            } else {
                world.removeObject(other);
            }
        }
        for (Egg egg : eggsInRange) {
            world.removeObject(egg);
        }
        world.addObject(new Explosion(), (int) bombPos.x, (int) bombPos.y);
    }

    /**
     * Move forwards an extra amount so as to move faster.
     */
    private static void actHatParty(Fish fish) {
        // 1x + 2x = 3x speed
        fish.move(fish.getSettings().getSwimSpeed() * 2.0);
    }
}
