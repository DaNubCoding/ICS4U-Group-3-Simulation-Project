import greenfoot.*;

/**
 * The fisher boats.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public class Fisher extends PixelActor {
    /**
     * Different tiers of boats that the Fishers may own,
     * with certain attributes specific to each tier.
     *
     * @author Martin Baldwin
     * @author Andrew Wang
     * @version April 2024
     */
    public enum BoatTier {
        WOODEN(new IntPair(30, 16), 1.3, new IntPair(19, 22)),
        STEEL(new IntPair(32, 11), 0.5, new IntPair(20, 17)),
        YACHT(new IntPair(38, 17), 0.1, new IntPair(21, 25));

        public final String imagePrefix;
        public final IntPair rodOffset;
        public final double driftMagnitudeFactor;
        public final IntPair centerOfRotation;

        /**
         * @param rodOffset The offset of the rod relative to the boat's image
         * @param driftMagnitudeFactor The factor to multiply any drift-related movement by
         * @param centerOfRotation The center of rotation of the boat relative to the boat's image
         */
        private BoatTier(IntPair rodOffset, double driftMagnitudeFactor, IntPair centerOfRotation) {
            imagePrefix = "boats/" + name().toLowerCase() + "_";
            this.rodOffset = rodOffset;
            this.driftMagnitudeFactor = driftMagnitudeFactor;
            this.centerOfRotation = centerOfRotation;
        }

        /**
         * Get the BoatTier one level above the current one.
         *
         * @return The next BoatTier
         */
        public BoatTier nextTier() {
            try {
                return BoatTier.values()[ordinal() + 1];
            } catch (IndexOutOfBoundsException err) {
                return this;
            }
        }
    }

    // 1 or 2
    private int side;
    // An invisible point the boat will return to even when drifting slightly
    private double anchorX;
    private double anchorY;
    // The position that the boat will attempt to move towards, gradually
    private double targetX;
    private double targetY;
    private int leftBound;
    private int rightBound;

    private BoatTier boatTier;
    private int exp;

    private Timer driftTimer;
    private double driftMagnitude;

    private Timer moveTimer;

    private FishingRod fishingRod;

    public Fisher(int side) {
        super();
        this.side = side;
        setBoatTier(BoatTier.WOODEN);
        exp = 0;

        IntPair center = boatTier.centerOfRotation;
        setCenterOfRotation(center.x, center.y);
        if (side == 2) {
            setMirrorX(true);
        }

        driftTimer = new Timer(0);
        initNextDrift();

        moveTimer = new Timer(0);
        initNextDrive();

        fishingRod = new FishingRod(this);
    }

    /**
     * Get which side this fisher is on.
     *
     * @return 1 if left, 2 if right
     */
    public int getSide() {
        return side;
    }

    /**
     * Get the position where this fisher's rod should be placed, relative to
     * the world.
     *
     * @return a DoublePair of x and y coordinates for this fisher's rod location
     */
    public DoublePair getRodPosition() {
        IntPair rodOffset = boatTier.rodOffset;
        return getImageOffsetGlobalPosition(rodOffset.x, rodOffset.y);
    }

    public void addedToWorld(World world) {
        anchorX = getX();
        anchorY = getY();

        if (side == 1) {
            leftBound = 30;
            rightBound = getWorld().getWidth() / 2 - 40;
            world.addObject(fishingRod, getX(), getY());
        } else {
            leftBound = getWorld().getWidth() / 2 + 40;
            rightBound = getWorld().getWidth() - 30;
            world.addObject(fishingRod, getX(), getY());
        }
    }

    public void act() {
        drift();
        drive();
        move();
        checkBounds();

        // Temporary test
        if (Util.randInt(0, 3000) == 0) {
            incrementBoatTier();
            fishingRod.incrementRodTier();
        }
    }

    /**
     * Move the boat in ways that don't significantly affect it but
     * make it look more realistic.
     */
    private void drift() {
        double magnitude = driftMagnitude * boatTier.driftMagnitudeFactor;
        setRotation(Math.sin(driftTimer.progress() * Math.PI * 2) * magnitude + 360);

        // Drift in a elliptical pattern, matching the rotation
        double driftOffsetX = Math.cos(driftTimer.progress() * Math.PI * 2) * magnitude * 0.8;
        double driftOffsetY = Math.sin(driftTimer.progress() * Math.PI * 2) * magnitude * 0.4;

        // Find the target location
        targetX = anchorX + driftOffsetX;
        targetY = anchorY + driftOffsetY;

        // Reset drift-related variables when a drift sequence ends
        if (driftTimer.ended()) {
            initNextDrift();
        }
    }

    /**
     * Reset the drift-related variables to random values.
     */
    private void initNextDrift() {
        driftTimer.restart(Util.randInt(160, 420));
        driftMagnitude = Util.randDouble(2, 6);
    }

    /**
     * Intentionally drive the boat a certain distance away.
     */
    private void drive() {
        if (moveTimer.ended()) {
            initNextDrive();
        }
    }

    /**
     * Reset the move-related variables to random values.
     */
    private void initNextDrive() {
        moveTimer.restart(Util.randInt(420, 720));
        anchorX += Util.randInt(10, 15) * (Util.randInt(1) == 0 ? -1 : 1);
    }

    /**
     * Move the boat towards where it wants to be.
     */
    private void move() {
        double newX = getDoubleX() + (targetX - getDoubleX()) * 0.02;
        double newY = getDoubleY() + (targetY - getDoubleY()) * 0.02;
        setLocation(newX, newY);
    }

    /**
     * Restrict the boat to its area.
     */
    private void checkBounds() {
        if (getDoubleX() < leftBound) {
            setLocation(leftBound, getDoubleY());
            anchorX += 5;
        } else if (getDoubleX() > rightBound) {
            setLocation(rightBound, getDoubleY());
            anchorX -= 5;
        }
    }

    /**
     * Set the baot's tier to a new tier, update image accordingly.
     *
     * @param boatTier The tier of the boat as a boatTier enum element
     */
    private void setBoatTier(BoatTier boatTier) {
        this.boatTier = boatTier;
        setImage(boatTier.imagePrefix + side + ".png");
        IntPair center = boatTier.centerOfRotation;
        setCenterOfRotation(center.x, center.y);
    }

    /**
     * Increase the tier of the boat.
     */
    public void incrementBoatTier() {
        setBoatTier(boatTier.nextTier());
    }

    /**
     * Add the specified number of experience points to this fisher.
     *
     * @param exp the number of points to add
     */
    public void addExp(int exp) {
        this.exp += exp;
    }

    /**
     * Get the current number of experience points this fisher has.
     *
     * @return this fisher's amount of XP earned
     */
    public int getExp() {
        return exp;
    }

    /**
     * Get the current boat tier of this fisher.
     *
     * @return this fisher's current BoatTier value
     */
    public BoatTier getBoatTier() {
        return boatTier;
    }

    /**
     * Get the fishing rod actor for this fisher.
     *
     * @return this fisher's FishingRod object
     */
    public FishingRod getFishingRod() {
        return fishingRod;
    }
}
