import greenfoot.*;

/**
 * A PixelActor subclass that simplifies the use of a GifImage as its image.
 * <p>
 * Subclasses of GifPixelActor should call the {@link #updateImage} method every
 * act.
 *
 * @author Martin Baldwin
 * @version April 2024
 */
public class GifPixelActor extends PixelActor {
    private final GifImage gif;

    /**
     * Creates a GifPixelActor on the defined rendering layer using the given
     * GifImage as its animated image.
     *
     * @param gif the GifImage to use to animate the actor's image
     * @param layer The {@link Layer} to add this actor to
     */
    public GifPixelActor(GifImage gif, Layer layer) {
        super(gif.getCurrentImage(), layer);
        this.gif = gif;
    }

    /**
     * Updates this actor's image according to its GifImage when appropriate.
     * <p>
     * Subclasses should call this method every act.
     */
    public void updateImage() {
        GreenfootImage frame = gif.getCurrentImage();
        if (getOriginalImage() != frame) {
            setImage(frame);
        }
    }
}
