import greenfoot.*;

/**
 * Utility class containing static methods helpful to the rest of the program.
 *
 * @author Andrew Wang
 * @author Martin Baldwin
 * @version April 2024
 */
public class Util {
    private Util() {
        // Prevent instantiation
    }

    /**
     * Scale an image by a factor and return a copy of it.
     * <p>A copy is returned in order to not modify the orignal image,
     * in case it is reused somewhere else.</p>
     *
     * @param image The image to scale
     * @param factor The factor to scale the image by
     * @return A new GreenfootImage with the scaled dimensions
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

    /**
     * Rotate a 2-component vector by an angle to obtain a new vector.
     *
     * @param x The x component of the vector
     * @param y The y component of the vector
     * @param angle The angle to rotate the vector by
     * @return A DoublePair of the x and y components of the rotated vector
     */
    public static DoublePair rotateVector(double x, double y, double angle) {
        double radians = Math.toRadians(angle);
        double newX = x * Math.cos(radians) - y * Math.sin(radians);
        double newY = x * Math.sin(radians) + y * Math.cos(radians);
        return new DoublePair(newX, newY);
    }

    /**
     * Interpolate from an angle to another angle multiplied by a factor.
     * <p>Will handle any angle in degrees, negative or >360.</p>
     *
     * @param currentAngle The angle to start from
     * @param endAngle The angle to move towards
     * @param factor The factor to multiply the difference by, in order
     *               to interpolate gradually
     * @return The new angle after interpolation
     */
    public static double interpolateAngle(double currentAngle, double endAngle, double factor) {
        double difference = Math.floorMod((int) (endAngle - currentAngle + 180), 360) - 180;
        return currentAngle + difference * factor;
    }
}
