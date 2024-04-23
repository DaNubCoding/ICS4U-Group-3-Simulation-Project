import greenfoot.*;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Settings World subclass
 *
 * @author Brandon Law
 * @author Andrew Wang
 * @author Matthew Li
 * @version April 2024
 */
public class GeneralSettingsWorld extends PixelWorld {
    private static final GreenfootImage background = new GreenfootImage("settings_world_background.png");

    private boolean keyPressed = true;

    private static UserSettings userSettings = new UserSettings();

    /** @see UserSettings */
    private UserSettingSlider<Integer> expThresholdSlider;
    private UserSettingSlider<Integer> eggSpawnAmountSlider;
    private UserSettingSlider<Integer> numOfStartFishSlider;

    private LinkedHashMap<String, Slider<?>> sliders;

    public GeneralSettingsWorld() {
        super(250, 160);

        constructSliders();

        int align = getWidth() / 2;
        int i = 0;
        for (Map.Entry<String, Slider<?>> slider : sliders.entrySet()) {
            Text text = new Text(slider.getKey(), Text.AnchorX.RIGHT, Text.AnchorY.CENTER);
            addObject(text, align - 4, 10 + i * 18);
            addObject(slider.getValue(), align + 4, 10 + i++ * 18);
        }

        addObject(new Button("Start!", () -> triggerFadeOut(0.02)), getWidth() / 2, 140);

        triggerFadeIn(0.02);
        render();
    }

    /**
     * Initialize the sliders.
     */
    private void constructSliders() {
        /** @see UserSettings */
        expThresholdSlider = new UserSettingSlider<Integer>(1, 5, 1, new Color(76, 45, 23), userSettings::setEvoPointThreshold);
        eggSpawnAmountSlider = new UserSettingSlider<Integer>(1, 5, 3, new Color(76, 45, 23), userSettings::setEggSpawnAmount);
        numOfStartFishSlider = new UserSettingSlider<Integer>(1, 10, 1, new Color(76, 45, 23), userSettings::setNumOfStartFish);

        sliders = new LinkedHashMap<String, Slider<?>>();

        sliders.put("EXP Threshold", expThresholdSlider);
        sliders.put("Max # of Eggs", eggSpawnAmountSlider);
        sliders.put("# of Starting Fish", numOfStartFishSlider);
    }

    @Override
    public void act() {
        render();
        Timer.incrementAct();

        if (Greenfoot.isKeyDown("Enter") && !keyPressed) {
            triggerFadeOut(0.02);
        }
        keyPressed = Greenfoot.isKeyDown("Enter");

        if (isFadeOutComplete()) {
            Greenfoot.setWorld(new SimulationWorld(userSettings));
        }
    }

    /**
     * Updates the display image of this world.
     */
    private void render() {
        GreenfootImage canvas = getCanvas();
        // Draw the background
        canvas.drawImage(background, 0, 0);
        // Draw actors
        renderPixelActors();

        // Display new canvas image
        updateImage();
    }
}
