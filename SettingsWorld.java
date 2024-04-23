import greenfoot.*;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * The superclass to all worlds that lets the user initialize settings of the simulation.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public abstract class SettingsWorld extends PixelWorld {
    private static final GreenfootImage background = new GreenfootImage("settings_world_background.png");

    private boolean keyPressed = true;

    private UserSettings userSettings;
    private LinkedHashMap<String, Slider<?>> sliders;

    /**
     * Create a SettingsWorld and supply a partially initialized UserSettings
     * from the previous SettingsWorld.
     *
     * @param userSettings The UserSettings object from the previous SettingsWorld
     */
    public SettingsWorld(UserSettings userSettings) {
        super(250, 160);
        this.userSettings = userSettings;
        this.sliders = new LinkedHashMap<String, Slider<?>>();
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
     * Create a SettingsWorld without supplying a partially initialized
     * UserSettings from the previous SettingsWorld.
     */
    public SettingsWorld() {
        this(new UserSettings());
    }

    /**
     * Initialize the sliders.
     */
    public abstract void constructSliders();

    @Override
    public void act() {
        render();
        Timer.incrementAct();

        if (Greenfoot.isKeyDown("Enter") && !keyPressed) {
            triggerFadeOut(0.02);
        }
        keyPressed = Greenfoot.isKeyDown("Enter");

        if (isFadeOutComplete()) {
            goToNextWorld();
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

    /**
     * Go to the world after this world.
     */
    public abstract void goToNextWorld();

    /**
     * Get the UserSettings object of this SettingsWorld, containing all settings
     * set in this world and the ones before it.
     *
     * @return The UserSettings object of this SettingsWorld
     */
    public UserSettings getUserSettings() {
        return userSettings;
    }

    /**
     * Add a new Slider to the list of Sliders that will be shown in this
     * SettingsWorld.
     *
     * @param label The label displayed next to the Slider
     * @param slider The Slider object
     */
    public void addSlider(String label, Slider<?> slider) {
        sliders.put(label, slider);
    }
}
