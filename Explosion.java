import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Explosion subclass
 * Creates an animated GIF image for bomb fish
 *
 * @author Brandon
 * @version April 2024
 */
public class Explosion extends GifPixelActor
{
    private int timer = 48;

    public Explosion() {
        super(new GifImage("explode.gif"));
    }

    public void act()
    {
        this.updateImage();
        timer--;
        if (timer <= 0){
            getWorld().removeObject(this);
        }
    }
}
