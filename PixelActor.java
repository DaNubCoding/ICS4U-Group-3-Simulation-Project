import greenfoot.*;
import java.util.List;

/**
 * An special actor class that can render itself onto a PixelWorld.
 * <ul>
 * <li>Has floating point coordinates</li>
 * <li>Can move towards any direction</li>
 * <li>Can rotate independently of movement</li>
 * <li>Can mirror itself</li>
 * <li>Has center of rotation</li>
 * </ul>
 *
 * @author Andrew Wang
 * @author Martin Baldwin
 * @version April 2024
 */
public abstract class PixelActor extends Actor {
    // Fill all images used to transform all actors with translucent backgrounds and show their positions with a point
    private static final boolean DEBUG_SHOW_IMAGE_BOUNDS = false;

    // The original upright image
    private GreenfootImage originalImage;
    private int originalWidth;
    private int originalHeight;
    private int centerOfRotationX;
    private int centerOfRotationY;
    // See createExpandedImage()
    private GreenfootImage expandedImage;
    // See createExpandedImage()
    private int maxDimension;
    // The image after transformations (rotation and mirror)
    private GreenfootImage transformedImage;
    // The width and height of the image after transformations
    private int transformedWidth;
    private int transformedHeight;
    // X and Y coordinates
    protected double x;
    protected double y;
    // The direction in degrees that the PixelActor will move towards
    private double heading;
    // The rotation of the image of the PixelActor
    private double rotation;
    // Whether the image is mirrored in either axis
    private boolean mirrorX;
    private boolean mirrorY;
    // Whether the PixelActor will be rendered
    private boolean visible;
    // The layer which this actor belongs to
    private Layer layer;

    /**
     * Create a PixelActor with a starting GreenfootImage and a defined rendering layer.
     *
     * @param image The starting image of the PixelActor
     * @param layer The {@link Layer} to add this actor to
     */
    public PixelActor(GreenfootImage image, Layer layer) {
        this.x = x;
        this.y = y;
        heading = 0;
        rotation = 0;
        mirrorX = mirrorY = false;
        visible = true;
        this.layer = layer;

        setImage(image);
        // Ensure Greenfoot does not draw this actor's image on its own
        super.setImage((GreenfootImage) null);
    }

    /**
     * Create a PixelActor with a starting image file and a defined rendering layer.
     *
     * @param imagePath The path to the starting image of the PixelActor
     * @param layer The {@link Layer} to add this actor to
     */
    public PixelActor(String imagePath, Layer layer) {
        this(new GreenfootImage(imagePath), layer);
    }

    /**
     * Create a PixelActor on a defined rendering layer without a starting image.
     */
    public PixelActor(Layer layer) {
        this((GreenfootImage) null, layer);
    }

    /**
     * Render the PixelActor onto the canvas of the world it lives in.
     * <p>If any additional rendering aside from rendering its image need to
     * be done, override this method.</p>
     *
     * @param canvas The GreenfootImage to render the PixelActor onto
     *        (almost always just the PixelWorld canvas)
     */
    public void render(GreenfootImage canvas) {
        if (originalImage == null || !visible) return;
        DoublePair center = getImageOffsetGlobalPosition(originalWidth / 2, originalHeight / 2);
        DoublePair imagePos = new DoublePair(center.x - transformedWidth / 2, center.y - transformedHeight / 2);
        canvas.drawImage(transformedImage, (int) Math.ceil(imagePos.x), (int) Math.ceil(imagePos.y));
        if (DEBUG_SHOW_IMAGE_BOUNDS) {
            canvas.setColor(Color.RED);
            canvas.fillRect(getX(), getY(), 1, 1);
        }
    }

    /**
     * Returns a null GreenfootImage. Greenfoot calls this method when painting
     * the world, but we don't want PixelActors to be automatically drawn by
     * Greenfoot (they must be scaled manually).
     *
     * @return null, always
     */
    @Override
    public GreenfootImage getImage() {
        return null;
    }

    /**
     * Get the original image of the PixelActor.
     *
     * @return The original image of the PixelActor
     */
    public GreenfootImage getOriginalImage() {
        return originalImage;
    }

    /**
     * Get the transformed image of the PixelActor.
     *
     * @return The transformed image of the PixelActor
     */
    public GreenfootImage getTransformedImage() {
        return transformedImage;
    }

    /**
     * Set the original image of the PixelActor to a copy of a GreenfootImage.
     *
     * @param newImage The GreenfootImage to set as the new image
     */
    @Override
    public void setImage(GreenfootImage newImage) {
        if (newImage == null) {
            originalImage = null;
            return;
        }
        originalWidth = newImage.getWidth();
        originalHeight = newImage.getHeight();
        if (DEBUG_SHOW_IMAGE_BOUNDS) {
            originalImage = new GreenfootImage(originalWidth, originalHeight);
            originalImage.setColor(new Color(255, 0, 0, 64));
            originalImage.fill();
            originalImage.drawImage(newImage, 0, 0);
        } else {
            originalImage = new GreenfootImage(newImage);
        }
        centerOfRotationX = originalWidth / 2;
        centerOfRotationY = originalHeight / 2;
        if (getMirrorX()) {
            originalImage.mirrorHorizontally();
            centerOfRotationX = originalWidth - 1 - centerOfRotationX;
        }
        if (getMirrorY()) {
            originalImage.mirrorVertically();
            centerOfRotationY = originalHeight - 1 - centerOfRotationY;
        }
        createExpandedImage();
        createRotatedImage();
    }

    /**
     * Set the original image of the PixelActor to an image file.
     *
     * @param path The path to the image file
     */
    @Override
    public void setImage(String path) {
        setImage(new GreenfootImage(path));
    }

    /**
     * Get the layer which this PixelActor belongs to.
     *
     * @return the {@link Layer} this actor was assigned
     */
    public Layer getLayer() {
        return layer;
    }

    /**
     * Set the point from which the image is rotated (center of rotation)
     * relative to the top-left corner of the original image.
     *
     * @param x The x offset of the center of rotation
     * @param y The y offset of the center of rotation
     */
    public void setCenterOfRotation(int x, int y) {
        centerOfRotationX = x;
        centerOfRotationY = y;
        if (getMirrorX()) centerOfRotationX = originalWidth - 1 - centerOfRotationX;
        if (getMirrorY()) centerOfRotationY = originalHeight - 1 - centerOfRotationY;
        createRotatedImage();
    }

    /**
     * Get the x coordinate of the center of rotation.
     *
     * @return The x coordinate of the center of rotation
     */
    public int getCenterOfRotationX() {
        return centerOfRotationX;
    }

    /**
     * Get the y coordinate of the center of rotation
     *
     * @return The y coordinate of the center of rotation
     */
    public int getCenterOfRotationY() {
        return centerOfRotationY;
    }

    /**
     * Generate the image that is a version of the centered image but with extra
     * margins, as to make it just large enough to contain any potential rotation
     * of the centered image.
     */
    private void createExpandedImage() {
        if (originalImage == null) return;
        maxDimension = (int) Math.ceil(Math.hypot(originalWidth, originalHeight));
        expandedImage = new GreenfootImage(maxDimension, maxDimension);
        int localX = (int) Math.floor((maxDimension - originalWidth) / 2);
        int localY = (int) Math.floor((maxDimension - originalHeight) / 2);
        if (DEBUG_SHOW_IMAGE_BOUNDS) {
            expandedImage.setColor(new Color(255, 255, 0, 64));
            expandedImage.fill();
        }
        expandedImage.drawImage(originalImage, localX, localY);
    }

    /**
     * Generate the rotated image while keeping it centered and expand the image
     * in order to fully contain the rotated image.
     */
    private void createRotatedImage() {
        if (originalImage == null) return;
        double angle = Math.toRadians(rotation);
        double sinAngle = Math.sin(angle);
        double cosAngle = Math.cos(angle);
        // Round to nearest even number to prevent jittering
        transformedWidth = (int) Math.ceil((Math.abs(originalWidth * cosAngle) + Math.abs(originalHeight * sinAngle)));
        transformedHeight = (int) Math.ceil((Math.abs(originalWidth * sinAngle) + Math.abs(originalHeight * cosAngle)));

        GreenfootImage rotatedImage = new GreenfootImage(expandedImage);
        rotatedImage.rotate((int) rotation);
        transformedImage = new GreenfootImage((int) transformedWidth, (int) transformedHeight);
        int localX = (int) Math.floor((transformedWidth - maxDimension) / 2);
        int localY = (int) Math.floor((transformedHeight - maxDimension) / 2);
        if (DEBUG_SHOW_IMAGE_BOUNDS) {
            transformedImage.setColor(new Color(0, 255, 0, 64));
            transformedImage.fill();
        }
        transformedImage.drawImage(rotatedImage, localX, localY);
    }

    /**
     * Set the location of PixelActor using integers.
     * <p>This is centered just like the default Actor setLocation method.</p>
     *
     * @param x The x coordinate
     * @param y The y coordinate
     */
    @Override
    public void setLocation(int x, int y) {
        this.x = (double) x;
        this.y = (double) y;
    }

    /**
     * Set the location of PixelActor using doubles.
     * <p>This is centered just like the default Actor setLocation method.</p>
     *
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set the direction of movement in degrees.
     * <p>This will change the direction the PixelActor moves toward.</p>
     *
     * @param heading The heading angle in degrees
     */
    public void setHeading(double heading) {
        double remainder = heading % 360.0;
        if (remainder < 0.0) {
            remainder += 360.0;
        }
        this.heading = remainder;
    }

    /**
     * Set the direction of movement to be heading towards a target.
     *
     * @param targetX The x coordinate of the target
     * @param targetY The y coordinate of the target
     */
    public void setHeading(double targetX, double targetY) {
        double dx = targetX - x;
        double dy = targetY - y;
        setHeading(Math.toDegrees(Math.atan2(dy, dx)));
    }

    /**
     * Get the direction the PixelActor is heading.
     */
    public double getHeading() {
        return heading;
    }

    /**
     * Set the horizontal mirror of the image.
     *
     * @param mirror Whether to mirror or not
     */
    public void setMirrorX(boolean mirror) {
        if (mirrorX == mirror) return;
        if (originalImage != null) {
            originalImage.mirrorHorizontally();
        }
        centerOfRotationX = originalWidth - 1 - centerOfRotationX;
        mirrorX = mirror;
        createExpandedImage();
        createRotatedImage();
    }

    /**
     * Set the vertical mirror of the image.
     *
     * @param mirror Whether to mirror or not
     */
    public void setMirrorY(boolean mirror) {
        if (mirrorY == mirror) return;
        if (originalImage != null) {
            originalImage.mirrorVertically();
        }
        centerOfRotationY = originalHeight - 1 - centerOfRotationY;
        mirrorY = mirror;
        createExpandedImage();
        createRotatedImage();
    }

    /**
     * Get whether the image is mirrored horizontally.
     *
     * @return True if mirrored horizontally, false otherwise
     */
    public boolean getMirrorX() {
        return mirrorX;
    }

    /**
     * Get whether the image is mirrored vertically.
     *
     * @return True if mirrored vertically, false otherwise
     */
    public boolean getMirrorY() {
        return mirrorY;
    }

    /**
     * Set the rotation of the image as an integer.
     *
     * @param rotation The rotation angle in degrees
     */
    @Override
    public void setRotation(int rotation) {
        setRotation((double) rotation);
    }

    /**
     * Set the rotation of the image as a double.
     *
     * @param rotation The rotation angle in degrees
     */
    public void setRotation(double rotation) {
        double remainder = rotation % 360.0;
        if (remainder < 0.0) {
            remainder += 360.0;
        }
        this.rotation = remainder;
        createRotatedImage();
    }

    /**
     * Set the rotation of the image as a double,
     * and potentially update the image.
     *
     * @param rotation The rotation angle in degrees
     * @param updateImage Whether to update the image immediately
     *        (this may be useful for performance if the method needs
     *        to be called multiple times in a single frame)
     */
    public void setRotation(double rotation, boolean updateImage) {
        this.rotation = rotation;
        if (updateImage) createRotatedImage();
    }

    /**
     * Get the rotation of the image as an integer.
     *
     * @return The rotation angle in degrees
     */
    @Override
    public int getRotation() {
        return (int) rotation;
    }

    /**
     * Get the rotation of the image as a double.
     *
     * @return The rotation angle in degrees
     */
    public double getDoubleRotation() {
        return rotation;
    }

    /**
     * Move the PixelActor by an integer distance in the current heading.
     *
     * @param distance The distance to move by
     */
    @Override
    public void move(int distance) {
        move((double) distance);
    }

    /**
     * Move the PixelActor by a decimal distance in the current heading.
     *
     * @param distance The distance to move by
     */
    public void move(double distance) {
        double angle = Math.toRadians(heading);
        double dx = Math.cos(angle) * distance;
        double dy = Math.sin(angle) * distance;
        x += dx;
        y += dy;
    }

    /**
     * Get the x coordinate as an integer.
     *
     * @return The x coordinate as an integer
     */
    @Override
    public int getX() {
        return (int) x;
    }

    /**
     * Get the y coordinate as an integer.
     *
     * @return The y coordinate as an integer
     */
    @Override
    public int getY() {
        return (int) y;
    }

    /**
     * Get the x coordinate as a double.
     *
     * @return The x coordinate as a double
     */
    public double getDoubleX() {
        return x;
    }

    /**
     * Get the y coordinate as a double.
     *
     * @return The y coordinate as a double
     */
    public double getDoubleY() {
        return y;
    }

    /**
     * Get the width of the original image.
     *
     * @return The width of the original image
     */
    public int getOriginalWidth() {
        return originalWidth;
    }

    /**
     * Get the height of the original image.
     *
     * @return The height of the original image
     */
    public int getOriginalHeight() {
        return originalHeight;
    }

    /**
     * Get the width of the transformed image.
     *
     * @return The width of the transformed image
     */
    public double getTransformedWidth() {
        return transformedWidth;
    }

    /**
     * get the height of the transformed image.
     *
     * @return The height of the transformed image
     */
    public double getTransformedHeight() {
        return transformedHeight;
    }

    /**
     * Get the PixelWorld that this PixelActor lives in.
     *
     * @return The PixelWorld the PixelActor lives in
     */
    @Override
    public PixelWorld getWorld() {
        return (PixelWorld) super.getWorld();
    }

    /**
     * Get the world coordinates of a point relative to the top left corner of
     * this PixelActor's image AFTER the transformations of the image.
     *
     * @param offsetX The x offset of the point, relative to the original image
     * @param offsetY The y offset of the point, relative to the original image
     * @return A DoublePair of the global x and y coordinates of the transformed relative point
     */
    public DoublePair getImageOffsetGlobalPosition(int offsetX, int offsetY) {
        if (mirrorX) offsetX = originalWidth - 1 - offsetX;
        if (mirrorY) offsetY = originalHeight - 1 - offsetY;
        DoublePair rotatedOffset = Util.rotateVector(offsetX - centerOfRotationX, offsetY - centerOfRotationY, rotation);
        return new DoublePair(rotatedOffset.x + x, rotatedOffset.y + y);
    }

    /**
     * Whether the PixelActor is visible.
     *
     * @return True if visible, false otherwise
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Set the visibility of the PixelActor, it will not be rendered
     * if visible is false.
     *
     * @param visible Whether the PixelActor should be visible
     */
    public void setVisibility(boolean visible) {
        this.visible = visible;
    }

    /**
     * Set the transparency to a value between 0 and 255 (inclusive).
     *
     * @param transparency The transparency value between 0 ~ 255 (inclusive)
     */
    public void setTransparency(int transparency) {
        transformedImage.setTransparency(transparency);
    }

    /**
     * Return all objects of the specified class within a specified radius
     * around this actor. An object is within range if the distance between its
     * center of rotation and this actor' center of rotation is less than or
     * equal to the given radius.
     * <p>
     * Note: this method ignores objects that do not subclass from PixelActor!
     *
     * @param radius the radius of the circle, in canvas pixels
     * @param cls the class of objects to look for, or {@code null} for all types of objects
     * @return a list of objects in this actor's world of the given class within the given range
     * @see PixelWorld#getObjectsInRange
     */
    @Override
    public <A> List<A> getObjectsInRange(int radius, Class<A> cls) {
        return getWorld().getObjectsInRange(radius, getX(), getY(), cls);
    }

    /**
     * Calculate the distance between this actor and another actor.
     *
     * @param other the other actor to compute the distance with
     * @return the straight-line distance between the two actors' positions (centers of rotation)
     */
    public double getDistanceTo(PixelActor other) {
        return Math.hypot(other.getDoubleX() - x, other.getDoubleY() - y);
    }

    /**
     * Calculate the distance between this actor and a point.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @return the straight-line distance between the actor's position (center of rotation) and the point
     */
    public double getDistanceTo(double x, double y) {
        return Math.hypot(x - this.x, y - this.y);
    }

    /**
     * Calculate the angle between this actor and another actor.
     *
     * @param other the other actor to compute the angle with
     * @return the angle in degrees from -180.0 to 180.0 between the two actors' positions (centers of rotation)
     */
    public double getAngleTo(PixelActor other) {
        return Math.toDegrees(Math.atan2(other.getDoubleY() - y, other.getDoubleX() - x));
    }

    /**
     * Calculate the angle between this actor and a point.
     *
     * @param x the x-coordinate of the point to compute the angle to
     * @param y the y-coordinate of the point to compute the angle to
     * @return the angle in degrees from -180.0 to 180.0 between the actor and the point's positions
     */
    public double getAngleTo(double x, double y) {
        return Math.toDegrees(Math.atan2(y - this.y, x - this.x));
    }
}
