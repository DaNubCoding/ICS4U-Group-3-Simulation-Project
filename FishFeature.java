import greenfoot.*;

/**
 * All features that may exist on fish, each with its own image to be drawn onto
 * a fish's image and an XP value it adds to a fish when caught.
 *
 * @author Martin Baldwin
 * @version April 2024
 */
public enum FishFeature {
    BIG_EYE("big_eye.png", 10), 
    ANGLER_BOMB("feature_angler_bomb.png", 15), ANGLER_SOCK("feature_angler_sock.png", 15), 
    HAT_BROWN("feature_hat_brown.png", 5), HAT_PARTY("feature_hat_party.png", 5);

    private final GreenfootImage image;
    private final int value;

    private FishFeature(String imagePath, int value) {
        image = new GreenfootImage(imagePath);
        this.value = value;
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
     * Returns the XP value of this FishFeature.
     *
     * @return the amount of XP this feature is worth
     */
    public int getValue() {
        return value;
    }
}
