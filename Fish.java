import greenfoot.*;
import java.util.Set;
import java.util.EnumSet;
import java.util.Map;
import java.util.Collections;

/**
 * An undersea actor and target of Fishers.
 * <p>
 * Each fish possesses a list of features, which may change as it evolves, each
 * adding a defined amount of XP value to a fish. A fish's features are drawn
 * onto its image on top of its body.
 * <p>
 * A subclass of Fish must define:
 * <ul>
 * <li>A base XP value
 * <li>A base body image
 * <li>A map of all FishFeatures to their image offsets relative to the body image
 * </ul>
 * <p>
 * The recommended way of doing so is defining them as static variables, then
 * returning them in their corresponding implementations of this class's
 * abstract methods.
 *
 * @author Martin Baldwin
 * @version April 2024
 */
public abstract class Fish extends PixelActor {
    // All features present on this fish
    private Set<FishFeature> features;

    /**
     * Creates a new Fish with the given features.
     * <p>
     * Pass any number of arguments of type FishFeature to this constructor
     * (including zero) or a FishFeature array and all of the features will be
     * added to the new Fish.
     *
     * @param features any FishFeatures to add to this Fish
     */
    public Fish(FishFeature... features) {
        super();
        // Sanity check to ensure every subclass has properly defined feature point maps
        if (getFeaturePoints().size() != FishFeature.class.getEnumConstants().length) {
            throw new IllegalArgumentException("Number of FishFeature attachment points on FishBody does not match the number of existing FishFeature types");
        }
        this.features = EnumSet.noneOf(FishFeature.class);
        Collections.addAll(this.features, features);
        updateImage();
    }

    /**
     * Adds a FishFeature to this Fish and updates its image accordingly.
     *
     * @param feature the FishFeature to add
     */
    public void addFeature(FishFeature feature) {
        features.add(feature);
        updateImage();
    }

    /**
     * Removes a FishFeature from this Fish and updates its image accordingly.
     *
     * @param feature the FishFeature to remove
     */
    public void removeFeature(FishFeature feature) {
        features.remove(feature);
        updateImage();
    }

    /**
     * Tests if the given feature is present on this Fish.
     *
     * @param feature the FishFeature to check for
     * @return true if this fish's feature list contains the given feature, false otherwise
     */
    public boolean hasFeature(FishFeature feature) {
        return features.contains(feature);
    }

    /**
     * Gets the total XP value of this Fish, including all features.
     *
     * @return the amount of XP value this Fish is worth
     */
    public int getValue() {
        int sum = getBaseValue();
        for (FishFeature feature : features) {
            sum += feature.getValue();
        }
        return sum;
    }

    /**
     * Gets the base XP value of this Fish (without any features).
     *
     * @return the amount of XP this Fish without features is worth
     */
    public abstract int getBaseValue();

    /**
     * Sets this Fish's image to a GreenfootImage with this Fish's body and
     * features drawn on it.
     * <p>
     * The new image will be just large enough to contain this Fish. All
     * features will be drawn on top of the body at their appropriate locations.
     */
    private void updateImage() {
        GreenfootImage bodyImage = getBodyImage();
        // If there are no features, the existing body image is sufficient
        if (features.isEmpty()) {
            setImage(bodyImage);
            return;
        }

        // Create an image of appropriate size to fit this fish with all of its features
        Map<FishFeature, IntPair> featurePoints = getFeaturePoints();
        // Keep track of the extreme locations of any feature relative to the body
        int left = 0;
        int right = bodyImage.getWidth();
        int top = 0;
        int bottom = bodyImage.getHeight();
        for (FishFeature feature : features) {
            // Get the leftmost and rightmost pixel locations of this feature relative to the body
            IntPair point = featurePoints.get(feature);
            int featLeft = point.x;
            int featRight = featLeft + feature.getImage().getWidth();
            if (featLeft < left) {
                left = featLeft;
            } else if (featRight > right) {
                right = featRight;
            }
            // Get the topmost and bottommost pixel locations of this feature relative to the body
            int featTop = point.y;
            int featBottom = featTop + feature.getImage().getHeight();
            if (featTop < top) {
                top = featTop;
            } else if (featBottom > bottom) {
                bottom = featBottom;
            }
        }
        GreenfootImage image = new GreenfootImage(right - left, bottom - top);

        // Draw the fish
        image.drawImage(bodyImage, -left, -top);
        for (FishFeature feature : features) {
            IntPair point = featurePoints.get(feature);
            image.drawImage(feature.getImage(), point.x - left, point.y - top);
        }
        setImage(image);
        setCenterOfRotation(bodyImage.getWidth() / 2 - left, bodyImage.getHeight() / 2 - top);
    }

    /**
     * Gets the image to be drawn as this Fish's body.
     * <p>
     * All FishFeatures are drawn on top of this image.
     *
     * @return the GreenfootImage representing this Fish's body
     */
    public abstract GreenfootImage getBodyImage();

    /**
     * Gets a map of all FishFeature types to their image offsets relative to
     * this Fish's body image.
     *
     * @return a Map object containing all values of FishFeature in its keys,
     *         each mapped to an pair of ints describing the feature's image offset
     */
    public abstract Map<FishFeature, IntPair> getFeaturePoints();
}
