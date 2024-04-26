import greenfoot.*;

/**
 * The superclass to both Fishers.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public abstract class Fisher extends PixelActor {
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
    private int totalExp;

    private Timer driftTimer;
    private double driftMagnitude;

    private Timer moveTimer;

    private FishingRod fishingRod;

    /**
     * Create a new Fisher.
     */
    public Fisher() {
        super(Layer.BOAT);
        setBoatTier(BoatTier.WOODEN);

        IntPair center = boatTier.centerOfRotation;
        setCenterOfRotation(center.x, center.y);

        driftTimer = new Timer(0);
        initNextDrift();

        moveTimer = new Timer(0);
        initNextDrive();

        fishingRod = new FishingRod(this);
        boatBar = new UIBar(30, 8, 1600, "ui/bar_water.png");
    }

    @Override
    public void addedToWorld(World world) {
        setAnchor(getX(), getY());

        world.addObject(getBoatBar(), getBarX(), 2);
        setBounds(getLeftBound(), getRightBound());
        world.addObject(getFishingRod(), getX(), getY());
    }

    /**
     * Get which side this fisher is on.
     *
     * @return 1 if left, 2 if right
     */
    public abstract int getSide();

    /**
     * Get the position where this fisher's rod should be placed, relative to
     * the world.
     *
     * @return a {@link DoublePair} of x and y coordinates for this fisher's rod location
     */
    public DoublePair getRodPosition() {
        IntPair rodOffset = boatTier.rodOffset;
        return getImageOffsetGlobalPosition(rodOffset.x, rodOffset.y);
    }

    @Override
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

        if (boatBar.getLevel() == BoatTier.values().length && boatBar.getExp() == boatBar.getMaxExp()) {
            SimulationWorld world = (SimulationWorld) getWorld();
            world.triggerFadeOut(0.02);
            world.setEndState(getSide() == 1 ? EndState.FISHER_1 : EndState.FISHER_2);
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
        if (getDoubleX() < getLeftBound()) {
            setLocation(getLeftBound(), getDoubleY());
            anchorX += 5;
        } else if (getDoubleX() > getRightBound()) {
            setLocation(getRightBound(), getDoubleY());
            anchorX -= 5;
        }
    }

    /**
     * Get the left bound of the region the boat will stay within.
     *
     * @return The left bound of the region
     */
    public abstract int getLeftBound();

    /**
     * Get the right bound of the region the boat will stay within.
     *
     * @return The right bound of the region
     */
    public abstract int getRightBound();

    /**
     * Set the boat's tier to a new tier, update image accordingly.
     *
     * @param boatTier The tier of the boat as a boatTier enum element
     */
    private void setBoatTier(BoatTier boatTier) {
        this.boatTier = boatTier;
        setImage(getBoatTier().imagePrefix + getSide() + ".png");
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
        totalExp += exp;
        UserSettings userSettings = ((SimulationWorld) getWorld()).getUserSettings();
        double percentage = userSettings.getExpPercentage(getSide());
        boatBar.gainExp((int) Math.floor(exp * percentage));
        fishingRod.getRodBar().gainExp((int) Math.ceil(exp * (1 - percentage)));
        fadeOutBars();
    }

    /**
     * Get the total amount of exp a player has earned
     *
     * @return The total amount of exp
     */
    public int getTotalExp() {
        return totalExp;
    }

    /**
     * Get the UIBar that shows the exp of the boat.
     *
     * @return The UIBar object of the Fisher
     */
    public UIBar getBoatBar() {
        return boatBar;
    }

    /**
     * Fade out the UIBars if the boat overlaps with them.
     */
    private void fadeOutBars() {
        int barRange = boatBar.getOriginalWidth() + 2;
        double percentage = getBarOverlapPercentage(barRange);
        percentage = Math.min(Math.max(percentage, 0.0), 1.0);
        int transparency = (int) (100 + 80 * percentage);
        boatBar.setTransparency(transparency);
        fishingRod.getRodBar().setTransparency(transparency);
    }

    /**
     * Get the x coordinate the Bars of this Fisher will be placed at.
     * <p>This is the position of the left edge of the bars.</p>
     *
     * @return The x-coordinate of the Bars
     */
    public abstract int getBarX();

    /**
     * Get the percentage of the bar that is overlapping the boat.
     * <p>Basically how much the edge of the boat is going into the bar.</p>
     *
     * @param barRange The horizontal range that is considered to be overlapping the bar
     * @return The percentage of overlap
     */
    public abstract double getBarOverlapPercentage(int barRange);

    /**
     * Set the left and right bounds of the region the boat will stay within.
     *
     * @param leftBound The x-coordinate of the left bound
     * @param rightBound The x-coordinate of the right bound
     */
    public void setBounds(int leftBound, int rightBound) {
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

    /**
     * Set the anchor of the Fisher.
     * <p>The anchor is the point that the Fisher boat will drift around.</p>
     *
     * @param x The x-coordinate of the anchor point
     * @param y The y-coordinate of the anchor point
     */
    public void setAnchor(int x, int y) {
        anchorX = x;
        anchorY = y;
    }
}
