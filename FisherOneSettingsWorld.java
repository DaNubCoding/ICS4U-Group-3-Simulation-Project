import greenfoot.*;

public class FisherOneSettingsWorld extends SettingsWorld {
    /** @see UserSettings */
    // Sliders here

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

        // Define sliders

        // Add sliders
    }

    @Override
    public void goToNextWorld() {
        Greenfoot.setWorld(new SimulationWorld(getUserSettings()));
    }
}
