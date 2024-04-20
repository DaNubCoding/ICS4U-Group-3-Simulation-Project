import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * Title World subclass
 * Uses an animated GIF world
 *
 * @author Brandon Law
 * @version April 2024
 */
public class TitleWorld extends PixelWorld
{
    private GifPixelActor gif = new GifPixelActor(new GifImage("TitleScreenRedux.gif"), Layer.BACKGROUND);
    private boolean keyPressed = true;

    /**
     * Constructor for objects of class TitleWorld.
     *
     */
    public TitleWorld()
    {
        super(250, 160);
        addObject(gif, 125, 80);
        render();
    }

    public void act() {
        render();
        Timer.incrementAct();
    }

    private void render() {
        gif.updateImage();
        gif.render(getCanvas());
        updateImage();
        if (Greenfoot.isKeyDown("enter") && !keyPressed){
            Greenfoot.setWorld(new SettingsWorld());
        }
        keyPressed = Greenfoot.isKeyDown("Enter");
    }
}
