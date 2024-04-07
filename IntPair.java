/**
 * A data class to store two integers in a single object, useful for image coordinates.
 *
 * @author Martin Baldwin
 * @version April 2024
 */
public class IntPair {
    public final int x;
    public final int y;

    /**
     * Creates a new Pair object describing the specified values.
     *
     * @param x the x value of this pair
     * @param y the y value of this pair
     */
    public IntPair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
