import greenfoot.*;
import java.util.Set;
import java.util.List;
import java.util.Collections;

/**
 * A class for storing and comparing information about a Fish object for
 * presenting it as an actor later on the summary screen.
 *
 * @author Martin Baldwin
 * @author Sandra Huang
 * @version April 2024
 */
public class FishRecord extends PixelActor implements Comparable<FishRecord> {
    private static final List<Class<? extends Fish>> typeOrder = List.of(
        Salmon.class, Mollusk.class, Jellyfish.class, Squid.class,
        Bass.class, Piranha.class, Barracuda.class, Anglerfish.class,
        Tuna.class, Flyingfish.class, Swordfish.class, Whale.class
    );

    // The class object representing the fish's class
    private final Class<? extends Fish> type;
    // An immutable set of features that were on the fish
    private final Set<FishFeature> features;
    // The total XP value of the fish
    private final int value;

    private static final double SPEED = 0.5;
    private static double speedMultiplier;
    private Text name;
    private Star tierStars;

    /**
     * Creates a new FishRecord using information from the given Fish.
     *
     * @param fish the Fish object to get information from
     */
    public FishRecord(Fish fish) {
        super();
        type = fish.getClass();
        features = Collections.unmodifiableSet(fish.getFeatureSet());
        value = fish.getValue();

        setImage(fish.getOriginalImage());
        name = new Text(type.getCanonicalName(), Text.AnchorX.CENTER, Text.AnchorY.TOP);
        tierStars = new Star(fish.getSettings().getTier());
    }

    @Override
    public void addedToWorld(World world) {
        world.addObject(name, getX(), getY() + getOriginalImage().getHeight());
        world.addObject(tierStars, getX(), getY() + getOriginalImage().getHeight() + 14);
    }

    @Override
    public void act() {
        move(SPEED * speedMultiplier);
        name.move(SPEED * speedMultiplier);
        tierStars.move(SPEED * speedMultiplier);
        World w = getWorld();
        if (getX() > w.getWidth()) {
            w.removeObject(name);
            w.removeObject(tierStars);
            w.removeObject(this);
        }
    }

    /**
     * Indicates whether some other object is equal to this FishRecord.
     * <p>
     * FishRecord objects are considered equal if they describe the same class
     * of fish and they have exactly matching sets of features.
     *
     * @param obj the object to compare to
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof FishRecord)) {
            return false;
        }
        FishRecord other = (FishRecord) obj;
        return type.equals(other.type) && features.equals(other.features);
    }

    @Override
    public int hashCode() {
        return 17 * type.hashCode() + features.hashCode();
    }

    /**
     * Compares this FishRecord with another FishRecord for order.
     * <p>
     * Fishes are first sorted by class according to the type order list defined
     * at the top of this class. They are then sorted by value, then by
     * features, placing featureless fishes earlier.
     *
     * @param other the FishRecord to be compared
     * @return a negative integer, zero, or a positive integer as this FishRecord is less than, equal to, or greater than the specified FishRecord
     * @see Comparable#compareTo
     */
    @Override
    public int compareTo(FishRecord other) {
        if (equals(other)) {
            return 0;
        }
        // Sort by type
        int typeDiff = typeOrder.indexOf(type) - typeOrder.indexOf(other.type);
        if (typeDiff != 0) {
            return typeDiff;
        }
        // Sort by value
        if (value != other.value) {
            return value - other.value;
        }
        // Sort featureless earlier
        boolean isEmpty = features.isEmpty();
        boolean isOtherEmpty = other.features.isEmpty();
        if (isEmpty != isOtherEmpty) {
            return isEmpty ? -1 : 1;
        }
        // Sort earlier features earlier
        for (FishFeature feature : FishFeature.values()) {
            boolean hasFeature = features.contains(feature);
            boolean otherHasFeature = other.features.contains(feature);
            if (hasFeature != otherHasFeature) {
                return hasFeature ? -1 : 1;
            }
        }
        // Unreachable: covered by equality test at top
        return 0;
    }

    public static void setSpeedMultiplier(double multiplier){
        speedMultiplier = multiplier;
    }

    public static double getSpeedMultiplier(){
        return speedMultiplier;
    }
}
