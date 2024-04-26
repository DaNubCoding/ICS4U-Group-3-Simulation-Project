import greenfoot.*;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;

/**
 * The superclass to all worlds that lets the user initialize settings of the simulation.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public abstract class SettingsWorld extends PixelWorld {
    private GreenfootImage background;

    private boolean keyPressed = true;
    private boolean next;
    private SettingsWorld previousWorld;
    private SettingsWorld nextWorld;

    private UserSettings userSettings;
    private LinkedHashMap<String, Slider<?>> sliders;
    private ArrayList<String> tooltips;
    private int sliderTop;

    /**
     * Create a SettingsWorld and supply a partially initialized UserSettings
     * from the previous SettingsWorld.
     *
     * @param previousWorld The SettingsWorld that came before this one
     * @param userSettings The UserSettings object from the previous SettingsWorld
     */
    public SettingsWorld(SettingsWorld previousWorld, UserSettings userSettings, String backgroundPath) {
        super(250, 160);
        this.userSettings = userSettings;
        this.previousWorld = previousWorld;
        this.background = new GreenfootImage(backgroundPath);

        this.sliders = new LinkedHashMap<String, Slider<?>>();
        this.tooltips = new ArrayList<String>();
        constructSliders();

        int align = getWidth() / 2;
        int i = 0;
        for (Map.Entry<String, Slider<?>> slider : sliders.entrySet()) {
            TextWithTooltip text = new TextWithTooltip(slider.getKey(), Text.AnchorX.RIGHT, Text.AnchorY.CENTER);
            text.setTooltipString(tooltips.get(i));
            addObject(text, align - 6, sliderTop + i * 18);
            addObject(slider.getValue(), align + 6, sliderTop + i++ * 18);
        }

        if (previousWorld != null) {
            addObject(new Button("Next", () -> {triggerFadeOut(0.05); next = true;}), getWidth() / 2 + 20, 140);
            addObject(new Button("Back", () -> {triggerFadeOut(0.05); next = false;}), getWidth() / 2 - 20, 140);
        } else {
            addObject(new Button("Next", () -> {triggerFadeOut(0.05); next = true;}), getWidth() / 2, 140);
        }

        triggerFadeIn(0.05);
        render();
    }

    /**
     * Set the next world to be a pre-existing world.
     *
     * @param nextWorld The next SettingsWorld
     */
    public void setNextWorld(SettingsWorld nextWorld) {
        this.nextWorld = nextWorld;
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
            next = true;
            triggerFadeOut(0.05);
        }
        keyPressed = Greenfoot.isKeyDown("Enter");

        if (isFadeOutComplete()) {
            if (next) { // Next is clicked
                // In case the next world needs to come back to this world
                // prepare it to fade in
                triggerFadeIn(0.05);
                if (nextWorld == null) {
                    goToNextWorld();
                } else {
                    Greenfoot.setWorld(nextWorld);
                }
            } else { // Back is clicked
                // Prepare it to fade in
                triggerFadeIn(0.05);
                previousWorld.setNextWorld(this);
                Greenfoot.setWorld(previousWorld);
            }
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
     * Set the actor y-coordinate to begin from when adding slider actors to
     * this world.
     *
     * @param top The first y-coordinate to add sliders from
     */
    public void setSliderTop(int top) {
        sliderTop = top;
    }

    /**
     * Add a new Slider to the list of Sliders that will be shown in this
     * SettingsWorld.
     *
     * @param label The label displayed next to the Slider
     * @param description The description that will appear on the tooltip
     * @param slider The Slider object
     */
    public void addSlider(String label, String tooltipString, Slider<?> slider) {
        sliders.put(label, slider);
        tooltips.add(tooltipString);
    }
}
