import greenfoot.*;
import java.util.Set;
import java.util.EnumSet;
import java.util.Map;
import java.util.Collections;
import java.util.List;

/**
 * An undersea actor and target of Fishers.
 * <p>
 * Each fish possesses a list of features, which may change as it evolves, each
 * adding a defined amount of XP value to a fish. A fish's features are drawn
 * onto its image on top of its body.
 * <p>
 * A subclass of Fish must create a {@link FishSettings} object defining all of
 * its subclass-specific settings, and pass it to this class's constructor.
 *
 * @author Martin Baldwin
 * @author Andrew Wang
 * @version April 2024
 */
public abstract class Fish extends PixelActor {
    // Fish subclass-specific settings
    private final FishSettings settings;
    // All features present on this fish
    private Set<FishFeature> features;
    private Hook bittenHook;
    protected Timer rotationTimer;

    /**
     * Creates a new Fish with the given features.
     * <p>
     * Pass any number of arguments of type FishFeature to this constructor
     * (including zero) or a FishFeature array and all of the features will be
     * added to the new Fish.
     *
     * @param features any FishFeatures to add to this Fish
     */
    public Fish(FishSettings settings, FishFeature... features) {
        super();
        // Store fish subclass-specific settings
        this.settings = settings;

        // Add features
        this.features = EnumSet.noneOf(FishFeature.class);
        Collections.addAll(this.features, features);
        updateImage();

        rotationTimer = new Timer(settings.getAverageTurnInterval());
    }

    /**
     * Adds a FishFeature to this Fish and updates its image accordingly.
     *
     * @param feature the FishFeature to add
     */
    public void addFeature(FishFeature feature) {
        features.add(feature);
        updateImage();
    }

    /**
     * Removes a FishFeature from this Fish and updates its image accordingly.
     *
     * @param feature the FishFeature to remove
     */
    public void removeFeature(FishFeature feature) {
        features.remove(feature);
        updateImage();
    }

    /**
     * Tests if the given feature is present on this Fish.
     *
     * @param feature the FishFeature to check for
     * @return true if this fish's feature list contains the given feature, false otherwise
     */
    public boolean hasFeature(FishFeature feature) {
        return features.contains(feature);
    }

    /**
     * Gets the total XP value of this Fish, including all features.
     *
     * @return the amount of XP value this Fish is worth
     */
    public int getValue() {
        int sum = settings.getBaseValue();
        for (FishFeature feature : features) {
            sum += feature.getValue();
        }
        return sum;
    }

    /**
     * Sets this Fish's image to a GreenfootImage with this Fish's body and
     * features drawn on it.
     * <p>
     * The new image will be just large enough to contain this Fish. All
     * features will be drawn on top of the body at their appropriate locations.
     */
    private void updateImage() {
        GreenfootImage bodyImage = settings.getBodyImage();
        // If there are no features, the existing body image is sufficient
        if (features.isEmpty()) {
            setImage(bodyImage);
            return;
        }

        // Create an image of appropriate size to fit this fish with all of its features
        Map<FishFeature, IntPair> featurePoints = settings.getFeaturePoints();
        // Keep track of the extreme locations of any feature relative to the body
        int left = 0;
        int right = bodyImage.getWidth();
        int top = 0;
        int bottom = bodyImage.getHeight();
        for (FishFeature feature : features) {
            // Get the leftmost and rightmost pixel locations of this feature relative to the body
            IntPair point = featurePoints.get(feature);
            int featLeft = point.x;
            int featRight = featLeft + feature.getImage().getWidth();
            if (featLeft < left) {
                left = featLeft;
            } else if (featRight > right) {
                right = featRight;
            }
            // Get the topmost and bottommost pixel locations of this feature relative to the body
            int featTop = point.y;
            int featBottom = featTop + feature.getImage().getHeight();
            if (featTop < top) {
                top = featTop;
            } else if (featBottom > bottom) {
                bottom = featBottom;
            }
        }
        GreenfootImage image = new GreenfootImage(right - left, bottom - top);

        // Draw the fish
        image.drawImage(bodyImage, -left, -top);
        for (FishFeature feature : features) {
            IntPair point = featurePoints.get(feature);
            image.drawImage(feature.getImage(), point.x - left, point.y - top);
        }
        setImage(image);
        setCenterOfRotation(bodyImage.getWidth() / 2 - left, bodyImage.getHeight() / 2 - top);
    }

    /**
     * Gets the coordinates of the point in the world where this Fish may be
     * caught from.
     *
     * @return a DoublePair describing this Fish's catch point in world space
     */
    public DoublePair getCatchPoint() {
        // Find where the catch point lies relative to this fish's current image
        // since features may change the image's dimensions
        GreenfootImage bodyImage = settings.getBodyImage();
        IntPair catchOffset = settings.getCatchOffset();
        int catchX = getCenterOfRotationX() - bodyImage.getWidth() / 2 + catchOffset.x;
        int catchY = getCenterOfRotationY() - bodyImage.getHeight() / 2 + catchOffset.y;
        // Transform the catch point into world space
        return getImageOffsetGlobalPosition(catchX, catchY);
    }

    /**
     * Call this in act(). Tests for hooks within the defined range.
     */
    public final void lookForHook() {
        List<Hook> hooks = getWorld().getObjects(Hook.class);
        for (Hook hook : hooks) {
            DoublePair catchPoint = getCatchPoint();
            DoublePair fishBitePoint = hook.getBitePoint();
            double distance = Math.hypot(catchPoint.x - fishBitePoint.x, catchPoint.y - fishBitePoint.y);
            if (distance < 8) {
                respondToHook(hook);
            }
        }
    }

    /**
     * Called when a hook gets within defined range the fish's mouth.
     * <p>Usually, simply calls biteIfMatchingTier(). May be overridden to
     * implement other behavior when detecting a hook.</p>
     *
     * @param hook The hook that has been detected
     */
    public void respondToHook(Hook hook) {
        biteIfMatchingTier(hook);
    }

    /**
     * Bite the hook if the tiers match.
     *
     * @param hook The hook that has been detected
     */
    protected final void biteIfMatchingTier(Hook hook) {
        // Unimplemented condition until rod tiers and fish tiers are implemented
        if (true) {
            bittenHook = hook;
            hook.reelIn();
        }
    }

    /**
     * Call this in act(). If hooked, moves the Fish to the location of the hook
     * in order to be reeled in.
     */
    protected final void attachToHook() {
        // If it has not bitten a hook yet, don't run the method
        if (bittenHook == null) return;

        // Move fish onto hook
        DoublePair newPos = bittenHook.getBitePoint();
        setLocation(newPos.x, newPos.y);
        setRotation(90 * (getMirrorX() ? 1 : -1));

        // Remove once hook is gone (reached boat)
        if (bittenHook.getWorld() == null) {
            bittenHook = null;
            getWorld().removeObject(this);
        }
    }

    /**
     * Call this in act(). Makes the fish swim.
     * <p>May be overridden to implement special swim patterns.</p>
     */
    protected void swim() {
        // Don't try to swim if it has already bitten a hook
        if (bittenHook != null) return;

        move(settings.getSwimSpeed());
        if (rotationTimer.ended()) {
            int maxAngle = settings.getMaxTurnDegrees();
            setHeading(getHeading() + Util.randInt(-maxAngle, maxAngle));
            rotationTimer.restart(settings.getAverageTurnInterval());
        }

        checkBounds();

        // Heading with respect to mirrorX
        double realHeading = getHeading() + (getMirrorX() ? 180 : 0);
        setRotation(Util.interpolateAngle(getRotation(), realHeading, 0.05));
    }

    /**
     * Obtain a number of frames within ±20% of the average number of
     * frames before the next rotation.
     * <p>This is used to set the time of the rotationTimer.</p>
     */
    private int getNextTurnInterval() {
        int avg = settings.getAverageTurnInterval();
        return Util.randInt((int) (avg * 0.8), (int) (avg * 1.2));
    }

    /**
     * Restrict the fish to be within the defined depths and the world.
     * <p>Between the left and right edge of the world and the minimum
     * and maximum depths.</p>
     */
    protected void checkBounds() {
        if (getY() < settings.getMinDepth()) {
            // Try to get back to its depth range by slowly turning downwards
            setHeading(Util.interpolateAngle(getHeading(), 90, 0.0025));
        } else if (getY() > settings.getMaxDepth()) {
            // Try to get back to its depth range by slowly turning upwards
            setHeading(Util.interpolateAngle(getHeading(), -90, 0.0025));
        } else {
            // Try to return to going straight by slowly turning to be horizontal
            int forward = getMirrorX() ? 180 : 0;
            setHeading(Util.interpolateAngle(getHeading(), forward, 0.0025));
        }

        if (getX() < 0) {
            // Flip angle across y-axis
            setHeading(180 - getHeading());
            setMirrorX(false);
            setRotation(getHeading());
        } else if (getX() > getWorld().getWidth()) {
            // Flip angle across y-axis
            setHeading(180 - getHeading());
            setMirrorX(true);
            // + 180 because mirroring
            setRotation(getHeading() + 180);
        }
    }
}
