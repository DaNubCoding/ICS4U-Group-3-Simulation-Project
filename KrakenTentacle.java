import greenfoot.*;

/**
 * A tentacle of the fearsome Kraken.
 *
 * @author Stanley Wang
 * @version April 2024
 */
public class KrakenTentacle extends PixelActor
{
    private int frames;
    private int yV = 7;

    /**
     * Create a new KrakenTentacle.
     *
     * @param layer The layer this actor belongs to
     */
    public KrakenTentacle(Layer layer) {
        super("endFish/Kraken/tentacle_0.png", layer);
        frames = 0;
    }

    @Override
    public void act()
    {
        setLocation(getX(), getY() - yV);
        if (yV > 0) { yV -= 1; }
        frames += 1;
        if (frames % 50 == 0) {
            setImage("endFish/Kraken/tentacle_1.png");
        }
        if (frames % 50 == 25) {
            setImage("endFish/Kraken/tentacle_0.png");
        }
    }
}
