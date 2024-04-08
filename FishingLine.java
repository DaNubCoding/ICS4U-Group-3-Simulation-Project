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
     * Initialize the fishing line with a master fisher.
     *
     * @param master The fisher the fishing line belongs to
     */
    public FishingLine(FishingRod fishingRod, Hook hook) {
        super();
        this.fishingRod = fishingRod;
        this.hook = hook;
        // Temporary until fishing rod casting is added
        double[] rodTip = fishingRod.getTipOffset();
        endX = fishingRod.getX() + rodTip[0];
        endY = fishingRod.getY() + 100;
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
        double[] rodTip = fishingRod.getTipOffset();
        startX = fishingRod.getX() + rodTip[0];
        startY = fishingRod.getY() + rodTip[1];
        endX = hook.getX();
        endY = hook.getY();
    }
}
