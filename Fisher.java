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
    private int leftBound;
    private int rightBound;
    
    private int swayCounter;
    private int swayPeriodCounter;
    private double swayMagnitude;
    
    private int sinkCounter;
    private int sinkPeriodCounter;
    private double sinkOffset;
    private double sinkMagnitude;
    
    private double driftOffset;
    
    private int moveCounter;
    // Number of frames before the next movement
    private int nextMove;
    private boolean moving;
    // Number of frames it will move for
    private int moveAmount;
    private double moveSpeed;
    
    public Fisher(int side) {
        super("boat" + side + ".png");
        setCenterOfRotation(24, 30);
        this.side = side;
        
        swayMagnitude = 3;
        sinkMagnitude = 2;
        nextMove = 120;
    }
    
    public void addedToWorld(World world) {
        anchorX = getX();
        anchorY = getY();
        
        if (side == 1) {
            leftBound = 30;
            rightBound = getWorld().getWorldWidth() / 2 - 30;
        } else {
            leftBound = getWorld().getWorldWidth() / 2 + 30;
            rightBound = getWorld().getWorldWidth() - 30;
        }
    }
    
    public void act() {
        doInconspicuousMovement();
        doConspicuousMovement();
        checkBounds();
    }
    
    /**
     * Move the boat in ways that don't significantly affect it but
     * make it look more realistic.
     */
    private void doInconspicuousMovement() {
        // Random tilt/sway
        setRotation(Math.sin(Math.toRadians(++swayCounter)) * swayMagnitude);
        // The one frame when the swayPeriodCounter increases
        if (swayCounter / 180 > swayPeriodCounter) {
            swayMagnitude = Util.randDouble(2, 5);
            swayPeriodCounter = (int) (swayCounter / 180);
        }
        
        // Random up and down motion
        // cos because we want it to begin at 0, and each "period" ends at 0
        sinkOffset = -Math.cos(Math.toRadians(++sinkCounter * 0.5)) * sinkMagnitude + sinkMagnitude;
        if (sinkCounter / 720 != sinkPeriodCounter) {
            sinkMagnitude = Util.randDouble(0.5, 1.5);
            sinkPeriodCounter = (int) (sinkCounter / 720);
        }
        
        // Random left and right drift
        // The drift is based on sway
        driftOffset += Math.sin(Math.toRadians(swayCounter)) * swayMagnitude * 0.012;
        driftOffset *= 0.995;
        
        // + sinkMagnitude to make the value always positive
        setLocation(anchorX + driftOffset, anchorY + sinkOffset);
    }
    
    /**
     * Intentionally move the boat a certain distance away.
     */
    private void doConspicuousMovement() {
        // Random movement
        if (++moveCounter == nextMove) {
            moveCounter = 0;
            nextMove = Util.randInt(240, 480);
            moving = true;
            moveAmount = Util.randInt(40, 120);
            moveSpeed = Util.randDouble(0.03, 0.12) * (Util.randInt(1) == 0 ? -1 : 1);
        }
        if (moving && moveCounter < moveAmount) {
            anchorX += moveSpeed;
        }
    }
    
    /**
     * Restrict the boat to its area.
     */
    private void checkBounds() {
        if (getDoubleX() < leftBound) {
            setLocation(leftBound, getDoubleY());
        } else if (getDoubleX() > rightBound) {
            setLocation(rightBound, getDoubleY());
        }
    }
}
