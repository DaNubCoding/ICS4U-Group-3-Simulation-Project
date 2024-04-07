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
    private int xOffsetFromRod;
    private int yOffsetFromRod;

    /**
     * Initialize the fishing line with a master fisher.
     *
     * @param master The fisher the fishing line belongs to
     */
    public FishingLine(FishingRod fishingRod, Hook hook) {
        super();
        this.fishingRod = fishingRod;
        this.hook = hook;
        // Temporary until the position the line spawns from is finalized
        if (!fishingRod.getMirrorX()) {
            // Left fisher
            xOffsetFromRod = 9;
            yOffsetFromRod = -9;
        } else {
            // Right fisher
            xOffsetFromRod = 9;
            yOffsetFromRod = -9;
        }
        // Temporary until fishing rod casting is added
        endX = fishingRod.getX() + xOffsetFromRod;
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
        double[] realOffset = fishingRod.getRelativeOffset(xOffsetFromRod, yOffsetFromRod);
        startX = fishingRod.getX() + realOffset[0];
        startY = fishingRod.getY() + realOffset[1];
        endX = hook.getX();
        endY = hook.getY();
    }
}
