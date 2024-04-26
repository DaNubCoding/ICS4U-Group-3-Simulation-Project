import greenfoot.*;

/**
 * Possible simulation end states.
 *
 * @author Martin Baldwin
 * @author Stanley Wang
 * @version April 2024
 */
public enum EndState {
    EXTINCTION(new Color(219, 233, 239, 200), "Swept up in the competition, the two fishers failed to realize that they had fished all the fish in the sea. Fish was now only a distant memory and a reminder of the fragility of life for centuries to come."),
    FISHER_1(new Color(156, 191, 219, 200), "With her astounding collection of unique fish, Marie B. O'Logist became renowned for her groundbreaking research into recent marine mutations. She continued to win many awards, even having a branch of science named after her."),
    FISHER_2(new Color(239, 208, 207, 200), "After creating an all-new menu of never-before-seen sushi, Su C. Sheph became a top sushi master known across the globe for his irreplicably innovative delicacies that seemed to have life-altering effects on those who got a taste."),
    BLOOP(new Color(191, 191, 191, 200), "The Fish God's mutations have gone too far. The last thing the fishers saw was the looming maw of the beast. With the ocean empty, The Bloop moves on to the rest of the planet, consuming everything, until there is nothing left."),
    KRAKEN(new Color(172, 183, 130, 200), "The Fish God's mutations have gone too far. The seas across the world soon blacken, suffocating all aquatic life. No human would ever set sail again, lest they cross the terror of The Kraken."),
    LEVIATHAN(new Color(176, 46, 46, 200), "The Fish God's mutations have gone too far. The rabid Leviathans would soon spread across all corners of the waters, tearing everything else apart. Soon, being adapted to land, war between the lands and seas would commence."),
    ;

    /**
     * The color of the text displayed on the EndWorld.
     */
    public final Color color;
    /**
     * A description of the end state displayed on the EndWorld.
     */
    public final String description;
    /**
     * The background image for the end state.
     */
    public final GreenfootImage background;

    private EndState(Color color, String description) {
        this.color = color;
        this.description = description;
        background = new GreenfootImage("ends/" + name().toLowerCase() + "_background.png");
    }
}
