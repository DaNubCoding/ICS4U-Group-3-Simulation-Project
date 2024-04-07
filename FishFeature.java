import greenfoot.*;

/**
 * All features that may exist on fish, each with its own image to be drawn onto
 * a fish's image.
 *
 * @author Martin Baldwin
 * @version April 2024
 */
public enum FishFeature {
    BIG_EYE("images/big_eye.png");

    private final GreenfootImage image;

    private FishFeature(String imagePath) {
        image = new GreenfootImage(imagePath);
    }

    /**
     * Returns the image of this FishFeature.
     *
     * @return the GreenfootImage for the appropriate type of feature
     */
    public GreenfootImage getImage() {
        return image;
    }
}
