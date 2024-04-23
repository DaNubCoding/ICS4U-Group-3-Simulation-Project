/**
 * Stores the user settings from the settings worlds.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public class UserSettings {
    private int eggSpawnAmount;
    private int expThreshold;
    private int numOfStartFish;

    /**
     * Set the maximum number of eggs that spawn from a fish.
     *
     * @param value the maximum number of eggs
     */
    public void setEggSpawnAmount(int value) {
        eggSpawnAmount = value;
    }

    /**
     * Set the number of evo-points needed before having a chance of evolving.
     *
     * @param value the number of evo-points needed before having a chance of evolving
     */
    public void setEvoPointThreshold(int value) {
        expThreshold = value;
    }

    /**
     * Set the number of starting fish of each tier-one fish type.
     *
     * @param value the number of starting fish
     */
    public void setNumOfStartFish(int value) {
        numOfStartFish = value;
    }

    /**
     * Get the maximum number of eggs that spawn from a fish.
     *
     * @return the maximum number of eggs
     */
    public int getEggSpawnAmount() {
        return eggSpawnAmount;
    }

    /**
     * Get the number of evo-points needed before having a chance of evolving.
     *
     * @return the number of evo-points needed before having a chance of evolving
     */
    public int getExpThreshold() {
        return expThreshold;
    }

    /**
     * Get the number of starting fish of each tier-one fish type.
     *
     * @return the number of starting fish
     */
    public int getNumOfStartFish() {
        return numOfStartFish;
    }
}
