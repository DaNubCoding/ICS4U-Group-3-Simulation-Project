import greenfoot.*;

/**
 * The settings world for the first Fisher.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public class FisherOneSettingsWorld extends SettingsWorld {
    private static final Color COLOR = new Color(54, 119, 122);

    /** @see UserSettings */
    private UserSettingSlider<Double> expPercentageSlider;
    private UserSettingSlider<Double> hookSpeedMultiplierSlider;
    private UserSettingSlider<Double> rodDelayMultiplierSlider;
    private UserSettingSlider<Double> multicastProbabilitySlider;

    /**
     * Create the world with the UserSettings from the GeneralSettingsWorld.
     *
     * @param previousWorld The previous world (GeneralSettingsWorld)
     * @param userSettings The partial UserSettings from the GeneralSettingsWorld
     */
    public FisherOneSettingsWorld(SettingsWorld previousWorld, UserSettings userSettings) {
        super(previousWorld, userSettings, "settings/fisher_1_background.png");

        // Add title and fisher sprite
        int midx = getWidth() / 2;
        int top = 15;
        addObject(new Text("~ Fisher 1 ~", Text.AnchorX.CENTER, Text.AnchorY.CENTER, new Color(127, 219, 223)), midx, top);
        addObject(new StillActor("images/fisher_1.png", Layer.UI), midx, top + 15);
    }

    @Override
    public void constructSliders() {
        UserSettings userSettings = getUserSettings();

        expPercentageSlider = new UserSettingSlider<Double>(0.0, 1.0, 0.5, 100, COLOR, userSettings::setExpPercentage1);
        hookSpeedMultiplierSlider = new UserSettingSlider<Double>(0.5, 3.0, 1.0, 100, COLOR, userSettings::setHookSpeedMultiplier1);
        rodDelayMultiplierSlider = new UserSettingSlider<Double>(0.5, 3.0, 1.0, 100, COLOR, userSettings::setRodDelayMultiplier1);
        multicastProbabilitySlider = new UserSettingSlider<Double>(0.0, 1.0, 0.2, 100, COLOR, userSettings::setMulticastProbability1);

        addSlider("Boat/Rod EXP Split", "The percentage of EXP from a fish granted to the boat, the rest goes to the fishing rod.", expPercentageSlider);
        addSlider("Hook Speed", "The factor to multiply the speed of the fishing hook by.", hookSpeedMultiplierSlider);
        addSlider("Fishing Rod Delay", "The factor to multiply the delay before the fishers cast their hooks again by.", rodDelayMultiplierSlider);
        addSlider("Multicast Probability", "The probability of a multicast occurring. Higher tiers of rods have a more powerful multicast.", multicastProbabilitySlider);

        setSliderTop(50);
    }

    @Override
    public void goToNextWorld() {
        Greenfoot.setWorld(new FisherTwoSettingsWorld(this, getUserSettings()));
    }
}
