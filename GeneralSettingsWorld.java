import greenfoot.*;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * SettingsWorld that allows the user to set the general settings of the simulation.
 *
 * @author Brandon Law
 * @author Andrew Wang
 * @author Matthew Li
 * @author Martin Baldwin
 * @version April 2024
 */
public class GeneralSettingsWorld extends SettingsWorld {
    /** @see UserSettings */
    private UserSettingSlider<Double> evoThresholdSlider;
    private UserSettingSlider<Integer> eggSpawnAmountSlider;

    // Buttons for toggling fish extinction protection
    private Button salmonButton;
    private Button bassButton;
    private Button tunaButton;

    public GeneralSettingsWorld() {
        super(null, new UserSettings(), "settings/general_background.png");

        // Add starting fish labels, buttons, and sliders
        UserSettings userSettings = getUserSettings();
        int midx = getWidth() / 2;
        int top = 15;
        addObject(new Text("Starting Fish", Text.AnchorX.CENTER, Text.AnchorY.CENTER, new Color(247, 210, 128)), midx, top);
        addObject(new Text("click to toggle protection from extinction", Text.AnchorX.CENTER, Text.AnchorY.CENTER), midx, top + 15);
        addObject(new Text("Page 1 of 3", Text.AnchorX.RIGHT, Text.AnchorY.BOTTOM), getWidth() - 6, getHeight() - 4);
        salmonButton = new Button(createFishIcon("fishes/salmon.png", false), this::toggleSalmonProtect);
        addObject(salmonButton, midx - 50, top + 40);
        addObject(new UserSettingSlider<Integer>(0, 10, 1, 30, new Color(229, 115, 115), userSettings::setSalmonCount), midx - 65, top + 60);
        bassButton = new Button(createFishIcon("fishes/bass.png", false), this::toggleBassProtect);
        addObject(bassButton, midx, top + 40);
        addObject(new UserSettingSlider<Integer>(0, 10, 1, 30, new Color(105, 201, 118), userSettings::setBassCount), midx - 15, top + 60);
        tunaButton = new Button(createFishIcon("fishes/tuna.png", false), this::toggleTunaProtect);
        addObject(tunaButton, midx + 50, top + 40);
        addObject(new UserSettingSlider<Integer>(0, 10, 1, 30, new Color(46, 92, 107), userSettings::setTunaCount), midx + 35, top + 60);

        Music.set("settings_music.wav");
    }

    @Override
    public void constructSliders() {
        UserSettings userSettings = getUserSettings();

        evoThresholdSlider = new UserSettingSlider<Double>(0.0, 5.0, 1.0, 100, new Color(76, 45, 23), userSettings::setEvoPointThreshold);
        eggSpawnAmountSlider = new UserSettingSlider<Integer>(1, 5, 3, 100, new Color(76, 45, 23), userSettings::setEggSpawnAmount);

        setSliderTop(97);
        addSlider("Evolution Threshold", "The number of evolutionary points needed before a fish has a chance of evolving.", evoThresholdSlider);
        addSlider("Max Egg Spawn Count", "The maximum number of eggs a fish can lay at once.", eggSpawnAmountSlider);
    }

    @Override
    public void goToNextWorld() {
        Greenfoot.setWorld(new FisherOneSettingsWorld(this, getUserSettings()));
    }

    /**
     * Toggles whether Salmons are protected from extinction.
     */
    private void toggleSalmonProtect() {
        UserSettings userSettings = getUserSettings();
        if (userSettings.isFishTypeProtected(Salmon.class)) {
            userSettings.removeFishTypeProtection(Salmon.class);
        } else {
            userSettings.addFishTypeProtection(Salmon.class);
        }
        salmonButton.setIcon(createFishIcon("fishes/salmon.png", userSettings.isFishTypeProtected(Salmon.class)));
    }

    /**
     * Toggles whether Basses are protected from extinction.
     */
    private void toggleBassProtect() {
        UserSettings userSettings = getUserSettings();
        if (userSettings.isFishTypeProtected(Bass.class)) {
            userSettings.removeFishTypeProtection(Bass.class);
        } else {
            userSettings.addFishTypeProtection(Bass.class);
        }
        bassButton.setIcon(createFishIcon("fishes/bass.png", userSettings.isFishTypeProtected(Bass.class)));
    }

    /**
     * Toggles whether Tunas are protected from extinction.
     */
    private void toggleTunaProtect() {
        UserSettings userSettings = getUserSettings();
        if (userSettings.isFishTypeProtected(Tuna.class)) {
            userSettings.removeFishTypeProtection(Tuna.class);
        } else {
            userSettings.addFishTypeProtection(Tuna.class);
        }
        tunaButton.setIcon(createFishIcon("fishes/tuna.png", userSettings.isFishTypeProtected(Tuna.class)));
    }

    /**
     * Creates a fish icon with an optional shield if protected from extinction.
     *
     * @param originalPath The path to the original fish image
     * @param protect Whether the fish is protected from extinction
     * @return The fish icon
     */
    private GreenfootImage createFishIcon(String originalPath, boolean protect) {
        // All starting fish are 14 x 10, shield size 18
        GreenfootImage icon = new GreenfootImage(18, 18);
        icon.drawImage(new GreenfootImage(originalPath), 2, 4);
        if (protect) {
            icon.setColor(Fish.SHIELD_COLOR);
            icon.fillOval(0, 0, 18, 18);
        }
        return icon;
    }
}
