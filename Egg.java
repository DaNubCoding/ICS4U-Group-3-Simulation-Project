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
     * The possible sizes of an egg, with certain data specific to each size.
     * 
     * @author Andrew Wang
     * @version April 2024
     */
    public static enum EggSize {
        SMALL(300),
        MEDIUM(600),
        LARGE(900),
        GIGANTIC(1200);

        public final int hatchTime;

        private EggSize(int hatchingTime) {
            this.hatchTime = hatchingTime;
        }

        /**
         * Get the egg size one level bigger than the current.
         * 
         * @return The egg size after this one
         */
        public EggSize nextSize() {
            try {
                return EggSize.values()[ordinal() + 1];
            } catch (IndexOutOfBoundsException err) {
                return this;
            }
        }
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

    private Fish parent;
    private EggSize size;
    private EggColor color;
    private double speed;
    private double sinkSpeed;
    private Timer spawnTimer;

    /**
     * Initialize an egg with a parent, a size and a color from the enums.
     * 
     * @param parent The parent Fish of the egg
     * @param size The size of the egg
     * @param color The color of the egg
     */
    public Egg(Fish parent, Egg.EggSize size, Egg.EggColor color) {
        super(constructImageString(size, color));
        this.parent = parent;
        this.size = size;
        this.color = color;

        int width = getOriginalWidth();
        int height = getOriginalHeight();
        setCenterOfRotation(width / 2, height / 2);
        setHeading(Util.randInt(360));
        speed = Util.randDouble(0.4, 0.8);
        sinkSpeed = Util.randDouble(0.2, 0.4);
        spawnTimer = new Timer((int) (size.hatchTime * Util.randDouble(0.8, 1.2)));
    }

    public void act() {
        speed *= 0.98;
        move(speed);

        setLocation(getDoubleX(), getDoubleY() + sinkSpeed);

        // Move it into the seafloor a bit
        int maxY = SimulationWorld.SEA_FLOOR_Y + getOriginalHeight() / 2;
        if (getDoubleY() > maxY) {
            setLocation(getDoubleX(), maxY);
        }
        if (getX() < 0) {
            setLocation(0, getDoubleY());
        } else if (getX() > getWorld().getWidth()) {
            setLocation(getWorld().getWidth(), getDoubleY());
        }

        if (spawnTimer.ended()) {
            hatch();
        }
    }
    /**
     * Generate the correct egg file name of a given size and color.
     * 
     * @param size The size of the egg image
     * @param color The color of the egg image
     * @return The file name
     */
    private static String constructImageString(Egg.EggSize size, Egg.EggColor color) {
        String sizeStr = size.name().toLowerCase();
        String colorStr = color.name().toLowerCase();
        return "egg_" + sizeStr + "_" + colorStr + ".png";
    }

    /**
     * Hatch the egg (spawns a new Fish).
     * <p>Calls parent.createOffspring().</p>
     */
    private void hatch() {
        PixelWorld world = getWorld();
        world.addObject(parent.createOffspring(), getX(), getY());
        world.removeObject(this);
    }
}
