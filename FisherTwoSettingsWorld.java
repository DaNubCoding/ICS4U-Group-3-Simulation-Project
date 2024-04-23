import greenfoot.*;

public class FisherTwoSettingsWorld extends SettingsWorld {
    /** @see UserSettings */
    private UserSettingSlider<Double> expPercentageSlider;

    /**
     * Create the world with the UserSettings from the GeneralSettingsWorld.
     *
     * @param userSettings The partial UserSettings from the GeneralSettingsWorld
     */
    public FisherTwoSettingsWorld(UserSettings userSettings) {
        super(userSettings);
    }

    @Override
    public void constructSliders() {
        UserSettings userSettings = getUserSettings();

        expPercentageSlider = new UserSettingSlider<Double>(0.0, 1.0, 0.5, 100, new Color(76, 45, 23), userSettings::setExpPercentage2);

        addSlider("EXP percentage", expPercentageSlider);
    }

    @Override
    public void goToNextWorld() {
        Greenfoot.setWorld(new SimulationWorld(getUserSettings()));
    }
}