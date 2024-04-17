import greenfoot.*;
import java.util.Set;
import java.util.Collections;

/**
 * A data class for storing information about a Fish object for presenting it
 * later on the summary screen.
 *
 * @author Martin Baldwin
 * @author Sandra Huang
 * @version April 2024
 */
public class FishRecord extends PixelActor {
    // The class object representing the fish's class
    private final Class<? extends Fish> type;
    // An immutable set of features that were on the fish
    private final Set<FishFeature> features;

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

    public static void setSpeedMultiplier(double multiplier){
        speedMultiplier = multiplier;
    }

    public static double getSpeedMultiplier(){
        return speedMultiplier;
    }
}
