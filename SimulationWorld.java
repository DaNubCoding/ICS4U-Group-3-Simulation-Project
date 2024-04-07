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

    public SimulationWorld() {
        super(250, 160);

        Fisher fisher1 = new Fisher(1);
        Fisher fisher2 = new Fisher(2);
        addObject(fisher1, 50, 36);
        addObject(fisher2, 200, 36);
        addObject(new FishingLine(fisher1), 0, 0);
        addObject(new FishingLine(fisher2), 0, 0);

        addObject(new Anglerfish(), 32, 80);
        addObject(new Anglerfish(FishFeature.BIG_EYE), 72, 130);

        render();
    }

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
