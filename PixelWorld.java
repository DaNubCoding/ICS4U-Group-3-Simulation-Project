import greenfoot.*;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;

/**
 * A type of world whose display image is an upscaled version of its canvas
 * image.
 * <p>
 * All rendering should be done to a PixelWorld's canvas, which may then be
 * displayed to the user in the Greenfoot window by calling {@link #updateImage}.
 * <p>
 * This class handles separating objects of the PixelActor class by layer for
 * rendering. This render order is separate from the paint order of all Actor
 * objects defined by {@link #setPaintOrder}.
 *
 * @author Martin Baldwin
 * @author Andrew Wang
 * @version April 2024
 */
public abstract class PixelWorld extends World {
    /** The scale factor of all PixelWorld display images. */
    public static final int PIXEL_SCALE = 4;

    private final int worldWidth;
    private final int worldHeight;
    private final GreenfootImage canvas;

    // All actors in this world mapped by their classes, for efficient access
    private Map<Class<? extends Actor>, List<Actor>> actorMap;

    // PixelActor objects by their assigned layer, for rendering order
    private Map<Layer, List<PixelActor>> actorsByLayer;

    private GreenfootImage fadeImage;
    private double fadePercentage;
    // negative for fade in, positive for fade out
    private double fadeSpeed;

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
        actorMap = new HashMap<Class<? extends Actor>, List<Actor>>();
        actorsByLayer = new EnumMap<Layer, List<PixelActor>>(Layer.class);
        for (Layer layer : Layer.values()) {
            actorsByLayer.put(layer, new ArrayList<PixelActor>());
        }

        fadeImage = new GreenfootImage(worldWidth, worldHeight);
        fadeImage.setColor(Color.BLACK);
        fadeImage.fill();
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
        renderFade();
        GreenfootImage scaled = new GreenfootImage(canvas);
        scaled.scale(worldWidth * PIXEL_SCALE, worldHeight * PIXEL_SCALE);
        setBackground(scaled);
    }

    private void renderFade() {
        fadePercentage += fadeSpeed;
        fadePercentage = Math.max(Math.min(fadePercentage, 1.0), 0.0);
        fadeImage.setTransparency((int) (255 * fadePercentage));
        if (fadePercentage == 0.0) return;
        canvas.drawImage(fadeImage, 0, 0);
    }

    /**
     * Renders all PixelActors currently in this world by layer. Actors are
     * rendered by calling the {@link PixelActor#render} method on this world's
     * canvas.
     * <p>
     * Render order is defined by the order of layers in the {@link Layer} enum.
     *
     * @see Layer
     */
    public void renderPixelActors() {
        for (List<PixelActor> layerActors : actorsByLayer.values()) {
            for (PixelActor actor : layerActors) {
                actor.render(canvas);
            }
        }
    }

    /**
     * Adds an Actor to this world, storing it for efficient access.
     *
     * @param object the object to add
     * @param x the x coordinate of the location where the object is added
     * @param y the y coordinate of the location where the object is added
     * @see World#addObject
     */
    @Override
    public void addObject(Actor object, int x, int y) {
        // Add this object to the list for its class
        List<Actor> list = actorMap.get(object.getClass());
        if (list == null) {
            list = new ArrayList<Actor>();
            actorMap.put(object.getClass(), list);
        }
        list.add(object);

        // Add actors to the list for their layers
        if (object instanceof PixelActor) {
            PixelActor actor = (PixelActor) object;
            actorsByLayer.get(actor.getLayer()).add(actor);
        }

        super.addObject(object, x, y);
    }

    /**
     * Removes an Actor from this world.
     *
     * @param object the object to remove
     * @see World#removeObject
     */
    @Override
    public void removeObject(Actor object) {
        super.removeObject(object);

        // Remove this object from the list for its class
        actorMap.get(object.getClass()).remove(object);

        // Remove actors from the list for their layers
        if (object instanceof PixelActor) {
            PixelActor actor = (PixelActor) object;
            actorsByLayer.get(actor.getLayer()).remove(actor);
        }
    }

    /**
     * Gets all objects of a particular class in this world.
     * <p>
     * This method is significantly more efficient than {@link World#getObjects},
     * making use of a map of classes to their actor instances.
     *
     * @param cls the class of objects to look for, or {@code null} to find all objects
     * @return a list of objects in this world that are instances of the given class
     * @see World#getObjects
     */
    @Override
    public <A> List<A> getObjects(Class<A> cls) {
        List result = new ArrayList();
        if (cls == null) {
            // Add all objects to result list
            for (List<Actor> list : actorMap.values()) {
                result.addAll(list);
            }
        } else {
            // Add all objects of classes that are subclasses of or the same as the given class
            for (Class<? extends Actor> keyCls : actorMap.keySet()) {
                if (cls.isAssignableFrom(keyCls)) {
                    result.addAll(actorMap.get(keyCls));
                }
            }
        }
        return result;
    }

    /**
     * Return all objects of the specified class within a specified radius
     * around a point. An object is within range if the distance between its
     * center of rotation and the given point is less than or equal to the
     * given radius.
     * <p>
     * Note: this method ignores objects that do not subclass from PixelActor!
     *
     * @param radius the radius of the circle, in canvas pixels
     * @param cls the class of objects to look for, or {@code null} for all types of objects
     * @return a list of objects in this actor's world of the given class within the given range
     */
    public <A> List<A> getObjectsInRange(int radius, int x, int y, Class<A> cls) {
        List<A> result = new ArrayList<A>();
        for (A obj : getObjects(cls)) {
            if (!(obj instanceof PixelActor)) {
                continue;
            }
            PixelActor actor = (PixelActor) obj;
            if (actor.getDistanceTo(x, y) <= radius) {
                result.add(obj);
            }
        }
        return result;
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
     * Start the fade-in process.
     * <p>This will cause the world to fade in from black.</p>
     * <p>If in the process of fading, this does nothing.</p>
     *
     * @param speed The speed the world will fade in
     */
    public void triggerFadeIn(double speed) {
        if (fadePercentage != 1.0 && fadePercentage != 0.0) return;
        fadePercentage = 1.0;
        fadeSpeed = -speed;
    }

    /**
     * Start the fade-out process.
     * <p>This will cause the world to fade to black.</p>
     * <p>If in the process of fading, this does nothing.</p>
     *
     * @param speed The speed the world will fade out
     */
    public void triggerFadeOut(double speed) {
        if (fadePercentage != 0.0) return;
        fadePercentage = 0.0;
        fadeSpeed = speed;
    }

    /**
     * Whether the world has fully faded in.
     *
     * @return True if done fading in, false otherwise
     */
    public boolean isFadeInComplete() {
        if (fadeSpeed >= 0) return false;
        return fadePercentage == 0.0;
    }

    /**
     * Whether the world has fully faded out (fully black).
     *
     * @return True if done fading out, false otherwise
     */
    public boolean isFadeOutComplete() {
        if (fadeSpeed <= 0) return false;
        return fadePercentage == 1.0;
    }
}
