import greenfoot.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * An undersea actor and target of Fishers.
 * <p>
 * Each fish possesses a list of features, which may change as it evolves. A
 * fish's features are drawn onto its image on top of its body.
 *
 * @author Martin Baldwin
 * @version April 2024
 */
public abstract class Fish extends PixelActor {
    // All features present on this fish
    private List<FishFeature> features;

    /**
     * Creates a new Fish with the given features.
     *
     * @param features any FishFeatures to add to this Fish
     */
    public Fish(FishFeature... features) {
        super();
        // Sanity check to ensure every subclass has properly defined feature point maps
        if (getFeaturePoints().size() != FishFeature.class.getEnumConstants().length) {
            throw new IllegalArgumentException("Number of FishFeature attachment points on FishBody does not match the number of existing FishFeature types");
        }
        this.features = new ArrayList<FishFeature>(Arrays.asList(features));
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
     * Sets this Fish's image to a GreenfootImage with this Fish's body and
     * features drawn on it.
     * <p>
     * The new image will be just large enough to contain this Fish. All
     * features will be drawn on top of the body at their appropriate locations.
     */
    private void updateImage() {
        if (features.isEmpty()) {
            // No features: existing body image is sufficient
            setImage(getBodyImage());
            return;
        }

        Map<FishFeature, IntPair> featurePoints = getFeaturePoints();
        // Create an image of appropriate size
        // Keep track of the extreme locations of any feature relative to the body
        int left = 0;
        int right = getBodyImage().getWidth();
        int top = 0;
        int bottom = getBodyImage().getHeight();
        for (FishFeature feature : features) {
            // Get the leftmost and rightmost pixel locations of this feature relative to the body
            IntPair point = featurePoints.get(feature);
            int featLeft = point.getX();
            int featRight = featLeft + feature.getImage().getWidth();
            if (featLeft < left) {
                left = featLeft;
            } else if (featRight > right) {
                right = featRight;
            }
            // Get the topmost and bottommost pixel locations of this feature relative to the body
            int featTop = point.getY();
            int featBottom = featTop + feature.getImage().getHeight();
            if (featTop < top) {
                top = featTop;
            } else if (featBottom > bottom) {
                bottom = featBottom;
            }
        }
        GreenfootImage image = new GreenfootImage(right - left, bottom - top);

        // Draw the fish
        image.drawImage(getBodyImage(), -left, -top);
        for (FishFeature feature : features) {
            IntPair point = featurePoints.get(feature);
            image.drawImage(feature.getImage(), point.getX() - left, point.getY() - top);
        }
        setImage(image);
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
