import greenfoot.*;

/**
 * The fisher boats.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public class Fisher extends PixelActor {
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
    private UIBar boatBar;

    private Timer driftTimer;
    private double driftMagnitude;

    private Timer moveTimer;

    private FishingRod fishingRod;

    public Fisher(int side) {
        super(Layer.BOAT);
        this.side = side;
        setBoatTier(BoatTier.WOODEN);

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
        boatBar = new UIBar(30, 8, 1600, "Water.jpg");
    }

    @Override
    public void addedToWorld(World world) {
        anchorX = getX();
        anchorY = getY();

        if (side == 1) {
            world.addObject(boatBar, 2, 2);
            leftBound = 30;
            rightBound = getWorld().getWidth() / 2 - 40;
            world.addObject(fishingRod, getX(), getY());
        } else {
            world.addObject(boatBar, world.getWidth() - 32, 2);
            leftBound = getWorld().getWidth() / 2 + 40;
            rightBound = getWorld().getWidth() - 30;
            world.addObject(fishingRod, getX(), getY());
        }
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

    public void act() {
        drift();
        drive();
        move();
        checkBounds();

        if (boatTier.ordinal() != boatBar.getLevel() - 1) {
            setBoatTier(BoatTier.values()[boatBar.getLevel() - 1]);
        }
        if (fishingRod.getRodTier().ordinal() != fishingRod.getRodBar().getLevel() - 1) {
            fishingRod.setRodTier(RodTier.values()[fishingRod.getRodBar().getLevel() - 1]);
        }

        fadeOutBars();
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

    /**
     * Add some exp to the fisher boat's bar.
     *
     * @param exp The amount of exp to gain
     */
    public void gainExp(int exp) {
        boatBar.gainExp(exp);
    }

    /**
     * Fade out the UIBars if the boat overlaps with them.
     */
    private void fadeOutBars() {
        double percentage;
        // The horizontal range that is considered within the bar
        double barRange = boatBar.getOriginalWidth() + 2;
        if (side == 1) {
            int left = getX() - (int) getTransformedWidth() / 2;
            percentage = left / barRange;
        } else {
            int right = getX() + (int) getTransformedWidth() / 2;
            percentage = (getWorld().getWidth() - right) / barRange;
        }
        percentage = Math.min(Math.max(percentage, 0.0), 1.0);
        int transparency = (int) (100 + 80 * percentage);
        boatBar.setTransparency(transparency);
        fishingRod.getRodBar().setTransparency(transparency);
    }
}
