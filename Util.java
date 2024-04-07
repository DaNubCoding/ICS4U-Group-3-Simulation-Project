import greenfoot.*;

/**
 * Utility class containing static methods helpful to the rest of the program.
 *
 * @author Andrew Wang
 * @author Martin Baldwin
 * @version April 2024
 */
public class Util {
    /**
     * Scale an image by a factor and return a copy of it.
     * <p>A copy is returned in order to not modify the orignal image,
     * in case it is reused somewhere else.</p>
     *
     * @param image The image to scale
     * @param factor The factor to scale the image by
     */
    public static GreenfootImage scaledImage(GreenfootImage image, double factor) {
        GreenfootImage scaledImage = new GreenfootImage(image);
        scaledImage.scale((int) (image.getWidth() * factor), (int) (image.getHeight() * factor));
        return image;
    }

    /**
     * Crop an image to a new width and height.
     * <p>
     * The original image is drawn so its top left corner is placed in the top
     * left corner of the cropped image.
     *
     * @param image The image to crop
     * @param width The new width of the cropped image
     * @param height The new height of the cropped image
     * @return a new GreenfootImage with the given dimensions containing the original image in its top left corner
     */
    public static GreenfootImage croppedImage(GreenfootImage image, int width, int height) {
        GreenfootImage croppedImage = new GreenfootImage(width, height);
        croppedImage.drawImage(image, 0, 0);
        return croppedImage;
    }
    
    /**
     * Get a random integer between a minimum and maximum value,
     * inclusive on both ends.
     * 
     * @param min The minimum bound (inclusive)
     * @param max The maximum bound (inclusive)
     * @return A random integer between the bounds
     */
    public static int randInt(int min, int max) {
        return Greenfoot.getRandomNumber(max - min + 1) + min;
    }
    
    /**
     * Get a random integer between a 0 and maximum value inclusive on both ends.
     * 
     * @param max The maximum bound (inclusive)
     * @return A random integer <= the upper bound
     */
    public static int randInt(int max) {
        return Greenfoot.getRandomNumber(max + 1);
    }
    
    /**
     * Get a random double between a minimum and maximum value.
     * 
     * @param min The lower bound
     * @param max The upper bound
     * @return A random double between the bounds
     */
    public static double randDouble(double min, double max) {
        return Math.random() * (max - min) + min;
    }
}
