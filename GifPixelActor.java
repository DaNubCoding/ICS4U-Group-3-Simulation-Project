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
     * Creates a GifPixelActor using the given GifImage as its animated image.
     *
     * @param gif the GifImage to use to animate the actor's image
     */
    public GifPixelActor(GifImage gif) {
        super(gif.getCurrentImage());
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
