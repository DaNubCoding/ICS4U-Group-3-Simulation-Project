import greenfoot.*;

/**
 * The beginning cutscenes.
 *
 * @author Brandon Law
 * @author Sandra Huang
 * @version April 2024
 */
public class CutscenesWorld extends PixelWorld
{
    private GifPixelActor f1 = new GifPixelActor(new GifImage("cutscenes/fisher1.gif"), Layer.BACKGROUND);
    private GifPixelActor f2 = new GifPixelActor(new GifImage("cutscenes/fisher2.gif"), Layer.BACKGROUND);
    private GifPixelActor god;
    private GifImage godMod;
    private AnimatedText t1;
    private Text nameLabel1 = new Text("Su C. Sheph", Text.AnchorX.CENTER, Text.AnchorY.BOTTOM, new Color(224, 196, 196, 200));
    private Text nameLabel2 = new Text("Marie B. O'Logist", Text.AnchorX.CENTER, Text.AnchorY.BOTTOM, new Color(196, 224, 224, 200));
    private Button continueButton;
    private Timer endTime;
    private int cutsceneNum = 1;
    private int savedNum = 1;
    private boolean keyPressed = true;

    /**
     * Constructor for objects of class CutscenesWorld.
     */
    public CutscenesWorld()
    {
        super(250, 160);
        addObject(f1, 125, 80);
        t1 = new AnimatedText("Have you heard? There's new fish in the reef!", Text.AnchorX.LEFT, Text.AnchorY.TOP, 75, AnimatedText.Voice.LOW);
        addObject(t1, 135, 67);
        addObject(nameLabel1, getWidth() / 2, getHeight() - 8);

        continueButton = new Button("Continue", () -> triggerFadeOut(0.04));
        addObject(continueButton, getWidth() - 38, getHeight() - 16);

        triggerFadeIn(0.02);
        render();

        Music.set("cutscene_music.wav");
    }

    @Override
    public void act() {
        render();
        Timer.incrementAct();

        if (Greenfoot.isKeyDown("Enter") && !keyPressed){
            if (cutsceneNum <= 5) {
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
                removeObject(nameLabel1);
                addObject(f2, 125, 80);
                t1 = new AnimatedText("New Fish? Can't wait to go fishing!", Text.AnchorX.LEFT, Text.AnchorY.TOP, 90, AnimatedText.Voice.HIGH);
                addObject(t1, 35, 72);
                addObject(nameLabel2, getWidth() / 2, getHeight() - 8);
            }

            if (cutsceneNum == 3) {
                removeObject(t1);
                removeObject(nameLabel2);
                addObject(f1, 125, 80);
                t1 = new AnimatedText("As a sushi chef, I can't wait to eat them!", Text.AnchorX.LEFT, Text.AnchorY.TOP, 75, AnimatedText.Voice.LOW);
                addObject(t1, 135, 67);
                addObject(nameLabel1, getWidth() / 2, getHeight() - 8);
            }

            if (cutsceneNum == 4) {
                removeObject(t1);
                removeObject(nameLabel1);
                addObject(f2, 125, 80);
                t1 = new AnimatedText("As a marine biologist, I can write a paper on this!", Text.AnchorX.LEFT, Text.AnchorY.TOP, 100, AnimatedText.Voice.HIGH);
                addObject(t1, 29, 67);
                addObject(nameLabel2, getWidth() / 2, getHeight() - 8);
            }

            if (cutsceneNum == 5) {
                removeObject(t1);
                removeObject(nameLabel2);
                removeObject(continueButton);
                addObject(new Button("Skip Cutscene", () -> triggerFadeOut(0.04)), getWidth() - 57, getHeight() - 16);
                godMod = new GifImage("cutscenes/fish_god.gif");
                god = new GifPixelActor(godMod, Layer.BACKGROUND);
                addObject(god, 125, 80);
                endTime = new Timer(850);
                Music.set("fish_god_music.wav");
            }

            savedNum++;
        }

        if (cutsceneNum > 5 || (endTime != null && endTime.ended())) {
            Greenfoot.setWorld(new GeneralSettingsWorld());
        }
    }

    private void render() {
        if (cutsceneNum == 1 || cutsceneNum == 3) {
            f1.updateImage();
        }

        if (cutsceneNum == 2 || cutsceneNum == 4) {
            f2.updateImage();
        }

        if (cutsceneNum == 5) {
            god.updateImage();
        }

        renderPixelActors();
        updateImage();
    }
}
