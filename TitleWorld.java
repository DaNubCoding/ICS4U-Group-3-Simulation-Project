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
     * @param fade Whether to start the world fading in
     */
    public TitleWorld(boolean fade)
    {
        super(250, 160);
        addObject(gif, 125, 80);

        if (fade) triggerFadeIn(0.02);
        render();
        prepare();
    }

    /**
     * Used by Greenfoot to create the initial TitleWorld, doesn't fade.
     */
    public TitleWorld() {
        this(false);
    }

    public void act() {
        render();
        Timer.incrementAct();

        if (Greenfoot.isKeyDown("enter") && !keyPressed){
            triggerFadeOut(0.02);
        }
        keyPressed = Greenfoot.isKeyDown("Enter");

        if (isFadeOutComplete()) {
            Greenfoot.setWorld(new SettingsWorld());
        }
    }

    private void render() {
        gif.updateImage();
        gif.render(getCanvas());
        updateImage();
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
    }
}
