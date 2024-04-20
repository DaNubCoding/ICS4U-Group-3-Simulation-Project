import greenfoot.*;

/**
 * Settings World subclass
 *
 * @author Brandon Law
 * @author Andrew Wang
 * @version April 2024
 */

public class SettingsWorld extends PixelWorld {
    private static final GreenfootImage background = new GreenfootImage("settings_world_background.png");

    private Slider slider1 = new Slider<Integer>(1, 5, 3, 25, new Color(229, 115, 115));
    private Slider slider2 = new Slider<Double>(1.0, 5.0, 2.5, 25, new Color(54, 119, 122));
    private Slider slider3 = new Slider<Integer>(0, 60, 0, 60, new Color(76, 45, 23));

    private boolean keyPressed = true;

    public SettingsWorld() {
        super(250, 160);

        addObject(slider1, 100, 50);
        addObject(slider2, 100, 70);
        addObject(slider3, 100, 90);
        addObject(new Button("Start!", () -> Greenfoot.setWorld(new SimulationWorld())), 120, 130);

        render();
    }

    @Override
    public void act() {
        render();
        Timer.incrementAct();

        if (Greenfoot.isKeyDown("Enter") && !keyPressed) {
            Greenfoot.setWorld(new SimulationWorld());
        }
        keyPressed = Greenfoot.isKeyDown("Enter");
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
