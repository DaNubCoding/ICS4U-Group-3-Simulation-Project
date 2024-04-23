import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The beginning cutscenes.
 *
 * @author Brandon Law
 * @version April 2024
 */
public class CutscenesWorld extends PixelWorld
{
    private GifPixelActor f1 = new GifPixelActor(new GifImage("fishercutscene1.gif"), Layer.BACKGROUND);
    private GifPixelActor f2 = new GifPixelActor(new GifImage("fishercutscene2.gif"), Layer.BACKGROUND);
    private GifPixelActor god;
    private GifImage godMod;
    private AnimatedText t1;
    private Timer endTime;
    private int cutsceneNum = 1;
    private int savedNum = 1;
    private boolean keyPressed = true;

    /**
     * Constructor for objects of class CutscenesWorld.
     *
     */
    public CutscenesWorld()
    {
        super(250, 160);
        addObject(f1, 125, 80);
        t1 = new AnimatedText("Have you heard? There's new fish in the reef!", Text.AnchorX.LEFT, Text.AnchorY.TOP, 75);
        addObject(t1, 130, 70);

        triggerFadeIn(0.02);
        render();
    }

    public void act() {
        render();
        Timer.incrementAct();

        if (Greenfoot.isKeyDown("Enter") && !keyPressed){
            if (cutsceneNum < 5) {
                triggerFadeOut(0.04);
            }
        }
        keyPressed = Greenfoot.isKeyDown("Enter");

        if (isFadeOutComplete()){
            cutsceneNum++;
        }

        if (cutsceneNum != savedNum) {
            //System.out.println("wal");
            triggerFadeIn(0.04);

            if (cutsceneNum == 2) {
                removeObject(t1);
                addObject(f2, 125, 80);
                t1 = new AnimatedText("New Fish? Can't wait to go fishing!", Text.AnchorX.LEFT, Text.AnchorY.TOP, 90);
                addObject(t1, 30, 75);
            }

            if (cutsceneNum == 3) {
                removeObject(t1);
                addObject(f1, 125, 80);
                t1 = new AnimatedText("As a sushi chef, I can't wait to eat them!", Text.AnchorX.LEFT, Text.AnchorY.TOP, 75);
                addObject(t1, 130, 70);
            }

            if (cutsceneNum == 4) {
                removeObject(t1);
                addObject(f2, 125, 80);
                t1 = new AnimatedText("As a marine biologist, I can write a paper on this!", Text.AnchorX.LEFT, Text.AnchorY.TOP, 100);
                addObject(t1, 30, 70);
            }

            if (cutsceneNum == 5) {
                removeObject(t1);
                godMod = new GifImage("fishgodcutscene.gif");
                god = new GifPixelActor(godMod, Layer.BACKGROUND);
                addObject(god, 125, 80);
                endTime = new Timer(850);
            }

            savedNum++;
        }

        if (endTime != null && endTime.ended()){
            Greenfoot.setWorld(new GeneralSettingsWorld());
        }
    }

    private void render() {
        if (cutsceneNum == 1 || cutsceneNum == 3) {
            f1.updateImage();
            f1.render(getCanvas());
        }

        if (cutsceneNum == 2 || cutsceneNum == 4) {
            f2.updateImage();
            f2.render(getCanvas());
        }

        if (cutsceneNum == 5) {
            god.updateImage();
            god.render(getCanvas());
        }

        renderPixelActors();
        updateImage();
    }
}