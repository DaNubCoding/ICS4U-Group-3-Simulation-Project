import greenfoot.*;

/**
 * The settings world for the second Fisher.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public class FisherTwoSettingsWorld extends SettingsWorld {
    private static final Color COLOR = new Color(229, 115, 115);

    /** @see UserSettings */
    private UserSettingSlider<Double> expPercentageSlider;
    private UserSettingSlider<Double> hookSpeedMultiplierSlider;
    private UserSettingSlider<Double> rodDelayMultiplierSlider;
    private UserSettingSlider<Double> multicastProbabilitySlider;

    /**
     * Create the world with the UserSettings from the FisherOneSettingsWorld.
     *
     * @param previousWorld The previous world (FisherOneSettingsWorld)
     * @param userSettings The partial UserSettings from the FisherOneSettingsWorld
     */
    public FisherTwoSettingsWorld(SettingsWorld previousWorld, UserSettings userSettings) {
        super(previousWorld, userSettings, "settings/fisher_2_background.png");

        // Add title and fisher sprite
        int midx = getWidth() / 2;
        int top = 15;
        addObject(new Text("Fisher 2 - Su C. Sheph", Text.AnchorX.CENTER, Text.AnchorY.CENTER, new Color(249, 171, 164)), midx, top);
        PixelActor littlePerson = new StillActor("images/fisher_2.png", Layer.UI);
        addObject(new Text("Page 3 of 3", Text.AnchorX.RIGHT, Text.AnchorY.BOTTOM), getWidth() - 6, getHeight() - 4);
        littlePerson.setMirrorX(true);
        addObject(littlePerson, midx, top + 15);
    }

    @Override
    public void constructSliders() {
        UserSettings userSettings = getUserSettings();

        expPercentageSlider = new UserSettingSlider<Double>(0.0, 1.0, 0.5, 100, COLOR, userSettings::setExpPercentage2);
        hookSpeedMultiplierSlider = new UserSettingSlider<Double>(0.5, 3.0, 1.0, 100, COLOR, userSettings::setHookSpeedMultiplier2);
        rodDelayMultiplierSlider = new UserSettingSlider<Double>(0.5, 3.0, 1.0, 100, COLOR, userSettings::setRodDelayMultiplier2);
        multicastProbabilitySlider = new UserSettingSlider<Double>(0.0, 1.0, 0.2, 100, COLOR, userSettings::setMulticastProbability2);

        addSlider("Boat/Rod EXP Split", "The percentage of EXP from a fish granted to the boat, the rest goes to the fishing rod.", expPercentageSlider);
        addSlider("Hook Speed", "The factor to multiply the speed of the fishing hook by.", hookSpeedMultiplierSlider);
        addSlider("Fishing Rod Delay", "The factor to multiply the delay before the fishers cast their hooks again by.", rodDelayMultiplierSlider);
        addSlider("Multicast Probability", "The probability of a multicast occurring. Higher tiers of rods have a more powerful multicast.", multicastProbabilitySlider);

        setSliderTop(50);
    }

    @Override
    public void goToNextWorld() {
        Greenfoot.setWorld(new SimulationWorld(getUserSettings()));
    }
}
