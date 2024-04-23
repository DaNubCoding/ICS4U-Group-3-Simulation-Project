import greenfoot.*;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * SettingsWorld that allows the user to set the general settings of the simulation.
 *
 * @author Brandon Law
 * @author Andrew Wang
 * @author Matthew Li
 * @version April 2024
 */
public class GeneralSettingsWorld extends SettingsWorld {
    /** @see UserSettings */
    private UserSettingSlider<Integer> expThresholdSlider;
    private UserSettingSlider<Integer> eggSpawnAmountSlider;
    private UserSettingSlider<Integer> numOfStartFishSlider;

    public GeneralSettingsWorld() {
        super();
    }

    @Override
    public void constructSliders() {
        UserSettings userSettings = getUserSettings();

        expThresholdSlider = new UserSettingSlider<Integer>(1, 5, 1, new Color(76, 45, 23), userSettings::setEvoPointThreshold);
        eggSpawnAmountSlider = new UserSettingSlider<Integer>(1, 5, 3, new Color(76, 45, 23), userSettings::setEggSpawnAmount);
        numOfStartFishSlider = new UserSettingSlider<Integer>(1, 10, 1, new Color(76, 45, 23), userSettings::setNumOfStartFish);

        addSlider("EXP Threshold", expThresholdSlider);
        addSlider("Max # of Eggs", eggSpawnAmountSlider);
        addSlider("# of Starting Fish", numOfStartFishSlider);
    }

    @Override
    public void goToNextWorld() {
        Greenfoot.setWorld(new FisherOneSettingsWorld(getUserSettings()));
    }
}
