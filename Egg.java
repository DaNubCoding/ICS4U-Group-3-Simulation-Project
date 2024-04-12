import greenfoot.*;

/**
 * The eggs spawns by fish, can be a variety of colors and sizes depending on
 * the fish that spawns it.
 * 
 * @author Andrew Wang
 * @version April 2024
 */
public class Egg extends PixelActor {
    /**
     * The possible sizes of an egg.
     * 
     * @author Andrew Wang
     * @version April 2024
     */
    public static enum EggSize {
        SMALL, MEDIUM, LARGE, GIGANTIC
    }

    /**
     * The possible colors of an egg.
     * 
     * @author Andrew Wang
     * @version April 2024
     */
    public static enum EggColor {
        PINK, GREEN, BLUE, BLACK
    }

    private EggSize size;
    private EggColor color;
    private double speed;
    private Timer spawnTimer;

    /**
     * Initialize an egg with a size and a color from the enums.
     * 
     * @param size The size of the egg
     * @param color The color of the egg
     */
    public Egg(EggSize size, EggColor color) {
        super("egg_" + size.name().toLowerCase() + "_" + color.name().toLowerCase() + ".png");
        this.size = size;
        this.color = color;

        int width = getOriginalWidth();
        int height = getOriginalHeight();
        setCenterOfRotation(width / 2, height / 2);
        setHeading(Util.randInt(360));
        speed = Util.randDouble(0.4, 0.8);
        spawnTimer = new Timer(400 + Util.randInt(-50, 50));
    }

    public void act() {
        speed *= 0.98;
        move(speed);

        setLocation(getDoubleX(), getDoubleY() + 0.3);

        if (getDoubleY() > SimulationWorld.SEA_FLOOR_Y) {
            setLocation(getDoubleX(), SimulationWorld.SEA_FLOOR_Y);
        }

        if (spawnTimer.ended()) {
            getWorld().removeObject(this);
        }
    }
}
