import greenfoot.*;
import java.util.ArrayList;

/**
 * The fishing rod, the thing that is upgraded.
 *
 * @author Andrew Wang
 * @author Martin Baldwin
 * @author Matthew Li
 * @version April 2024
 */
public class FishingRod extends PixelActor {
    private static final SoundEffect catchSound = new SoundEffect("catch.wav");

    private Fisher fisher;
    private ArrayList<FishingLine> fishingLines;
    private ArrayList<Hook> hooks;
    private RodTier rodTier;
    private UIBar rodBar;
    private Icon hookIcon;

    private Timer castTimer;

    /**
     * Create a new FishingRod for the given Fisher.
     *
     * @param fisher The Fisher that this FishingRod belongs to
     */
    public FishingRod(Fisher fisher) {
        super(Layer.FISHING_ROD);
        this.fisher = fisher;
        setRodTier(RodTier.WOODEN);

        setCenterOfRotation(0, getOriginalImage().getHeight() - 1);

        hooks = new ArrayList<Hook>();
        fishingLines = new ArrayList<FishingLine>();

        castTimer = new Timer(100);
        rodBar = new UIBar(30, 8, 1400, "ui/bar_gold.png");
        hookIcon = new Icon(2);
    }

    @Override
    public void addedToWorld(World world) {
        world.addObject(rodBar, fisher.getBarX(), 12);
        world.addObject(hookIcon, fisher.placeIcon(), 16);
        if (fisher.getMirrorX()) {
            setMirrorX(true);
        }

        act();
    }

    @Override
    public void act() {
        DoublePair newPos = fisher.getRodPosition();
        setLocation(newPos.x, newPos.y);
        setRotation(fisher.getRotation());

        // Check if the previous hook has been pulled back
        if (castTimer.ended() && hooks.isEmpty()) {
            cast();
            castTimer.restart(getNextCastDelay());
        }
    }

    /**
     * Cast the fishing rod.
     */
    public void cast() {
        // Spawn the hooks at the tip of the fishing rod
        DoublePair hookPos = getTipPosition();

        UserSettings userSettings = ((SimulationWorld) getWorld()).getUserSettings();
        int hookCount = 1;
        double p = Util.randDouble(0, 1);
        double multicastProbability = userSettings.getMulticastProbability(fisher.getSide());
        if (p < multicastProbability) {
            hookCount = (int) (Math.log(p / multicastProbability) / Math.log(0.5)) + 2;
            hookCount = Math.min(hookCount, rodTier.maxMulticast);
        }

        for (int i = 0; i < hookCount; i++) {
            FishingLine fishingLine = new FishingLine(this);
            Hook hook = new Hook(this, fishingLine, hookCount - 1);
            fishingLines.add(fishingLine);
            hooks.add(hook);
            fishingLine.setHook(hook);

            PixelWorld world = getWorld();
            world.addObject(hook, (int) hookPos.x, (int) hookPos.y);
            world.addObject(fishingLine, 0, 0);
        }
    }

    /**
     * Get the number of acts before the next time the rod is casted.
     *
     * @return The number of acts before next cast
     */
    private int getNextCastDelay() {
        UserSettings userSettings = ((SimulationWorld) getWorld()).getUserSettings();
        double multiplier = userSettings.getRodDelayMultiplier(fisher.getSide());
        return (int) (rodTier.castFrequency * Util.randDouble(0.8, 1.2) * multiplier);
    }

    /**
     * Reel in the fishing rod when the hook returns to the surface.
     * <p>Effectively gains the fish's XP value and removes the fish, hook, and fishing line.</p>
     *
     * @param hook The hook that is being reeled in
     */
    public void reelIn(Hook hook) {
        PixelWorld world = getWorld();
        Fish caughtFish = hook.getAttachedFish();
        if (caughtFish != null) {
            fisher.gainExp(caughtFish.getValue());
            world.removeObject(caughtFish);
            catchSound.play();
        }
        fishingLines.remove(hook.getFishingLine());
        hooks.remove(hook);
        world.removeObject(hook.getFishingLine());
        world.removeObject(hook);
    }

    /**
     * Get the coordinates of the tip of the rod relative to the world.
     *
     * @return The global x and y coordinates of the tip as a {@link DoublePair}
     */
    public DoublePair getTipPosition() {
        return getImageOffsetGlobalPosition(getOriginalImage().getWidth() - 1, 0);
    }

    /**
     * Get the maximum depth beneath the surface of the water this
     * fishing rod can reach.
     *
     * @return The maximum depth
     */
    public int getMaxDepth() {
        return rodTier.maxDepth;
    }

    /**
     * Get the speed at which the fishing rod reels in fish.
     *
     * @return The speed at which the fish is reeled in
     */
    public double getReelInSpeed() {
        return rodTier.reelInSpeed;
    }

    /**
     * Get the tier of the rod.
     *
     * @return The tier of the rod represented by a {@link RodTier} enum entry
     */
    public RodTier getRodTier() {
        return rodTier;
    }

    /**
     * Set the rod's tier to a new tier, update image accordingly.
     *
     * @param rodTier The tier of the rod as a RodTier enum element
     */
    public void setRodTier(RodTier rodTier) {
        this.rodTier = rodTier;
        setImage(rodTier.imagePrefix + fisher.getSide() + ".png");
        setCenterOfRotation(0, getOriginalImage().getHeight() - 1);
    }

    /**
     * Get the UIBar object that represents the EXP of this fishing rod.
     *
     * @return The UIBar object
     */
    public UIBar getRodBar() {
        return rodBar;
    }

    /**
     * Get the Icon for the rod UIBar
     *
     * @return The hook Icon of this fishing rod
     */
    public Icon getHookIcon() {
        return hookIcon;
    }

    /**
     * Get the Fisher that this FishingRod belongs to.
     *
     * @return The Fisher this FishingRod belongs to
     */
    public Fisher getFisher() {
        return fisher;
    }
}
