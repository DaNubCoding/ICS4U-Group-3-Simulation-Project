import greenfoot.*;

/**
 * The Fisher on the right.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public class RightFisher extends Fisher {
    public RightFisher() {
        super();
        setMirrorX(true);
    }

    @Override
    public void addedToWorld(World world) {
        setAnchor(getX(), getY());

        world.addObject(getBoatBar(), world.getWidth() - 32, 2);
        setBounds(world.getWidth() / 2 + 40, world.getWidth() - 30);
        world.addObject(getFishingRod(), getX(), getY());
    }

    @Override
    public int getSide() {
        return 2;
    }

    @Override
    public int getLeftBound() {
        return getWorld().getWidth() / 2 + 40;
    }

    @Override
    public int getRightBound() {
        return getWorld().getWidth() - 30;
    }

    @Override
    public int getBarX() {
        return getWorld().getWidth() - 32;
    }

    @Override
    public double getBarOverlapPercentage(int barRange) {
        int right = getX() + (int) getTransformedWidth() / 2;
        return (getWorld().getWidth() - right) / (double) barRange;
    }
}
