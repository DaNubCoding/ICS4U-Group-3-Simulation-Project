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
public class SettingsWorld extends PixelWorld {
    private static final GreenfootImage background = new GreenfootImage("settings_world_background.png");

    private boolean keyPressed = true;

    // The factor to multiply evolutionary point of fish by
    private static Slider evoPointMultiplierSlider = new Slider<Double>(0.0, 2.0, 1.0, 100, new Color(76, 45, 23));
    // How much EXP a fish needs to gain before having a chance to evolve
    private static Slider expThresholdSlider = new Slider<Integer>(1, 5, 1, 100, new Color(76, 45, 23));
    // Maximum number of eggs a fish can spawn
    private static Slider eggSpawnAmountSlider = new Slider<Integer>(1, 5, 3, 100, new Color(76, 45, 23));
    // Number of beginning fish
    private static Slider numOfStartFishSlider = new Slider<Integer>(1, 10, 1, 100, new Color(76, 45, 23));

    private static LinkedHashMap<String, Slider> sliders = new LinkedHashMap<String, Slider>();

    static {
        sliders.put("Evolution Point Multiplier", evoPointMultiplierSlider);
        sliders.put("EXP Threshold", expThresholdSlider);
        sliders.put("Max # of Eggs", eggSpawnAmountSlider);
        sliders.put("# of Starting Fish", numOfStartFishSlider);
    }

    public SettingsWorld() {
        super(250, 160);

        int align = getWidth() / 2;
        int i = 0;
        for (Map.Entry<String, Slider> slider : sliders.entrySet()) {
            Text text = new Text(slider.getKey(), Text.AnchorX.RIGHT, Text.AnchorY.CENTER);
            addObject(text, align - 4, 10 + i * 18);
            addObject(slider.getValue(), align + 4, 10 + i++ * 18);
        }

        addObject(new Button("Start!", () -> triggerFadeOut(0.02)), getWidth() / 2, 140);

        triggerFadeIn(0.02);
        render();
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
            Greenfoot.setWorld(new SimulationWorld(this));
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

    public int getEggSpawnAmount() {
        return (int) eggSpawnAmountSlider.getIntValue();
    }

    public int getExpThreshold() {
        return (int) expThresholdSlider.getIntValue();
    }

    public int getNumOfStartFish() {
        return (int) numOfStartFishSlider.getIntValue();
    }

    public double getEvoPointMultiplier() {
        return (double) evoPointMultiplierSlider.getIntValue();
    }
}
