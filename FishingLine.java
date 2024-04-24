import greenfoot.*;

/**
 * The fishing lines attached to fishing rods, only the line part.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public class FishingLine extends PixelActor {
    private FishingRod fishingRod;
    private Hook hook;
    private double startX;
    private double startY;
    private double endX;
    private double endY;

    /**
     * Initialize the fishing line attached to a fishing rod and hook.
     *
     * @param fishingRod The fishing rod the fishing line belongs to
     * @param hook The hook on this fishing line
     */
    public FishingLine(FishingRod fishingRod) {
        super(Layer.FISHING_LINE);
        this.fishingRod = fishingRod;
    }

    /**
     * Set the hook this fishing line connects to.
     */
    public void setHook(Hook hook) {
        this.hook = hook;
    }

    /**
     * Act when added to world, so that it shows up in the correct location.
     */
    @Override
    public void addedToWorld(World world) {
        act();
    }

    /**
     * Overriding super.render in order to draw a line instead of
     * render an image.
     */
    @Override
    public void render(GreenfootImage canvas) {
        canvas.setColor(Color.BLACK);
        canvas.drawLine((int) startX, (int) startY, (int) endX, (int) endY);
    }

    public void act() {
        // Start from the tip of the rod and end at the hook
        DoublePair rodTip = fishingRod.getTipPosition();
        startX = rodTip.x;
        startY = rodTip.y;
        endX = hook.getX();
        endY = hook.getY();
    }
}
