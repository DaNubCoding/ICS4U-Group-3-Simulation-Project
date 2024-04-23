import greenfoot.*;

public class FisherOneSettingsWorld extends SettingsWorld {
    private static final Color COLOR = new Color(54, 119, 122);

    /** @see UserSettings */
    private UserSettingSlider<Double> expPercentageSlider;
    private UserSettingSlider<Double> hookSpeedMultiplierSlider;
    private UserSettingSlider<Double> rodDelayMultiplierSlider;

    /**
     * Create the world with the UserSettings from the GeneralSettingsWorld.
     *
     * @param userSettings The partial UserSettings from the GeneralSettingsWorld
     */
    public FisherOneSettingsWorld(UserSettings userSettings) {
        super(userSettings);
    }

    @Override
    public void constructSliders() {
        UserSettings userSettings = getUserSettings();

        expPercentageSlider = new UserSettingSlider<Double>(0.0, 1.0, 0.5, 100, COLOR, userSettings::setExpPercentage1);
        hookSpeedMultiplierSlider = new UserSettingSlider<Double>(0.5, 3.0, 1.0, 100, COLOR, userSettings::setHookSpeedMultiplier1);
        rodDelayMultiplierSlider = new UserSettingSlider<Double>(0.5, 3.0, 1.0, 100, COLOR, userSettings::setRodDelayMultiplier1);

        addSlider("EXP Percentage", "The percentage of EXP from a fish granted to the boat, the rest goes to the fishing rod.", expPercentageSlider);
        addSlider("Hook Speed", "The factor to multiply the speed of the fishing hook by.", hookSpeedMultiplierSlider);
        addSlider("Fishing Rod Delay", "The factor to multiply the delay before the fishers cast their hooks again by.", rodDelayMultiplierSlider);
    }

    @Override
    public void goToNextWorld() {
        Greenfoot.setWorld(new FisherTwoSettingsWorld(getUserSettings()));
    }
}
