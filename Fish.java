import greenfoot.*;
import java.util.Set;
import java.util.EnumSet;
import java.util.List;

/**
 * An undersea actor and target of Fishers.
 * <p>
 * Each fish possesses a unique list of features, each adding a defined amount
 * of XP value to it when caught. A fish's features are drawn onto its image on
 * top of its body.
 * <p>
 * A subclass of Fish must create a {@link FishSettings} object defining all of
 * its subclass-specific settings, and pass it to this class's constructor.
 *
 * @author Martin Baldwin
 * @author Andrew Wang
 * @version April 2024
 */
public abstract class Fish extends PixelActor {
    /** The distance from which a fish will bite a hook. */
    public static final double HOOK_BITE_DISTANCE = 8.0;

    // When a fish is being protected from extinction, a circle of this color is drawn on top of it
    private static final Color SHIELD_COLOR = new Color(0, 255, 255, 64);

    // Fish subclass-specific settings
    private final FishSettings settings;
    // Whether or not this fish may become protected from extinction
    private final boolean canProtect;

    // All features present on this fish
    private Set<FishFeature> features;
    // Whether or not this fish is currently invincible, protected from extinction
    private boolean isProtected;
    // Hook to follow once caught
    private Hook bittenHook;
    // Behaviour timers
    private Timer rotationTimer;
    private Timer bubbleTimer;
    private Timer eggSpawnTimer;
    // Evolutionary points
    private int evoPoints;
    // Age of this fish, in acts
    private int age;
    // Random speed variation
    private double swimSpeedMultiplier;
    // The number of the same type of fish nearby
    private int nearbyKinsCount;

    // Offset of body image currently in use to compensate for features
    private int bodyOffsetX;
    private int bodyOffsetY;

    /**
     * Creates a new Fish with the given settings and features, as well as a
     * starting number of evolutionary points.
     * <p>
     * This constructor works just like {@link #Fish(FishSettings, int, boolean, FishFeature...)},
     * except that the new Fish may not be protected from extinction.
     *
     * @param settings The settings of the Fish
     * @param evoPoints The number of evolutionary points to start with
     * @param features Any FishFeatures to specifically add to this Fish, or {@code null} for no features beyond what is required from {@code settings}
     * @throws IllegalArgumentException if any of the given features are not allowed on this Fish type
     */
    public Fish(FishSettings settings, int evoPoints, FishFeature... features) {
        this(settings, evoPoints, false, features);
    }

    /**
     * Creates a new Fish with the given settings, features, and starting number
     * of evolutionary points, with the option to provide the ability to be
     * protected from extinction.
     * <p>
     * If {@code protect} is true, the new Fish can become invincible when it is
     * the only fish of its kind left swimming in the world.
     * <p>
     * This constructor adds one random feature from each set of required
     * features defined as per {@link FishSettings#addRequiredFeatureSet},
     * using {@link FishSettings#chooseRequiredFeatures}.
     * <p>
     * Pass any number of additional features to this constructor (including
     * zero, see note below) and all of those features will be added to the new
     * Fish.
     * <p>
     * If {@code features} is empty and not {@code null}, random features from
     * the settings' defined set of allowed features will be chosen to add to
     * the new Fish as well. To prevent random selection of additional features
     * when constructing a Fish with no specific features, pass {@code null} as
     * the third argument to this constructor.
     *
     * @param settings The settings of the Fish
     * @param evoPoints The number of evolutionary points to start with
     * @param protect Whether to allow the Fish to be protected from extinction
     * @param features Any FishFeatures to specifically add to this Fish, or {@code null} for no features beyond what is required from {@code settings}
     * @throws IllegalArgumentException if any of the given features are not allowed on this Fish type
     */
    public Fish(FishSettings settings, int evoPoints, boolean canProtect, FishFeature... features) {
        super(Layer.FISH);
        // Store fish subclass-specific settings
        this.settings = settings;
        this.evoPoints = evoPoints;
        this.canProtect = canProtect;
        isProtected = false;

        this.features = EnumSet.noneOf(FishFeature.class);
        // Add required features
        for (FishFeature feature : settings.chooseRequiredFeatures()) {
            addFeature(feature);
        }
        // Add specified features and random additional features
        if (features != null) {
            for (FishFeature feature : features) {
                addFeature(feature);
            }
            for (FishFeature feature : settings.getAllowedFeatures()) {
                if (Util.randDouble(0, 1) < feature.getChance()) {
                    addFeature(feature);
                }
            }
        }
        updateImage();

        rotationTimer = new Timer(settings.getAverageTurnInterval());
        eggSpawnTimer = new Timer((int) (settings.getEggSpawnFrequency() * Util.randDouble(0.8, 1.2)));
        bubbleTimer = new Timer(Util.randInt(240, 480));
        age = 0;
        swimSpeedMultiplier = Util.randDouble(0.8, 1.2);
    }

    /**
     * Adds a FishFeature to this Fish.
     * <p>
     * This method is for use within the constructor only. It does not update
     * the fish's image.
     *
     * @param feature the FishFeature to add
     * @throws IllegalArgumentException if the given feature is not allowed on this Fish type
     */
    private void addFeature(FishFeature feature) {
        if (!settings.isFeatureAllowed(feature)) {
            throw new IllegalArgumentException("FishFeature " + feature + " is not allowed on this Fish type");
        }
        // Do nothing if this feature is incompatible with this fish's existing features
        if (!feature.isCompatibleWith(features)) {
            return;
        }
        features.add(feature);
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
     * Returns a set of all features on this Fish.
     *
     * @return a new set containing all FishFeatures on this fish
     */
    public Set<FishFeature> getFeatureSet() {
        return EnumSet.copyOf(features);
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
     * Get the settings of the fish.
     *
     * @return The FishSettings object
     */
    public FishSettings getSettings() {
        return settings;
    }

    /**
     * Gets the age of this fish, in acts.
     *
     * @return the number of acts that this fish has existed in its world for
     */
    public int getAge() {
        return age;
    }

    /**
     * Tests if this fish is currently protected from extinction.
     *
     * @return true if this fish may not be caught, false for a normal fish
     */
    public boolean isProtected() {
        return isProtected;
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
        if (features.isEmpty() && !isProtected) {
            setImage(bodyImage);
            return;
        }

        // Create an image of appropriate size to fit this fish with all of its features
        // Keep track of the extreme locations of any feature relative to the body
        int left, right, top, bottom;
        int shieldSize = Math.max(bodyImage.getWidth(), bodyImage.getHeight()) + 4;
        if (!isProtected) {
            left = 0;
            right = bodyImage.getWidth();
            top = 0;
            bottom = bodyImage.getHeight();
        } else {
            // Make room for shield
            left = (bodyImage.getWidth() - shieldSize) / 2;
            right = left + shieldSize;
            top = (bodyImage.getHeight() - shieldSize) / 2;
            bottom = top + shieldSize;
        }
        for (FishFeature feature : features) {
            // Get the leftmost and rightmost pixel locations of this feature relative to the body
            IntPair point = settings.getFeaturePoint(feature);
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
            IntPair point = settings.getFeaturePoint(feature);
            image.drawImage(feature.getImage(), point.x - left, point.y - top);
        }
        if (isProtected) {
            // Shield is a circle on top of the fish
            image.setColor(SHIELD_COLOR);
            image.fillOval(0, 0, shieldSize, shieldSize);
        }
        setImage(image);
        setCenterOfRotation(bodyImage.getWidth() / 2 - left, bodyImage.getHeight() / 2 - top);
        // Store body image offset for other point calculations
        bodyOffsetX = -left;
        bodyOffsetY = -top;
    }

    /**
     * Gets the current horizontal offset of this fish's body image, relative to
     * its full image.
     *
     * @return the number of pixels from the left of this fish's actor image to the left of its body image
     */
    public int getBodyOffsetX() {
        return bodyOffsetX;
    }

    /**
     * Gets the current vertical offset of this fish's body image, relative to
     * its full image.
     *
     * @return the number of pixels from the top of this fish's actor image to the top of its body image
     */
    public int getBodyOffsetY() {
        return bodyOffsetY;
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
        IntPair catchOffset = settings.getCatchOffset();
        // Transform the catch point into world space
        return getImageOffsetGlobalPosition(bodyOffsetX + catchOffset.x, bodyOffsetY + catchOffset.y);
    }

    /**
     * Call this in act(). Tests for hooks within the defined range.
     */
    public final void lookForHook() {
        // If already bitten a hook or protected from extinction, do nothing
        if (bittenHook != null || isProtected) {
            return;
        }
        for (Hook hook : getWorld().getObjects(Hook.class)) {
            if (hook.isOccupied()) continue;
            DoublePair catchPoint = getCatchPoint();
            DoublePair fishBitePoint = hook.getBitePoint();
            double distance = Math.hypot(catchPoint.x - fishBitePoint.x, catchPoint.y - fishBitePoint.y);
            if (distance < HOOK_BITE_DISTANCE) {
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
        if (hook.getTier().ordinal() + 1 >= settings.getTier()) {
            bittenHook = hook;
            hook.reelIn(this);
            // Set center of rotation (location of actor position) to catch point, relative to full image
            IntPair catchOffset = settings.getCatchOffset();
            setCenterOfRotation(bodyOffsetX + catchOffset.x, bodyOffsetY + catchOffset.y);
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
        setRotation((bittenHook.getRotation() + 90) * (getMirrorX() ? 1 : -1));
    }

    /**
     * Tests if this fish has been hooked by a fisher.
     *
     * @return true if this fish is currently on a hook, false otherwise
     */
    public boolean isHooked() {
        return bittenHook != null;
    }

    @Override
    public void act() {
        // Only move with hook and do nothing else if it has already bitten a hook
        if (bittenHook != null) {
            attachToHook();
            return;
        }

        // Protect this fish from extinction when it is the only fish of its kind left in the world
        if (canProtect) {
            boolean wasProtected = isProtected;
            isProtected = true;
            for (Fish other : getWorld().getObjects(getClass())) {
                if (other != this && !other.isHooked()) {
                    isProtected = false;
                    break;
                }
            }
            if (isProtected != wasProtected) {
                updateImage();
            }
        }

        // Feature-specific behaviour
        for (FishFeature feature : features) {
            feature.actOn(this);
            if (getWorld() == null) {
                return;
            }
        }

        // React to other fish features
        if (!hasFeature(FishFeature.ANGLER_SOCK)) {
            repelFromSocks();
        }

        // Standard behaviour
        swim();
        reproduce();
        spawnBubbles();
        lookForHook();
        age++;
    }

    /**
     * Call this in act(). Makes the fish swim.
     * <p>May be overridden to implement special swim patterns.</p>
     */
    protected void swim() {
        move(settings.getSwimSpeed() * swimSpeedMultiplier);
        if (rotationTimer.ended()) {
            int maxAngle = settings.getMaxTurnDegrees();
            setHeading(getHeading() + Util.randInt(-maxAngle, maxAngle));
            rotationTimer.restart(settings.getAverageTurnInterval());
        }
        doBoidBehavior();

        checkBounds();
        setMirrorX(getHeading() > 90 && getHeading() < 270);
        // Heading with respect to mirrorX
        double realHeading = getHeading() + (getMirrorX() ? 180 : 0);
        setRotation(Util.interpolateAngle(getRotation(), realHeading, 0.05));
    }

    /**
     * Attempt to spawn eggs.
     */
    private void reproduce() {
        if (eggSpawnTimer.ended() && nearbyKinsCount < 8) {
            spawnEgg();
            eggSpawnTimer.restart((int) (settings.getEggSpawnFrequency() * Util.randDouble(0.8, 1.2)));
        }
    }

    /**
     * Do boid-like behavior, fish bunch up with others of the same type,
     * forming schools of fish.
     */
    private void doBoidBehavior() {
        if (hasFeature(FishFeature.HAT_PARTY)) return;
        if (hasFeature(FishFeature.ANGLER_SOCK)) return;

        nearbyKinsCount = 0;
        double averageSin = 0;
        double averageCos = 0;
        int averageX = 0;
        int averageY = 0;
        for (Fish other : getWorld().getObjects(getClass())) {
            if (other == this) continue;
            if (other.hasFeature(FishFeature.HAT_PARTY) || other.hasFeature(FishFeature.ANGLER_SOCK)) continue;
            double distance = getDistanceTo(other);
            if (distance < 40) {
                averageSin += Math.sin(Math.toRadians(other.getHeading()));
                averageCos += Math.cos(Math.toRadians(other.getHeading()));
                averageX += other.getX();
                averageY += other.getY();
                nearbyKinsCount++;
                if (distance < (this.getOriginalHeight() + other.getOriginalHeight()) / 2) {
                    // Separation
                    setHeading(Util.interpolateAngle(getHeading(), -getAngleTo(other), 0.008));
                }
            }
        }
        if (nearbyKinsCount != 0) {
            averageSin /= nearbyKinsCount;
            averageCos /= nearbyKinsCount;
            averageX /= nearbyKinsCount;
            averageY /= nearbyKinsCount;
            // Alignment
            setHeading(Util.interpolateAngle(getHeading(), Math.toDegrees(Math.atan2(averageSin, averageCos)), 0.008));
            // Cohesion
            setHeading(Util.interpolateAngle(getHeading(), getAngleTo(averageX, averageY), 0.005));
        }
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
        if (getY() < SimulationWorld.SEA_SURFACE_Y) {
            if (getHeading() > 180) {
                setHeading(getHeading() > 270 ? 0 : 180);
            }
            setLocation(getDoubleX(), SimulationWorld.SEA_SURFACE_Y);
        } else if (getY() > SimulationWorld.SEA_FLOOR_Y) {
            if (getHeading() < 180) {
                setHeading(getHeading() < 90 ? 0 : 180);
            }
            setLocation(getDoubleX(), SimulationWorld.SEA_FLOOR_Y);
        } else if (getY() < settings.getMinDepth()) {
            // Try to get back to its depth range by slowly turning downwards
            setHeading(Util.interpolateAngle(getHeading(), 90, 0.02));
        } else if (getY() > settings.getMaxDepth()) {
            // Try to get back to its depth range by slowly turning upwards
            setHeading(Util.interpolateAngle(getHeading(), -90, 0.02));
        } else {
            // Try to return to going straight by slowly turning to be horizontal
            int forward = getMirrorX() ? 180 : 0;
            setHeading(Util.interpolateAngle(getHeading(), forward, 0.005));
        }

        if (getX() < 0) {
            // Flip angle across y-axis
            setHeading(180 - getHeading());
            setRotation(getHeading());
            setLocation(0, getDoubleY());
        } else if (getX() > getWorld().getWidth()) {
            // Flip angle across y-axis
            setHeading(180 - getHeading());
            // + 180 because mirroring
            setRotation(getHeading() + 180);
            setLocation(getWorld().getWidth(), getDoubleY());
        }
    }

    /**
     * Try to swim away from any fishes with socks within a certain radius.
     */
    protected void repelFromSocks() {
        for (Fish other : ((SimulationWorld) getWorld()).getFishesByFeature(FishFeature.ANGLER_SOCK)) {
            // Find the world position of the sock itself, offset from the feature, body image, and fish position
            IntPair sockOffset = other.getSettings().getFeaturePoint(FishFeature.ANGLER_SOCK);
            DoublePair sockPos = other.getImageOffsetGlobalPosition(other.getBodyOffsetX() + sockOffset.x + 10, other.getBodyOffsetY() + sockOffset.y + 9);
            // Repel from this sock only if it is close enough
            double distance = getDistanceTo(sockPos.x, sockPos.y);
            if (distance > 32) continue;

            // Calculate the target angle required to optimally avoid the sock
            double avoidanceAngle = getAngleTo(other) + 180;
            // Give it a random extra bit of rotation
            avoidanceAngle += Util.randInt(-10, 10);
            avoidanceAngle %= 360;
            // Minimum angle of 40 degrees above and below horizontal
            avoidanceAngle = Math.max(avoidanceAngle, 40);
            avoidanceAngle = Math.min(avoidanceAngle, 320);
            // How much the Fish wants to avoid the sock as a percentage
            // i.e. how close it is to the sock
            // Clamp it to avoid too extreme values
            double eagerness = Math.min(Math.max(1 - distance / 32, 0.2), 0.8);
            // Interpolate towards this new angle
            setHeading(Util.interpolateAngle(getHeading(), avoidanceAngle, 0.24 * eagerness));
            // Prevent the fish from turning too vertical while avoiding the sock
            double heading = getHeading() % 360;
            if (heading <= 90) {
                setHeading(Math.min(heading, 60));
            } else if (heading <= 180) {
                setHeading(Math.max(heading, 120));
            } else if (heading <= 270) {
                setHeading(Math.min(heading, 240));
            } else {
                setHeading(Math.max(heading, 300));
            }
            // Finally move the fish based on how eager it is to get away
            move(settings.getSwimSpeed() * 3 * eagerness);
        }
    }

    /**
     * Spawn bubbles at the Fish's mouth location (catchPoint).
     */
    protected void spawnBubbles() {
        if (bubbleTimer.ended()) {
            DoublePair catchPoint = getCatchPoint();
            int numOfBubbles = Util.randInt(1, 4);
            for (int i = 0; i < numOfBubbles; i++) {
                getWorld().addObject(new Bubble(), (int) catchPoint.x, (int) catchPoint.y);
            }
            bubbleTimer.restart(Util.randInt(180, 480));
        }
    }

    /**
     * Spawn a random number of eggs at the location of the fish.
     */
    private void spawnEgg() {
        int gain = settings.getEvoPointGain();
        evoPoints += Util.randInt((int) (gain * Util.randDouble(0.8, 1.2)));

        UserSettings userSettings = ((SimulationWorld) getWorld()).getUserSettings();
        int numOfEggs = Util.randInt(1, userSettings.getEggSpawnAmount());

        for (int i = 0; i < numOfEggs; i++) {
            // Determine whether the fish should evolve based on evolution chance
            Egg.Size size;
            Class<? extends Fish> hatchClass;
            int childEvoPoints = evoPoints;
            boolean canEvolve = evoPoints >= 100 * userSettings.getEvoPointThreshold();
            boolean willEvolve = Util.randDouble(0, 1) < settings.getEvolutionChance();
            if (canEvolve && willEvolve) {
                // Increase egg size and hatch a random evolution of this fish type
                size = settings.getEggSize().nextSize();
                List<Class<? extends Fish>> evolutions = settings.getEvolutions();
                if (evolutions.size() > 0) {
                    hatchClass = evolutions.get(Util.randInt(0, evolutions.size() - 1));
                } else {
                    hatchClass = getClass();
                }
                childEvoPoints = 0;
            } else {
                // Keep egg size and hatch the same fish type as this fish
                size = settings.getEggSize();
                hatchClass = getClass();
            }

            Egg egg = new Egg(size, settings.getEggColor(), hatchClass, childEvoPoints);
            getWorld().addObject(egg, getX(), getY());
        }
    }
}
