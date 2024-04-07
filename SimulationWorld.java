import greenfoot.*;

/**
 *
 *
 * @author Martin Baldwin
 * @version April 2024
 */
public class SimulationWorld extends PixelWorld {
    private static final GreenfootImage background = new GreenfootImage("background.png");
    private static final GreenfootImage foreground = new GreenfootImage("foreground.png");

    static {
        foreground.setTransparency(100);
    }

    public SimulationWorld() {
        super(250, 160);

        addObject(new Fisher(1), 50, 36);
        addObject(new Fisher(2), 200, 36);

        render();
    }

    public void act() {
        render();
    }

    /**
     * Updates the display image of this world.
     */
    private void render() {
        GreenfootImage canvas = getCanvas();
        // Draw the background
        canvas.setColor(Color.WHITE);
        canvas.fill();
        canvas.drawImage(background, 0, 0);
        // Draw actors
        for (PixelActor actor : getObjects(PixelActor.class)) {
            actor.render(canvas);
        }
        canvas.drawImage(foreground, 0, 0);
        // Display new canvas image
        updateImage();
    }
}
