import greenfoot.*;

/**
 * The Fisher on the left.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public class LeftFisher extends Fisher {
    /**
     * Constructs a new LeftFisher.
     */
    public LeftFisher() {
        super();
    }

    @Override
    public int getSide() {
        return 1;
    }

    @Override
    public int getLeftBound() {
        return 30;
    }

    @Override
    public int getRightBound() {
        return getWorld().getWidth() / 2 - 40;
    }

    @Override
    public int getBarX() {
        return 2;
    }

    @Override
    public double getBarOverlapPercentage(int barRange) {
        int left = getX() - (int) getTransformedWidth() / 2;
        return left / (double) barRange;
    }
}
