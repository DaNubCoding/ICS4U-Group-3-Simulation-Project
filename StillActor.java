import greenfoot.*;

/**
 * A PixelActor that defines no inherent additonal behaviour, for displaying
 * images.
 *
 * @author Martin Baldwin
 * @version April 2024
 */
public class StillActor extends PixelActor {
    /**
     * Creates a behaviorless actor with an image and a defined rendering layer.
     *
     * @param image the image of the actor
     * @param layer the {@link Layer} to add this actor to
     */
    public StillActor(GreenfootImage image, Layer layer) {
        super(image, layer);
    }

    /**
     * Creates a behaviorless actor with an image file and a defined rendering layer.
     *
     * @param imagePath the path to the image of the actor
     * @param layer the {@link Layer} to add this actor to
     */
    public StillActor(String imagePath, Layer layer) {
        this(new GreenfootImage(imagePath), layer);
    }
}
