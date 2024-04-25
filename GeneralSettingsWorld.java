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
    private static final GreenfootSound settingsMusic = new GreenfootSound("settings_music.wav");

    /** @see UserSettings */
    private UserSettingSlider<Double> evoThresholdSlider;
    private UserSettingSlider<Integer> eggSpawnAmountSlider;

    // Buttons for toggling fish extinction protection
    private Button salmonButton;
    private Button bassButton;
    private Button tunaButton;

    public GeneralSettingsWorld() {
        super(null, new UserSettings(), "general_settings_background.png");

        // Add starting fish labels, buttons, and sliders
        UserSettings userSettings = getUserSettings();
        int midx = getWidth() / 2;
        int top = 15;
        addObject(new Text("~ Starting Fish ~", Text.AnchorX.CENTER, Text.AnchorY.CENTER), midx, top);
        addObject(new Text("click to toggle protection from extinction", Text.AnchorX.CENTER, Text.AnchorY.CENTER), midx, top + 11);
        salmonButton = new Button(createFishIcon("fishes/salmon.png", false), this::toggleSalmonProtect);
        addObject(salmonButton, midx - 40, top + 35);
        addObject(new UserSettingSlider<Integer>(0, 10, 1, 30, new Color(229, 115, 115), userSettings::setSalmonCount), midx - 55, top + 55);
        bassButton = new Button(createFishIcon("fishes/bass.png", false), this::toggleBassProtect);
        addObject(bassButton, midx, top + 35);
        addObject(new UserSettingSlider<Integer>(0, 10, 1, 30, new Color(105, 201, 118), userSettings::setBassCount), midx - 15, top + 55);
        tunaButton = new Button(createFishIcon("fishes/tuna.png", false), this::toggleTunaProtect);
        addObject(tunaButton, midx + 40, top + 35);
        addObject(new UserSettingSlider<Integer>(0, 10, 1, 30, new Color(46, 92, 107), userSettings::setTunaCount), midx + 25, top + 55);

        Music.set(settingsMusic);
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

    private void toggleSalmonProtect() {
        UserSettings userSettings = getUserSettings();
        if (userSettings.isFishTypeProtected(Salmon.class)) {
            userSettings.removeFishTypeProtection(Salmon.class);
        } else {
            userSettings.addFishTypeProtection(Salmon.class);
        }
        salmonButton.setIcon(createFishIcon("fishes/salmon.png", userSettings.isFishTypeProtected(Salmon.class)));
    }

    private void toggleBassProtect() {
        UserSettings userSettings = getUserSettings();
        if (userSettings.isFishTypeProtected(Bass.class)) {
            userSettings.removeFishTypeProtection(Bass.class);
        } else {
            userSettings.addFishTypeProtection(Bass.class);
        }
        bassButton.setIcon(createFishIcon("fishes/bass.png", userSettings.isFishTypeProtected(Bass.class)));
    }

    private void toggleTunaProtect() {
        UserSettings userSettings = getUserSettings();
        if (userSettings.isFishTypeProtected(Tuna.class)) {
            userSettings.removeFishTypeProtection(Tuna.class);
        } else {
            userSettings.addFishTypeProtection(Tuna.class);
        }
        tunaButton.setIcon(createFishIcon("fishes/tuna.png", userSettings.isFishTypeProtected(Tuna.class)));
    }

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
