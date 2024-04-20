/**
 * Possible simulation end states.
 *
 * @author Martin Baldwin
 * @version April 2024
 */
public enum EndState {
    TEST("This is a test end state. Description of ending goes here.\n\npleas remove later !"),
    EXTINCTION("The two fishers, swept up in their competition, failed to realize that they had fished all the fish in the sea."),
    ;

    public final String description;

    private EndState(String description) {
        this.description = description;
    }
}
