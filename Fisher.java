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
        WOODEN("wooden_boat_", new IntPair(33, 16), 1.3, new IntPair(19, 22)),
        STEEL("steel_boat_", new IntPair(38, 11), 0.5, new IntPair(20, 17));

        public final String imagePrefix;
        public final IntPair rodOffset;
        public final double driftMagnitudeFactor;
        public final IntPair centerOfRotation;

        /**
         * @param imagePrefix The file name prefix of the boat's image
         * @param rodOffset The offset of the rod relative to the boat's image
         * @param driftMagnitudeFactor The factor to multiply any drift-related movement by
         * @param centerOfRotation The center of rotation of the boat relative to the boat's image
         */
        private BoatTier(String imagePrefix, IntPair rodOffset, double driftMagnitudeFactor, IntPair centerOfRotation) {
            this.imagePrefix = imagePrefix;
            this.rodOffset = rodOffset;
            this.driftMagnitudeFactor = driftMagnitudeFactor;
            this.centerOfRotation = centerOfRotation;
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

    private Timer driftTimer;
    private double driftMagnitude;

    private Timer moveTimer;

    private FishingRod fishingRod;

    public Fisher(int side) {
        super(BoatTier.WOODEN.imagePrefix + side + ".png");
        boatTier = BoatTier.WOODEN;

        IntPair center = boatTier.centerOfRotation;
        setCenterOfRotation(center.x, center.y);
        this.side = side;
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
            increaseBoatTier();
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
    public void increaseBoatTier() {
        switch (boatTier) {
            case WOODEN:
                setBoatTier(BoatTier.STEEL);
            case STEEL:
                // We await the luxury boat!
        }
    }
}
