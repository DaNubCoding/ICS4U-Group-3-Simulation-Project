import greenfoot.*;

/**
 * The fishers' fishing lines, only the line part.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public class FishingLine extends PixelActor {
    private Fisher master;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private int xOffsetFromBoat;
    private int yOffsetFromBoat;

    /**
     * Initialize the fishing line with a master fisher.
     *
     * @param master The fisher the fishing line belongs to
     */
    public FishingLine(Fisher master) {
        super();
        this.master = master;
        // Temporary until the position the line spawns from is finalized
        if (master.getSide() == 1) {
            xOffsetFromBoat = 42 - master.getCenterOfRotationX();
            yOffsetFromBoat = 8 - master.getCenterOfRotationY();
        } else {
            xOffsetFromBoat = -4 - master.getCenterOfRotationX();
            yOffsetFromBoat = 8 - master.getCenterOfRotationY();
        }
        // Temporary until fishing rod casting is added
        endX = master.getX() + xOffsetFromBoat;
        endY = master.getY() + 100;
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
        double[] realOffset = master.getRelativeOffset(xOffsetFromBoat, yOffsetFromBoat);
        startX = master.getX() + realOffset[0];
        startY = master.getY() + realOffset[1];
    }
}
