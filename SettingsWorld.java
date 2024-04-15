import greenfoot.*;

public class SettingsWorld extends PixelWorld {
    private static final GreenfootImage background = new GreenfootImage("settings_world_background.png");

    private Slider slider1 = new Slider<Integer>(1, 5, 25, new Color(229, 115, 115));
    private Slider slider2 = new Slider<Double>(1.0, 5.0, 25, new Color(54, 119, 122));
    private Slider slider3 = new Slider<Integer>(0, 60, 60, new Color(76, 45, 23));

    public SettingsWorld() {
        super(250, 160);

        setRenderOrder(null);

        addObject(slider1, 100, 50);
        addObject(slider2, 100, 70);
        addObject(slider3, 100, 90);

        render();
    }

    @Override
    public void act() {
        render();
        Timer.incrementAct();
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
