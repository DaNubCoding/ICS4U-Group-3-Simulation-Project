/**
 * A data class to store two doubles in a single object, useful for coordinate calculations.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public class DoublePair {
    public final double x;
    public final double y;

    /**
     * Creates a new Pair object describing the specified values.
     *
     * @param x the x value of this pair
     * @param y the y value of this pair
     */
    public DoublePair(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
