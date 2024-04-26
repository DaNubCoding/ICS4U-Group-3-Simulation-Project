import greenfoot.*;

/**
 * Title World subclass.
 * Uses an animated GIF.
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

        Greenfoot.setSpeed(50);
        Music.set("title_music.wav");
    }

    /**
     * Used by Greenfoot to create the initial TitleWorld, doesn't fade.
     */
    public TitleWorld() {
        this(false);
    }

    @Override
    public void act() {
        render();
        Timer.incrementAct();

        if (Greenfoot.isKeyDown("enter") && !keyPressed){
            triggerFadeOut(0.04);
        }
        keyPressed = Greenfoot.isKeyDown("Enter");

        if (isFadeOutComplete()) {
            Greenfoot.setWorld(new CutscenesWorld());
        }
    }

    private void render() {
        gif.updateImage();
        gif.render(getCanvas());
        updateImage();
    }
}
