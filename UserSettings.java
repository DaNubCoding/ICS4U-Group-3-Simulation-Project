import java.util.Set;
import java.util.HashSet;

/**
 * Stores the user settings from the settings worlds.
 *
 * @author Andrew Wang
 * @author Martin Baldwin
 * @version April 2024
 */
public class UserSettings {
    private int eggSpawnAmount;
    private double evoPointThreshold;
    private int salmonCount;
    private int bassCount;
    private int tunaCount;
    private Set<Class<? extends Fish>> protectedFish = new HashSet<>();

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
    public void setEvoPointThreshold(double value) {
        evoPointThreshold = value;
    }

    /**
     * Set the number of starting fish of the Salmon fish type.
     *
     * @param count the number of starting Salmon fish
     */
    public void setSalmonCount(int count) {
        salmonCount = count;
    }

    /**
     * Set the number of starting fish of the Bass fish type.
     *
     * @param count the number of starting Bass fish
     */
    public void setBassCount(int count) {
        bassCount = count;
    }

    /**
     * Set the number of starting fish of the Tuna fish type.
     *
     * @param count the number of starting Tuna fish
     */
    public void setTunaCount(int count) {
        tunaCount = count;
    }

    /**
     * Set a type of fish to be protected from extinction.
     *
     * @param type the subclass of Fish to protect
     */
    public void addFishTypeProtection(Class<? extends Fish> type) {
        protectedFish.add(type);
    }

    /**
     * Unset a type of fish to be protected from extinction.
     *
     * @param type the subclass of Fish to remove protection from
     */
    public void removeFishTypeProtection(Class<? extends Fish> type) {
        protectedFish.remove(type);
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
    public double getEvoPointThreshold() {
        return evoPointThreshold;
    }

    /**
     * Get the number of starting fish of the Salmon fish type.
     *
     * @return the number of starting Salmon fish
     */
    public int getSalmonCount() {
        return salmonCount;
    }

    /**
     * Get the number of starting fish of the Bass fish type.
     *
     * @return the number of starting Bass fish
     */
    public int getBassCount() {
        return bassCount;
    }

    /**
     * Get the number of starting fish of the Tuna fish type.
     *
     * @return the number of starting Tuna fish
     */
    public int getTunaCount() {
        return tunaCount;
    }

    /**
     * Test whether or not a type of fish is to be protected from extinction.
     *
     * @return true if protecting the given subclass of Fish from extinction, false otherwise
     */
    public boolean isFishTypeProtected(Class<? extends Fish> type) {
        return protectedFish.contains(type);
    }
}
