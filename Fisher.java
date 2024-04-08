import greenfoot.*;

/**
 * The fisher boats.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public class Fisher extends PixelActor {
    /**
     * Different tiers of boats that the Fishers may own, defining where fishing
     * rods should be attached to them.
     *
     * @author Martin Baldwin
     * @version April 2024
     */
    public enum BoatTier {
        ONE(new IntPair(33, 16));

        private final IntPair rodOffset;

        private BoatTier(IntPair rodOffset) {
            this.rodOffset = rodOffset;
        }

        public IntPair getRodOffset() {
            return rodOffset;
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
        super("boat" + side + ".png");
        setCenterOfRotation(19, 22);
        this.side = side;
        if (side == 2) {
            setMirrorX(true);
        }
        boatTier = BoatTier.ONE;

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
        IntPair rodOffset = boatTier.getRodOffset();
        return getImageOffsetGlobalPosition(rodOffset.x, rodOffset.y);
    }

    public void addedToWorld(World world) {
        anchorX = getX();
        anchorY = getY();

        if (side == 1) {
            leftBound = 30;
            rightBound = getWorld().getWorldWidth() / 2 - 40;
            world.addObject(fishingRod, getX(), getY());
        } else {
            leftBound = getWorld().getWorldWidth() / 2 + 40;
            rightBound = getWorld().getWorldWidth() - 30;
            world.addObject(fishingRod, getX(), getY());
        }
    }

    public void act() {
        drift();
        drive();
        move();
        checkBounds();
    }

    /**
     * Move the boat in ways that don't significantly affect it but
     * make it look more realistic.
     */
    private void drift() {
        setRotation(Math.sin(driftTimer.progress() * Math.PI * 2) * driftMagnitude + 360);

        // Drift in a elliptical pattern, matching the rotation
        double driftOffsetX = Math.cos(driftTimer.progress() * Math.PI * 2) * driftMagnitude * 0.8;
        double driftOffsetY = Math.sin(driftTimer.progress() * Math.PI * 2) * driftMagnitude * 0.4;

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
        driftMagnitude = Util.randDouble(1, 6);
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
}
