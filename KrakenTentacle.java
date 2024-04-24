import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A tentacle of the fearsome Kraken
 *
 * @author Stanley Wang
 * @version April 2024
 */
public class KrakenTentacle extends PixelActor
{
    private int frames;
    private int yV = 7;
    public KrakenTentacle(Layer l) {
        super("endFish/Kraken/tentacle_0.png", l);
        frames = 0;
    }
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
