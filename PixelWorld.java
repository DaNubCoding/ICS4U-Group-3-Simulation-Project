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

    /**
     * The width of character images found in ./images/characters/.
     * Text rendering assumes all characters have this width.
     */
    public static final int CHARACTER_WIDTH = 5;

    /**
     * The height of character images found in ./images/characters/.
     * Text rendering assumes all characters have this height.
     */
    public static final int CHARACTER_HEIGHT = 7;

    /**
     * The number of pixels to leave between characters in text.
     */
    public static final int CHARACTER_SPACING = 2;

    // Array where each image is the character representing the value of its index
    private static final GreenfootImage[] digitImages;
    static {
        digitImages = new GreenfootImage[10];
        for (int i = 0; i <= 9; i++) {
            digitImages[i] = new GreenfootImage("characters/" + i + ".png");
        }
    }

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
        scaled.scale(worldWidth * PIXEL_SCALE, worldHeight * PIXEL_SCALE);
        setBackground(scaled);
    }

    /**
     * Gets the width of the downscaled world.
     * <p>
     * This is not the width of the final world that is displayed to the user.
     * To calculate that value, multiply the value returned by this method by
     * PixelWorld.PIXEL_SCALE.
     *
     * @return the width of the world, before scaling
     */
    @Override
    public int getWidth() {
        return worldWidth;
    }

    /**
     * Gets the height of the downscaled world.
     * <p>
     * This is not the height of the final world that is displayed to the user.
     * To calculate that value, multiply the value returned by this method by
     * PixelWorld.PIXEL_SCALE.
     *
     * @return the height of the world, before scaling
     */
    @Override
    public int getHeight() {
        return worldHeight;
    }

    /**
     * Creates an image with a readable representation of an integer value.
     *
     * @param value the integer to render to an image
     * @return a new GreenfootImage containing a representation of the given value
     */
    public static GreenfootImage createIntImage(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("PixelWorld.renderInt does not handle negative values");
        }
        String digits = String.valueOf(value);
        // Draw each digit with its corresponding character image one after the other on the x-axis
        GreenfootImage result = new GreenfootImage((CHARACTER_WIDTH + CHARACTER_SPACING) * digits.length() - CHARACTER_SPACING, CHARACTER_HEIGHT);
        for (int i = 0, x = 0; i < digits.length(); i++, x += CHARACTER_WIDTH + CHARACTER_SPACING) {
            result.drawImage(digitImages[digits.charAt(i) - '0'], x, 0);
        }
        return result;
    }
}
