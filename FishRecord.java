import greenfoot.*;
import java.util.Set;
import java.util.Collections;

/**
 * A data class for storing information about a Fish object for presenting it
 * later on the summary screen.
 *
 * @author Martin Baldwin
 * @version April 2024
 */
public class FishRecord {
    /** The class object representing the fish's class. */
    public final Class<? extends Fish> type;
    /** An immutable set of features that were on the fish. */
    public final Set<FishFeature> features;
    /** An image representing the fish, including its features. */
    public final GreenfootImage image;

    /**
     * Creates a new FishRecord using information from the given Fish.
     *
     * @param fish the Fish object to get information from
     */
    public FishRecord(Fish fish) {
        type = fish.getClass();
        features = Collections.unmodifiableSet(fish.getFeatureSet());
        image = fish.getOriginalImage();
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
        }
        if (!(obj instanceof FishRecord)) {
            return false;
        }
        FishRecord other = (FishRecord) obj;
        return type.equals(other.type) && features.equals(other.features);
    }

    @Override
    public int hashCode() {
        return 17 * type.hashCode() + features.hashCode();
    }
}
