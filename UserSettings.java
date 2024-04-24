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

    // Fisher settings
    private double[] expPercentage = new double[2];
    private double[] hookSpeedMultiplier = new double[2];
    private double[] rodDelayMultiplier = new double[2];
    private double[] multicastProbability = new double[2];

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
     * Set the percentage of EXP that goes to the boat. (Fisher 1)
     *
     * @param value the percentage of EXP that goes to the boat
     */
    public void setExpPercentage1(double value) {
        expPercentage[0] = value;
    }

    /**
     * Set the percentage of EXP that goes to the boat. (Fisher 2)
     *
     * @param value the percentage of EXP that goes to the boat
     */
    public void setExpPercentage2(double value) {
        expPercentage[1] = value;
    }

    /**
     * Set the factor to multiply the speed of the fishing hook by. (Fisher 1)
     *
     * @param value the factor to multiply the speed of the hook by
     */
    public void setHookSpeedMultiplier1(double value) {
        hookSpeedMultiplier[0] = value;
    }

    /**
     * Set the factor to multiply the speed of the fishing hook by. (Fisher 2)
     *
     * @param value the factor to multiply the speed of the hook by
     */
    public void setHookSpeedMultiplier2(double value) {
        hookSpeedMultiplier[1] = value;
    }

    /**
     * Set the factor to multiply the delay before the fishing rod is casted by. (Fisher 1)
     *
     * @param value the factor to multiply the delay by
     */
    public void setRodDelayMultiplier1(double value) {
        rodDelayMultiplier[0] = value;
    }

    /**
     * Set the factor to multiply the delay before the fishing rod is casted by. (Fisher 2)
     *
     * @param value the factor to multiply the delay by
     */
    public void setRodDelayMultiplier2(double value) {
        rodDelayMultiplier[1] = value;
    }

    /**
     * Set the probability of a multicast occurring. (Fisher 1)
     *
     * @param value the probability of a multicast
     */
    public void setMulticastProbability1(double value) {
        multicastProbability[0] = value;
    }

    /**
     * Set the probability of a multicast occurring. (Fisher 2)
     *
     * @param value the probability of a multicast
     */
    public void setMulticastProbability2(double value) {
        multicastProbability[1] = value;
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

    /**
     * Get the percentage of EXP that goes to the boat.
     *
     * @param fisher the side-number of the fisher (1 for left and 2 for right)
     * @return the percentage of EXP that goes to the boat
     */
    public double getExpPercentage(int fisher) {
        return expPercentage[fisher - 1];
    }

    /**
     * Get the factor to multiply the speed of the fishing hook by.
     *
     * @param fisher the side-number of the fisher (1 for left and 2 for right)
     * @return the factor to multiply the speed of the hook by
     */
    public double getHookSpeedMultiplier(int fisher) {
        return hookSpeedMultiplier[fisher - 1];
    }

    /**
     * Get the factor to multiply the delay before the fishing rod is casted by.
     *
     * @param fisher the side-number of the fisher (1 for left and 2 for right)
     * @return the factor to multiply the delay by
     */
    public double getRodDelayMultiplier(int fisher) {
        return rodDelayMultiplier[fisher - 1];
    }

    /**
     * Get the probability of a multicast occurring.
     *
     * @param fisher the side-number of the fisher (1 for left and 2 for right)
     * @return the probability of a multicast
     */
    public double getMulticastProbability(int fisher) {
        return multicastProbability[fisher - 1];
    }
}
