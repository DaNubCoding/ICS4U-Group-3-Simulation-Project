import greenfoot.*;

/**
 * A type of world whose display image is an upscaled version of its canvas
 * image.
 *
 * @author Martin Baldwin
 * @version April 2024
 */
public abstract class PixelWorld extends World {
    /**
     * The scale factor of all PixelWorld display images
     */
    public static final int PIXEL_SCALE = 4;

    private final int worldWidth;
    private final int worldHeight;
    private final GreenfootImage canvas;

    /**
     * Creates a new PixelWorld with the specified dimensions.
     * <p>
     * All PixelWorld objects use a Greenfoot cell size of 1 and are unbounded.
     *
     * @param worldWidth the width of this world, in canvas pixels
     * @param worldHeight the height of this world, in canvas pixels
     */
    public PixelWorld(int worldWidth, int worldHeight) {
        super(worldWidth * PIXEL_SCALE, worldHeight * PIXEL_SCALE, 1, false);
        canvas = new GreenfootImage(worldWidth, worldHeight);
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    /**
     * Returns the canvas image of this world, the GreenfootImage that is scaled
     * and displayed as this world's display image.
     * <p>
     * This image has dimensions matching the size specified when constructing
     * the world, and all rendering should be done to this image.
     *
     * @return the canvas image of this world, before scaling
     */
    public GreenfootImage getCanvas() {
        return canvas;
    }

    /**
     * Draws the display image of this world.
     * <p>
     * The canvas image of this world is scaled and drawn onto the world
     * background. This method should be called after all world rendering has
     * been done.
     */
    public void updateImage() {
        GreenfootImage scaled = new GreenfootImage(canvas);
        scaled.scale(getWidth(), getHeight());
        getBackground().drawImage(scaled, 0, 0);
    }

    /**
     * Get the width of the downscaled world.
     *
     * @return The width of the world
     */
    public int getWorldWidth() {
        return worldWidth;
    }

    /**
     * Get the height of the downscaled world.
     *
     * @return The height of the world
     */
    public int getWorldHeight() {
        return worldHeight;
    }
}
