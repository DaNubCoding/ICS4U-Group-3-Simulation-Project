import greenfoot.*;
import java.util.List;
import java.util.ListIterator;

/**
 * A type of world whose display image is an upscaled version of its canvas
 * image.
 * <p>
 * All rendering should be done to a PixelWorld's canvas, which may then be
 * displayed to the user in the Greenfoot window by calling {@link #updateImage}.
 * <p>
 * This class provides tools to handle rendering objects of the PixelActor class
 * in a specific order. This render order is separate from the paint order of
 * all Actor objects defined by {@link #setPaintOrder}.
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

    // PixelActor subclasses in order of rendering, from bottom to top
    private Class<? extends PixelActor>[] renderOrder;

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
        renderOrder = null;
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
     * Sets the order in which PixelActors in this world will be rendered. Order
     * is specified by class: objects of classes listed later will be drawn on
     * top of objects of classes listed earlier.
     * <p>
     * Objects of classes not listed will be drawn on top of all other objects,
     * in the order of {@code getObjects(PixelActor.class)}.
     * <p>
     * The render order can be unset by passing {@code null}.
     *
     * @param classes the classes of PixelActors in render order, from bottom to top, or {@code null}
     */
    public void setRenderOrder(Class<? extends PixelActor>... classes) {
        renderOrder = classes;
    }

    /**
     * Renders all PixelActors currently in this world according to the current
     * render order. Actors are rendered by calling the {@link PixelActor#render}
     * method on this world's canvas.
     * <p>
     * If no render order is set, actors are rendered in the order of
     * {@code getObjects(PixelActor.class)}.
     *
     * @see #setRenderOrder
     */
    public void renderPixelActors() {
        List<PixelActor> actorsToDraw = getObjects(PixelActor.class);
        // Render actors in the set order
        if (renderOrder != null) {
            for (Class<? extends PixelActor> cls : renderOrder) {
                // Render actors only of the current class and remove them from the list
                for (ListIterator<PixelActor> iter = actorsToDraw.listIterator(); iter.hasNext();) {
                    PixelActor actor = iter.next();
                    if (!cls.isInstance(actor)) {
                        continue;
                    }
                    actor.render(canvas);
                    iter.remove();
                }
            }
        }
        // Render remaining actors that are not in the set order (or all of them if no order was set)
        for (PixelActor actor : actorsToDraw) {
            actor.render(canvas);
        }
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
}
