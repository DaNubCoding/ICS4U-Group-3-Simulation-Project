import greenfoot.*;

/**
 * Explosion subclass.
 * Creates an animated GIF image for bomb fish.
 *
 * @author Brandon Law
 * @version April 2024
 */
public class Explosion extends GifPixelActor
{
    private static final SoundEffect explosionSound = new SoundEffect("explosion.wav");

    private int timer = 48;

    /**
     * Create an explosion.
     */
    public Explosion() {
        super(new GifImage("explode.gif"), Layer.EFFECT);
        explosionSound.play();
    }

    @Override
    public void act()
    {
        this.updateImage();
        timer--;
        if (timer <= 0){
            getWorld().removeObject(this);
        }
    }
}
