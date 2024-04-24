import greenfoot.*;

public class FisherTwoSettingsWorld extends SettingsWorld {
    private static final Color COLOR = new Color(229, 115, 115);

    /** @see UserSettings */
    private UserSettingSlider<Double> expPercentageSlider;
    private UserSettingSlider<Double> hookSpeedMultiplierSlider;
    private UserSettingSlider<Double> rodDelayMultiplierSlider;

    /**
     * Create the world with the UserSettings from the FisherOneSettingsWorld.
     *
     * @param previousWorld The previous world (FisherOneSettingsWorld)
     * @param userSettings The partial UserSettings from the FisherOneSettingsWorld
     */
    public FisherTwoSettingsWorld(SettingsWorld previousWorld, UserSettings userSettings) {
        super(previousWorld, userSettings, "fisher2_settings_background.png");

        // Add title and fisher sprite
        int midx = getWidth() / 2;
        int top = 15;
        addObject(new Text("~ Fisher 2 ~", Text.AnchorX.CENTER, Text.AnchorY.CENTER, new Color(240, 178, 171)), midx, top);
        PixelActor littlePerson = new PixelActor("images/fisher2.png", Layer.UI);
        littlePerson.setMirrorX(true);
        addObject(littlePerson, midx, top + 15);
    }

    @Override
    public void constructSliders() {
        UserSettings userSettings = getUserSettings();

        expPercentageSlider = new UserSettingSlider<Double>(0.0, 1.0, 0.5, 100, COLOR, userSettings::setExpPercentage2);
        hookSpeedMultiplierSlider = new UserSettingSlider<Double>(0.5, 3.0, 1.0, 100, COLOR, userSettings::setHookSpeedMultiplier2);
        rodDelayMultiplierSlider = new UserSettingSlider<Double>(0.5, 3.0, 1.0, 100, COLOR, userSettings::setRodDelayMultiplier2);

        addSlider("Boat/Rod EXP Split", "The percentage of EXP from a fish granted to the boat, the rest goes to the fishing rod.", expPercentageSlider);
        addSlider("Hook Speed", "The factor to multiply the speed of the fishing hook by.", hookSpeedMultiplierSlider);
        addSlider("Fishing Rod Delay", "The factor to multiply the delay before the fishers cast their hooks again by.", rodDelayMultiplierSlider);
        setSliderTop(56);
    }

    @Override
    public void goToNextWorld() {
        Greenfoot.setWorld(new SimulationWorld(getUserSettings()));
    }
}
