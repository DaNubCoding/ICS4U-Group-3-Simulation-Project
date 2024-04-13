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
    public static final int SEA_FLOOR_Y = 140;
    public static final int SEA_SURFACE_Y = 37;

    private Fisher fisher1;
    private Fisher fisher2;

    // Test text object: draw the current act count in the top right corner of the world
    private Text actText;

    public SimulationWorld() {
        super(250, 160);

        setRenderOrder(Egg.class, Bubble.class, FishingRod.class, Fisher.class, Fish.class, FishingLine.class, Hook.class, Text.class);

        fisher1 = new Fisher(1);
        fisher2 = new Fisher(2);
        addObject(fisher1, 50, 36);
        addObject(fisher2, 200, 36);

        addObject(new Anglerfish(0), 16, 80);
        addObject(new Anglerfish(0, FishFeature.BIG_EYE), 72, 100);
        addObject(new Tuna(0), Util.randInt(0, getWidth()), Util.randInt(SEA_SURFACE_Y, SEA_FLOOR_Y));

        // TODO: remove this
        actText = new Text(Timer.getCurrentAct(), Text.AnchorX.RIGHT, Text.AnchorY.TOP) {
            @Override
            public void act() {
                setContent(Timer.getCurrentAct());
            }
        };
        addObject(actText, getWidth() - 4, 4);

        render();
    }

    public void act() {
        render();
        Timer.incrementAct();

        // Temporary test
        if (Greenfoot.isKeyDown("e")) {
            Greenfoot.setWorld(new SummaryWorld(this));
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
        // Draw water gradient on top of underwater actors
        canvas.drawImage(foreground, 0, 0);

        // Display new canvas image
        updateImage();
    }

    /**
     * Gets the fisher actor on the given side.
     *
     * @param side either 1 or 2, corresponding to the left and right fishers, respectively
     * @return the fisher actor in this world with the matching side value
     */
    public Fisher getFisher(int side) {
        switch (side) {
        case 1:
            return fisher1;
        case 2:
            return fisher2;
        default:
            throw new IllegalArgumentException("Player side value must be 1 or 2");
        }
    }
}
