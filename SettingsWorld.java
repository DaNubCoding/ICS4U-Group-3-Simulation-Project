import greenfoot.*;

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

    // number of eggs spawned
    private Slider eggSpawnAmountSlider = new Slider<Integer>(1, 5, 3, 25, new Color(229, 115, 115));
    // number of how much exp for evolution
    private Slider expThresholdSlider = new Slider<Integer>(1, 5, 1, 25, new Color(54, 119, 122));
    // number of how many beginning fish
    private Slider numOfStartFishSlider = new Slider<Integer>(1, 10, 1, 50, new Color(76, 45, 23));
    // The one at the top. evo point multipler
    private Slider evoPointMultiplierSlider = new Slider<Double>(0.0, 2.0, 1.0, 50, new Color(255, 0, 0));

    private boolean keyPressed = true;

    public SettingsWorld() {
        super(250, 160);

        addObject(evoPointMultiplierSlider, 100, 30);
        addObject(expThresholdSlider, 100, 70);
        addObject(numOfStartFishSlider, 100, 90);
        addObject(eggSpawnAmountSlider, 100, 50);
        addObject(new Button("Start!", () -> triggerFadeOut(0.02)), 120, 130);

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
