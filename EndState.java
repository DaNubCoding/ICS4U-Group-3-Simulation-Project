/**
 * Possible simulation end states.
 *
 * @author Martin Baldwin
 * @author Stanley Wang
 * @version April 2024
 */
public enum EndState {
    TEST("This is a test end state. Description of ending goes here.\n\npleas remove later !"),
    EXTINCTION("The two fishers, swept up in their competition, failed to realize that they had fished all the fish in the sea."),
    BLOOP("The Fish God's mutations have gone too far. The last thing the fishers saw was the looming maw of the beast. With the ocean empty, The Bloop moves on to the rest of the planet, consuming everything, until there is nothing left."),
    KRAKEN("The Fish God's mutations have gone too far. The seas across the world soon blacken, suffocating all aquatic life No human would ever sets sail again, lest they cross the terror of The Kraken."),
    LEVIATHAN("The Fish God's mutations have gone too far. The rabid Leviathans would soon spread across all corners of the waters, tearing everything else apart. Soon, the mutations would cause them to adapt to land, and the war between the lands and seas would commence."),
    ;

    public final String description;

    private EndState(String description) {
        this.description = description;
    }
}
